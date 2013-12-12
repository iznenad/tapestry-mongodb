
package org.pac.bootstraplab.services.db.criteria.internal;

import org.pac.bootstraplab.services.db.criteria.DBObjectContainer;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;

public class DBListExpression implements Setter, DBObjectContainer {

	private BasicDBList dbList;
	
	public DBListExpression() {
		dbList = new BasicDBList();
	}
	
	@Override
	public void set(Object object) {
		dbList.add(object);
	}
	
	@Override
	public DBObject dbObject() {
		return dbList;
	}
}

