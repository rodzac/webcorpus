package org.github.webcorpus.services.repository;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.CssSelectorNodeFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.HasParentFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.Translate;

public class WebCrawler implements Crawler
{
	private final Logger logger;

	public WebCrawler()
	{
		logger = Logger.getLogger(getClass());
	}

	public List<String> getUrls(String url) throws IOException
	{
		final URLConnection conn = new URL(url).openConnection();
        final Parser parser = createParser(conn);
//		final Parser parser = createParser("teste.html");
        logger.debug("--> starting parser");
        final List<String> urls = new ArrayList<String>();
        final NodeFilter filter = new AndFilter(new NodeClassFilter(LinkTag.class), new HasParentFilter(new AndFilter(new TagNameFilter("h3"), new HasParentFilter(new AndFilter(new TagNameFilter("li"), new HasParentFilter(new HasAttributeFilter("id", "ultnot-list-noticias")))))));
        for(Node node : getNodes(parser, filter))
        {
        	final String possibleUrl = ((LinkTag) node).extractLink();
        	if(possibleUrl.startsWith("http://esporte.uol.com.br/") && possibleUrl.matches(".*/\\d{4}/\\d{2}/\\d{2}/.*"))
        		urls.add(possibleUrl);
        }
        logger.debug("--> end");
        return urls;
	}

	public CrawlerDocument extract(final String url) throws IOException
	{
		final URLConnection conn = new URL(url).openConnection();
		if(!"text/html".equals(conn.getContentType()))
				return null;

        final Parser parser = createParser(conn);
        logger.debug("--> starting parser");
        System.out.println(url);
        
        final String title = getValue(parser, new AndFilter(new TagNameFilter("h1"), new HasParentFilter(new HasAttributeFilter("class", "conteudo"))));
        System.out.println(title);
        
        final String created = getValue(parser, new AndFilter(new CssSelectorNodeFilter(".data"), new HasParentFilter(new HasAttributeFilter("class", "conteudo"))));
        System.out.println(created);
        
        String body = getValue(parser, new AndFilter(new TagNameFilter("p"), new HasParentFilter(new HasAttributeFilter("id", "texto")))).replaceAll("\\n{1,}\\s*", "\n");
        if(body.isEmpty())
        	body = getValue(parser, new AndFilter(new TagNameFilter("p"), new HasParentFilter(new HasAttributeFilter("dir")))).replaceAll("\\n{1,}\\s*", "\n");
        System.out.println(body);
       
        logger.debug("--> end");
        return new CrawlerDocument(url, toDate(created), Calendar.getInstance().getTime(), title, body);
	}

	private String getValue(final Parser parser, final NodeFilter filter) throws IOException
	{
		parser.reset();
		final StringBuilder content = new StringBuilder();
        for(Node node : getNodes(parser, filter))
        	content.append(Translate.decode(node.toPlainTextString().trim())).append("\n");
        return content.length() > 0 ? content.delete(content.length() - 1, content.length()).toString() : content.toString();
	}

	private Date toDate(String date)
	{
		//04/02/2011 - 21h47
		try
		{
			return new SimpleDateFormat("dd/mm/yyyy - HH'h'MM").parse(date);
		}catch(ParseException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	//wrapper methods...
	private Node[] getNodes(final Parser parser, final NodeFilter filter) throws IOException
	{
        try
        {
        	return parser.extractAllNodesThatMatch(filter).toNodeArray();
        }catch(ParserException e)
        {
        	throw new IOException(e);
        }
	}

	private Parser createParser(final URLConnection conn) throws IOException
	{
        try
        {
        	return new Parser(conn);
        }catch(ParserException e)
        {
        	throw new IOException(e);
        }
	}

//	private Parser createParser(final String path) throws IOException
//	{
//        try
//        {
//        	return new Parser(path); //"teste.html");
//        }catch(ParserException e)
//        {
//        	throw new IOException(e);
//        }
//	}
}
