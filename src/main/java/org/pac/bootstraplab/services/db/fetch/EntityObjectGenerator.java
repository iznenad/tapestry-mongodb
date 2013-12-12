/*
 * $Id$
 */
package org.pac.bootstraplab.services.db.fetch;

import com.mongodb.DBObject;

public interface EntityObjectGenerator<T> {

	<T> T generate(Class<T> spec, DBObject dbObject);
	
}

