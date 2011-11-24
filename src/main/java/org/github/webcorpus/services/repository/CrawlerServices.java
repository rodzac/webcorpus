package org.github.webcorpus.services.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.github.webcorpus.services.core.dao.Document;
import org.github.webcorpus.services.core.dao.Document.Status;
import org.github.webcorpus.services.core.dao.DocumentRepository;
import org.github.webcorpus.services.core.dao.mongo.MongoDBDocumentRepository;

import com.mongodb.MongoException;

public class CrawlerServices
{
	private final Crawler crawler;
	private final DocumentRepository repository;

	public CrawlerServices(Crawler crawler, DocumentRepository repository)
	{
		this.crawler = crawler;
		this.repository = repository;
	}

	public void execute(List<String> seeds) throws IOException
	{
		for(String seedUrl : seeds)
			for(String url : crawler.getUrls(seedUrl))
			{
				final CrawlerDocument document = crawler.extract(url);
				if(document != null)
					repository.update(createDocument(document));
			}

	}

	private Document createDocument(final CrawlerDocument web)
	{
		final String text = new StringBuilder(web.getTitle()).append("\n").append(web.getBody()).toString();
		return new Document(null, web.getUrl(), web.getCreated(), web.getCollected(), text, Status.COLLECTED);
	}

	public static void main(String[] args) throws MongoException, IOException
	{
		final List<String> urls = new ArrayList<String>();
		urls.add("http://esporte.uol.com.br/futebol/campeonatos/paulista/ultimas-noticias/");
		for(int i = 1; i < 30; i++)
			urls.add("http://esporte.uol.com.br/futebol/campeonatos/paulista/ultimas-noticias/index" + i + ".jhtm");

		new CrawlerServices(new WebCrawler(), new MongoDBDocumentRepository()).execute(urls);
	}

}
