/*
 * $Id$
 */
package org.pac.bootstraplab.services.db.fetch;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Collection;
import java.util.List;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.pac.bootstraplab.services.db.CollectionSpec;
import org.pac.bootstraplab.services.db.SubDocument;
import org.slf4j.Logger;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class EntityGeneratorImpl<T> implements EntityGenerator<T> {

	@Inject
	private Logger logger;

	@Inject
	private EntityFieldValueDecoder fieldValueDecoder;

	@Override
	public <T> T generate(Class<T> spec, DBObject dbObject) {

		if(dbObject == null) return null;
		
		Constructor constructor = getDefaultConstructor(spec);

		Object entity = invoke(constructor);

		for (Field field : spec.getDeclaredFields()) {

			if (!dbObject.containsField(field.getName())) {
				continue;
			}

			Object fieldValue = null;

			if (field.getAnnotation(SubDocument.class) != null) {
				fieldValue = generate(field.getType(), (BasicDBObject) dbObject.get(field.getName()));
			} 
			else if(Collection.class.isAssignableFrom(field.getType())) {
				
				Class targetClazz = field.getType();

				CollectionSpec collectionSpec = field.getAnnotation(CollectionSpec.class);
				
				if(collectionSpec != null)
					targetClazz = collectionSpec.type();
				
				Object o = dbObject.get(field.getName());
				
				List<Object> list = CollectionFactory.newList();
				
				if(o != null && BasicDBList.class.isAssignableFrom(o.getClass())) {
					BasicDBList values = (BasicDBList) o;
					
					for (Object object : values) {						
						list.add(fieldValueDecoder.decode(targetClazz, object));
					}
				}
				
				fieldValue = list;
			}	
			else {
				fieldValue = fieldValueDecoder.decode(field.getType(), dbObject.get(field.getName()));
			}
			
			setFieldValue(entity, field, fieldValue);
		}

		return (T) entity;
	}

	private void setFieldValue(Object entity, Field field, Object fieldValue) {

		if (!field.isAccessible())
			field.setAccessible(true);

		try {
			field.set(entity, fieldValue);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			logger.warn("Something went wrong while decoding field - fieldName: {}, entityClass{}.", new Object[] { field.getName(),
					entity.getClass().getName() });
		}

	}

	private Object invoke(Constructor defaultConstructor) {
		try {
			return defaultConstructor.newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}

		throw new IllegalStateException("An entity model must provide a visible default constructor.");
	}

	private Constructor getDefaultConstructor(Class spec) {
		
		try {
			return spec.getConstructor();
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		throw new IllegalStateException("An entity model must provide a visible default constructor.");
	}

}
