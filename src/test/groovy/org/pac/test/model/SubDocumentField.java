/*
 * $Id$
 */
package org.pac.test.model;

public class SubDocumentField {

	private String myValue;
	
	public SubDocumentField() {
		myValue = "read me!";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((myValue == null) ? 0 : myValue.hashCode());
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
		SubDocumentField other = (SubDocumentField) obj;
		if (myValue == null) {
			if (other.myValue != null)
				return false;
		} else if (!myValue.equals(other.myValue))
			return false;
		return true;
	}
	
	
}

