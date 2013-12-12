package org.pac.bootraplab.services.db.queries.expressions

import org.pac.bootstraplab.services.db.query.criteria.FetchCriteria
import org.pac.bootstraplab.services.db.query.criteria.MongoFetchCriteria

import spock.lang.Specification

import com.mongodb.BasicDBObject

class SimpleLogicalExpressionSpec extends Specification {
	def "should create an implicit and expression containing a hash of conditions" () {
		
		FetchCriteria simpleCriteria = new MongoFetchCriteria()
		
		def expectedObject = new BasicDBObject().append("galaxy-name", 'milky way').append("planet-count", new BasicDBObject('$gt', 10000))
		
		when:
		def result = simpleCriteria.query().where("galaxy-name").eq("milky way").glue().where("planet-count").greaterThen(10000).end()
		
		
		then:
		result.dbObject() == expectedObject
		
	}
}

