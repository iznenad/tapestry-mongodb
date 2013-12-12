/*
 * $Id$
 */
package org.pac.bootstraplab.services.db.fetch;

public class DefaultFieldValueDecoder implements EntityFieldValueDecoder<Object> {

	@Override
	public Object decode(Class spec, Object value) {
		return value;
	}

}

