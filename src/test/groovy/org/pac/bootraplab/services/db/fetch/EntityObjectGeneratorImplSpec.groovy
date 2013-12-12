/*
 * $Id$
 */
package org.pac.bootraplab.services.db.fetch

import org.apache.tapestry5.ioc.annotations.Inject
import org.apache.tapestry5.ioc.annotations.SubModule
import org.pac.bootstraplab.services.db.fetch.EntityGenerator
import org.pac.bootstraplab.services.db.persist.DBObjectGenerator
import org.pac.test.model.CollectionTestEntity
import org.pac.test.model.IntTestEntity
import org.pac.test.model.TestEntity
import org.pac.test.model.TestEntityWithSubDocument
import org.pac.test.model.URITestEntity
import org.pac.test.modules.DBTestModule

import spock.lang.Ignore
import spock.lang.Specification

import com.mongodb.BasicDBObject
import com.mongodb.DBObject


//@Ignore
@SubModule(DBTestModule)
class EntityObjectGeneratorImplSpec extends Specification {

	@Inject
	private EntityGenerator entityObjectGenerator
	
	@Inject
	private DBObjectGenerator dbObjectGenerator
	
	def "should create a test entity object with values specified in the BasicDBObject provided"(){
	
		TestEntity expectedResult = new TestEntity()
		
		BasicDBObject dbObject = new BasicDBObject().append("uid", "test-identifier").append("nullValue", null)
	
		when:
		def result = entityObjectGenerator.generate(TestEntity.class, dbObject)
		
		println(result)
		
		then:
		result == expectedResult
	}
	
	def "assert recursive deserialization"(){
		
		def entity = new TestEntityWithSubDocument()
		
		DBObject object = dbObjectGenerator.generateFrom(entity)
		
		when:
		def result = entityObjectGenerator.generate(TestEntityWithSubDocument, object)
		
		then:
		result == entity
		
	}
	
	def "assert primitive values are casted properly"(){
		def entity = new IntTestEntity()
		
		DBObject object = dbObjectGenerator.generateFrom(entity)
		
		when:
		def result = entityObjectGenerator.generate(IntTestEntity, object)
		
		then:
		result == entity
	}
	
	def "assert collection serialization/deserialization"(){
		def entity = new CollectionTestEntity()
		
		DBObject object = dbObjectGenerator.generateFrom(entity)
		
		println(object)
		
		when:
		def result = entityObjectGenerator.generate(CollectionTestEntity, object)
		
		println(result)
		
		then:
		result == entity
	}
	
	def "assert uri serialization/deserialization"(){
		def entity = new URITestEntity()
		
		DBObject object = dbObjectGenerator.generateFrom(entity)
		
		when:
		def result = entityObjectGenerator.generate(URITestEntity, object)
		
		println(result)
		
		then:
		result == entity
	}
	
	def "handle null subdocument fields"(){
		
		def entity = new TestEntityWithSubDocument()
		
		entity.goIntoField = null
		
		DBObject object = dbObjectGenerator.generateFrom(entity)
		
		when:
		def result = entityObjectGenerator.generate(TestEntityWithSubDocument, object)
		
		println(result)
		
		then:
		result == entity
	}
}

