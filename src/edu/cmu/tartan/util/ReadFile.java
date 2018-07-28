package edu.cmu.tartan.util;

import java.io.FileReader;

public class ReadFile implements ReadFileInterface {
    @Override
    public FileReader read(String filePath) {

        FileReader fileReader;
        try {
            fileReader = new FileReader(filePath);
            return fileReader;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
