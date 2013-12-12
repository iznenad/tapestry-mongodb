package org.pac.bootstraplab.services.db;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * Explain the generator what to what type to genarate the value
 * 
 * Type should be a Class of the type from the Collection. 
 * 
 * @author Vladimir Spasic (vladimir.spasic.86@gmail.com)
 *
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface CollectionSpec {

	public Class type();
	
}
