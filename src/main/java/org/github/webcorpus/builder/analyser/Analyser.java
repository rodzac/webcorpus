package org.github.webcorpus.builder.analyser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.github.webcorpus.builder.analyser.Token.TokenType;

public class Analyser
{

	public static void main(String[] args) throws IOException
	{
//		final Analyser analyser = new Analyser();
//		final File path = new File("/Users/rod/rod/workspaces/mestrado/webcorpus/corpus/text/");
//		for(File current : path.listFiles())
//		{
//			for(Sentence sentence : analyser.parse(current).getSentences())
//			{
//				for(Token token : sentence.getTokens())
//					System.out.print(token + " ");
//				System.out.println();
//			}
//			break;
//		}

		final StringBuilder content = new StringBuilder("texto de teste.");
		for(Sentence sentence : new Analyser().parse(content).getSentences())
		{
			for(Token token : sentence.getTokens())
				System.out.print(token + " ");
			System.out.println();
		}
		
	}

//	public Text parse(final File path) throws IOException
	public Text parse(final StringBuilder content) throws IOException
	{
//		final StringBuilder content = read(path);
		final Text text = new Text();
		while(content.length()  > 0)
			text.add(parseSentence(content));
		return text;
	}

	private Sentence parseSentence(final StringBuilder content)
	{
		final Sentence sentence = new Sentence();
		Token token = null;
		do
		{
			token = nextToken(content);
		}while(!token.getToken().equals(".") && !token.getToken().equals("\n"));
		return sentence;
	}

	private Token nextToken(final StringBuilder content)
	{
		final int nextSpace = nextDelimiter(content);
		int end = nextSpace < 0 ? content.length() - 1 : nextSpace;
		for(; end > 0 && isMeta(content.charAt(end)); end--);
		return createToken(content, end, nextSpace >= 0);
	}

	private int nextDelimiter(final StringBuilder content)
	{
		for(int i = 0; i < content.length(); i++)
		{
			final char c = content.charAt(i);
			if(c == ' ' || c == '\n')
				return i;
		}
		return -1;
	}

	private Token createToken(final StringBuilder content, final int end, boolean eraseNext)
	{
		final String text = content.substring(0, end);
		content.delete(0, eraseNext ? end + 1 : end);
		return new Token(text, text.length() == 1 && isMeta(text.charAt(0)) ? TokenType.META : TokenType.TEXT);
	}

	private boolean isMeta(final char c)
	{
		return c == '.' || c == ',' || c == ';' || c == '?' || c == '!' || c == ':';
	}

	private StringBuilder read(final File path) throws IOException
	{
		final FileInputStream input = new FileInputStream(path);
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final byte[] buffer = new byte[1024];
		int read = 0;
		while((read = input.read(buffer)) > -1)
			baos.write(buffer, 0, read);
		input.close();
		return new StringBuilder(new String(baos.toByteArray(), "UTF-8"));
	}
}
