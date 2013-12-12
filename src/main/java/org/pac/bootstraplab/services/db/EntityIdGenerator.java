/*
 * $Id$
 */
package org.pac.bootstraplab.services.db;

import org.pac.bootstraplab.models.uid.UID;

public interface EntityIdGenerator {

	UID generateFor(Object entity);
	
	UID generateFrom(String fullUid);
}

