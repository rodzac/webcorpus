package org.github.webcorpus.builder.analyser;

public class Token
{
	enum TokenType
	{
		TEXT, META;
	}

	private final String token;
	private final TokenType type;

	public Token(final String token, final TokenType type)
	{
		this.token = token;
		this.type = type;
	}

	public String getToken()
	{
		return token;
	}

	public TokenType getType()
	{
		return type;
	}

}
