package controllers;

import org.apache.kafka.clients.producer.ProducerConfig;
import services.BuffertoKafkaWriter;
import model.FileEntriesBuffer;
import services.FileToBufferWriter;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;

import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;

public class Controller {
    public static void main(String[]args){
        FileToBufferWriter writer = new FileToBufferWriter();
        FileEntriesBuffer buffer = new FileEntriesBuffer(new ArrayBlockingQueue<String>(4, true));

        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "sandbox-hdp.hortonworks.com:6667");
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = new KafkaProducer<String, String>(props);


        Thread tReader = new Thread(
                ()-> writer.readFileFromFileToBuffer("ex_train.csv", buffer));
        tReader.start();


        BuffertoKafkaWriter buffertoKafkaWriter = new BuffertoKafkaWriter(buffer, producer, "t");

        Thread t1 = new Thread(new BuffertoKafkaWriter(buffer, producer, "file-streaming"));
        Thread t2 = new Thread(new BuffertoKafkaWriter(buffer, producer, "file-streaming"));
        t1.start();
        t2.start();
    }
}
