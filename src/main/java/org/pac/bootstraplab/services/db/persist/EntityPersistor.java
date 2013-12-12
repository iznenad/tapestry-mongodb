package org.pac.bootstraplab.services.db.persist;

import org.pac.bootstraplab.models.AbstractEntity;


public interface EntityPersistor {
	void persistEntity(AbstractEntity entity);
}
