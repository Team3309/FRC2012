/*
 * Copyright (C) 2012 Friarbots FRC Team 3309
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at 
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */

package org.team3309.properties;

import java.io.IOException;
import java.io.InputStreamReader;

import javax.microedition.io.Connector;

import com.sun.squawk.io.BufferedReader;
import com.sun.squawk.microedition.io.FileConnection;
import com.sun.squawk.util.SquawkHashtable;

/**
 * Properties is a class that allows you to read key-value pairs from the cRIO's
 * filesystem. This allows you to change values (such as motor speeds) without
 * editing code, recompiling, and rebooting the cRIO. Properties follows the
 * Singleton pattern, and by default reads key-value pairs from \/properties.txt
 * You can edit this file over FTP. An example of a key-value pair is:
 * balanceSpeed=.25 Key-value pairs are separated by new lines You must call
 * {@link #reload()} if you want to reload properties after
 * {@link #getInstance()} was called
 * 
 * @author vmagro
 * 
 */
public class Properties {

	private static Properties instance = null;

	private static String sPath = "/properties.txt";

	private SquawkHashtable map = new SquawkHashtable();

	/**
	 * Gets the single instance of Properties
	 * 
	 * @return the instance of Properties
	 */
	public static Properties getInstance() {
		if (instance == null)
			try {
				instance = new Properties();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return instance;
	}

	/**
	 * Changes the path that the properties file is stored in
	 * 
	 * @param path
	 *            the path to the file that you want to read in
	 */
	public static void setPath(String path) {
		if (instance != null)
			throw new IllegalArgumentException(
					"Properties instance already created!\nUse getInstance() to use it");
		sPath = path;
	}

	/**
	 * Private constructor for Properties that reads in the keys and values
	 * 
	 * @param path
	 * @throws IOException
	 */
	private Properties() throws IOException {
		reload();
	}

	/**
	 * Reloads the properties from the file after {@link #getInstance()} is
	 * called for the first time You do not need to call this method unless you
	 * want to reload properties without rebooting
	 * 
	 * @throws IOException
	 */
	public void reload() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					((FileConnection) Connector.open("file://" + sPath))
							.openInputStream()));
			String line = null;
			while ((line = in.readLine()) != null) {
				String key = line.substring(0, line.indexOf("="));
				String value = line.substring(line.indexOf("=") + 1);
				map.put(key, value);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Gets a property
	 * 
	 * @param key
	 *            name of the value
	 * @return Object that is the value for the key
	 * @throws PropertiesException
	 */
	public Object get(String key) throws PropertiesException {
		Object obj = map.get(key);
		if (obj == null)
			throw new PropertiesException("Property (" + key
					+ ") was not found");
		return obj;
	}

	/**
	 * Gets a String property
	 * 
	 * @param key
	 *            name of the value
	 * @return String that is the value for the key
	 * @throws PropertiesException
	 */
	public String getString(String key) throws PropertiesException {
		return get(key).toString();
	}

	/**
	 * Gets a double property
	 * 
	 * @param key
	 *            name of the value
	 * @return double that is the value for the key
	 * @throws PropertiesException
	 */
	public double getDouble(String key) throws PropertiesException {
		return Double.parseDouble(getString(key));
	}

	/**
	 * Gets an int property
	 * 
	 * @param key
	 *            name of the value
	 * @return int that is the value for the key
	 * @throws PropertiesException
	 */
	public int getInt(String key) throws PropertiesException {
		return Integer.parseInt(getString(key));
	}

	/**
	 * Gets a long property
	 * 
	 * @param key
	 *            name of the value
	 * @return long that is the value for the key
	 * @throws PropertiesException
	 */
	public long getLong(String key) throws PropertiesException {
		return Long.parseLong(getString(key));
	}

	/**
	 * Gets a byte property
	 * 
	 * @param key
	 *            name of the value
	 * @return byte that is the value for the key
	 * @throws PropertiesException
	 */
	public byte getByte(String key) throws PropertiesException {
		return Byte.parseByte(getString(key));
	}

	/**
	 * Gets a property if it exists, otherwise returns the default value
	 * 
	 * @param key
	 *            name of the value
	 * @param def
	 *            default value
	 * @return property if it exists, def if it does not
	 */
	public Object opt(String key, Object def) {
		Object obj;
		try {
			obj = get(key);
		} catch (PropertiesException e) {
			e.printStackTrace();
			return def;
		}
		return obj;
	}

	/**
	 * Gets a String property if it exists, otherwise returns the default value
	 * 
	 * @param key
	 *            name of the value
	 * @param def
	 *            default value
	 * @return property if it exists, def if it does not
	 */
	public String optString(String key, String def) {
		return opt(key, def).toString();
	}

	/**
	 * Gets a double property if it exists, otherwise returns the default value
	 * 
	 * @param key
	 *            name of the value
	 * @param def
	 *            default value
	 * @return property if it exists, def if it does not
	 */
	public double optDouble(String key, double def) {
		return Double.parseDouble(optString(key, String.valueOf(def)));
	}

	/**
	 * Gets an int property if it exists, otherwise returns the default value
	 * 
	 * @param key
	 *            name of the value
	 * @param def
	 *            default value
	 * @return property if it exists, def if it does not
	 */
	public int optInt(String key, int def) {
		return Integer.parseInt(optString(key, String.valueOf(def)));
	}

	/**
	 * Gets a long property if it exists, otherwise returns the default value
	 * 
	 * @param key
	 *            name of the value
	 * @param def
	 *            default value
	 * @return property if it exists, def if it does not
	 */
	public long optLong(String key, long def) {
		return Long.parseLong(optString(key, String.valueOf(def)));
	}

	/**
	 * Gets a byte property if it exists, otherwise returns the default value
	 * 
	 * @param key
	 *            name of the value
	 * @param def
	 *            default value
	 * @return property if it exists, def if it does not
	 */
	public byte optByte(String key, byte def) {
		return Byte.parseByte(optString(key, String.valueOf(def)));
	}

	/**
	 * Gets a property if it exists, otherwise returns a predefined default
	 * value
	 * 
	 * @param key
	 * @return the property if it exists, or null if it does not
	 */
	public Object opt(String key) {
		return opt(key, null);
	}

	/**
	 * Gets a String property if it exists, otherwise returns a predefined
	 * default value
	 * 
	 * @param key
	 * @return the property if it exists, or an empty String if it does not
	 */
	public String optString(String key) {
		return optString(key, "");
	}

	/**
	 * Gets a double property if it exists, otherwise returns a predefined
	 * default value
	 * 
	 * @param key
	 * @return the property if it exists, or 0 if it does not
	 */
	public double optDouble(String key) {
		return optDouble(key, 0);
	}

	/**
	 * Gets a int property if it exists, otherwise returns a predefined default
	 * value
	 * 
	 * @param key
	 * @return the property if it exists, or 0 if it does not
	 */
	public int optInt(String key) {
		return optInt(key, 0);
	}

	/**
	 * Gets a long property if it exists, otherwise returns a predefined default
	 * value
	 * 
	 * @param key
	 * @return the property if it exists, or 0 if it does not
	 */
	public long optLong(String key) {
		return optLong(key, 0);
	}

	/**
	 * Gets a byte property if it exists, otherwise returns a predefined default
	 * value
	 * 
	 * @param key
	 * @return the property if it exists, or 0 if it does not
	 */
	public byte optByte(String key) {
		return optByte(key, (byte) 0);
	}
}
