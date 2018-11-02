package services;

import model.FileEntriesBuffer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;


public class BuffertoKafkaWriter implements Runnable {
     private FileEntriesBuffer buffer;
     private Producer producer;
     private String topic;

    public BuffertoKafkaWriter(FileEntriesBuffer buffer,Producer producer, String topic) {
        this.buffer = buffer;
        this.topic = topic;
        this.producer = producer;
    }

    @Override
    public void run() {
        while(true){
            if(buffer.getBuffer().size()>0)
              writeToKafkaTopic(buffer.getBuffer().poll());
            else{
                if(buffer.allTheFileWasStreamedToBuffer()){
                    break;
                }
                else
                    continue;
            }
        }
    }

    public void writeToKafkaTopic(String message){

        producer.send(new ProducerRecord(topic, message));
        System.out.println(" Writing by thread "+ Thread.currentThread().getId()+ "   "+  message);
    }

}
