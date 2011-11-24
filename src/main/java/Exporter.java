import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;


public class Exporter
{

	public static void main(String[] args) throws Exception
	{
		final DB dataBase = new Mongo("127.0.0.1", 27017).getDB("documents");
		final DBCollection news = dataBase.getCollection("news");
		final DBCursor cursor = news.find(new BasicDBObject("status", "DONE"));

		while(cursor.hasNext())
		{
			final DBObject object = cursor.next();
			final String id = get(object, "id");
//			final DocumentBuilder builder = new DocumentBuilder(id);
//			add(object, "url", builder);
//			add(object, "collected", builder);
//			builder.add("text", replaceTags(get(object, "text")));
			final FileOutputStream fos = new FileOutputStream(new File("/Users/rod/rod/workspaces/mestrado/webcorpus/corpus/text-plain/" + id + ".txt"));
			fos.write(replaceTags(get(object, "text")).getBytes());
			fos.flush();
			fos.close();
		}
	}

	public static String replaceTags(final String text)
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

	private static void add(DBObject object, String field, DocumentBuilder builder)
	{
		builder.add(field, get(object, field));
	}

	private static String get(DBObject obj, String field)
	{
		final Object value = obj.get(field);
		if(value instanceof String)
			return (String) value;
		return ((Date) value).toString();
	}

}
