package com.cubead.ca.common.file;

import java.io.File;
import java.io.IOException;

public interface IWriter {
    public static enum FlushMode {
        BATCH, QUICK;
    }

    public void write(File file, byte[] data) throws IOException;

    public void write(String filename, byte[] data) throws IOException;
}
