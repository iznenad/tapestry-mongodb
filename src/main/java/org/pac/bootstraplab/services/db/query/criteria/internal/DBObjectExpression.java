package org.pac.bootstraplab.services.db.query.criteria.internal;

import org.bson.BSONObject;
import org.pac.bootstraplab.services.db.query.criteria.DBObjectContainer;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class DBObjectExpression implements Setter, DBObjectContainer{
	
	private BasicDBObject dbObject;
	
	public DBObjectExpression() {
		dbObject = new BasicDBObject();
	}
	
	@Override
	public void set(Object object) {
		dbObject.putAll((BSONObject) object);
	}

	@Override
	public DBObject dbObject() {
		return dbObject;
	}

}

