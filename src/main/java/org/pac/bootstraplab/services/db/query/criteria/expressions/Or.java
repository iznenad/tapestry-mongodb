/*
 * $Id$
 */
package org.pac.bootstraplab.services.db.query.criteria.expressions;

import org.pac.bootstraplab.services.db.query.criteria.FetchCriteria;
import org.pac.bootstraplab.services.db.query.criteria.internal.DBListExpression;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class Or extends AbstractLogicalExpression  {
	private static final String OR_KEYWORD = "$or";
	
	public Or(FetchCriteria parent) {
		super(parent, new DBListExpression());
	}

	@Override
	public DBObject dbObject() {
		return new BasicDBObject(OR_KEYWORD, super.getDataHolder());
	}
	
}

