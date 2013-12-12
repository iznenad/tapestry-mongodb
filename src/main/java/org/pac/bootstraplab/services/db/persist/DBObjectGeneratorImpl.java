package org.pac.bootstraplab.services.db.persist;

import java.lang.reflect.Field;

import org.apache.tapestry5.func.F;
import org.apache.tapestry5.func.Reducer;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.pac.bootstraplab.services.db.SubDocument;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class DBObjectGeneratorImpl implements DBObjectGenerator{

	@Inject
	private EntityFieldValueEncoder fieldValueExtractor;
	
	@Override
	public DBObject generateFrom(final Object entity) {
		
		if(entity == null) return null;
		
		Field[] fields = entity.getClass().getDeclaredFields();
		
		return F.flow(fields).reduce(new Reducer<BasicDBObject, Field>(){
			
			@Override
			public BasicDBObject reduce(BasicDBObject accumulator, Field element) {
				
				return accumulator.append(element.getName(), extractFieldValue(entity, element));
			}

		}, new BasicDBObject());
	}

	private Object extractFieldValue(Object entity, Field element) {
		try {
			
			if(!element.isAccessible()) element.setAccessible(true);
			
			if(element.getAnnotation(SubDocument.class) != null) 
				return generateFrom(element.get(entity));
			
			return fieldValueExtractor.encode(element.get(entity));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
