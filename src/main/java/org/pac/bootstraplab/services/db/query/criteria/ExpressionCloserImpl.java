package org.pac.bootstraplab.services.db.query.criteria;

import org.pac.bootstraplab.services.db.query.criteria.FetchCriteria.ExpressionOpener;
import org.pac.bootstraplab.services.db.query.criteria.FetchCriteria.LogicalExpression;
import org.pac.bootstraplab.services.db.query.criteria.internal.LogicalExpressionAware;
import org.pac.bootstraplab.services.db.query.criteria.internal.Setter;
import org.pac.bootstraplab.services.db.query.cursor.CursorOperations;
import org.pac.bootstraplab.services.db.query.cursor.CursorOperationsImpl;

public class ExpressionCloserImpl implements FetchCriteria.ExpressionCloser {
	
	private FetchCriteria criteria;
	private Setter currentExpression;
	
	public ExpressionCloserImpl(FetchCriteria criteria, Setter currentExpression) {
		this.criteria = criteria;
		this.currentExpression = currentExpression;
	}
	
	@Override
	public ExpressionOpener glue() {
		return new ExpressionOpenerImpl(currentExpression);
	}

	@Override
	public CursorOperations cursor() {
		
		((LogicalExpressionAware) criteria).addExpresion((LogicalExpression) currentExpression);
		
		return new CursorOperationsImpl(criteria);
	}
	
	@Override
	public FetchCriteria end() {
		
		((LogicalExpressionAware) criteria).addExpresion((LogicalExpression) currentExpression);
		
		return criteria;
	}
	
}

