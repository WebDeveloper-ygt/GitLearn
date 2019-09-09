package com.quiz.common.interceptors;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

public class GZipReaderInterceptor implements ReaderInterceptor {
    @Override
    public Object aroundReadFrom(ReaderInterceptorContext context) throws IOException, WebApplicationException {

        MultivaluedMap<String, String> headers = context.getHeaders();
        headers.add("Accept-Encoding", "gzip");

        InputStream inputStream = context.getInputStream();
        GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);
        context.setInputStream(gzipInputStream);
        return context.proceed();
    }
}
