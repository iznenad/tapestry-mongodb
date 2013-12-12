/*
 * $Id$
 */
package org.pac.bootstraplab.services.db.query.criteria.expressions;

import org.pac.bootstraplab.services.db.query.criteria.FetchCriteria;
import org.pac.bootstraplab.services.db.query.criteria.internal.DBListExpression;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class And extends AbstractLogicalExpression {

	private static final String AND_KEYWORD = "$and";

	public And(FetchCriteria parent) {
		super(parent, new DBListExpression());
	}

	@Override
	public DBObject dbObject() {
		return new BasicDBObject(AND_KEYWORD, super.getDataHolder());
	}

}
