import java.util.Calendar;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;


public class UpdateDocument
{

	public static void main(String[] args) throws Exception
	{
		final DB dataBase = new Mongo("127.0.0.1", 27017).getDB("documents");
		final DBCollection news = dataBase.getCollection("news");
		final DBObject obj = news.findOne(new BasicDBObject("id", "135"));
		
		final Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 7);
		calendar.set(Calendar.MONTH, Calendar.MARCH);
		
		calendar.set(Calendar.HOUR_OF_DAY, 12);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 32);
		
		obj.put("collected", calendar.getTime());
		
		news.update(new BasicDBObject("_id", obj.get("_id")), obj);

	}

}
