import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;


public class Stats
{

	public static void main(String[] args) throws Exception
	{
		new Stats().execute();
	}

	public void execute() throws Exception
	{
		final DB dataBase = new Mongo("127.0.0.1", 27017).getDB("documents");
		final DBCollection news = dataBase.getCollection("news");
		final DBCursor cursor = news.find(new BasicDBObject("status", "DONE"));

		final Map<String, Map<String, Counter>> tags = new HashMap<String, Map<String, Counter>>();
		int words = 0;
		int size = 0;
		while(cursor.hasNext())
		{
			final DBObject object = cursor.next();
			for(Tag tag : countTags((String) object.get("text")))
				add(tags, tag);
			final String text = removeTags((String) object.get("text"));
			size += text.length();
			words += text.split("\\s+").length;
		}
		
		int entities = 0;
		for(Entry<String, Map<String, Counter>> entry : tags.entrySet())
		{
		 	System.out.println(entry.getKey());
		 	final List<Counter> counters = new ArrayList<Counter>(entry.getValue().values());
		 	Collections.sort(counters);
		 	int sum = 0;
		 	for(Counter counter : counters)
		 	{
		 		sum += counter.counter;
		 		System.out.println(counter.counter + "\t" +counter.text);
		 	}
		 	entities += sum;
		 	System.out.println("-->sum " + sum + " items " + counters.size());
		 	System.out.println("-------------------------------------------");
		}
	 	System.out.println("-->words " + words + " size " + size + " entities " + entities);
	 	System.out.println("-------------------------------------------");
		
	}

	private void add(Map<String, Map<String, Counter>> tags, Tag tag)
	{
		Map<String, Counter> xxx = tags.get(tag.tag);
		if(xxx == null)
			tags.put(tag.tag, xxx = new HashMap<String, Stats.Counter>());
		
		Counter counter = xxx.get(tag.value);
		if(counter == null)
			xxx.put(tag.value, new Counter(tag.value));
		else
			counter.plus();
	}

	public List<Tag> countTags(final String text)
	{
		final List<Stats.Tag> tags = new ArrayList<Stats.Tag>();
		final StringBuilder content = new StringBuilder(text);
		int begin = 0;
		while((begin = content.indexOf("<", begin)) > -1)
		{
			final int openTagEnd = content.indexOf(">", begin);
			final String tag = content.substring(begin + 1, openTagEnd).trim();
			final int anotherBegin = content.indexOf("<", openTagEnd);
			final String value = content.substring(openTagEnd + 1, anotherBegin);
			tags.add(new Stats.Tag(tag, value));
			begin = anotherBegin + tag.length() + 2;
		}
		return tags;
	}

	public String removeTags(final String text)
	{
		final StringBuilder content = new StringBuilder(text);
		int begin = 0;
		while((begin = content.indexOf("<", begin)) > -1)
		{
			final int openTagEnd = content.indexOf(">", begin);
			content.replace(begin, openTagEnd + 1, "");
		}
		return content.toString();
	}

	class Counter implements Comparable<Counter>
	{
		private final String text;
		private int counter;

		public Counter(final String text)
		{
			this.text = text;
			counter = 1;
		}
		
		void plus()
		{
			counter++;
		}

		@Override
		public int compareTo(Counter o)
		{
			return counter == o.counter ? 0 : counter > o.counter ? 1 : -1;
		}
	}

	class Tag
	{
		private final String tag;
		private final String value;
	
		public Tag(final String tag, final String value)
		{
			this.tag = tag;
			this.value = value;
		}

		String tag()
		{
			return tag;
		}
		
		String value()
		{
			return value;
		}

	}
}
