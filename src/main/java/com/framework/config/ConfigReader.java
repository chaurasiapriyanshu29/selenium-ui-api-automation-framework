package com.framework.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigReader {

	private static final Properties PROPS = new Properties();

	static {
		load("config/config.properties");
		load("config/api.properties");
	}

	private ConfigReader() {
	}

	private static void load(String file) {
		try (InputStream is = ConfigReader.class.getClassLoader().getResourceAsStream(file)) {
			if (is == null)
				throw new IllegalStateException("Properties file not found: " + file);
			PROPS.load(is);
		} catch (IOException e) {
			throw new IllegalStateException("Cannot load " + file, e);
		}
	}

	public static String get(String key) {
		String sys = System.getProperty(key);
		return (sys != null && !sys.isBlank() && !sys.startsWith("${")) ? sys : PROPS.getProperty(key);
	}

	public static int getInt(String key) {
		return Integer.parseInt(get(key).trim());
	}

	public static boolean getBoolean(String key) {
		return Boolean.parseBoolean(get(key).trim());
	}
}
