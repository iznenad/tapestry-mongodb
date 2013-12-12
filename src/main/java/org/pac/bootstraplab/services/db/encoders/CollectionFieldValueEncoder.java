/*
 * $Id$
 */
package org.pac.bootstraplab.services.db.encoders;

import java.util.Collection;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.pac.bootstraplab.services.db.persist.EntityFieldValueEncoder;

import com.mongodb.BasicDBList;

public class CollectionFieldValueEncoder implements EntityFieldValueEncoder<Collection> {
	
	private final EntityFieldValueEncoder encoder;
	
	public CollectionFieldValueEncoder(EntityFieldValueEncoder entityFieldValueEncoder) {
		encoder = entityFieldValueEncoder;
	}
	
	@Override
	public Object encode(Collection fieldValue) {
		
		BasicDBList list = new BasicDBList(); 
		for(Object value : fieldValue){	
			list.add(encoder.encode(value));
		}
		
		return list;
	}

}

