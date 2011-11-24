package org.github.webcorpus.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.github.webcorpus.services.CorpusServices;
import org.github.webcorpus.services.DocumentInfo;
import org.github.webcorpus.services.Services;
import org.github.webcorpus.services.TagInfo;

public class CorpusServlet extends HttpServlet 
{
	private static final long serialVersionUID = -96006775289878938L;

	private final CorpusServices corpus;

	public CorpusServlet()
	{
		corpus = Services.corpus();
	}

	//-------------->servlet<--------------
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		final String id = getId(req);
		final String json = toJson(isEmpty(id) || "next".equals(id) ? corpus.next() : corpus.get(id), corpus.tags());
		final byte[] result = json.getBytes("UTF-8");
		resp.setContentType("application/json;charset=utf-8");
		resp.getOutputStream().write(result);
		resp.setContentLength(result.length);
		resp.getOutputStream().flush();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		System.out.println("id:   " + getId(req));
		final String text = read(req.getInputStream());
		System.out.println("text: " + text);
		corpus.update(new DocumentInfo(getId(req), text));
	}

	//-------------->id<--------------
	private String getId(final HttpServletRequest req)
	{
		final String uri = req.getRequestURI();
		return uri.substring(uri.lastIndexOf('/') + 1);
	}

	//-------------->misc<--------------
	private String toJson(final DocumentInfo document, List<TagInfo> tags)
	{
		final JsonBuilder json = new JsonBuilder();
		json.add("document", new JsonBuilder().add("id", document.getId()).add("text", document.getText()));
		final List<JsonBuilder> jsonTags = new ArrayList<JsonBuilder>(tags.size());
		for(TagInfo info : tags)
			jsonTags.add(new JsonBuilder().add("name", info.getName()).add("label", info.getLabel()).add("color", info.getColor()));
		json.add("tags", jsonTags);
		return json.build();
	}

	private String read(InputStream input)
	{
		try
		{
			final byte[] array = new byte[1024];
			final ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int read = 0;
			while ((read = input.read(array)) >= 0)
				baos.write(array, 0, read);
			baos.close();
			return new StringBuilder(baos.toString("UTF-8")).toString();
		}catch(Exception e)
		{
			//logger.warn(new StringBuilder("Cannot read inputstream [").append(input).append("]").toString(), e);
			return null;
		}
	}

//	private String replaceTags(final String text)
//	{
//		final StringBuilder content = new StringBuilder(text);
//		int begin = 0;
//		while((begin = content.indexOf("<span", begin)) > -1)
//		{
//			final int classBegin = content.indexOf("class=\"", begin);
//			final int classEnd = content.indexOf("\"", classBegin + 7);
//			final int openTagEnd = content.indexOf(">", classEnd);
//			final String tagName = content.substring(classBegin + 7, classEnd);
//			content.replace(begin, openTagEnd + 1, tagName.isEmpty() ? "" : new StringBuilder("<").append(tagName).append(">").toString());
//			final int closeTagBegin = content.indexOf("</span>", begin);
//			content.replace(closeTagBegin, closeTagBegin + 7, tagName.isEmpty() ? "" : new StringBuilder("</").append(tagName).append(">").toString());
//		}
//		return content.toString();
//	}

	private boolean isEmpty(String value)
	{
		return value == null || value.isEmpty();
	}

}
