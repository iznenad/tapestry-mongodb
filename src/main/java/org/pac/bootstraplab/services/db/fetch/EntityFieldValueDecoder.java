/*
 * $Id$
 */
package org.pac.bootstraplab.services.db.fetch;


public interface EntityFieldValueDecoder<T> {
	<T> T decode(Class<T> spec, Object value);
}

