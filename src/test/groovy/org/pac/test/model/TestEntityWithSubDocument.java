/*
 * $Id$
 */
package org.pac.test.model;

import org.pac.bootstraplab.models.AbstractEntity;
import org.pac.bootstraplab.models.uid.UID;
import org.pac.bootstraplab.services.db.SubDocument;

public class TestEntityWithSubDocument implements AbstractEntity {

	private UID uid;
	
	@SubDocument
	private SubDocumentField goIntoField;
	
	public TestEntityWithSubDocument() {
		this.uid = new UID() {
			
			@Override
			public String getType() {
				return "test";
			}
			
			@Override
			public String getIdentifier() {
				return "identifier";
			}
			
			@Override
			public String getFully() {
				return "test-identifier";
			}
		};
		this.goIntoField = new SubDocumentField();
	}

	@Override
	public UID getUid() {
		return uid;
	}

	@Override
	public void setUid(UID uid) {
		
	}
	
	@Override
	public String toString() {
		return "TestEntityWithGoIntoField [uid=" + uid + ", goIntoField=" + goIntoField + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((goIntoField == null) ? 0 : goIntoField.hashCode());
		result = prime * result + ((uid == null) ? 0 : uid.hashCode());
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
		TestEntityWithSubDocument other = (TestEntityWithSubDocument) obj;
		if (goIntoField == null) {
			if (other.goIntoField != null)
				return false;
		} else if (!goIntoField.equals(other.goIntoField))
			return false;
		if (uid == null) {
			if (other.uid != null)
				return false;
		} else if (!uid.equals(other.uid))
			return false;
		return true;
	}
	
	
}

