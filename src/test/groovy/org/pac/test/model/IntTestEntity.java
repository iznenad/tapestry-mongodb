package org.pac.test.model;

import org.bson.types.ObjectId;
import org.pac.bootstraplab.models.AbstractEntity;
import org.pac.bootstraplab.models.uid.UID;
import org.pac.bootstraplab.services.db.IdSpec;

/**
 * @author Nenad Nikolic (nenad.nikolic@net-m.de)
 *
 */
@IdSpec(type = "INTTESTENTITY", identifierFields = {"number"})
public class IntTestEntity implements AbstractEntity{

	private ObjectId _id;
	
	private UID uid;
	private int number = 3;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + number;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IntTestEntity other = (IntTestEntity) obj;
		if (number != other.number)
			return false;
		return true;
	}

	@Override
	public UID getUid() {
			
		return uid;
	}

	@Override
	public void setUid(UID uid) {
		this.uid = uid;
	}

	@Override
	public String toString() {
		return "IntTestEntity [_id=" + _id + ", uid=" + uid + ", number=" + number + "]";
	}

}

