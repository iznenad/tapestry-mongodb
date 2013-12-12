package org.pac.bootstraplab.services.db.criteria;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.tapestry5.func.F;
import org.apache.tapestry5.func.Reducer;
import org.pac.bootstraplab.services.db.criteria.internal.DBListExpression;
import org.pac.bootstraplab.services.db.criteria.internal.DBObjectExpression;
import org.pac.bootstraplab.services.db.criteria.internal.Setter;
import org.pac.bootstraplab.services.db.cursor.CursorOperation;
import org.pac.bootstraplab.services.db.cursor.CursorOperations;
import org.pac.bootstraplab.services.db.cursor.CursorOperationsAware;
import org.pac.bootstraplab.services.db.cursor.CursorOperationsImpl;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class MongoFetchCriteria implements FetchCriteria, CursorOperationsAware {

	static class ExpressionOpenerImpl implements FetchCriteria.ExpressionOpener {
		
		private Setter expression;
		
		public ExpressionOpenerImpl(Setter expression) {
			this.expression = expression;
		}
		
		@Override
		public LogicalExpression where(String field) {
			
			expression.set(field);
			
			return (LogicalExpression) expression;
		}
		
	};

	static class ExpressionCloserImpl implements FetchCriteria.ExpressionCloser {
		
		private FetchCriteria criteria;
		private Setter currentExpression;
		
		public ExpressionCloserImpl(FetchCriteria criteria, Setter currentExpression) {
			this.criteria = criteria;
			this.currentExpression = currentExpression;
		}
		
		@Override
		public ExpressionOpener _() {
			return new ExpressionOpenerImpl(currentExpression);
		}

		@Override
		public CursorOperations cursor() {
			return new CursorOperationsImpl((CursorOperationsAware) criteria);
		}
		
		@Override
		public FetchCriteria end() {
			
			((MongoFetchCriteria) criteria).addExpresion((LogicalExpression) currentExpression);
			
			return criteria;
		}
		
	};
	
	static abstract class LogicalExpressionImpl implements FetchCriteria.LogicalExpression, Setter {

		private static final String IN_KEYWORD = "$in";

		private static final String GREATER_THEN_KEYWORD = "$gt";

		private static final String LESS_THEN_KEYWORD = "$lt";
		
		
		private String field;
		
		public void set(Object object) {
			this.field = (String) field;
		} 
		
		private Setter dataHolder;
		private FetchCriteria parent;
		
		public LogicalExpressionImpl(FetchCriteria parent, Setter dataHolder) {
			this.parent = parent;
			this.dataHolder = dataHolder;
		}
		
		@Override
		public org.pac.bootstraplab.services.db.criteria.FetchCriteria.ExpressionCloser eq(Object value) {
			
			throwExceptionIfFieldIsNull();
			
			dataHolder.set(new BasicDBObject(field, value));
			
			return new ExpressionCloserImpl(parent, this);
		}

		@Override
		public org.pac.bootstraplab.services.db.criteria.FetchCriteria.ExpressionCloser in(Object... values) {
			throwExceptionIfFieldIsNull();
			
			dataHolder.set(new BasicDBObject(field, new BasicDBObject(IN_KEYWORD, values)));
			
			return new ExpressionCloserImpl(parent, this);
		}

		@Override
		public org.pac.bootstraplab.services.db.criteria.FetchCriteria.ExpressionCloser greaterThen(Object value) {
			throwExceptionIfFieldIsNull();
			
			dataHolder.set(new BasicDBObject(field, new BasicDBObject(GREATER_THEN_KEYWORD, value)));
			
			return new ExpressionCloserImpl(parent, this);
		}

		@Override
		public org.pac.bootstraplab.services.db.criteria.FetchCriteria.ExpressionCloser lessThen(Object value) {
			throwExceptionIfFieldIsNull();
			
			dataHolder.set(new BasicDBObject(field, new BasicDBObject(LESS_THEN_KEYWORD, value)));
			
			return new ExpressionCloserImpl(parent, this);
		}
		
		private void throwExceptionIfFieldIsNull() {

			if (StringUtils.isBlank(field))
				throw new IllegalStateException("You must specify a field which whill be used in the query.");
		}
	}
	
	private static class And extends LogicalExpressionImpl{

		private static final String AND_KEYWORD = "$and";
		
		public And(FetchCriteria parent) {
			super(parent, new DBListExpression());
		}

		@Override
		public DBObject dbObject() {
			return new BasicDBObject(AND_KEYWORD, super.dataHolder);
		}
		
	}
	
	private static class Or extends LogicalExpressionImpl {

		private static final String OR_KEYWORD = "$or";
		
		public Or(FetchCriteria parent) {
			super(parent, new DBListExpression());
		}

		@Override
		public DBObject dbObject() {
			return new BasicDBObject(OR_KEYWORD, ((DBObjectContainer) super.dataHolder).dbObject());
		}
		
	}

	static class SimpleLogicalExpression extends LogicalExpressionImpl {
		
		public SimpleLogicalExpression(FetchCriteria parent) {
			super(parent, new DBObjectExpression());
		}

		@Override
		public DBObject dbObject() {
			return ((DBObjectContainer) super.dataHolder).dbObject();
		}
		
		
	};
	
	private final List<LogicalExpression> expressions;
	private final List<CursorOperation> operations;
	
	private void addExpresion(LogicalExpression expression) {
		expressions.add(expression);
	}
	
	public void setOperation(CursorOperation operation) {
		operations.add(operation);
	}
	
	public MongoFetchCriteria() {
		expressions = new ArrayList<>();
		operations = new ArrayList<>();
	}

	@Override
	public DBObject dbObject() {
		
		return F.flow(expressions).reduce(new Reducer<BasicDBObject, FetchCriteria.LogicalExpression>() {
			@Override
			public BasicDBObject reduce(BasicDBObject accumulator, LogicalExpression element) {

				accumulator.putAll(element.dbObject());
				
				return accumulator; 
			}
		}, new BasicDBObject());
	}

	@Override
	public ExpressionOpener or() {
		return new ExpressionOpenerImpl(new Or(this));
	}

	@Override
	public ExpressionOpener and() {
		return new ExpressionOpenerImpl(new And(this));
	}

	@Override
	public ExpressionOpener simpleQuery() {
		return new ExpressionOpenerImpl(new SimpleLogicalExpression(this));
	}

}
