package org.github.webcorpus.services.repository;

import java.io.IOException;
import java.util.List;

public interface Crawler
{
	List<String> getUrls(String url) throws IOException;

	CrawlerDocument extract(final String url) throws IOException;
}
