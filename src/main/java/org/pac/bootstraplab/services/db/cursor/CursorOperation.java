package org.pac.bootstraplab.services.db.cursor;

import com.mongodb.DBCursor;

public interface CursorOperation {
	DBCursor executeOn(DBCursor cursor);
}

