package org.pac.bootstraplab.services.db.query.criteria;

import org.apache.commons.lang3.tuple.Pair;
import org.pac.bootstraplab.services.db.query.Gluer;
import org.pac.bootstraplab.services.db.query.cursor.CursorOperations;


public interface FetchCriteria extends DBObjectContainer {

	ExpressionOpener query();

	ExpressionOpener or();
	
	ExpressionOpener and();
	
	ExpressionCloser findAll();
	
	static interface LogicalExpression extends DBObjectContainer {
		
		ExpressionCloser eq(Object value);
		ExpressionCloser in(Object... values);
		ExpressionCloser greaterThen(Object value);
		ExpressionCloser lessThen(Object value);
		ExpressionCloser elemMatch(Pair<String, Object> ... fieldToValue);
	}
	
	static interface ExpressionOpener {
		LogicalExpression where(String field);
	}
	
	static interface ExpressionCloser extends Gluer<ExpressionOpener>{
		CursorOperations cursor();
	}
}
