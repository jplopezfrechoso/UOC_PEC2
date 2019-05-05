package uoc.pec2.tecnologia.twitter.functions;

import org.apache.spark.api.java.function.Function;

import scala.Tuple2;
import uoc.pec2.tecnologia.twitter.wordlists.SenticonWords;

public class GetSentimentData implements Function<String, Tuple2<String, Double>> {
	
	private static final long serialVersionUID = 1L;

	@Override
	public Tuple2<String, Double> call(String w) throws Exception {
		
		return new Tuple2<String, Double>(w, (double) SenticonWords.getInstance().evaluate(w));
	}

}
