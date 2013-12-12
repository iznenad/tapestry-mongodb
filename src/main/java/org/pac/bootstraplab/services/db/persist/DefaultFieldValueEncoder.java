package org.pac.bootstraplab.services.db.persist;


public class DefaultFieldValueEncoder implements EntityFieldValueEncoder<Object> {

	@Override
	public Object encode(Object fieldValue) {
		return fieldValue;
	}

}
