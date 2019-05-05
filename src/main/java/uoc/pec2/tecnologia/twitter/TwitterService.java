package uoc.pec2.tecnologia.twitter;

import static org.apache.spark.sql.functions.desc;
import static org.apache.spark.sql.functions.expr;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.twitter.TwitterUtils;
import org.springframework.stereotype.Service;

import twitter4j.Status;
import uoc.pec2.tecnologia.twitter.functions.FilterEnum;
import uoc.pec2.tecnologia.twitter.functions.GetMentions;
import uoc.pec2.tecnologia.twitter.functions.GetMessageHashtags;
import uoc.pec2.tecnologia.twitter.functions.GetMessageWords;
import uoc.pec2.tecnologia.twitter.functions.HashtagFilter;
import uoc.pec2.tecnologia.twitter.functions.SaveDataToJSON;
import uoc.pec2.tecnologia.twitter.functions.TwitterDataMapper;
import uoc.pec2.tecnologia.twitter.functions.UserMentionedFilter;
import uoc.pec2.tecnologia.web.DirectoryRegistry;
import uoc.pec2.tecnologia.web.beans.Word;

@Service
public class TwitterService {

	private JavaStreamingContext ssc = null;

	private SparkConf config = null;
	
	public void start(FilterEnum filter, String word) {

        this.config = new SparkConf().setAppName("Twitter Analysis");
        this.config.setMaster("local[5]");

        this.ssc = new JavaStreamingContext(this.config, Durations.seconds(30));
        
        JavaReceiverInputDStream<Status> jdstream = TwitterUtils.createStream(ssc);
        
        Function<Status,Boolean> filterFunction = null;
        switch (filter) {
		case MENTION:
			filterFunction = new UserMentionedFilter(word);
			break;
		case HASHTAG:
		default:
			filterFunction = new HashtagFilter(word);
			break;
		}
        
        JavaDStream<Status> filteredTweets =  jdstream.filter(filterFunction);
        
        JavaDStream<MessageData> mapTwitterData = filteredTweets.map(new TwitterDataMapper());
        

        JavaDStream<String> wordsData = mapTwitterData.flatMap(new GetMessageWords());
        JavaDStream<String> hashtagData = mapTwitterData.flatMap(new GetMessageHashtags());
        JavaDStream<String> mentionsData = mapTwitterData.flatMap(new GetMentions());
                
        JavaPairDStream<String, Long> wordsDataCounted = wordsData.countByValue();
        JavaPairDStream<String, Long> hashtagDataCounted = hashtagData.countByValue();
        JavaPairDStream<String, Long> mentionsDataCounted = mentionsData.countByValue();
        
        JavaDStream<MessageData> locationFiltered = mapTwitterData.filter(d -> {
        	return d.getLocation() !=null;
        });
        locationFiltered.print();
        
        hashtagDataCounted.foreachRDD(new SaveDataToJSON(FilterEnum.HASHTAG.name()));
        
        wordsDataCounted.foreachRDD(new SaveDataToJSON(FilterEnum.WORD.name()));
        
        mentionsDataCounted.foreachRDD(new SaveDataToJSON(FilterEnum.MENTION.name()));
        
        ssc.start();
	}
	
	public List<Word> getWords(FilterEnum filter) {

		List<Word> result = null;
		
		String path =DirectoryRegistry.getInstance().getPath(filter.name());
		
		if(path != null) {
			try {
				if(path != null && this.config != null) {
					SparkSession session = SparkSession.builder().config(this.config).getOrCreate();
					Dataset<Row> words = session.read().json(path + File.separator + "*");
					Dataset<Row> resultData = words.groupBy("text").agg(expr("sum(weight) as weight")).orderBy(desc("weight"));
				    result = resultData.as(Encoders.bean(Word.class)).takeAsList(100);
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return result;
	}
}
