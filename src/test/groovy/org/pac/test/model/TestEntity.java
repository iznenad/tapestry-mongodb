package org.pac.test.model;

import org.pac.bootstraplab.models.AbstractEntity;
import org.pac.bootstraplab.models.uid.AbstractUID;
import org.pac.bootstraplab.models.uid.UID;

public class TestEntity implements AbstractEntity{

	private UID uid;
	
	private Object nullValue;
	
	public TestEntity() {
		this.uid = new AbstractUID() {
			
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
	}

	@Override
	public UID getUid() {
		// TODO Auto-generated method stub
		return uid;
	}

	@Override
	public void setUid(UID uid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toString() {
		return "TestEntity [uid=" + uid + ", nullValue=" + nullValue + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nullValue == null) ? 0 : nullValue.hashCode());
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
		TestEntity other = (TestEntity) obj;
		if (nullValue == null) {
			if (other.nullValue != null)
				return false;
		} else if (!nullValue.equals(other.nullValue))
			return false;
		if (uid == null) {
			if (other.uid != null)
				return false;
		} else if (!uid.equals(other.uid))
			return false;
		return true;
	}

	
}
