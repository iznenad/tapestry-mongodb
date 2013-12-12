package org.pac.bootstraplab.services.db.cursor;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;



public class CursorOperationsImpl implements CursorOperations {

	private CursorOperationsAware operationsContainer;
	
	public CursorOperationsImpl(CursorOperationsAware operationsContainer) {
		this.operationsContainer = operationsContainer;
	}
	
	private static class SortOperation implements CursorOperation {
		
		private String field;
		private SortConstraint constraint;
		
		public SortOperation(String field, SortConstraint constraint) {
			this.field = field;
			this.constraint = constraint;
		}

		@Override
		public DBCursor executeOn(DBCursor cursor) {
			return cursor.sort(new BasicDBObject(field, constraint.getConstraint()));
		}
		
	}
	
	private static class LimitOperation implements CursorOperation {

		private int limit;
		
		public LimitOperation(int limit) {
			this.limit = limit;
		}
		
		@Override
		public DBCursor executeOn(DBCursor cursor) {
			return cursor.limit(limit);
		}
		
	}
	
	private static class SkipOperation implements CursorOperation {

		private int skip;
		
		public SkipOperation(int skip) {
			this.skip = skip;
		}
		
		@Override
		public DBCursor executeOn(DBCursor cursor) {
			return cursor.skip(skip);
		}
		
	}
	
	@Override
	public CursorOperations sort(String field, SortConstraint constraint) {
		
		operationsContainer.setOperation(new SortOperation(field, constraint));
		
		return this;
	}

	@Override
	public CursorOperations limit(int limitTo) {
		
		operationsContainer.setOperation(new LimitOperation(limitTo));
		
		return this;
	}

	@Override
	public CursorOperations skip(int skip) {
		
		operationsContainer.setOperation(new SkipOperation(skip));
		
		return this;
	}

}

