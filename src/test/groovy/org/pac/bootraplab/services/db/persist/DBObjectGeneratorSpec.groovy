package org.pac.bootraplab.services.db.persist;

import org.apache.tapestry5.ioc.annotations.Inject
import org.apache.tapestry5.ioc.annotations.SubModule
import org.pac.bootstraplab.services.db.persist.DBObjectGenerator
import org.pac.test.model.IntTestEntity
import org.pac.test.model.TestEntity
import org.pac.test.model.TestEntityWithSubDocument
import org.pac.test.modules.DBTestModule

import spock.lang.Specification

import com.mongodb.BasicDBObject
import com.mongodb.DBObject


///@Ignore
@SubModule([DBTestModule])
public class DBObjectGeneratorSpec extends Specification{

	@Inject
	DBObjectGenerator generator

	def "should extract value from entity"(){
		setup:
		def entity = new TestEntity()
		
		when:
		DBObject value = generator.generateFrom(entity)

		println(value)
		
		then:
		value.get("uid") == "test-identifier"
	} 
	
	def "should extract value from entity with gointo field"(){
		setup:
		def entity = new TestEntityWithSubDocument()
		
		when:
		DBObject value = generator.generateFrom(entity)

		println(value)
		
		then:
		value.get("uid") == "test-identifier"
		value.get("goIntoField") == new BasicDBObject("myValue", "read me!")
	}
	
	def "should write as number"(){
		setup:
		def entity = new IntTestEntity()
		
		when:
		DBObject value = generator.generateFrom(entity)

		println(value)
		println(value.get("number").getClass().getName())
		
		then:
		value.get("number") == 3
	}
}
