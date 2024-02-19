package com.ricardo.traker.util;

import org.springframework.http.HttpHeaders;

import java.io.IOException;

public class HttpUtils {

    public static HttpHeaders getFileDownloadHeaders(byte[] localFile, String fileName, String docType) throws IOException {


        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Pragma", "public");
        responseHeaders.set("Expires", "0");
        responseHeaders.set("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        responseHeaders.set("Content-Type", docType);
        responseHeaders.set("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        responseHeaders.set("Content-Transfer-Encoding", "binary");
        responseHeaders.set("Content-Length", Long.toString(localFile.length));

        return responseHeaders;

    }
}
