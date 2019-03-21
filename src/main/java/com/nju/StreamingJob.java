package com.nju;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nju.datatypes.T_KJ_ZZQKM;
import org.apache.flink.api.common.functions.FlatMapFunction;
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
import org.apache.flink.util.Collector;
import org.apache.hadoop.io.compress.SplitCompressionInputStream;


import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.*;

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
	private static Set<String> hashSet = new HashSet<>();

	public static void main(String[] args) throws Exception {
		final StreamExecutionEnvironment env;
		String topic = "test-T_KJ_ZZQKM";
		String path = null;
		int sourceParallelism = 1;
		int sinkParallelism = 1;
		Properties properties = new Properties();

		if (args.length == 0){
			env = StreamExecutionEnvironment.createLocalEnvironment();
			properties.setProperty("bootstrap.servers", "localhost:9092");
			properties.setProperty("group.id", "test-kafka-0");
			properties.setProperty("consumer.timeout.ms", "1200");
		}
		else {
			env = StreamExecutionEnvironment.getExecutionEnvironment();
			if (args.length != 4){
				System.err.println("Usage: ./bin/flink run -c com.nju.StreamingJob /flink-test-1.0-SNAPSHOT.jar <topic> <path> <sourceParallelism> <sinkParallelism>\n");
			}

			topic = args[0];
			path = args[1];

			sourceParallelism = Integer.parseInt(args[2]);
			sinkParallelism = Integer.parseInt(args[3]);

			properties.setProperty("bootstrap.servers", "kafka-headless.zhongchuang.svc.cluster.local:9092");
			properties.setProperty("group.id", "test-kafka-1");
			properties.setProperty("consumer.timeout.ms", "60000");
			properties.setProperty("fs.default-scheme","hdfs://hadoop-spark-master.hive-test.svc.cluster.local:9000");

		}
		env.enableCheckpointing(5000);
//		env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);


		DataStream<String> stream = env
				.addSource(new FlinkKafkaConsumer<>(topic, new SimpleStringSchema(), properties).setStartFromEarliest()).setParallelism(sourceParallelism);

		DataStream<String> JGXX_stream = env.addSource(new FlinkKafkaConsumer<>("test-test-testT_GX_JGXX", new SimpleStringSchema(), properties).setStartFromEarliest());

		DataStream<String> id_stream = JGXX_stream.map(new MapFunction<String, String>() {
			@Override
			public String map(String s) throws Exception {
				Gson gson = new Gson();
				JsonObject result = new JsonParser().parse(s).getAsJsonObject();
				String id = result.get("NBJGH").getAsString();
				hashSet.add(id);
				return id;
			}
		});

		id_stream.print();


		DataStream<T_KJ_ZZQKM> itemStream = stream.map(new MapFunction<String, T_KJ_ZZQKM>() {
			@Override
			public T_KJ_ZZQKM map(String s) throws Exception {
				return T_KJ_ZZQKM.fromString(s);
			}
		}).setParallelism(sinkParallelism);

//		itemStream.print();

		SplitStream<T_KJ_ZZQKM> split = itemStream.split(new OutputSelector<T_KJ_ZZQKM>() {
			@Override
			public Iterable<String> select(T_KJ_ZZQKM t_kj_zzqkm) {
				List<String> output = new ArrayList<String>();
				if (!t_kj_zzqkm.isNotnull()){
					output.add("null");
				}
				else if(!hashSet.contains(t_kj_zzqkm.getNBJGH())){
					System.out.println("error");
					output.add("null");
				}
				else {
					output.add("notnull");

				}
				return output;
			}
		});

		DataStream<T_KJ_ZZQKM> falseStream = split.select("null");
		falseStream.print();

		DataStream<T_KJ_ZZQKM> trueStream = split.select("notnull");
//		trueStream.print();
//		trueStream.flatMap(new FlatMapFunction<T_KJ_ZZQKM, Object>() {
//			long received = 0;
//			long logfreq = 1000000;
//			long lastTime = -1;
//			long lastElements = 0;
//			@Override
//			public void flatMap(T_KJ_ZZQKM t_kj_zzqkm, Collector<Object> collector) {
//				received++;
//				if (received % logfreq == 0){
//					long now = System.currentTimeMillis();
//					if(lastTime == -1){
//						lastTime = now;
//						lastElements = received;
//					}
//					else {
//						long timeDiff = now - lastTime;
//						long eleDiff = received - lastElements;
//						double ex = (1000/(double)timeDiff);
//						System.out.println("During the test " + timeDiff + "ms, we received " + eleDiff + "  speed: "+ ex*eleDiff +
//						"elements/sec. Received " + (received * 400)/1024/1024 + "MB");
//						lastTime = now;
//						lastElements = received;
//					}
//				}
//			}
//		});

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = df.format(new Date());
		System.out.println("*********** hdfs *************   current time: " + date);


//		BucketingSink<T_KJ_ZZQKM> bucketingSink = new BucketingSink<>("hdfs://hadoop-spark-master.hive-test.svc.cluster.local:9000/base/path"+path);
//
//		bucketingSink.setWriter(new StringWriter<>())
//				.setBatchSize(1024 * 1024 * 6000)
//				.setBatchRolloverInterval(10 * 60 * 1000)
//				.setUseTruncate(false);


//		split.select("notnull").addSink(bucketingSink).setParallelism(sinkParallelism);

//		split.select("notnull").addSink(bucketingSink);
//		trueStream.writeAsText("hdfs://hadoop-spark-master.hive-test.svc.cluster.local:9000/base/path/test-partition-T_KJ_ZZQKM");
		env.execute("Flink Streaming Java API Skeleton");
	}


}
