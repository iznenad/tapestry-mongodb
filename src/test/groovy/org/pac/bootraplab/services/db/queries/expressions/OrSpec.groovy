package org.pac.bootraplab.services.db.queries.expressions

import org.pac.bootstraplab.services.db.query.criteria.FetchCriteria
import org.pac.bootstraplab.services.db.query.criteria.MongoFetchCriteria

import spock.lang.Specification

import com.mongodb.BasicDBList
import com.mongodb.BasicDBObject

class OrSpec extends Specification {
	def "should create a or expression containing a list of conditions" () {
		
		FetchCriteria orCriteria = new MongoFetchCriteria()
		def expectedObject = new BasicDBList()
		expectedObject.add(new BasicDBObject("galaxy-name", 'milky way'))
		expectedObject.add(new BasicDBObject("planet-count", new BasicDBObject('$gt', 10000)))
		
		
		when:
		def result = orCriteria.or().where("galaxy-name").eq("milky way").glue().where("planet-count").greaterThen(10000).end()
		
		
		then:
		result.dbObject() == new BasicDBObject('$or', expectedObject)
		
	}
}

