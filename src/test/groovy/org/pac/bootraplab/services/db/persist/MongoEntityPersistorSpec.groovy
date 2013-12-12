package org.pac.bootraplab.services.db.persist

import org.pac.bootstraplab.services.db.MongoDBProvider
import org.pac.bootstraplab.services.db.persist.DBObjectGenerator
import org.pac.bootstraplab.services.db.persist.MongoEntityPersistor
import org.pac.test.model.TestEntity
import org.pac.test.model.TestEntityWithSubDocument
import org.slf4j.Logger

import spock.lang.Specification

import com.mongodb.BasicDBObject
import com.mongodb.DB
import com.mongodb.DBCollection

class MongoEntityPersistorSpec extends Specification {

	MongoDBProvider provider = Mock()
	DBObjectGenerator objectGenerator = Mock()
	MongoEntityPersistor persistor
	
	def setup(){
		persistor = new MongoEntityPersistor()
		
		persistor.mongoDBProvider = provider
		persistor.dbObjectGenerator = objectGenerator
		persistor.logger = Mock(Logger)
	}
	
	def "should write entity" (){
		
		def entity = new TestEntity()
		DB db = Mock()
		DBCollection collection = Mock()
		
		when:
		persistor.persistEntity(entity)
		
		then:
		1*provider.provideDB() >> db
		
		then:
		1*db.getCollection("TestEntity") >> collection
		
		then:
		1*objectGenerator.generateFrom(entity) >> new BasicDBObject("uid", "test-uid")
		
		then:
		1*collection.save(new BasicDBObject("uid", "test-uid"))
		
	}
	
	def "should write entity with go into field"(){
		
		def entity = new TestEntityWithSubDocument()
		DB db = Mock()
		DBCollection collection = Mock()
		
		when:
		persistor.persistEntity(entity)
		
		then:
		1*provider.provideDB() >> db 
		
		then:
		1*db.getCollection("TestEntityWithSubDocument") >> collection
		
		then:
		1*objectGenerator.generateFrom(entity) >> new BasicDBObject("uid", "test-uid").append("goIntoField", new BasicDBObject("myValue", "read me!"))
		
		then:
		1*collection.save(new BasicDBObject("uid", "test-uid").append("goIntoField", new BasicDBObject("myValue", "read me!")))
		
	}
}
