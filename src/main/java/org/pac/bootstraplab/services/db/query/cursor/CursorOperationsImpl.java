package org.pac.bootstraplab.services.db.query.cursor;

import org.pac.bootstraplab.services.db.query.Gluer;
import org.pac.bootstraplab.services.db.query.criteria.FetchCriteria;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;


public class CursorOperationsImpl implements CursorOperations {

	private FetchCriteria operationsContainer;
	
	public CursorOperationsImpl(FetchCriteria operationsContainer) {
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
	public Gluer<CursorOperations> sort(String field, SortConstraint constraint) {
		
		return new CursorOperationsGluer(operationsContainer, new SortOperation(field, constraint));
	}

	@Override
	public Gluer<CursorOperations> limit(int limitTo) {
		
		return new CursorOperationsGluer(operationsContainer, new LimitOperation(limitTo));
	}

	@Override
	public Gluer<CursorOperations> skip(int skip) {
		
		return new CursorOperationsGluer(operationsContainer, new SkipOperation(skip));
	}

}

