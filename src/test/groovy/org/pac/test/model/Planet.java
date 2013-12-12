package org.pac.test.model;

import org.pac.bootstraplab.models.AbstractEntity;
import org.pac.bootstraplab.models.uid.UID;
import org.pac.bootstraplab.services.db.IdSpec;

/**
 * @author Nenad Nikolic (nenad.nikolic@net-m.de)
 *
 */
@IdSpec(type = "planet")
public class Planet implements AbstractEntity {

	private UID uid;

	private String name;
	private double gravity;
	private boolean atmosphere;
	private boolean water;
	private int orbitalPeriod;
	
	@Override
	public UID getUid() {
		return uid;
	}

	@Override
	public void setUid(UID uid) {
		this.uid = null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (atmosphere ? 1231 : 1237);
		long temp;
		temp = Double.doubleToLongBits(gravity);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		temp = Double.doubleToLongBits(orbitalPeriod);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((uid == null) ? 0 : uid.hashCode());
		result = prime * result + (water ? 1231 : 1237);
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
		Planet other = (Planet) obj;
		if (atmosphere != other.atmosphere)
			return false;
		if (Double.doubleToLongBits(gravity) != Double.doubleToLongBits(other.gravity))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Double.doubleToLongBits(orbitalPeriod) != Double.doubleToLongBits(other.orbitalPeriod))
			return false;
		if (uid == null) {
			if (other.uid != null)
				return false;
		} else if (!uid.equals(other.uid))
			return false;
		if (water != other.water)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PlanetEntity [uid=" + uid + ", name=" + name + ", gravity=" + gravity + ", atmosphere=" + atmosphere + ", water=" + water + ", orbitalPeriod="
				+ orbitalPeriod + "]";
	}

	
}

