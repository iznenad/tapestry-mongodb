package org.pac.bootstraplab.services.db.cursor;

public interface CursorOperations {
	
	CursorOperations sort(String field, SortConstraint constraint);
	
	CursorOperations limit(int limitTo);
	
	CursorOperations skip(int skip);
}

