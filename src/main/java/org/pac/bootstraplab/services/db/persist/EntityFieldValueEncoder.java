package org.pac.bootstraplab.services.db.persist;

public interface EntityFieldValueEncoder<T> {
	Object encode(T fieldValue);
}
