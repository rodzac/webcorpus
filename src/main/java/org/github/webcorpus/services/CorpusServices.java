package org.github.webcorpus.services;

import java.util.List;

public interface CorpusServices
{
	DocumentInfo get(String id);

	DocumentInfo next();

	void update(DocumentInfo info);

//	List<DocumentInfo> list();

	List<TagInfo> tags();
}
