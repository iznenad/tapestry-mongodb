package org.pac.bootstraplab.services.db;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.services.ApplicationDefaults;
import org.apache.tapestry5.ioc.services.ChainBuilder;
import org.apache.tapestry5.ioc.services.StrategyBuilder;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.pac.bootstraplab.models.uid.UID;
import org.pac.bootstraplab.services.db.decoders.CollectionFieldValueDecoder;
import org.pac.bootstraplab.services.db.decoders.EnumFieldValueDecoder;
import org.pac.bootstraplab.services.db.decoders.UIDFieldValueDecoder;
import org.pac.bootstraplab.services.db.decoders.URIFieldValueDecoder;
import org.pac.bootstraplab.services.db.encoders.CollectionFieldValueEncoder;
import org.pac.bootstraplab.services.db.encoders.EnumFieldValueEncoder;
import org.pac.bootstraplab.services.db.encoders.UidFieldValueEncoder;
import org.pac.bootstraplab.services.db.encoders.UriFieldValueEncoder;
import org.pac.bootstraplab.services.db.fetch.DefaultFieldValueDecoder;
import org.pac.bootstraplab.services.db.fetch.EntityFetcher;
import org.pac.bootstraplab.services.db.fetch.EntityFieldValueDecoder;
import org.pac.bootstraplab.services.db.fetch.EntityGenerator;
import org.pac.bootstraplab.services.db.fetch.MongoEntityFetcher;
import org.pac.bootstraplab.services.db.persist.DBObjectGenerator;
import org.pac.bootstraplab.services.db.persist.DefaultFieldValueEncoder;
import org.pac.bootstraplab.services.db.persist.EntityFieldValueEncoder;
import org.pac.bootstraplab.services.db.persist.EntityPersistor;
import org.pac.bootstraplab.services.db.persist.MongoEntityPersistor;



public class DBModule {

	public static void bind(ServiceBinder binder) {
		binder.bind(MongoDBProvider.class);
		binder.bind(EntityIdGenerator.class);
		
		//persist
		binder.bind(EntityPersistor.class, MongoEntityPersistor.class);
		binder.bind(DBObjectGenerator.class);
		
		//fetch
		binder.bind(EntityFetcher.class, MongoEntityFetcher.class);
		binder.bind(EntityGenerator.class);
	}

	public EntityFieldValueEncoder buildEntityFieldValueExtractor(StrategyBuilder builder, Map<Class, EntityFieldValueEncoder> configuraition){
		return builder.build(EntityFieldValueEncoder.class, configuraition);
	}
	
	@Contribute(EntityFieldValueEncoder.class)
	public static void contributeFieldValueExtractorStrategies(MappedConfiguration<Class, EntityFieldValueEncoder> configuration, EntityFieldValueEncoder encoder){
		configuration.add(Object.class, new DefaultFieldValueEncoder());
		configuration.add(Enum.class, new EnumFieldValueEncoder());
		configuration.add(URI.class, new UriFieldValueEncoder());
		configuration.add(java.util.Collection.class, new CollectionFieldValueEncoder(encoder));
		configuration.add(UID.class, new UidFieldValueEncoder());
	}
	
	@Contribute(SymbolProvider.class)
	@ApplicationDefaults
	public static void provideApplicationDefaults(MappedConfiguration<String, String> configuration){
		configuration.add(MongoSymbolConstants.HOST, "localhost");
		configuration.add(MongoSymbolConstants.PORT, "27017");
		configuration.add(MongoSymbolConstants.DBNAME, "bootstrap-tst");
	}
	
	public EntityFieldValueDecoder buildEntityFieldValueDecoder(ChainBuilder builder, List<EntityFieldValueDecoder> configuraition){
		return builder.build(EntityFieldValueDecoder.class, configuraition);
	}
	
	@Contribute(EntityFieldValueDecoder.class)
	public static void contributeFieldValueDecoderStrategies(OrderedConfiguration<EntityFieldValueDecoder> configuration, EntityFieldValueDecoder decoder){
		configuration.add("URI", new URIFieldValueDecoder(), "before:*");
		configuration.add("Collection", new CollectionFieldValueDecoder(decoder), "after:URI");
		configuration.add("Enum", new EnumFieldValueDecoder(), "after:Collection");
		configuration.add("UID", new UIDFieldValueDecoder(), "after:Enum");
		configuration.add("Default", new DefaultFieldValueDecoder(), "after:*");
	}
	
}
