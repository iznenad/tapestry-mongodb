/*
 * $Id$
 */
package org.pac.bootstraplab.services.db.fetch;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.pac.bootstraplab.services.db.SubDocument;
import org.slf4j.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class EntityObjectGeneratorImpl<T> implements EntityObjectGenerator<T> {

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
			} else {
				fieldValue = fieldValueDecoder.decode(field.getType(), dbObject.get(field.getName()));
			}
			
			System.err.println(spec);
			
			System.err.println(fieldValue);
			

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
