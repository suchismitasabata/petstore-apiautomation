package com.petstore.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtiles {
	Properties prop;
	public PropertiesUtiles(String filePath){
		try {
		FileReader reader = new FileReader(filePath);
		prop = new Properties();
		prop.load(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Object getValue(String key) {
		return prop.get(key);
	}

	
}
