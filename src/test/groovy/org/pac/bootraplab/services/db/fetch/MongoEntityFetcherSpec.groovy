/*
 * $Id$
 */
package org.pac.bootraplab.services.db.fetch

import org.pac.bootstraplab.services.db.MongoDBProvider
import org.pac.bootstraplab.services.db.criteria.FetchCriteria
import org.pac.bootstraplab.services.db.fetch.EntityGenerator
import org.pac.bootstraplab.services.db.fetch.MongoEntityFetcher
import org.pac.test.model.TestEntity
import org.slf4j.Logger

import spock.lang.Specification

import com.mongodb.BasicDBObject
import com.mongodb.DB
import com.mongodb.DBCollection
import com.mongodb.DBCursor


class MongoEntityFetcherSpec extends Specification{

	MongoDBProvider mongoDBProvider = Mock()
	
	Logger logger = Mock()
		
	EntityGenerator entityObjectGenerator = Mock()
	
	MongoEntityFetcher fetcher = new MongoEntityFetcher()
	def setup(){
		fetcher.mongoDBProvider = mongoDBProvider
		fetcher.logger = logger
		fetcher.entityObjectGenerator = entityObjectGenerator
	}
	
	def "should call the db and object generator"(){
		
		FetchCriteria criteria = Mock()
		DB db = Mock()
		DBCollection collection = Mock()
		DBCursor cursor = Mock()
		
		given:
		cursor.hasNext() >>> [true, false]
		
		when:
		def result = fetcher.fetch(TestEntity, criteria)
		
		then:
		1*mongoDBProvider.provideDB() >> db
		
		then:
		1*db.getCollection(TestEntity.simpleName) >> collection
		
		then:
		2*criteria.dbObject() >> new BasicDBObject() //two times because it's called for logging
		
		then:
		1*collection.find(new BasicDBObject()) >> cursor
		
		then:
		1*cursor.count() >> 1
	
		then:
		1*cursor.next() >> new BasicDBObject("uid", "test-identifier")
		
		then:
		1*entityObjectGenerator.generate(TestEntity.class, new BasicDBObject("uid", "test-identifier")) >> new TestEntity()
		
		expect:
		result == [new TestEntity()]
		
	}
	
}

