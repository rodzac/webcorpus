package org.github.webcorpus.web;

import java.util.List;


public class JsonBuilder
{
	private final StringBuilder content;
	
	public JsonBuilder()
	{
		content = new StringBuilder();
	}

	//----------->json<-----------
	public JsonBuilder add(String name, String value)
	{
		appendComma();
		if(value == null)
			content.append(createName(name)).append(":null");
		else
			content.append(createName(name)).append(":\"").append(escapeQuote(escapeBackSlash(new StringBuilder(value)))).append("\"");
		return this;
	}
	
	public JsonBuilder add(String name, int value)
	{
		appendComma();
		content.append(createName(name)).append(":").append(value);
		return this;
	}

	public JsonBuilder add(String name, Object value)
	{
		appendComma();
		content.append(createName(name)).append(":").append(value);
		return this;
	}
	
	public JsonBuilder add(String name, Object[] values)
	{
		appendComma();
		content.append(createName(name)).append(":").append(toJsonArray(values)).append("");
		return this;
	}

	public JsonBuilder add(String name, List<Object> values)
	{
		appendComma();
		content.append(createName(name)).append(":").append(toJsonArray(values.toArray())).append("");
		return this;
	}

	private String createName(String name)
	{
		return new StringBuilder("\"").append(name).append("\"").toString();
	}
	
	public String build()
	{
		return new StringBuilder("{").append(content.toString()).append("}").toString();
	}

	@Override
	public String toString()
	{
		return build();
	}
	
	//----------->misc<-----------
	private void appendComma()
	{
		if(content.length() > 0)
			content.append(", ");
	}

	private String toJsonArray(Object[] values)
	{
		if(values == null)
			return "null";
		final StringBuilder content = new StringBuilder("[ ");
		for(Object value : values)
			content.append(value).append(",");
		return content.replace(content.length() - 1, content.length(), " ]").toString();
	}
	
	private StringBuilder escapeBackSlash(StringBuilder content)
	{
		return escape(escape(escape(content, "\n", "\\n"), "\t", "\\t"), "\r", "\\r");
	}
	
	private StringBuilder escapeQuote(StringBuilder content)
	{
		return escape(content, "\"", "\\\"");
	}

	private StringBuilder escape(StringBuilder content, String replaced, String replacedBy)
	{
		int position = -1;
		while((position = content.indexOf(replaced, position)) > -1)
		{
			content.replace(position, position + 1, replacedBy);
			position += replacedBy.length();
		}
		return content;
	}

}