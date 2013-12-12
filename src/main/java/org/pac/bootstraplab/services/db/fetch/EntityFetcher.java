/*
 * $Id$
 */
package org.pac.bootstraplab.services.db.fetch;

import java.util.List;

import org.pac.bootstraplab.services.db.criteria.FetchCriteria;

import com.mongodb.DBObject;

public interface EntityFetcher {

	<T> List<T> fetch(Class<T> spec, FetchCriteria criteria);

	<T> List<T> fetch(Class<T> spec, DBObject dbObject);

}

