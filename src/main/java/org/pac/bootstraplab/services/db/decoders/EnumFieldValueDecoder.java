/*
 * $Id$
 */
package org.pac.bootstraplab.services.db.decoders;

import org.pac.bootstraplab.services.db.fetch.EntityFieldValueDecoder;

public class EnumFieldValueDecoder implements EntityFieldValueDecoder<Enum>{

	@Override
	public Enum decode(Class spec, Object value) {
		
		try {
			 return Enum.valueOf(spec, (String) value);
		} catch (Exception e) {
			return null;
		}
		
	}

}

