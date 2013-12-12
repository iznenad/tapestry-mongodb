/*
 * $Id$
 */
package org.pac.bootraplab.services.db.queries.expressions

import org.pac.bootstraplab.services.db.query.criteria.FetchCriteria
import org.pac.bootstraplab.services.db.query.criteria.MongoFetchCriteria

import spock.lang.Specification

import com.mongodb.BasicDBList
import com.mongodb.BasicDBObject

class ComplexCriteriaSpec extends Specification {

	def "should create a valid query object with multiple logical expressions" () {
		
		def planetNames = ['Jupiter', 'Saturn', 'Mars', 'Venus']
		
															
		def andList = new BasicDBList()
		andList.add(new BasicDBObject("planet-name", new BasicDBObject('$in', planetNames)))
		andList.add(new BasicDBObject("gravity", new BasicDBObject('$gt', 10.44)))
		
		def orList = new BasicDBList()
		orList.add(new BasicDBObject("atmosphere", true))
		orList.add(new BasicDBObject("water", true))
		
		def expected = new BasicDBObject().append('$and', andList).append('$or', orList)
		
		when:
		FetchCriteria criteria = new MongoFetchCriteria()	.and().where("planet-name").in('Jupiter', 'Saturn', 'Mars', 'Venus').glue().where("gravity").greaterThen(10.44).end()
															.or().where("atmosphere").eq(true).glue().where('water').eq(true).end()
		
		
		println criteria.dbObject()
															
		then:
		criteria.dbObject() == expected
	}
	
}

