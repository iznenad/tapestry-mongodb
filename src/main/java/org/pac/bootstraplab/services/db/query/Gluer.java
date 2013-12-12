/*
 * $Id$
 */
package org.pac.bootstraplab.services.db.query;

import org.pac.bootstraplab.services.db.query.criteria.FetchCriteria;

public interface Gluer<TypeOfExpression> {

	TypeOfExpression glue();
	FetchCriteria end();
	
}

