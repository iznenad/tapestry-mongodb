package org.pac.bootstraplab.services.db.persist;

import com.mongodb.DBObject;

public interface DBObjectGenerator {
	DBObject generateFrom(Object entity);
}
