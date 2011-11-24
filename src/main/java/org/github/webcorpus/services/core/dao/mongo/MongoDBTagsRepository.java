package org.github.webcorpus.services.core.dao.mongo;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.github.webcorpus.services.core.dao.Tag;
import org.github.webcorpus.services.core.dao.TagRepository;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class MongoDBTagsRepository implements TagRepository
{
	private final DB dataBase;
	private final DBCollection tags;

	public MongoDBTagsRepository()
	{
		try
		{
			dataBase = new Mongo("127.0.0.1", 27017).getDB("documents");
			tags = dataBase.getCollection("tags");
		}catch(UnknownHostException e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Tag> list()
	{
		final List<Tag> list = new ArrayList<Tag>();
		for(DBObject obj : tags.find())
			list.add(toTag(obj));
		return list;
	}

	private Tag toTag(final DBObject obj)
	{
		return new Tag((String) obj.get("name"), (String) obj.get("label"), (String) obj.get("color"));
	}

}
