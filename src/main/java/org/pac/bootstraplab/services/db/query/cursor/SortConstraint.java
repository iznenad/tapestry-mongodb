/*
 * $Id$
 */
package org.pac.bootstraplab.services.db.query.cursor;

public enum SortConstraint {
	ASC(1), DESC(-1);
	
	private int sortConstraint;
	
	private SortConstraint(int sortConstraint) {
		this.sortConstraint = sortConstraint;
	}
	
	public int getConstraint(){
		return sortConstraint;
	}
}

