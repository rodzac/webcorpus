package org.github.webcorpus.services;

import org.github.webcorpus.services.core.SimpleCorpusServices;
import org.github.webcorpus.services.core.dao.mongo.MongoDBDocumentRepository;
import org.github.webcorpus.services.core.dao.mongo.MongoDBTagsRepository;

public final class Services
{
	private static final CorpusServices corpus = new SimpleCorpusServices(new MongoDBDocumentRepository(), new MongoDBTagsRepository());		//TODO ROd -> create instance...

	private Services()
	{
	}

	public static CorpusServices corpus()
	{
		return corpus;
	}

}
