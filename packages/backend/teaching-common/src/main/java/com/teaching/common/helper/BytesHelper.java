package com.teaching.common.helper;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @Description：
 * @Author：sacher
 * @Create：2023/5/5 18:01
 **/
public class BytesHelper {
    public static final Charset UTF8 = StandardCharsets.UTF_8;

    private static final int EOF = -1, BUF_LENGTH = 8192;
    public static ServletInputStream castServletInputStream(InputStream stream) {
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }
            @Override
            public boolean isReady() {
                return false;
            }
            @Override
            public void setReadListener(ReadListener readListener) {}
            @Override
            public int read() throws IOException {
                return stream.read();
            }
        };
    }
    public static byte[] utf8Bytes(final String info) {
        if(StringHelper.isBlank(info)){
            return new byte[0];
        }
        return info.getBytes(UTF8);
    }

    private static void copyLarge(final Reader input, final Writer output, final char[] buffer) throws IOException {
        int n; while (EOF != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
    }
    public static String string(final InputStream input){
        return string(input, UTF8);
    }
    private static String string(final InputStream input, final Charset charset) {
        final StringWriter sw = new StringWriter();
        final InputStreamReader in = new InputStreamReader(input, charset);
        try {
            copyLarge(in, sw, new char[BUF_LENGTH]);
            return sw.toString();
        } catch (Exception e) {
            throw new RuntimeException("input to string error", e);
        } finally {
            close(in);
            close(sw);
        }
    }
    public static void close(final AutoCloseable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (Exception ioe) {
            throw new RuntimeException("close stream error: ", ioe);
        }
    }
}
