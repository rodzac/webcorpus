
public class DocumentBuilder
{
	private final StringBuilder content;

	public DocumentBuilder(final String id)
	{
		content = new StringBuilder("<document id=\"").append(id).append("\">\n");
	}

	public void add(String name, String value)
	{
		content.append("\t<").append(name).append("><![CDATA[").append(value).append("]]></").append(name).append(">\n");
	}

	public String build()
	{
		return content.append("</document>").toString();
	}

}
