package nl.tsfs.rager.model;


import java.io.*;
import java.lang.reflect.*;
import java.util.*;


abstract class Persistable implements Serializable {
	private static final long serialVersionUID = 7375437118810828229L;
	
	
	private transient ArrayList<Field> persistableFields;
	
	
	Persistable() {
		persistableFields = new ArrayList<>();
		
		addPersistableFields();
	}
	
	
	private void addPersistableFields() {
		Class<?> c = getClass();
		
		while(!c.equals(Persistable.class)) {
			addPersistableFields(c);
			
			c = c.getSuperclass();
		}
	}
	
	private void addPersistableFields(Class<?> c) {
		for(Field field : c.getDeclaredFields()) {
			int modifiers = field.getModifiers();
			
			boolean isStatic = Modifier.isStatic(modifiers);
			boolean isTransient = Modifier.isTransient(modifiers);
			boolean isPersistable = Persistable.class.isAssignableFrom(field.getType());
			
			if(!isStatic && !isTransient && isPersistable) {
				// Allow persistable members to be private.
				field.setAccessible(true);
				
				persistableFields.add(field);
			}
		}
	}
	
	private Persistable getPersistable(Field persistableField) {
		try {
			return (Persistable) persistableField.get(this);
		}
		catch(IllegalAccessException exception) {
			// This shouldn't happen.
			throw new Error();
		}
	}
	
	
	void load() {
		for(Field persistableField : persistableFields) {
			// Retrieve the most recent instance.
			Persistable persistable = getPersistable(persistableField);
			
			persistable.load();
		}
	}
	
	void save() {
		for(Field persistableField : persistableFields) {
			// Retrieve the most recent instance.
			Persistable persistable = getPersistable(persistableField);
			
			persistable.save();
		}
	}
}
