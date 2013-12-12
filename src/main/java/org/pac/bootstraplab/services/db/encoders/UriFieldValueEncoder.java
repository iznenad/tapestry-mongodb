/*
 * $Id$
 */
package org.pac.bootstraplab.services.db.encoders;

import java.net.URI;

import org.pac.bootstraplab.services.db.persist.EntityFieldValueEncoder;

public class UriFieldValueEncoder implements EntityFieldValueEncoder<URI>{

	@Override
	public Object encode(URI fieldValue) {
		return fieldValue.toString();
	}

}

