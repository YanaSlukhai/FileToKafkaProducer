package model;

import java.util.concurrent.ArrayBlockingQueue;

public class FileEntriesBuffer {
    private  ArrayBlockingQueue<String> buffer;
    private volatile Boolean allTheFileWasStreamedToBuffer = false;

    public Boolean allTheFileWasStreamedToBuffer() {
        return allTheFileWasStreamedToBuffer;
    }

    public void setAllTheFileWasStreamedToBuffer(Boolean allTheFileWasStreamedToBuffer) {
        this.allTheFileWasStreamedToBuffer = allTheFileWasStreamedToBuffer;
    }

    public FileEntriesBuffer(ArrayBlockingQueue<String> buffer) {
        this.buffer = buffer;
    }

    public ArrayBlockingQueue<String> getBuffer() {
        return buffer;
    }
}
