package nl.tsfs.rager.json;


import java.lang.reflect.*;

import com.google.gson.*;

import nl.tsfs.rager.model.*;


/**
 * Based on https://stackoverflow.com/questions/5800433/polymorphism-with-gson.
 */
public class ActionAdapter implements JsonSerializer<Action>, JsonDeserializer<Action> {
	private static final String ActionPackageSimpleName = "action";
	
	
	private Gson gson;
	
	
	public ActionAdapter() {
		gson = new Gson();
	}
	
	
	@Override
	public JsonElement serialize(Action action, Type type, JsonSerializationContext context) {
		// Prevent infinite recursion by using another serializer.
		return gson.toJsonTree(action);
	}
	
	@Override
	public Action deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
		// Read the class name from the JSON and use that to enable polymorphism.
		JsonObject object = json.getAsJsonObject();
		
		String simpleName = object.get("type").getAsString();
		String className = Action.class.getPackageName() + "." + ActionPackageSimpleName + "." + simpleName;
		
		try {
			Class<?> actionClass = Class.forName(className);
			
			// Prevent infinite recursion by using another deserializer.
			return (Action) gson.fromJson(json, actionClass);
		}
		catch(ClassNotFoundException exception) {
			throw new JsonParseException(exception);
		}
	}
}
