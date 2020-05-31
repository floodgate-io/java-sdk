package io.floodgate.sdk.io;

import java.io.*;

public class FileReader {
    public InputStream getStream(String path) throws FileNotFoundException {
        return new FileInputStream(path);
    }
}
