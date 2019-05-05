package uoc.pec2.tecnologia.twitter.functions;

import java.util.Iterator;

import org.apache.spark.api.java.function.FlatMapFunction;

import uoc.pec2.tecnologia.twitter.MessageData;

public class GetMentions implements FlatMapFunction<MessageData, String>{
	
	private static final long serialVersionUID = 1L;

	@Override
	public Iterator<String> call(MessageData d) throws Exception {
		return d.getMentions().iterator();
	}

}
