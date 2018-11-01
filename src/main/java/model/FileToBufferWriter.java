package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;

public class FileToBufferWriter {

    public void readFileFromFileToBuffer(String filePath, ArrayBlockingQueue<String> buffer) {

        try
        {
            File f = new File(filePath);
            BufferedReader b = new BufferedReader(new FileReader(f));
            String readLine = "";

            System.out.println("Reading file using Buffered Reader");
            while ((readLine = b.readLine()) != null) {
                System.out.println(readLine);
                buffer.put(readLine);
            }

        } catch( IOException | InterruptedException e)
        {
            e.printStackTrace();
        }
}


}
