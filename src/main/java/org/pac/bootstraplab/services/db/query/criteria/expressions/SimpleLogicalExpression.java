package org.pac.bootstraplab.services.db.query.criteria.expressions;

import org.pac.bootstraplab.services.db.query.criteria.FetchCriteria;
import org.pac.bootstraplab.services.db.query.criteria.internal.DBObjectExpression;

import com.mongodb.DBObject;

public class SimpleLogicalExpression extends AbstractLogicalExpression {
	public SimpleLogicalExpression(FetchCriteria parent) {
		super(parent, new DBObjectExpression());
	}

	@Override
	public DBObject dbObject() {
		return super.getDataHolder();
	}
	
}

