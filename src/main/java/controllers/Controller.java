package controllers;

import org.apache.kafka.clients.producer.ProducerConfig;
import services.ThreadedBuffertoKafkaWriter;
import model.FileEntriesBuffer;
import services.FileToBufferWriter;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;

import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Controller {
    public static void main(String[]args){
        FileToBufferWriter writer = new FileToBufferWriter();
        FileEntriesBuffer buffer = new FileEntriesBuffer(new LinkedBlockingQueue<>());

        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, /*"sandbox-hdp.hortonworks.com:6667");*/ "localhost:8080");
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = new KafkaProducer<>(props);


        Thread tReader = new Thread(
                ()-> writer.readFileFromFileToBuffer("src/main/resources/test.csv", buffer));
        tReader.start();

        ThreadedBuffertoKafkaWriter buffertoKafkaWriter = new ThreadedBuffertoKafkaWriter(buffer, producer, "t");

        ArrayList<Thread>  bufferToKafkaWriterthreads = new ArrayList<>();
        for(int i = 0; i <4; i++ ) {
            bufferToKafkaWriterthreads.add(new Thread(buffertoKafkaWriter));
            bufferToKafkaWriterthreads.get(i).start();
        }
    }
}
