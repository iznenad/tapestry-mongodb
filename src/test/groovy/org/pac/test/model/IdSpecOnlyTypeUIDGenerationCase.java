/*
 * $Id$
 */
package org.pac.test.model;

import org.pac.bootstraplab.models.AbstractEntity;
import org.pac.bootstraplab.models.uid.UID;
import org.pac.bootstraplab.services.db.IdSpec;

@IdSpec(type = "TEST")
public class IdSpecOnlyTypeUIDGenerationCase implements AbstractEntity{
	
	private UID uid;

	@Override
	public UID getUid() {
		return uid;
	}

	@Override
	public void setUid(UID uid) {
		this.uid = uid;
	}

}
