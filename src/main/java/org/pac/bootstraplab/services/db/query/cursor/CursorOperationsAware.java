package org.pac.bootstraplab.services.db.query.cursor;

import java.util.List;

public interface CursorOperationsAware {	
	void setOperation(CursorOperation operation);
	List<CursorOperation> getOperations();
}

