/*
 * $Id$
 */
package org.pac.bootstraplab.services.db.fetch;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.pac.bootstraplab.services.db.MongoDBProvider;
import org.pac.bootstraplab.services.db.query.criteria.FetchCriteria;
import org.pac.bootstraplab.services.db.query.cursor.CursorOperation;
import org.pac.bootstraplab.services.db.query.cursor.CursorOperationsAware;
import org.slf4j.Logger;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class MongoEntityFetcher implements EntityFetcher {

	@Inject
	private MongoDBProvider mongoDBProvider;
	
	@Inject
	private Logger logger;
	
	@Inject
	private EntityGenerator entityObjectGenerator;
	
	@Override
	public <T> List<T> fetch(Class<T> spec, FetchCriteria criteria) {
		
		long startTime = System.currentTimeMillis();
		
		assert spec!=null;
		assert criteria != null;
		
		DB db = mongoDBProvider.provideDB();
		
		DBCollection collection = db.getCollection(spec.getSimpleName());
		
		logger.info("Executing query: {}", criteria.dbObject());
		
		DBCursor cursor = executeCursorOperations(collection.find(criteria.dbObject()), criteria);
		
		logger.info("Found {} items for {}", new Object[]{cursor.count(), spec.getSimpleName()});
		
		List<T> result = new ArrayList<>();
		
		while(cursor.hasNext()){
			
			DBObject object = cursor.next();
			
			result.add((T) entityObjectGenerator.generate(spec, object));
			
		}
		
		logger.info(String.format("Query time: %d ms",  System.currentTimeMillis() - startTime));
		
		return result;
	}

	private DBCursor executeCursorOperations(final DBCursor cursor, FetchCriteria criteria){
		if(!(criteria instanceof CursorOperationsAware)){
			return cursor;
		}
		
		CursorOperationsAware operationsContainer = (CursorOperationsAware) criteria;
		DBCursor result = cursor;
		for(CursorOperation operation : operationsContainer.getOperations()) {
			result = operation.executeOn(cursor);
		}
		
		return result;
	}
}

