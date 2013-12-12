/*
 * $Id$
 */
package org.pac.bootstraplab.services.db.decoders;

import java.net.URI;

import org.pac.bootstraplab.services.db.fetch.EntityFieldValueDecoder;

public class URIFieldValueDecoder implements EntityFieldValueDecoder<URI>{

	@Override
	public URI decode(Class spec, Object value) {
		
		if(!spec.equals(URI.class)) return null;
		if(!(value instanceof String)) return null;
		
		return URI.create((String)value);
	}

}

