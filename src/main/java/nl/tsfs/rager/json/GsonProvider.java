package nl.tsfs.rager.json;


import com.google.gson.*;


public class GsonProvider {
	private InstanceProvider instanceProvider;
	
	private GsonBuilder builder;
	private Gson gson;
	
	
	public GsonProvider() {
		instanceProvider = new InstanceProvider();
		
		builder = new GsonBuilder();
		gson = null;
	}
	
	
	public void load(Object instance, JsonObject json) {
		if(gson != null) {
			instanceProvider.setInstance(instance);
			
			gson.fromJson(json, instance.getClass());
		}
		else {
			throw new IllegalStateException();
		}
	}
	
	public JsonObject save(Object instance) {
		JsonElement json = gson.toJsonTree(instance);
		
		if(json.isJsonObject()) {
			return json.getAsJsonObject();
		}
		else {
			throw new IllegalArgumentException();
		}
	}
	
	
	public void register(Class<?> type) {
		builder.registerTypeAdapter(type, instanceProvider);
	}
	
	public void create() {
		// TODO Add any JSON building/parsing parameters.
		gson = builder.create();
	}
	
	
	public Gson getGson() {
		if(gson != null) {
			return gson;
		}
		else {
			throw new IllegalStateException();
		}
	}
}
