/*
 * $Id$
 */
package org.pac.bootraplab.services.db.fetch

import org.pac.bootstraplab.services.db.criteria.FetchCriteria
import org.pac.bootstraplab.services.db.criteria.MongoFetchCriteria

import spock.lang.Ignore
import spock.lang.Specification

import com.mongodb.BasicDBObject


class MongoFetchCriteriaSpec extends Specification{

	def "assert simple where"(){
		when:
		FetchCriteria criteria = new MongoFetchCriteria().where("hero").eq("superman")

		then:
		criteria.dbObject() == new BasicDBObject("hero", "superman")
	}

	def "assert or"(){

		when:
		FetchCriteria criteria = new MongoFetchCriteria().or(new MongoFetchCriteria().where("hero").eq("superman").and().where("notahero").eq("batman"))

		println(criteria.dbObject())

		then:
		criteria.dbObject() == new BasicDBObject().append('$or', new BasicDBObject().append("hero", "superman").append("notahero", "batman"))
	}

	def "assert in"(){

		when:
		FetchCriteria criteria = new MongoFetchCriteria().where("hero").in("superman", "batman", "hulk")
		println(criteria.dbObject())
		then:
		criteria.dbObject() == new BasicDBObject().append("hero", new BasicDBObject('$in', ["superman", "batman", "hulk"]))
	}

	def "assert greater then"(){
		when:
		FetchCriteria criteria = new MongoFetchCriteria().where("gravity").greaterThen("9.81")
		println(criteria.dbObject())
		then:
		criteria.dbObject() == new BasicDBObject("gravity", new BasicDBObject('$gt', "9.81"))
	}

	def "assert less then"(){
		when:
		FetchCriteria criteria = new MongoFetchCriteria().where("gravity").lessThen("9.81")

		println(criteria.dbObject())

		then:
		criteria.dbObject() == new BasicDBObject("gravity", new BasicDBObject('$lt', "9.81"))
	}

	def "assert and"(){
		when:
		FetchCriteria galaxyCriteria = new MongoFetchCriteria().where("starCount").greaterThen("1000000").and().where("populatedPlanets").lessThen("1000")

		println(galaxyCriteria.dbObject())

		then:
		galaxyCriteria.dbObject() == new BasicDBObject().append("starCount", new BasicDBObject('$gt', "1000000")).append("populatedPlanets", new BasicDBObject('$lt', "1000"))
	}

	def "assert complex query (where and or with greater/less then)"(){

		when:
		FetchCriteria placeToLiveInCriteria = new MongoFetchCriteria().where("climate").eq("continental").and().where("continent").eq("Europe")
				.or(new MongoFetchCriteria().where("population").greaterThen("1000000").where("lifePrice").eq("low"))

		println(placeToLiveInCriteria.dbObject())

		then:
		placeToLiveInCriteria.dbObject() == new BasicDBObject().append("climate", "continental").append("continent", "Europe").append('$or', new BasicDBObject().append("population", new BasicDBObject('$gt', "1000000")).append("lifePrice", "low"))
	}
	
	def "test addAll on BasicDBObject"(){
		def input = new BasicDBObject ('ja', 'sam car');

		def output = new BasicDBObject('apsolutno', 'jesam');
		
		def expect = new BasicDBObject().append('ja', 'sam car').append('apsolutno', 'jesam') 
		
		when:
		output.putAll(input)
		
		println output
		
		then:
		output == expect
	}
}

