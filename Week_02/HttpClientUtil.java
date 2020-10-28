package io.github.kimmking.netty.server;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.SocketConfig;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 *     httpclient
 * </p>
 *
 * @author yangbo
 * @date 2020/10/27 11:47 上午
 **/
public class HttpClientUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class.getSimpleName());
    private static final int HTTP_TIMEOUT = 10000;
    private static final int HTTP_MAX_TOTAL = 1000;
    private static final int HTTP_MAX_PERROUTE = 500;
    private static final int HTTP_CON_TIMEOUT = 10000;
    private static final int HTTP_CON_REQ_TIMEOUT = 500;
    private static final String DEF_CHARSET = "UTF-8";
    private static int minTimeSecond = 0;
    private static CloseableHttpClient closeableHttpClient = null;
    private static RequestConfig defaultRequestConfig = null;

    static {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).build();
        connectionManager.setDefaultSocketConfig(socketConfig);
        connectionManager.setMaxTotal(HTTP_MAX_TOTAL);
        connectionManager.setDefaultMaxPerRoute(HTTP_MAX_PERROUTE);
        defaultRequestConfig = RequestConfig.custom().setSocketTimeout(HTTP_TIMEOUT).setConnectTimeout(HTTP_CON_TIMEOUT).setConnectionRequestTimeout(HTTP_CON_REQ_TIMEOUT).build();
        closeableHttpClient = HttpClients.custom().setConnectionManager(connectionManager).setDefaultRequestConfig(defaultRequestConfig).build();
    }


    public static String get(String url, int timeout) throws Exception{
        if (timeout < 0) {
            throw new IllegalArgumentException("timeout is not right :" + timeout);
        }
        RequestConfig requestConfig = RequestConfig.copy(defaultRequestConfig).setSocketTimeout(timeout).build();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        long beginTime = System.currentTimeMillis();
        CloseableHttpResponse response = closeableHttpClient.execute(httpGet);
        long costTime =  System.currentTimeMillis() - beginTime;
        if (costTime>=minTimeSecond){
            logger.info("costTime="+costTime+"ms,url="+url);
        }
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity, DEF_CHARSET);
        EntityUtils.consume(entity);
        return result;
    }

    public static String get(String url, Header header) throws Exception {
        RequestConfig requestConfig = RequestConfig.copy(defaultRequestConfig).setSocketTimeout(HTTP_TIMEOUT).build();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader(header);
        httpGet.setConfig(requestConfig);

        return get(httpGet, url);
    }

    private static String get(HttpGet httpGet, String url) throws Exception {
        long beginTime = System.currentTimeMillis();
        CloseableHttpResponse response = closeableHttpClient.execute(httpGet);
        long costTime = System.currentTimeMillis() - beginTime;
        if (costTime >= minTimeSecond) {
            logger.info("costTime=" + costTime + "ms,url=" + url);
        }
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity, DEF_CHARSET);
        EntityUtils.consume(entity);
        return result;
    }

    public static String get(String url) throws Exception{
        return get(url, HTTP_TIMEOUT);
    }

    public static String post(String url, String content) throws Exception{
        HttpEntity httpEntity = new StringEntity(content, DEF_CHARSET);
        return post(url, httpEntity);
    }

    public static String post(String url, HttpEntity httpEntity) throws Exception {
        RequestConfig config = RequestConfig.copy(defaultRequestConfig).setSocketTimeout(HTTP_TIMEOUT).build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(config);
        httpPost.setEntity(httpEntity);
        long beginTime = System.currentTimeMillis();
        CloseableHttpResponse response = closeableHttpClient.execute(httpPost);
        long costTime =  System.currentTimeMillis() - beginTime;
        if (costTime>=minTimeSecond){
            logger.info("costTime="+costTime+"ms,url="+url);
        }
        HttpEntity responseEntity = response.getEntity();
        String rt = EntityUtils.toString(responseEntity, DEF_CHARSET);
        EntityUtils.consume(responseEntity);
        return rt;
    }


    public static void main(String[] args) {
        try {
            String res = HttpClientUtil.get("http://localhost:8808/test");
            logger.info("res==:,{}",res);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}