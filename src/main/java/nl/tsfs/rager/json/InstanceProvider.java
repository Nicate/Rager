package nl.tsfs.rager.json;

import java.lang.reflect.Type;

import com.google.gson.InstanceCreator;

public class InstanceProvider implements InstanceCreator<Object> {
	private Object instance;
	
	
	InstanceProvider() {
		instance = null;
	}
	
	
	@Override
	public Object createInstance(Type type) {
		return instance;
	}
	
	
	void setInstance(Object instance) {
		this.instance = instance;
	}
}
