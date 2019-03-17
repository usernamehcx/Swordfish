package com.nju;

import com.google.gson.Gson;
import com.nju.datatypes.T_KJ_ZZQKM;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.collector.selector.OutputSelector;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SplitStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.fs.StringWriter;
import org.apache.flink.streaming.connectors.fs.bucketing.Bucketer;
import org.apache.flink.streaming.connectors.fs.bucketing.BucketingSink;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.hadoop.io.compress.SplitCompressionInputStream;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Skeleton for a Flink Streaming Job.
 *
 * <p>For a tutorial how to write a Flink streaming application, check the
 * tutorials and examples on the <a href="http://flink.apache.org/docs/stable/">Flink Website</a>.
 *
 * <p>To package your application into a JAR file for execution, run
 * 'mvn clean package' on the command line.
 *
 * <p>If you change the name of the main class (with the public static void main(String[] args))
 * method, change the respective entry in the POM.xml file (simply search for 'mainClass').
 */
public class StreamingJob {

	public static void main(String[] args) throws Exception {
		final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

		env.enableCheckpointing(5000);
//		env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

		if (args.length != 4){
			System.err.println("Usage: ./bin/flink run -c com.nju.StreamingJob /flink-test-1.0-SNAPSHOT.jar <topic> <path> <sourceParallelism> <sinkParallelism>\n");
		}

		String topic = args[0];
		String path = args[1];

		int sourceParallelism = Integer.parseInt(args[2]);
		int sinkParallelism = Integer.parseInt(args[3]);

		Properties properties = new Properties();
		properties.setProperty("bootstrap.servers", "kafka-headless.zhongchuang.svc.cluster.local:9092");
		properties.setProperty("group.id", "test-kafka-1");


		properties.setProperty("fs.default-scheme","hdfs://hadoop-spark-master.hive-test.svc.cluster.local:9000");
		//DataStreamSource<ZZKQM> table = env.addSource(new KafkaSource());

		Gson gson = new Gson();
		DataStream<String> stream = env
				.addSource(new FlinkKafkaConsumer<>(topic, new SimpleStringSchema(), properties).setStartFromEarliest()).setParallelism(sourceParallelism);

		DataStream<T_KJ_ZZQKM> itemStream = stream.map(new MapFunction<String, T_KJ_ZZQKM>() {
			@Override
			public T_KJ_ZZQKM map(String s) throws Exception {
				return T_KJ_ZZQKM.fromString(s);
			}
		});

		SplitStream<T_KJ_ZZQKM> split = itemStream.split(new OutputSelector<T_KJ_ZZQKM>() {
			@Override
			public Iterable<String> select(T_KJ_ZZQKM t_kj_zzqkm) {
				List<String> output = new ArrayList<String>();
				if (t_kj_zzqkm.isNotnull()){
					output.add("notnull");
				}
				else {
					output.add("null");
				}
				return output;
			}
		});

		DataStream<T_KJ_ZZQKM> falseStream = split.select("null");
		falseStream.print();

		System.out.println("*********** hdfs *************");

		//stream.writeAsText("hdfs://hadoop-spark-master.hive-test.svc.cluster.local:9000/base/path");
		BucketingSink<T_KJ_ZZQKM> bucketingSink = new BucketingSink<>("hdfs://hadoop-spark-master.hive-test.svc.cluster.local:9000/base/path"+path);

		bucketingSink.setWriter(new StringWriter<>())
				.setBatchSize(1024 * 1024 * 400)
				.setBatchRolloverInterval(10 * 60 * 1000)
				.setUseTruncate(false);

		split.select("notnull").addSink(bucketingSink).setParallelism(sinkParallelism);
		env.execute("Flink Streaming Java API Skeleton");
	}


}
