
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.github.webcorpus.services.TagInfo;
import org.github.webcorpus.services.core.dao.Document.Status;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class TestMongoDB
{

	public static void main(String[] args) throws UnknownHostException, MongoException, ParseException, IOException
	{
		DB dataBase = new Mongo("127.0.0.1", 27017).getDB("documents");
		DBCollection news = dataBase.getCollection("news");
		
//		final BasicDBObject value = new BasicDBObject(6);
//		value.put("id", "67");
//		value.put("url", "http://esporte.uol.com.br/futebol/ultimas-noticias/2011/03/02/jogadores-do-g-prudente-participam-de-projeto-e-visitam-criancas-em-escola.jhtm");
//		
//		final SimpleDateFormat formatter = new SimpleDateFormat("MM dd yyyy HH:mm:ss");
//		value.put("created", formatter.parse("03 02 2011 17:03:00"));
//		value.put("collected", formatter.parse("03 07 2011 12:56:56"));
//		value.put("text", "Jogadores do G. Prudente participam de projeto e visitam crian�as em escola\nOs alunos da Escola Municipal Alayde Tortorella Faria Motta, localizada em Presidente Prudente, foram os primeiros em 2011 a receber a visita do projeto Futuro Torcedor, promovido em parceria entre Gr�mio Prudente e Prefeitura Municipal. O encontro ocorreu na manh� desta quarta-feira e contou com as presen�as do volante Roberto e do atacante Rhayner.\nOs jogadores conversaram por quase uma hora com cerca de 250 alunos das 4� e 5� s�ries. O objetivo do projeto � aproximar as crian�as prudentinas do Gr�mio Prudente, oferecendo a oportunidade de que recebam jogadores em suas escolas, sejam elas p�blicas ou particulares.\n�Assim como voc�s lutam para estar aqui nesta escola todos os dias, eu tamb�m superei muitas batalhas para chegar onde estou hoje. Voc�s n�o podem desistir nunca, dos estudos ou dos seus sonhos�, disse o jovem atacante Rhayner, que atualmente trata uma les�o no tornozelo.\nO volante Roberto, que se recupera de um problema no joelho, tamb�m ressaltou �s crian�as a import�ncia da escola. �O estudo � muito importante e voc�s por ainda serem novos precisam se dedicar a ele, respeitar seus pais, amigos e professores. E, principalmente, nunca desistir dos sonhos�, falou o jogador do Gr�mio Prudente.\nOs jogadores tamb�m foram �entrevistados� pelos alunos. Entre as perguntas, \"qual gol mais importante da carreira\", \"o que fariam se n�o fossem atletas\", e at� \"quantas refei��es eles fazem por dia\". �Eu sempre quis conhecer um jogador de perto, eu imaginava que eles fossem legais e vi que s�o mesmo�, disse Matheus Richard, de apenas nove anos.\nDepois disso, os gremistas entregaram � escola uma camisa do clube autografada e distribu�ram aut�grafos a funcion�rios e alunos. Na pr�xima sexta-feira, quarenta alunos da escola ir�o � partida entre Gr�mio Prudente e Ituano, no Prudent�o, quando v�o entrar em campo com os jogadores.");
//		value.put("status", "COLLECTED");
//		news.update(new BasicDBObject("id", "67"), value);
	

//		DBCursor result = news.find(new BasicDBObject("id", "7"));
////		DBCursor result = news.find();
//		while(result.hasNext())
//		{
//			final DBObject obj = result.next();
////			backup(obj);
////			changeValue(obj, "status", Status.COLLECTED.name());
//			final String text = replaceTags((String) obj.get("text"));
//			changeValue(obj, "text", text);
//			obj.put("size", text.length());
//			news.update(new BasicDBObject("id", obj.get("id")), obj);
//			System.out.println(obj.get("id"));
////			System.out.println(obj.get("_id"));
//			
////			total += ((String) obj.get("text")).split("\\s+").length;
//			
////			final String title = [0];
////			System.out.println(obj.get("id") + "\t" + replaceTags(title));
//		}
		
//		//06/03/2011 - 13h20
//		final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH'h'mm");
//		final BufferedReader reader = new BufferedReader(new FileReader(new File("ids.log")));
//		for(String line = reader.readLine(); line != null; line = reader.readLine())
//		{
//			final String[] values = line.split("\t");
//			final BasicDBObject obj = new BasicDBObject("created", formatter.parse(values[1]));
//			obj.put("url", values[2]);
////			System.out.println(values[0] + "\t" + formatter.parse(values[1]));
//			news.update(new BasicDBObject("id", values[0]), new BasicDBObject("$set", obj), false, false);
//		}
		
//		news.update(new BasicDBObject("id", "67"), new BasicDBObject("$set", new BasicDBObject("status", "XXX")), false, false);
		
		
//		DBCollection sequencies = dataBase.getCollection("seqs");
		
//		final BasicDBObject obj = new BasicDBObject();
//		obj.put("name", "news");
//		obj.put("seq", 1);
//		sequencies.insert(obj);

		
//		System.out.println(sequencies.findAndModify(new BasicDBObject("name", "news"), new BasicDBObject("$inc", new BasicDBObject("seq", 1))));
		
//		final DBObject seq = new BasicDBObject("name", "news"); 
//		final DBObject increment = new BasicDBObject("$inc", new BasicDBObject("seq", 1));
//		final DBObject fields = new BasicDBObject("seq", "true");
//		final DBObject obj = sequencies.findAndModify(seq, fields, null, false, increment, true, false);
//		System.out.println(((Integer) obj.get("seq")).toString());

//		System.out.println(dataBase.getCollection("news").count());
		
		
		DBCollection tags = dataBase.getCollection("tags");
		tags.drop();
		tags.insert(createTag("person", "Pessoa", "red"));
		tags.insert(createTag("team", "Time", "purple"));
		tags.insert(createTag("stadium", "Estádio", "teal"));
		tags.insert(createTag("championship", "Campeonato", "brown"));
		tags.insert(createTag("fans", "Torcida", "olive"));
		tags.insert(createTag("place", "Lugar", "blue"));
		tags.insert(createTag("organization", "Organização", "green"));
	}

	private static void backup(DBObject obj) throws IOException
	{
		final String id = get(obj, "id");
		final File file = new File("/Users/rod/rod/workspaces/mestrado/webcorpus/backup/" + id);
		final FileOutputStream fos = new FileOutputStream(file);
		write(fos, id);
		write(fos, get(obj, "created"));
		write(fos, get(obj, "url"));
		write(fos, get(obj, "text"));
		fos.flush();
		fos.close();
	}

	private static void write(FileOutputStream fos, String value) throws IOException
	{
		fos.write(value.getBytes());
		fos.write("\n".getBytes());
	}

	private static String get(DBObject obj, String field)
	{
		final Object value = obj.get(field);
		if(value instanceof String)
			return (String) value;
		return String.valueOf(((Date) value).getTime());
	}

	private static void changeValue(DBObject obj, String field, String value)
	{
		obj.removeField(field);
		obj.put(field, value);
	}

	public static String replaceTags(final String text)
	{
		final StringBuilder content = new StringBuilder(text);
		int begin = 0;
		while((begin = content.indexOf("<", begin)) > -1)
		{
			final int openTagEnd = content.indexOf(">", begin);
			content.replace(begin, openTagEnd + 1, "");
		}
		return content.toString();
	}
	
	private static DBObject createTag(String name, String label, String color)
	{
		final DBObject obj = new BasicDBObject(3);
		obj.put("name", name);
		obj.put("label", label);
		obj.put("color", color);
		return obj;
	}
}
