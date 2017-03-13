package com.teriyakiegg.lbs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Load properties file to use
 * 
 * @author teriyakiegg
 *
 */
public class PropertiesLoader {

	protected Properties properties;

	public PropertiesLoader(String propertiesPath) {

		properties = new Properties();

		try {
			FileInputStream fis = new FileInputStream(new File(propertiesPath));
			properties.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected String getProperty (String key) {
		return properties.getProperty(key);
	}
}
