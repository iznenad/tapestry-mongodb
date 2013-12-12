package org.pac.bootstraplab.services.db.persist;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.pac.bootstraplab.models.AbstractEntity;
import org.pac.bootstraplab.services.db.EntityIdGenerator;
import org.pac.bootstraplab.services.db.MongoDBProvider;
import org.slf4j.Logger;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class MongoEntityPersistor implements EntityPersistor {

	@Inject
	private MongoDBProvider mongoDBProvider;

	@Inject
	private DBObjectGenerator dbObjectGenerator;

	@Inject
	private EntityIdGenerator entityIdGenerator;

	@Inject
	private Logger logger;

	@Override
	public void persistEntity(AbstractEntity entity) {

		DB database = mongoDBProvider.provideDB();

		DBCollection collection = database.getCollection(entity.getClass().getSimpleName());

		if (entity.getUid() == null) {
			entity.setUid(entityIdGenerator.generateFor(entity));
		}
		
		DBObject preparedEntity = dbObjectGenerator.generateFrom(entity);
		
		logger.info("Inserting object: {}, into collection: {}", new Object[] { preparedEntity, collection.getName() });

		collection.save(preparedEntity);
		
	}
}
