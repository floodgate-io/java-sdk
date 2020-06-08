package io.floodgate.sdk.io;

import java.io.*;

/**
 * Simple wrapper object
 */
public class FileReader {
    public InputStream getStream(String path) throws FileNotFoundException {
        return new FileInputStream(path);
    }
}
