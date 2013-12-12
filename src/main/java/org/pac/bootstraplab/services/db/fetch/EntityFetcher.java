/*
 * $Id$
 */
package org.pac.bootstraplab.services.db.fetch;

import java.util.List;

import org.pac.bootstraplab.services.db.query.criteria.FetchCriteria;

public interface EntityFetcher {

	<T> List<T> fetch(Class<T> spec, FetchCriteria criteria);

}

