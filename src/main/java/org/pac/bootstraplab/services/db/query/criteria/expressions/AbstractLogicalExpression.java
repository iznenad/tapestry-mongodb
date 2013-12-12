package org.pac.bootstraplab.services.db.query.criteria.expressions;

import org.apache.commons.lang3.StringUtils;
import org.pac.bootstraplab.services.db.query.criteria.DBObjectContainer;
import org.pac.bootstraplab.services.db.query.criteria.ExpressionCloserImpl;
import org.pac.bootstraplab.services.db.query.criteria.FetchCriteria;
import org.pac.bootstraplab.services.db.query.criteria.FetchCriteria.ExpressionCloser;
import org.pac.bootstraplab.services.db.query.criteria.FetchCriteria.LogicalExpression;
import org.pac.bootstraplab.services.db.query.criteria.internal.Setter;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public abstract class AbstractLogicalExpression implements FetchCriteria.LogicalExpression, Setter {

	private static final String IN_KEYWORD = "$in";

	private static final String GREATER_THEN_KEYWORD = "$gt";

	private static final String LESS_THEN_KEYWORD = "$lt";
	
	
	private String field;
	
	public void set(Object object) {
		this.field = (String) object;
	} 
	
	private Setter dataHolder;
	private FetchCriteria parent;
	
	public AbstractLogicalExpression(FetchCriteria parent, Setter dataHolder) {
		this.parent = parent;
		this.dataHolder = dataHolder;
	}
	
	@Override
	public org.pac.bootstraplab.services.db.query.criteria.FetchCriteria.ExpressionCloser eq(Object value) {
		
		throwExceptionIfFieldIsNull();
		
		dataHolder.set(new BasicDBObject(field, value));
		
		return new ExpressionCloserImpl(parent, this);
	}

	@Override
	public org.pac.bootstraplab.services.db.query.criteria.FetchCriteria.ExpressionCloser in(Object... values) {
		throwExceptionIfFieldIsNull();
		
		dataHolder.set(new BasicDBObject(field, new BasicDBObject(IN_KEYWORD, values)));
		
		return new ExpressionCloserImpl(parent, this);
	}

	@Override
	public org.pac.bootstraplab.services.db.query.criteria.FetchCriteria.ExpressionCloser greaterThen(Object value) {
		throwExceptionIfFieldIsNull();
		
		dataHolder.set(new BasicDBObject(field, new BasicDBObject(GREATER_THEN_KEYWORD, value)));
		
		return new ExpressionCloserImpl(parent, this);
	}

	@Override
	public org.pac.bootstraplab.services.db.query.criteria.FetchCriteria.ExpressionCloser lessThen(Object value) {
		throwExceptionIfFieldIsNull();
		
		dataHolder.set(new BasicDBObject(field, new BasicDBObject(LESS_THEN_KEYWORD, value)));
		
		return new ExpressionCloserImpl(parent, this);
	}
	
	protected DBObject getDataHolder () {
		return ((DBObjectContainer) dataHolder).dbObject();
	}
	
	private void throwExceptionIfFieldIsNull() {

		if (StringUtils.isBlank(field))
			throw new IllegalStateException("You must specify a field which whill be used in the query.");
	}
}
