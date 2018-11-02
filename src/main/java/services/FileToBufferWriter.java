package services;

import model.FileEntriesBuffer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileToBufferWriter {

    public void readFileFromFileToBuffer(String filePath, FileEntriesBuffer buffer) {

        try
        {
            File f = new File(filePath);
            BufferedReader b = new BufferedReader(new FileReader(f));
            String readLine = "";

            System.out.println("Reading file using Buffered Reader");
            while ((readLine = b.readLine()) != null) {
                System.out.println("Reading from file " + readLine);
                buffer.getBuffer().put(readLine);
            }
            buffer.setAllTheFileWasStreamedToBuffer(true);

        } catch( IOException | InterruptedException e)
        {
            e.printStackTrace();
        }
}


}
