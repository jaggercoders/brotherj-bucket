package com.brotherj.brotherjutil.wrapper;



import com.brotherj.brotherjutil.util.AESUtil;
import org.apache.commons.io.IOUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class BodyCachingHttpServletRequestWrapper extends HttpServletRequestWrapper {


    private byte[] requestEncryptionBody;

    private byte[] body;

    private ServletInputStreamWrapper inputStreamWrapper;

    public BodyCachingHttpServletRequestWrapper(HttpServletRequest request, String aesKey) throws IOException {
        super(request);
        this.requestEncryptionBody = IOUtils.toByteArray(request.getInputStream());
        this.body = AESUtil.decrypt(new String(this.requestEncryptionBody, "utf-8"), aesKey).getBytes("utf-8");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.body);
        this.inputStreamWrapper = new ServletInputStreamWrapper(byteArrayInputStream);
        resetInputStream();
    }

    private void resetInputStream() {
        this.inputStreamWrapper.setInputStream(new ByteArrayInputStream(this.body != null ? this.body : new byte[0]));
    }

    public byte[] getRequestEncryptionBody() {
        return this.requestEncryptionBody;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return this.inputStreamWrapper;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.inputStreamWrapper));
    }


    private static class ServletInputStreamWrapper extends ServletInputStream {

        private InputStream inputStream;

        @Override
        public boolean isFinished() {
            return true;
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setReadListener(ReadListener readListener) {

        }

        @Override
        public int read() throws IOException {
            return this.inputStream.read();
        }

        public InputStream getInputStream() {
            return inputStream;
        }

        public void setInputStream(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        public ServletInputStreamWrapper(InputStream inputStream) {
            this.inputStream = inputStream;
        }
    }
}