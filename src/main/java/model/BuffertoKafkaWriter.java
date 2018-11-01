package model;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.concurrent.ArrayBlockingQueue;

public class BuffertoKafkaWriter implements Runnable {
     private ArrayBlockingQueue<String> buffer;
     private Producer producer;
     private String topic;

    public BuffertoKafkaWriter(ArrayBlockingQueue<String> buffer,Producer producer, String topic) {
        this.buffer = buffer;
        this.topic = topic;
        this.producer = producer;
    }

    @Override
    public void run() {
        while(buffer.size()>0){
           writeToKafkaTopic(buffer.poll());
        }
    }

    public void writeToKafkaTopic(String message){

       // producer.send(new ProducerRecord(topic, message));
        System.out.println("This is thread "+ Thread.currentThread().getId()+ "\n Message =  "+  message);
    }

}
