package com.qqj.utils;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.Assert;

/**
 * copy from springside
 */
public class ServletUtils {

    // -- Content Type --//
    public static final String TEXT_TYPE = "text/plain";

    public static final String JSON_TYPE = "application/json";

    public static final String XML_TYPE = "text/xml";

    public static final String HTML_TYPE = "text/html";

    public static final String JS_TYPE = "text/javascript";

    public static final String EXCEL_TYPE = "application/vnd.ms-excel";

    // -- Header --//
    public static final String AUTHENTICATION_HEADER = "Authorization";

    // -- Const--//
    public static final long ONE_YEAR_SECONDS = 60 * 60 * 24 * 365;

    public static void setExpiresHeader(HttpServletResponse response,
                                        long expiresSeconds) {
        // Http 1.0 header
        response.setDateHeader("Expires", System.currentTimeMillis()
                + expiresSeconds * 1000);
        // Http 1.1 header
        response.setHeader("Cache-Control", "private, max-age="
                + expiresSeconds);
    }

    public static void setDisableCacheHeader(HttpServletResponse response) {
        // Http 1.0 header
        response.setDateHeader("Expires", 1L);
        response.addHeader("Pragma", "no-cache");
        // Http 1.1 header
        response.setHeader("Cache-Control", "no-cache, no-store, max-age=0");
    }

    public static void setLastModifiedHeader(HttpServletResponse response,
                                             long lastModifiedDate) {
        response.setDateHeader("Last-Modified", lastModifiedDate);
    }

    public static void setEtag(HttpServletResponse response, String etag) {
        response.setHeader("ETag", etag);
    }

    public static boolean checkIfModifiedSince(HttpServletRequest request,
                                               HttpServletResponse response, long lastModified) {
        long ifModifiedSince = request.getDateHeader("If-Modified-Since");
        if ((ifModifiedSince != -1) && (lastModified < ifModifiedSince + 1000)) {
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            return false;
        }
        return true;
    }

    public static boolean checkIfNoneMatchEtag(HttpServletRequest request,
                                               HttpServletResponse response, String etag) {
        String headerValue = request.getHeader("If-None-Match");
        if (headerValue != null) {
            boolean conditionSatisfied = false;
            if (!"*".equals(headerValue)) {
                StringTokenizer commaTokenizer = new StringTokenizer(
                        headerValue, ",");

                while (!conditionSatisfied && commaTokenizer.hasMoreTokens()) {
                    String currentToken = commaTokenizer.nextToken();
                    if (currentToken.trim().equals(etag)) {
                        conditionSatisfied = true;
                    }
                }
            } else {
                conditionSatisfied = true;
            }

            if (conditionSatisfied) {
                response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                response.setHeader("ETag", etag);
                return false;
            }
        }
        return true;
    }

    public static void setFileDownloadHeader(HttpServletResponse response,
                                             String fileName) {
        try {
            // 中文文件名支持
            String encodedfileName = new String(fileName.getBytes(),
                    "ISO8859-1");
            response.setHeader("Content-Disposition", "attachment; filename=\""
                    + encodedfileName + "\"");
        } catch (UnsupportedEncodingException e) {
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> getParametersStartingWith(
            ServletRequest request, String prefix) {
        Assert.notNull(request, "Request must not be null");
        Enumeration paramNames = request.getParameterNames();
        Map<String, Object> params = new TreeMap<String, Object>();
        if (prefix == null) {
            prefix = "";
        }
        while (paramNames != null && paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            if ("".equals(prefix) || paramName.startsWith(prefix)) {
                String unprefixed = paramName.substring(prefix.length());
                String[] values = request.getParameterValues(paramName);
                if (values == null || values.length == 0) {
                    // Do nothing, no values found at all.
                } else if (values.length > 1) {
                    params.put(unprefixed, values);
                } else {
                    params.put(unprefixed, values[0]);
                }
            }
        }
        return params;
    }
}
