package org.github.webcorpus.services;

public class TagInfo
{
	private final String name;
	private final String label;
	private final String color;

	public TagInfo(String name, String label, String color)
	{
		this.name = name;
		this.label = label;
		this.color = color;
	}

	public String getName()
	{
		return name;
	}

	public String getLabel()
	{
		return label;
	}

	public String getColor()
	{
		return color;
	}

}
