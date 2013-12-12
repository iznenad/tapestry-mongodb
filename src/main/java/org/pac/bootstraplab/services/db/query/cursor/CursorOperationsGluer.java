package org.pac.bootstraplab.services.db.query.cursor;

import org.pac.bootstraplab.services.db.query.Gluer;
import org.pac.bootstraplab.services.db.query.criteria.FetchCriteria;

public class CursorOperationsGluer implements Gluer<CursorOperations> {

	private FetchCriteria criteria;
	private CursorOperation currentOperation;
	
	public CursorOperationsGluer(FetchCriteria criteria, CursorOperation currentOperation) {
		this.criteria = criteria;
		this.currentOperation = currentOperation;
	}
	
	@Override
	public CursorOperations glue() {
		
		((CursorOperationsAware) criteria).setOperation(currentOperation);
		
		return new CursorOperationsImpl(criteria);
	}

	@Override
	public FetchCriteria end() {
		
		((CursorOperationsAware) criteria).setOperation(currentOperation);
		
		return criteria;
	}

}

