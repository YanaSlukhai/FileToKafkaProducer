package controllers;

import model.BuffertoKafkaWriter;
import model.FileToBufferWriter;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;

import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;

public class Controller {
    public static void main(String[]args){
        FileToBufferWriter writer = new FileToBufferWriter();
        ArrayBlockingQueue<String> buffer = new ArrayBlockingQueue<String>(4, true);


        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = new KafkaProducer<String, String>(props);
        

        Thread tReader = new Thread(
                ()-> writer.readFileFromFileToBuffer("src/main/resources/ex_train.csv", buffer));
        tReader.start();

        Thread t1 = new Thread(new BuffertoKafkaWriter(buffer, producer, "t"));
        Thread t2 = new Thread(new BuffertoKafkaWriter(buffer, producer, "t"));
        t1.start();
        t2.start();
    }
}
