package edu.cmu.tartan.util;

import java.io.FileWriter;
import java.io.IOException;

public class WriteFile implements WriteFileInterface {
    @Override
    public void write(String filepath, String content) {
        try (FileWriter fileWriter = new FileWriter(filepath)){
            fileWriter.write(content);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
