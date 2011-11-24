package org.github.webcorpus.services.core.dao;

public interface DocumentRepository
{
	Document get(String id);

	Document update(final Document document);

	void remove(String id);

	Document next();
}
