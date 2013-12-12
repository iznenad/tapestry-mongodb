package org.pac.bootstraplab.services.db;

import java.util.List;

import org.pac.bootstraplab.models.AbstractEntity;
import org.pac.bootstraplab.services.db.criteria.FetchCriteria;

public interface EntitySource {

	<T> List<T> query(FetchCriteria criteria);
	
	public void persist(AbstractEntity entity);
	
}
