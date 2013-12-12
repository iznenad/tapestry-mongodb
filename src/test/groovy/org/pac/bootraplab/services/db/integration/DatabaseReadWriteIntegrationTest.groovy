/*
 * $Id$
 */
package org.pac.bootraplab.services.db.integration

import org.apache.tapestry5.ioc.annotations.Inject
import org.apache.tapestry5.ioc.annotations.SubModule
import org.pac.bootstraplab.services.db.DBModule
import org.pac.bootstraplab.services.db.MongoDBProvider
import org.pac.bootstraplab.services.db.fetch.EntityFetcher
import org.pac.bootstraplab.services.db.persist.EntityPersistor
import org.pac.bootstraplab.services.db.query.criteria.MongoFetchCriteria
import org.pac.bootstraplab.services.db.query.cursor.SortConstraint
import org.pac.test.model.IntTestEntity
import org.pac.test.model.Planet

import spock.lang.Shared
import spock.lang.Specification

import com.mongodb.DB

@SubModule(DBModule)
class DatabaseReadWriteIntegrationTest extends Specification {

	@Inject
	private MongoDBProvider mongoDBProvider

	@Inject
	private EntityPersistor entityPersistor

	@Inject
	private EntityFetcher entityFetcher

	def planets 
	
	@Shared
	def mercury, venus, earth, mars, saturn, jupiter, uranus, neptun
	
	def setup(){
		mercury = new Planet()
		mercury.name = "Mercury"
		mercury.atmosphere = true
		mercury.gravity = 3.7
		mercury.water = false
		mercury.orbitalPeriod = 88
		
		venus = new Planet()
		venus.name = "Venus"
		venus.atmosphere = true
		venus.gravity = 8.87
		venus.water = true
		venus.orbitalPeriod = 225
		
		earth = new Planet()
		earth.name = "Earth"
		earth.atmosphere = true
		earth.gravity = 9.81
		earth.water = true
		earth.orbitalPeriod = 365
		
		mars = new Planet()
		mars.name = "Mars"
		mars.atmosphere = true
		mars.gravity = 3.711
		mars.water = true
		mars.orbitalPeriod = 687
		
		saturn = new Planet()
		saturn.name = "Saturn"
		saturn.atmosphere = true
		saturn.gravity = 10.44
		saturn.water = false
		saturn.orbitalPeriod = 10759
		
		jupiter = new Planet()
		jupiter.name = "Jupiter"
		jupiter.atmosphere = true
		jupiter.gravity = 24.79
		jupiter.water = false
		jupiter.orbitalPeriod = 4332
		
		uranus = new Planet()
		uranus.name = "Uranus"
		uranus.atmosphere = true
		uranus.gravity = 8.69
		uranus.water = false
		uranus.orbitalPeriod = 30799
		
		neptun = new Planet()
		neptun.name = "Neptune"
		neptun.atmosphere = true
		neptun.gravity = 11.15
		neptun.water = true
		neptun.orbitalPeriod = 60190
		
		planets = [mercury, venus, earth, mars, jupiter, saturn, uranus, neptun]
		
		DB db = mongoDBProvider.provideDB()

		def collection = db.getCollection("IntTestEntity")

		collection.drop()
		
		collection = db.getCollection("Planet")
		
		collection.drop()
	}

	def "read write"(){

		DB db = mongoDBProvider.provideDB()

		def e1 = new IntTestEntity()
		e1.number = 1
		def e2 = new IntTestEntity()
		e2.number = 2
		def e3 = new IntTestEntity()
		e3.number = 3
		def e4 = new IntTestEntity()
		e4.number = 4
		def e5 = new IntTestEntity()
		e5.number = 5

		def entityList = [e1, e2, e3, e4, e5]

		entityList.each { entityPersistor.persistEntity(it) }

		when:
		def result = entityFetcher.fetch(IntTestEntity, new MongoFetchCriteria().query().where("number").greaterThen(1).end())

		println(result)

		then:
		result == [e2, e3, e4, e5]
	}
	
	def "read write with cursors" () {
		
		setup:
		planets.each({it -> entityPersistor.persistEntity(it)})
		
		when:
		def result = entityFetcher.fetch(Planet, criteria)
		
		println result
		
		then:
		result == expectedResult
		
		where:
		criteria 																											|  expectedResult
		new MongoFetchCriteria().and().where('gravity').greaterThen(9.81 as double).glue().where("water").eq(true).end() 	| [neptun]
		new MongoFetchCriteria().or().where('gravity').greaterThen(9.81 as double).glue().where("water").eq(true).cursor().sort("orbitalPeriod", SortConstraint.ASC).end()| [venus,earth, mars, jupiter, saturn, neptun]
		new MongoFetchCriteria().findAll().cursor().sort("gravity", SortConstraint.ASC).end() | [mercury, mars, uranus, venus, earth, saturn, neptun, jupiter]
		new MongoFetchCriteria().findAll().cursor().sort("gravity", SortConstraint.ASC).glue().skip(2).end() | [uranus, venus, earth, saturn, neptun, jupiter]
		new MongoFetchCriteria().findAll().cursor().sort("gravity", SortConstraint.ASC).glue().skip(2).glue().limit(3).end() | [uranus, venus, earth]
 
	} 
}

