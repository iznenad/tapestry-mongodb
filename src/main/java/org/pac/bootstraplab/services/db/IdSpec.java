package org.pac.bootstraplab.services.db;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 
 * Explain the generator what to use for UID generation.
 * Type should be a string which explains what domain model this UID is generated for.
 * Example Theme could have type THM or something similar
 * 
 * Identifier fields are names of fields on the model whose values should be used when generating the unique identifier.
 * 
 * For example for a user the only relevant identifier field would be username. Use the generated field constants when defining these values.
 * 
 * @author Nenad Nikolic (nenad.nikolic@net-m.de)
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface IdSpec {

	public String type() default "";
	
	public String [] identifierFields() default { };
	
}

