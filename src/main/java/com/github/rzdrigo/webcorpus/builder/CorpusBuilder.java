package com.github.rzdrigo.webcorpus.builder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.github.webcorpus.services.core.dao.Document.Status;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class CorpusBuilder
{

	public static void main(String[] args) throws MongoException, IOException
	{
		final DB dataBase = new Mongo("127.0.0.1", 27017).getDB("documents");
		DBCollection news = dataBase.getCollection("news");
		
		final DBObject obj = new BasicDBObject("status", Status.DONE.name());
		final DBCursor results = news.find(obj);
		
		while(results.hasNext())
		{
			final DBObject current = results.next();
			final FileOutputStream fos = new FileOutputStream(new File("/Users/rod/rod/workspaces/mestrado/webcorpus/corpus/text/" + current.get("id") + ".news"));
			fos.write(replaceTags((String) current.get("text")).getBytes("UTF-8"));
			fos.flush();
		}
	}

	private static String replaceTags(String text)
	{
		final StringBuilder content = new StringBuilder(text);
		int begin = -1;
		while((begin = content.indexOf("<", begin)) > 0)
			content.delete(begin, content.indexOf(">", begin) + 1);
		return content.toString();
	}
}
