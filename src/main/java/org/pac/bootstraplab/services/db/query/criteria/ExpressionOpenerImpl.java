/*
 * $Id$
 */
package org.pac.bootstraplab.services.db.query.criteria;

import org.pac.bootstraplab.services.db.query.criteria.FetchCriteria.LogicalExpression;
import org.pac.bootstraplab.services.db.query.criteria.internal.Setter;

public class ExpressionOpenerImpl implements FetchCriteria.ExpressionOpener {
	
	private Setter expression;
	
	public ExpressionOpenerImpl(Setter expression) {
		this.expression = expression;
	}
	
	@Override
	public LogicalExpression where(String field) {
		
		expression.set(field);
		
		return (LogicalExpression) expression;
	}
	
}

