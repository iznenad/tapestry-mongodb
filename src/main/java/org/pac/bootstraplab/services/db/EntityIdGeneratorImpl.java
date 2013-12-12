package org.pac.bootstraplab.services.db;

import java.lang.reflect.Field;
import java.util.UUID;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.pac.bootstraplab.models.uid.AbstractUID;
import org.pac.bootstraplab.models.uid.UID;
import org.slf4j.Logger;

public class EntityIdGeneratorImpl implements EntityIdGenerator {

	@Inject
	private Logger logger;
	
	@Override
	public UID generateFor(final Object entity) {
		
		final IdSpec spec = entity.getClass().getAnnotation(IdSpec.class) ;
		
		if(spec == null) throw new IllegalStateException("No specification on how to generate the UID for : " + entity.getClass().getName());
		
		return new AbstractUID() {
			
			private String defaultIdentifier = UUID.randomUUID().toString().replace("-", ".");
			
			@Override
			public String getType() {
				return spec.type().equals("") ? entity.getClass().getSimpleName() : spec.type();
			}
			
			@Override
			public String getIdentifier() {
				
				if(spec.identifierFields().length == 0) return defaultIdentifier;
				
				StringBuilder identifierBuilder = new StringBuilder();
				
				for(String fieldName : spec.identifierFields() ){
					try {
						Field field = entity.getClass().getDeclaredField(fieldName);
						
						field.setAccessible(true);
						
						Object fieldValue = field.get(entity);
						
						identifierBuilder.append(String.valueOf(fieldValue) + ".");
					} catch (NoSuchFieldException |IllegalAccessException | SecurityException e) {
						e.printStackTrace();
					}
				}
				
				return identifierBuilder.substring(0, identifierBuilder.length() - 1);
			}
			
			@Override
			public String getFully() {
				return getType() + "-" + getIdentifier();
			}
		};
	}
	
	@Override
	public UID generateFrom(final String fullUid) {
		
		if (fullUid == null) {
			throw new IllegalArgumentException("UID string provided is null, please provide a valid UID string");
		}
		
		final String [] typeAndIdentifier = fullUid.split("-");
		
		if(typeAndIdentifier.length != 2) {
			logger.error("Invalid full UID string provided: {}", fullUid);
			throw new IllegalArgumentException("UID string: " + fullUid + " is invalid. Please provide a valid UID string");
		}
		
		return new AbstractUID() {
			
			@Override
			public String getType() {
				return typeAndIdentifier[0];
			}
			
			@Override
			public String getIdentifier() {
				return typeAndIdentifier[1];
			}
			
			@Override
			public String getFully() {
				// TODO Auto-generated method stub
				return fullUid;
			}
		};
	}

}

