/*
 * $Id$
 */
package org.pac.bootraplab.services.db.integration

import org.apache.tapestry5.ioc.annotations.Inject
import org.apache.tapestry5.ioc.annotations.SubModule
import org.pac.bootstraplab.services.db.DBModule
import org.pac.bootstraplab.services.db.MongoDBProvider
import org.pac.bootstraplab.services.db.criteria.MongoFetchCriteria
import org.pac.bootstraplab.services.db.fetch.EntityFetcher
import org.pac.bootstraplab.services.db.persist.EntityPersistor
import org.pac.test.model.CollectionTestEntity
import org.pac.test.model.IntTestEntity

import spock.lang.Specification

import com.mongodb.DB

//@Ignore
@SubModule(DBModule)
class DatabaseReadWriteIntegrationTest extends Specification {

	@Inject
	private MongoDBProvider mongoDBProvider

	@Inject
	private EntityPersistor entityPersistor

	@Inject
	private EntityFetcher entityFetcher

	def setup(){
		DB db = mongoDBProvider.provideDB()

		def collection = db.getCollection("IntTestEntity")

		collection.drop()
	}

	def "read write"(){

		DB db = mongoDBProvider.provideDB()

		def e1 = new IntTestEntity()
		e1.number = 1
		def e2 = new IntTestEntity()
		e2.number = 2
		def e3 = new IntTestEntity()
		e3.number = 3
		def e4 = new IntTestEntity()
		e4.number = 4
		def e5 = new IntTestEntity()
		e5.number = 5

		def entityList = [e1, e2, e3, e4, e5]

		entityList.each { entityPersistor.persistEntity(it) }

		when:
		def result = entityFetcher.fetch(IntTestEntity, new MongoFetchCriteria().where("number").greaterThen(1))

		println(result)

		then:
		result == [e2, e3, e4, e5]
	}
}

