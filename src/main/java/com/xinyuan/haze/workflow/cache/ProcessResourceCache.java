package com.xinyuan.haze.workflow.cache;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ProcessResourceCache {

	public static Map<String,Object> resources = new HashMap<String,Object>();
	
	public Map<String, Object> addProcessResource(String deploymentId, String resourceName, InputStream inputStream) {
		//resources.put(deploymentId, value);
		return resources;
	}
}
