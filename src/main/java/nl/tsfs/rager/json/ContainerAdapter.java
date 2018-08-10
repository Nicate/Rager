package nl.tsfs.rager.json;


import java.lang.reflect.*;

import com.google.gson.*;

import nl.tsfs.rager.model.*;


public class ContainerAdapter<S extends Container<T>, T> implements JsonSerializer<S>, JsonDeserializer<S> {
	private static final String DefaultTypeName = "type";
	
	
	private Class<S> containerClass;
	private Class<T> containedClass;
	
	private String packageName;
	private String typeName;
	
	
	public ContainerAdapter(Class<S> containerClass, Class<T> containedClass, String packageName, String typeName) {
		this.containerClass = containerClass;
		this.containedClass = containedClass;
		
		this.packageName = packageName;
		this.typeName = typeName;
	}
	
	public ContainerAdapter(Class<S> containerClass, Class<T> containedClass, String packageName) {
		this(containerClass, containedClass, packageName, DefaultTypeName);
	}
	
	public ContainerAdapter(Class<S> containerClass, Class<T> containedClass) {
		this(containerClass, containedClass, null, null);
	}
	
	
	@Override
	public JsonElement serialize(S container, Type type, JsonSerializationContext context) {
		// Pull the array elements through the interface.
		JsonArray array = new JsonArray();
		
		for(T contained : container) {
			JsonElement element = context.serialize(contained);
			
			array.add(element);
		}
		
		return array;
	}
	
	@Override
	public S deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
		S container;
		
		// The container constructor may be private.
		try {
			Constructor<S> constructor = containerClass.getDeclaredConstructor();
			
			constructor.setAccessible(true);
			
			container = constructor.newInstance();
		}
		catch(Exception exception) {
			throw new JsonParseException(exception);
		}
		
		// Push the array elements through the interface.
		JsonArray array = json.getAsJsonArray();
		
		for(JsonElement element : array) {
			Type containedType = containedClass;
			
			// Provide standardized support for polymorphism in containers.
			JsonObject object = element.getAsJsonObject();
			
			if(packageName != null && typeName != null && object.has(typeName)) {
				String className = object.get(typeName).getAsString();
				
				if(!packageName.isEmpty()) {
					className = packageName + "." + className;
				}
				
				try {
					Class<?> classToken = Class.forName(className);
					
					if(containedClass.isAssignableFrom(classToken)) {
						containedType = classToken;
					}
				}
				catch(ClassNotFoundException exception) {
					throw new JsonParseException(exception);
				}
			}
			
			// Deserialize either the general case or the specific case.
			T contained = context.deserialize(element, containedType);
			
			container.contain(contained);
		}
		
		return container;
	}
}
