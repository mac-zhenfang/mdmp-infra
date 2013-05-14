package com.mdmp.infra.operator.sql.udaf;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cisco.ramp.api.report.DirectoryTraverser;

public class UDAFMapping {
	private static final String GENERATOR_CLZ = UDAFMapping.class.getName();
	private static Map<String, Class<UDAF>> udafMapping = new HashMap<String, Class<UDAF>>();

	static {
		initUDAFMapping();
	}

	@SuppressWarnings({ "unchecked" })
	private static void initUDAFMapping() {
		DirectoryTraverser traverser = DirectoryTraverser.getInstance();
		List<String> classes = traverser.traverse(traverser.getRootPath(
				GENERATOR_CLZ, 1));
		String workerRoot = GENERATOR_CLZ.substring(0,
				GENERATOR_CLZ.lastIndexOf("."));
		for (String name : classes) {
			name = name.replace(File.separator, ".").split(workerRoot)[1];
			name = name.substring(0, name.lastIndexOf("."));
			name = workerRoot + name;
			try {
				Class<?> clazz = Class.forName(name);
				if (clazz.isInterface() || !UDAF.class.isAssignableFrom(clazz)) {
					continue;
				}
				udafMapping.put(clazz.getSimpleName().toLowerCase(), (Class<UDAF>) clazz);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	public static Set<String> getUDAFNameList() {
		return udafMapping.keySet();
	}

	public static UDAF createUDAFInstance(String udafName) throws InstantiationException, IllegalAccessException {
		Class<UDAF> UDAFClass = udafMapping.get(udafName.toLowerCase());
		if (UDAFClass == null) {
			return null;
		}
		return UDAFClass.newInstance();
	}
}
