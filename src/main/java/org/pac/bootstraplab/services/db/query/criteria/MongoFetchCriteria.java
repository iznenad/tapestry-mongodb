package org.pac.bootstraplab.services.db.query.criteria;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.func.F;
import org.apache.tapestry5.func.Reducer;
import org.pac.bootstraplab.services.db.query.criteria.expressions.And;
import org.pac.bootstraplab.services.db.query.criteria.expressions.Or;
import org.pac.bootstraplab.services.db.query.criteria.expressions.SimpleLogicalExpression;
import org.pac.bootstraplab.services.db.query.criteria.internal.LogicalExpressionAware;
import org.pac.bootstraplab.services.db.query.cursor.CursorOperation;
import org.pac.bootstraplab.services.db.query.cursor.CursorOperationsAware;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class MongoFetchCriteria implements FetchCriteria, CursorOperationsAware, LogicalExpressionAware {
	
	private final List<LogicalExpression> expressions;
	private final List<CursorOperation> operations;
	
	@Override
	public void addExpresion(LogicalExpression expression) {
		expressions.add(expression);
	}
	
	@Override
	public void setOperation(CursorOperation operation) {
		operations.add(operation);
	}
	
	@Override
	public List<CursorOperation> getOperations() {
		return operations;
	}
	
	public MongoFetchCriteria() {
		expressions = new ArrayList<>();
		operations = new ArrayList<>();
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
	public ExpressionOpener query() {
		return new ExpressionOpenerImpl(new SimpleLogicalExpression(this));
	}
	
	@Override
	public ExpressionCloser findAll() {
		return new ExpressionCloserImpl(this, new SimpleLogicalExpression(this));
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

}
