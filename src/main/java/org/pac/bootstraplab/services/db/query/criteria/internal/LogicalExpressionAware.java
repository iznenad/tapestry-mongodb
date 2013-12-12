package org.pac.bootstraplab.services.db.query.criteria.internal;

import org.pac.bootstraplab.services.db.query.criteria.FetchCriteria.LogicalExpression;

public interface LogicalExpressionAware {
	void addExpresion(LogicalExpression expression);
}

