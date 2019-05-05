package uoc.pec2.tecnologia.twitter.functions;

import java.io.File;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.VoidFunction2;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Time;

import com.google.common.io.Files;

import uoc.pec2.tecnologia.web.DirectoryRegistry;
import uoc.pec2.tecnologia.web.beans.Word;

public class SaveDataToJSON implements VoidFunction2<JavaPairRDD<String, Long>, Time> {

	private String datasetPath = null;
	
	private String datasetName = null;
	
	public SaveDataToJSON(String dataset) {
		this.datasetPath = Files.createTempDir() + File.separator + dataset;
		this.datasetName = dataset;
		DirectoryRegistry.getInstance().addToRegistry(datasetName, datasetPath);
	}
	
	@Override
	public void call(JavaPairRDD<String, Long> rdd, Time t) throws Exception {
    	SparkSession session = SparkSession.builder().config(rdd.context().getConf()).getOrCreate();
    	
    	JavaRDD<Word> rowRDD = rdd.map(tuple -> {
            Word record = new Word(tuple._1(), tuple._2);
            return record;
        });
    	
    	Dataset<Row> wordsDataSet = session.createDataFrame(rowRDD, Word.class);
    	
    	wordsDataSet.write().mode(SaveMode.Append).json(this.datasetPath);		
	}
	
}
