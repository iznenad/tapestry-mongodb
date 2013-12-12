/*
 * $Id$
 */
package org.pac.test.model;

import java.net.URI;

import org.pac.bootstraplab.models.AbstractEntity;
import org.pac.bootstraplab.models.uid.UID;

public class URITestEntity implements AbstractEntity {

	private URI uri = URI.create("http://bla.bla.com");

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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uri == null) ? 0 : uri.hashCode());
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
		URITestEntity other = (URITestEntity) obj;
		if (uri == null) {
			if (other.uri != null)
				return false;
		} else if (!uri.equals(other.uri))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "URITestEntity [uri=" + uri + "]";
	}

}
