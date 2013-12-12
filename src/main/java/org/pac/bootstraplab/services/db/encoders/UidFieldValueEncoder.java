/*
 * $Id$
 */
package org.pac.bootstraplab.services.db.encoders;

import org.pac.bootstraplab.models.uid.UID;
import org.pac.bootstraplab.services.db.persist.EntityFieldValueEncoder;

public class UidFieldValueEncoder implements EntityFieldValueEncoder<UID> {

	@Override
	public Object encode(UID fieldValue) {
		return fieldValue.getFully();
	}

}

