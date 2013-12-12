package org.pac.bootstraplab.models.uid;


public abstract class AbstractUID implements UID {

	@Override
	public boolean equals(Object obj) {
		
		if(obj == null) return false;
		
		if(!(obj instanceof UID)){
			return false;
		}

		return getFully().equals(((UID) obj).getFully());
	}
	
	@Override
	public String toString() {
		return getFully();
	}
	
}

