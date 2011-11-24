

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.HasParentFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.ParserException;

public class LinkTest
{
	private static final String url = "http://esporte.uol.com.br/futebol/campeonatos/paulista/ultimas-noticias/";

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ParserException 
	 */
	public static void main(String[] args) throws ParserException, IOException
	{
		test();
		test2();
	}

	public static void test() throws IOException, ParserException
	{
		final Parser parser = new Parser(new URL(url).openConnection());
        final List<String> urls = new ArrayList<String>();
        final NodeFilter filter = new AndFilter(new NodeClassFilter(LinkTag.class), new HasParentFilter(new AndFilter(new TagNameFilter("h3"), new HasParentFilter(new AndFilter(new TagNameFilter("li"), new HasParentFilter(new HasAttributeFilter("id", "ultnot-list-noticias")))))));
        for(Node node : getNodes(parser, filter))
        {
        	final String possibleUrl = ((LinkTag) node).extractLink();
        	if(possibleUrl.startsWith("http://esporte.uol.com.br/") && possibleUrl.matches(".*/\\d{4}/\\d{2}/\\d{2}/.*"))
        		urls.add(possibleUrl);
        }
        System.out.println(urls.size());
	}

	public static void test2() throws IOException, ParserException
	{
		final Parser parser = new Parser(new URL(url).openConnection());
        final List<String> urls = new ArrayList<String>();
        final NodeFilter filter = new TagNameFilter("a");
        for(Node node : getNodes(parser, filter))
        {
        	final String possibleUrl = ((LinkTag) node).extractLink();
        	if(possibleUrl.startsWith("http://esporte.uol.com.br/"))
        		urls.add(possibleUrl);
        }
        System.out.println(urls.size());
	}

	
	private static Node[] getNodes(final Parser parser, final NodeFilter filter) throws IOException
	{
        try
        {
        	return parser.extractAllNodesThatMatch(filter).toNodeArray();
        }catch(ParserException e)
        {
        	throw new IOException(e);
        }
	}

	private static Parser createParser(final URL url) throws IOException
	{
        try
        {
        	return new Parser(url.openConnection());
        }catch(ParserException e)
        {
        	throw new IOException(e);
        }
	}

}
