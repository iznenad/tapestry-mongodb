package org.pac.bootstraplab.services.db.encoders;

import org.pac.bootstraplab.services.db.persist.EntityFieldValueEncoder;

public class EnumFieldValueEncoder implements EntityFieldValueEncoder<Enum>{

	@Override
	public Object encode(Enum fieldValue) {
		return fieldValue.name();
	}
	
}
