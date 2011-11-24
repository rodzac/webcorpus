package org.github.webcorpus.services.repository;

import java.util.Date;

public class CrawlerDocument
{
	private final String url;
	private final Date created;
	private final Date collected;
	private final String title;
	private final String body;

	public CrawlerDocument(String url, Date created, Date collected, String title, String body)
	{
		this.url = url;
		this.created = created;
		this.collected = collected;
		this.title = title;
		this.body = body;
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

	public String getTitle()
	{
		return title;
	}

	public String getBody()
	{
		return body;
	}

}
