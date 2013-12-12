/*
 * $Id$
 */
package org.pac.test.model;

import java.util.Arrays;
import java.util.List;

import org.pac.bootstraplab.models.AbstractEntity;
import org.pac.bootstraplab.models.uid.UID;
import org.pac.bootstraplab.services.db.IdSpec;
@IdSpec(type = "Collection")
public class CollectionTestEntity implements AbstractEntity {

	private static enum Browsers {
		CHROME, FIREFOX
	} 
	private List<Browsers> browsers = Arrays.asList(Browsers.CHROME, Browsers.FIREFOX); 
	
	@Override
	public UID getUid() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setUid(UID uid) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String toString() {
		return "CollectionTestEntity [browsers=" + browsers + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((browsers == null) ? 0 : browsers.hashCode());
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
		CollectionTestEntity other = (CollectionTestEntity) obj;
		if (browsers == null) {
			if (other.browsers != null)
				return false;
		} else if (!browsers.equals(other.browsers))
			return false;
		return true;
	}

	
}

