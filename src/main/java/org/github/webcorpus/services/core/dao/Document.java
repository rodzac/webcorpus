package org.github.webcorpus.services.core.dao;

import java.util.Date;

public class Document
{
	public enum Status
	{
		COLLECTED, IN_PROGRESS, DONE;
	}
	
	private final String id;
	private final String url;
	private final Date created;
	private final Date collected;
	private final String text;
	private final Status status;

	public Document(final String id, String url, Date created, Date collected, String text, Status status)
	{
		this.id = id;
		this.url = url;
		this.created = created;
		this.collected = collected;
		this.text = text;
		this.status = status;
	}

	public String getId()
	{
		return id;
	}

	public String getUrl()
	{
		return url;
	}

	public Date getCreated()
	{
		return created;
	}

	public Date getCollected()
	{
		return collected;
	}

	public String getText()
	{
		return text;
	}

	public Status getStatus()
	{
		return status;
	}

}
