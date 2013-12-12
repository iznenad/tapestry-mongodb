package org.pac.test.modules;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.services.ChainBuilder;
import org.apache.tapestry5.ioc.services.StrategyBuilder;
import org.pac.bootstraplab.models.uid.UID;
import org.pac.bootstraplab.services.db.EntityIdGenerator;
import org.pac.bootstraplab.services.db.decoders.CollectionFieldValueDecoder;
import org.pac.bootstraplab.services.db.decoders.UIDFieldValueDecoder;
import org.pac.bootstraplab.services.db.decoders.URIFieldValueDecoder;
import org.pac.bootstraplab.services.db.encoders.CollectionFieldValueEncoder;
import org.pac.bootstraplab.services.db.encoders.UidFieldValueEncoder;
import org.pac.bootstraplab.services.db.encoders.UriFieldValueEncoder;
import org.pac.bootstraplab.services.db.fetch.DefaultFieldValueDecoder;
import org.pac.bootstraplab.services.db.fetch.EntityFieldValueDecoder;
import org.pac.bootstraplab.services.db.fetch.EntityGenerator;
import org.pac.bootstraplab.services.db.persist.DBObjectGenerator;
import org.pac.bootstraplab.services.db.persist.DefaultFieldValueEncoder;
import org.pac.bootstraplab.services.db.persist.EntityFieldValueEncoder;

public class DBTestModule {
	
	public static void bind(ServiceBinder binder) {
		binder.bind(DBObjectGenerator.class);
		binder.bind(EntityGenerator.class);
		binder.bind(EntityIdGenerator.class);
	}
	
	public EntityFieldValueEncoder buildEntityFieldValueExtractor(StrategyBuilder builder, Map<Class, EntityFieldValueEncoder> configuraition){
		return builder.build(EntityFieldValueEncoder.class, configuraition);
	}
	
	@Contribute(EntityFieldValueEncoder.class)
	public static void contributeFieldValueExtractorStrategies(MappedConfiguration<Class, EntityFieldValueEncoder> configuraition,  EntityFieldValueEncoder encoder){
		configuraition.add(Object.class, new DefaultFieldValueEncoder());
		configuraition.add(URI.class, new UriFieldValueEncoder());
		configuraition.add(java.util.Collection.class, new CollectionFieldValueEncoder(encoder));
		configuraition.add(UID.class, new UidFieldValueEncoder());
		
	}
	
	public EntityFieldValueDecoder buildEntityFieldValueDecoder(ChainBuilder builder, List<EntityFieldValueDecoder> configuraition){
		return builder.build(EntityFieldValueDecoder.class, configuraition);
	}
	
	@Contribute(EntityFieldValueDecoder.class)
	public static void contributeFieldValueDecoderStrategies(OrderedConfiguration<EntityFieldValueDecoder> configuration, EntityFieldValueDecoder decoder){
		
		configuration.add("URI", new URIFieldValueDecoder(), "before:*");
		configuration.add("UID", new UIDFieldValueDecoder(), "before:*");
		configuration.add("Collection", new CollectionFieldValueDecoder(decoder), "before:Default");
		configuration.add("Default", new DefaultFieldValueDecoder(), "after:*");
	}
}
