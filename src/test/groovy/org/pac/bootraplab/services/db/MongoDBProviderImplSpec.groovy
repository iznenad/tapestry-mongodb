package org.pac.bootraplab.services.db

import org.pac.bootstraplab.services.db.MongoDBProviderImpl

import spock.lang.Shared
import spock.lang.Specification

import com.mongodb.BasicDBObject
import com.mongodb.DB
import com.mongodb.DBCollection
import com.mongodb.DBCursor

class MongoDBProviderImplSpec extends Specification{

	@Shared
	def host = "localhost"
	@Shared
	def port = 27017
	@Shared
	def dbname = "integration-test"
	
	@Shared
	MongoDBProviderImpl provider
	
	def setupSpec(){
		provider = new MongoDBProviderImpl(host, port, dbname)
	}
	
	def "should be configured with provided host, port and dbname"(){
	
		when:
		DB db = provider.provideDB()
		
		then:
		db.name == dbname
	}
	
	def "should be able to write into instance"(){
		DB db = provider.provideDB()
		
		setup:
		db.createCollection("testCollection", new BasicDBObject())
		
		when:
		DBCollection coll = db.getCollection("testCollection")
		
		then:
		coll.insert(new BasicDBObject("testKey", "testValue"))
		
		then:
		db.getCollection("testCollection").count() == 1
		
	}
	
	def "assert type after read"(){
		
		DB db = provider.provideDB()
		
		
		when:
		DBCollection coll = db.getCollection("testCollection")
		
		then:
		coll.insert(new BasicDBObject("testKey", 3))
		
		then:
		db.getCollection("testCollection").count() == 2
		
		then:
		DBCursor cursor = db.getCollection("testCollection").find()
		
		println(cursor.next().get("testKey").getClass().getName())
		
		
	} 
	
	def cleanupSpec(){
		provider.mongoClient.dropDatabase(dbname)
	}
}
