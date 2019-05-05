package uoc.pec2.tecnologia.twitter.functions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.spark.api.java.function.FlatMapFunction;

import uoc.pec2.tecnologia.twitter.MessageData;

public class GetMessageHashtags implements FlatMapFunction<MessageData, String>{
	
	private static final long serialVersionUID = 1L;

	@Override
	public Iterator<String> call(MessageData d) throws Exception {
		List<String> result = new ArrayList<>();
		
		for(String hashtag : d.getHashtags()) {
			result.add(hashtag.toLowerCase());
		}
		return result.iterator();
	}
}
