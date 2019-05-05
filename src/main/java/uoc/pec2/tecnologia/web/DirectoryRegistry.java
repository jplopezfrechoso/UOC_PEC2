package uoc.pec2.tecnologia.web;

import java.util.HashMap;
import java.util.Map;

public class DirectoryRegistry {
	
	private static final DirectoryRegistry INSTANCE = new DirectoryRegistry();
	
	private Map<String, String> registry = new HashMap<>();
	
	public static DirectoryRegistry getInstance() {
		return DirectoryRegistry.INSTANCE;
	}
	
	public void addToRegistry(String key, String path) {
		this.registry.put(key, path);
	}
	
	public String getPath(String key) {
		return this.registry.get(key);
	}
}
