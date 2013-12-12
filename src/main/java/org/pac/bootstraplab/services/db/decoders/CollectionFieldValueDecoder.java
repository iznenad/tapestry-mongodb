/*
 * $Id$
 */
package org.pac.bootstraplab.services.db.decoders;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.pac.bootstraplab.services.db.fetch.EntityFieldValueDecoder;

import com.mongodb.BasicDBList;

public class CollectionFieldValueDecoder implements EntityFieldValueDecoder<Collection> {

	private final EntityFieldValueDecoder decoder;
	
	public CollectionFieldValueDecoder(EntityFieldValueDecoder entityFieldValueDecoder) {
		decoder = entityFieldValueDecoder;
	}
	
	@Override
	public Collection decode(Class spec, Object value) {

		if (!spec.equals(List.class))
			return null;
		if (!(value instanceof BasicDBList))
			return null;

		List<Object> list = new ArrayList<>();

		for (Object object : ((BasicDBList) value).toArray()) {
			
			if(object != null)
				list.add(decoder.decode(object.getClass(), object));
			else
				list.add(object);
		}

		return list;
	}

}
