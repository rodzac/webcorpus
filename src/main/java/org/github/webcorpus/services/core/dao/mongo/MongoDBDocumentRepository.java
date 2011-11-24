package org.github.webcorpus.services.core.dao.mongo;

import java.net.UnknownHostException;
import java.util.Date;

import org.github.webcorpus.services.core.dao.Document;
import org.github.webcorpus.services.core.dao.Document.Status;
import org.github.webcorpus.services.core.dao.DocumentRepository;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class MongoDBDocumentRepository implements DocumentRepository
{
	private final DB dataBase;
	private final DBCollection news;
	private final DBCollection sequencies;

	public MongoDBDocumentRepository() 
	{
		try
		{
			dataBase = new Mongo("127.0.0.1", 27017).getDB("documents");
			news = dataBase.getCollection("news");
			sequencies = dataBase.getCollection("seqs");
		}catch(UnknownHostException e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public Document get(String id)
	{
		return toDocument(news.findOne(new BasicDBObject("id", id)));
	}

	@Override
	public Document update(Document document)
	{
		if(document.getId() != null)
			news.update(new BasicDBObject("id", document.getId()), toUpdateDBObject(document), false, false);
		else
		{
			final DBObject xxx = news.findOne(new BasicDBObject("url", document.getUrl()));
			if(xxx == null)
				news.insert(toDBObject(document));
			else
				news.update(new BasicDBObject("id", document.getId()), toDBObject(document));
		}
		return document;
	}

	@Override
	public void remove(String id)
	{
		news.remove(new BasicDBObject("id", id));
	}

	@Override
	public Document next()
	{
		final DBCursor result = news.find(new BasicDBObject("status", Status.COLLECTED.name())).sort(new BasicDBObject("size", 1));
		if(!result.hasNext())
			return null;
		return toDocument(result.next());
//		return toDocument(news.findOne(new BasicDBObject("status", Status.COLLECTED.name())));
	}

	private DBObject toDBObject(final Document document)
	{
		final DBObject obj = new BasicDBObject(6);
		obj.put("id", document.getId() == null ? createId() : document.getId());
		addField("url", document.getUrl(), obj);
		addField("created", document.getCreated(), obj);
		addField("collected", document.getCollected(), obj);
		addField("text", document.getText(), obj);
		addField("status", document.getStatus() == null ? null : document.getStatus().toString(), obj);
		addField("size", document.getText().length(), obj);
		return obj;
	}

	private DBObject toUpdateDBObject(final Document document)
	{
		final DBObject obj = new BasicDBObject(2);
//		obj.put("id", document.getId() == null ? createId() : document.getId());
		addField("text", document.getText(), obj);
		addField("status", document.getStatus() == null ? null : document.getStatus().toString(), obj);
		return new BasicDBObject("$set", obj);
	}

	private void addField(final String name, final Object value, final DBObject obj)
	{
		if(value != null)
			obj.put(name, value);
	}

	private String createId()
	{
		final DBObject seq = new BasicDBObject("name", "news"); 
		final DBObject increment = new BasicDBObject("$inc", new BasicDBObject("seq", 1));
		final DBObject fields = new BasicDBObject("seq", "true");
		final DBObject obj = sequencies.findAndModify(seq, fields, null, false, increment, true, false);
		return ((Integer) obj.get("seq")).toString();
	}

	private Document toDocument(final DBObject obj)
	{
		return new Document((String) obj.get("id"), (String) obj.get("url"), (Date) obj.get("created"), (Date) obj.get("collected"), (String) obj.get("text"), Status.valueOf((String) obj.get("status")));
	}

}
