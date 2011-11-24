package org.github.webcorpus.builder.analyser;

import java.util.ArrayList;
import java.util.List;

public class Sentence
{
	private final List<Token> tokens;

	public Sentence()
	{
		tokens = new ArrayList<Token>();
	}

	public void add(final Token token)
	{
		tokens.add(token);
	}

	public List<Token> getTokens()
	{
		return tokens;
	}

}
