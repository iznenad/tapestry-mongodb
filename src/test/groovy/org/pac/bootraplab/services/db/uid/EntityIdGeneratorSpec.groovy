package org.pac.bootraplab.services.db.uid

import org.apache.tapestry5.ioc.annotations.Inject
import org.apache.tapestry5.ioc.annotations.SubModule
import org.pac.bootstraplab.models.uid.AbstractUID
import org.pac.bootstraplab.services.db.EntityIdGenerator
import org.pac.test.model.IdSpecOnlyTypeUIDGenerationCase
import org.pac.test.model.IdSpecTypeAndIdentifierUIDGenerationCase
import org.pac.test.model.NoIDSpecUIDGenerationCase
import org.pac.test.modules.DBTestModule

import spock.lang.Specification
import spock.lang.Unroll

@SubModule(DBTestModule)
class EntityIdGeneratorSpec extends Specification {

	@Inject
	private EntityIdGenerator entityIdGenerator


	def "should return an uid formed out of type from IdSpec and identifier fields"(){
		def testEntity = new IdSpecTypeAndIdentifierUIDGenerationCase()

		when:
		def result = entityIdGenerator.generateFor(testEntity)

		then:
		result.getFully() == "TEST-Nenad Nikolic"
	}

	def "should return a uid with the type defined in IdSpec and a random UUID as identifier"(){
		def testEntity = new IdSpecOnlyTypeUIDGenerationCase()

		when:
		def result = entityIdGenerator.generateFor(testEntity)

		System.err.println result

		then:
		result.getType() == "TEST"
	}
	
	def "should throw an illegal state exception if trying to generate uid for an entity IdSpec"(){
		def testEntity = new NoIDSpecUIDGenerationCase()

		when:
		def result = entityIdGenerator.generateFor(testEntity)

		then:
		thrown(IllegalStateException)
	}
	
	def "should deserialize a string of full UID into UID object" (){
		def fullUID = "TEST-BLABLa.Identifier"
		
		def typeAndIdentifier = fullUID.split('-')
		def expected = new AbstractUID(){
			String getFully() {
				return fullUID
			};
			String getIdentifier() {
				typeAndIdentifier[1]
			};
			String getType() {
				typeAndIdentifier[0]
			};
		}
		when:
		def result = entityIdGenerator.generateFrom(fullUID)
		
		then:
		result == expected
	}
	
	@Unroll("IllegalArgumentException thrown for inputs: '#fullUID'")
	def "should throw an illegal argument exception in case UID is invalid" () {
		
		when:
		entityIdGenerator.generateFrom(fullUID)
		
		then:
		thrown(IllegalArgumentException)
		
		where:
		fullUID << ["sdakdmsad", null, "", "sdadsad-dsadsadsa-dsadsad-dsadsa-dsadsa"]
	}
}

