package org.pac.bootstraplab.services.db.query.cursor;

import org.pac.bootstraplab.services.db.query.Gluer;

public interface CursorOperations {
	
	Gluer<CursorOperations> sort(String field, SortConstraint constraint);
	
	Gluer<CursorOperations> limit(int limitTo);
	
	Gluer<CursorOperations> skip(int skip);

}

