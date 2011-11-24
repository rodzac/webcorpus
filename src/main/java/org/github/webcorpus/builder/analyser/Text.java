package org.github.webcorpus.builder.analyser;

import java.util.ArrayList;
import java.util.List;

public class Text
{
	private final List<Sentence> sentences;

	public Text()
	{
		sentences = new ArrayList<Sentence>();
	}

	public void add(Sentence sentence)
	{
		sentences.add(sentence);
	}

	public List<Sentence> getSentences()
	{
		return sentences;
	}

}
