package org.github.webcorpus.services;

public class DocumentInfo
{
	private final String id;
	private final String text;

	public DocumentInfo(final String id, final String text)
	{
		this.id = id;
		this.text = text;
	}

	public String getId()
	{
		return id;
	}

	public String getText()
	{
		return text;
	}

}
