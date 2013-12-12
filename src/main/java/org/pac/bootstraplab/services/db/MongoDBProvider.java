package org.pac.bootstraplab.services.db;

import com.mongodb.DB;

public interface MongoDBProvider {

	DB provideDB();
	
}
