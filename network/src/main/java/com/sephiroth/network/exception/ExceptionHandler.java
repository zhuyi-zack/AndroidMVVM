package com.sephiroth.network.exception;

import android.net.ParseException;

import com.google.gson.JsonParseException;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.net.ConnectException;

import retrofit2.HttpException;

public class ExceptionHandler {
    public static ResponseThrowable handleException(Throwable throwable) {
        ResponseThrowable responseThrowable = null;
        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            responseThrowable = new ResponseThrowable(throwable, httpException.code());
            responseThrowable.message = "网络异常";

        } else if (throwable instanceof ServerException) {
            ServerException resultException = (ServerException) throwable;
            responseThrowable = new ResponseThrowable(resultException, resultException.code);
            responseThrowable.message = resultException.message;
        } else if (throwable instanceof JsonParseException
                || throwable instanceof JSONException
                || throwable instanceof ParseException) {
            responseThrowable = new ResponseThrowable(throwable, ERROR.PARSE_ERROR);
            responseThrowable.message = "解析错误";
        } else if (throwable instanceof ConnectException) {
            responseThrowable = new ResponseThrowable(throwable, ERROR.NETWORD_ERROR);
            responseThrowable.message = "连接失败";
        } else if (throwable instanceof javax.net.ssl.SSLHandshakeException) {
            responseThrowable = new ResponseThrowable(throwable, ERROR.SSL_ERROR);
            responseThrowable.message = "证书验证失败";
        } else if (throwable instanceof ConnectTimeoutException) {
            responseThrowable = new ResponseThrowable(throwable, ERROR.TIMEOUT_ERROR);
            responseThrowable.message = "连接超时";
        } else if (throwable instanceof java.net.SocketTimeoutException) {
            responseThrowable = new ResponseThrowable(throwable, ERROR.TIMEOUT_ERROR);
            responseThrowable.message = "连接超时";
        } else {
            responseThrowable = new ResponseThrowable(throwable, ERROR.UNKNOWN);
            responseThrowable.message = "未知异常";
        }
        return responseThrowable;
    }

    /**
     * 约定异常
     */
    public class ERROR {
        /**
         * 未知错误
         */
        public static final int UNKNOWN = 1000;
        /**
         * 解析错误
         */
        public static final int PARSE_ERROR = 1001;
        /**
         * 网络错误
         */
        public static final int NETWORD_ERROR = 1002;
        /**
         * 协议出错
         */
        public static final int HTTP_ERROR = 1003;

        /**
         * 证书出错
         */
        public static final int SSL_ERROR = 1005;

        /**
         * 连接超时
         */
        public static final int TIMEOUT_ERROR = 1006;
    }

    public static class ResponseThrowable extends Exception {
        public int code;
        public String message;

        public ResponseThrowable(Throwable throwable, int code) {
            super(throwable);
            this.code = code;
        }
    }

    public static class ServerException extends RuntimeException {
        public int code;
        public String message;
    }
}
