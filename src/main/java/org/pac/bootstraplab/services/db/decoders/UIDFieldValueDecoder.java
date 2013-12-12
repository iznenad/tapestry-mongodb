/*
 * $Id$
 */
package org.pac.bootstraplab.services.db.decoders;

import org.pac.bootstraplab.models.uid.AbstractUID;
import org.pac.bootstraplab.models.uid.UID;
import org.pac.bootstraplab.services.db.fetch.EntityFieldValueDecoder;

public class UIDFieldValueDecoder implements EntityFieldValueDecoder<UID> {
	
	@Override
	public UID decode(Class spec, Object value) {
		
		if(!spec.equals(UID.class)) return null;
		if(!(value instanceof String)) return null;
		
		final String fullValue = (String) value;
		
		final String [] typeAndIdentifier = fullValue.split("-");
		
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
				return fullValue;
			}
		};
	}

}

