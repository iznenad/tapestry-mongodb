package org.pac.bootstraplab.services.db.criteria;

import org.pac.bootstraplab.services.db.cursor.CursorOperations;


public interface FetchCriteria extends DBObjectContainer {

	ExpressionOpener simpleQuery();

	ExpressionOpener or();
	
	ExpressionOpener and();
	
	static interface LogicalExpression extends DBObjectContainer {
		
		ExpressionCloser eq(Object value);
		ExpressionCloser in(Object... values);
		ExpressionCloser greaterThen(Object value);
		ExpressionCloser lessThen(Object value);
		
	}
	
	static interface ExpressionOpener {
		LogicalExpression where(String field);
	}
	
	static interface ExpressionCloser {
		ExpressionOpener _();
		
		CursorOperations cursor();
		
		FetchCriteria end();
	}
}
