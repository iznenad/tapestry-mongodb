package org.pac.bootstraplab.services.db;

import java.net.UnknownHostException;

import org.apache.tapestry5.ioc.annotations.Symbol;

import com.mongodb.DB;
import com.mongodb.MongoClient;

public class MongoDBProviderImpl implements MongoDBProvider{

	private MongoClient mongoClient;
	private String databaseName;
	public MongoDBProviderImpl(@Symbol(MongoSymbolConstants.HOST) String host, @Symbol(MongoSymbolConstants.PORT) int port, @Symbol(MongoSymbolConstants.DBNAME) String databaseName) {
		
		try {
			
			this.mongoClient = new MongoClient( host , port );
			this.databaseName = databaseName;
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public DB provideDB() {
		return mongoClient.getDB(databaseName);
	}
}
