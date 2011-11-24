package org.github.webcorpus.web.jstl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Functions
{
	private static Map<String, Map<String, String>> locales;
	static
	{
		try
		{
			locales = createLocales();
		}catch(IOException e)
		{
			throw new RuntimeException(e);
		}
	}	

	private static Map<String, Map<String, String>> createLocales() throws IOException
	{
		final Map<String, Map<String, String>> locales = new HashMap<String, Map<String,String>>();
		locales.put("en_us", createLocationMap(Functions.class.getResourceAsStream("en_us.properties")));
		locales.put("pt_br", createLocationMap(Functions.class.getResourceAsStream("pt_br.properties")));
		return locales;
	}

	private static Map<String, String> createLocationMap(final InputStream is) throws IOException
	{
		final Map<String, String> current = new HashMap<String, String>();
		final BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		try
		{
			for(String line = reader.readLine(); line != null; line = reader.readLine())
			{
				final String[] tokens = line.trim().split("=");
				current.put(tokens[0], tokens[1]);
			}
		}finally
		{
			if(reader != null)
				reader.close();
		}
		return current;
	}

	public static String message(String key)
	{
		return locales.get("pt_br").get(key);
	}

}
