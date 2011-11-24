package org.github.webcorpus.services.core;

import java.util.ArrayList;
import java.util.List;

import org.github.webcorpus.services.CorpusServices;
import org.github.webcorpus.services.DocumentInfo;
import org.github.webcorpus.services.TagInfo;
import org.github.webcorpus.services.core.dao.Document;
import org.github.webcorpus.services.core.dao.Document.Status;
import org.github.webcorpus.services.core.dao.DocumentRepository;
import org.github.webcorpus.services.core.dao.Tag;
import org.github.webcorpus.services.core.dao.TagRepository;

public class SimpleCorpusServices implements CorpusServices
{
	private final DocumentRepository documents;
	private final TagRepository tags;

	public SimpleCorpusServices(DocumentRepository documents, TagRepository tags)
	{
		this.documents = documents;
		this.tags = tags;
	}

	@Override
	public DocumentInfo get(String id)
	{
		return toInfo(documents.get(id));
	}

	@Override
	public DocumentInfo next()
	{
		return toInfo(documents.next());
	}

	@Override
	public void update(DocumentInfo info)
	{
		documents.update(new Document(info.getId(), null, null, null, info.getText(), Status.DONE));
	}

	@Override
	public List<TagInfo> tags()
	{
		return toInfo(tags.list());
	}

	//-------------->converters<--------------
	private DocumentInfo toInfo(final Document document)
	{
		return new DocumentInfo(document.getId(), document.getText());
	}

	private List<TagInfo> toInfo(final List<Tag> tags)
	{
		final List<TagInfo> infos = new ArrayList<TagInfo>();
		for(Tag tag : tags)
			infos.add(toInfo(tag));
		return infos;
	}

	private TagInfo toInfo(final Tag tag)
	{
		return new TagInfo(tag.getName(), tag.getLabel(), tag.getColor());
	}
}
