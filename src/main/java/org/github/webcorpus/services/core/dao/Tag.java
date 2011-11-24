package org.github.webcorpus.services.core.dao;

public class Tag
{
	private final String name;
	private final String label;
	private final String color;

	public Tag(final String name, final String label, final String color)
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
