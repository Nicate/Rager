package nl.tsfs.rager.model;


import java.io.*;
import java.lang.reflect.*;
import java.util.*;


abstract class Persistable implements Serializable {
	private static final long serialVersionUID = 7375437118810828229L;
	
	
	private transient ArrayList<Field> fields;
	
	
	Persistable() {
		fields = new ArrayList<>();
		
		addFields();
	}
	
	
	private void addFields() {
		Class<?> c = getClass();
		
		while(!c.equals(Persistable.class)) {
			addFields(c);
			
			c = c.getSuperclass();
		}
	}
	
	private void addFields(Class<?> c) {
		for(Field field : c.getDeclaredFields()) {
			int modifiers = field.getModifiers();
			Class<?> type = field.getType();
			
			boolean isStatic = Modifier.isStatic(modifiers);
			boolean isTransient = Modifier.isTransient(modifiers);
			
			boolean isPersistable = Persistable.class.isAssignableFrom(type);
			boolean isPersistableArray = type.isArray() && Persistable.class.isAssignableFrom(type.getComponentType());
			
			boolean isCollection = Collection.class.isAssignableFrom(type);
			boolean isMap = Map.class.isAssignableFrom(type);
			
			if(!isStatic && !isTransient) {
				if(isPersistable || isPersistableArray || isCollection || isMap) {
					// Allow (potentially) persistable members to be private.
					field.setAccessible(true);
					
					fields.add(field);
				}
			}
		}
	}
	
	
	private Object getObject(Field field) {
		try {
			return field.get(this);
		}
		catch(IllegalAccessException exception) {
			// This shouldn't happen.
			throw new Error();
		}
	}
	
	
	private List<Persistable> findPersistables() {
		ArrayList<Persistable> persistables = new ArrayList<>();
		
		for(Field field : fields) {
			// Retrieve the most recent instance.
			Object object = getObject(field);
			
			findPersistables(object, persistables);
		}
		
		return persistables;
	}
	
	private void findPersistables(Object object, List<Persistable> persistables) {
		if(object instanceof Persistable) {
			Persistable persistable = (Persistable) object;
			
			persistables.add(persistable);
		}
		
		if(object instanceof Persistable[]) {
			Persistable[] persistableArray = (Persistable[]) object;
			
			for(Persistable persistable : persistableArray) {
				persistables.add(persistable);
			}
		}
		
		// This is not an else case, since technically persistables can also be collections.
		if(object instanceof Collection<?>) {
			Collection<?> collection = (Collection<?>) object;
			
			for(Object element : collection) {
				findPersistables(element, persistables);
			}
		}
		
		// This is not an else case, since technically persistables can also be maps.
		if(object instanceof Map<?, ?>) {
			Map<?, ?> map = (Map<?, ?>) object;
			
			for(Object value : map.values()) {
				findPersistables(value, persistables);
			}
		}
	}
	
	
	void load() {
		List<Persistable> persistables = findPersistables();
		
		for(Persistable persistable : persistables) {
			persistable.load();
		}
	}
	
	void save() {
		List<Persistable> persistables = findPersistables();
		
		for(Persistable persistable : persistables) {
			persistable.save();
		}
	}
}
