package cn.ysy.open.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author wumu
 */
public class HttpPostMethod extends BasicHttpMethod{

    public HttpPostMethod(Method method) {
        super(method);
    }

    @Override
    public void setEntity(String json) {
        //super.setEntity(json);
        if (json != null) {
            StringEntity entity = new StringEntity(json, StandardCharsets.UTF_8);
            ((HttpPost) httpRequestBase).setEntity(entity);
        }
    }

    @Override
    public void setEntity(Map<String, String> stringMap, Map<String, String> fileMap) {
        //super.setEntity(stringMap, fileMap);
        this.upload = true;
        Set<Map.Entry<String,StringBody>> stringBodySet = null;
        if (stringMap != null) {
            Set<Map.Entry<String,String>> stringSet = stringMap.entrySet();
            Map<String,StringBody> stringEntryMap = new HashMap<String,StringBody>();
            for (Map.Entry<String,String> entry : stringSet) {
                stringEntryMap.put(entry.getKey(),new StringBody(entry.getValue(), ContentType.create(HTTP.PLAIN_TEXT_TYPE, HTTP.UTF_8)));
            }
            stringBodySet = stringEntryMap.entrySet();
        }
        Set<Map.Entry<String,FileBody>> fileBodySet = null;
        if(fileMap != null) {
            Set<Map.Entry<String,String>> fileSet = fileMap.entrySet();
            Map<String,FileBody> fileBodyMap = new HashMap<String, FileBody>();
            for (Map.Entry<String,String> entry : fileSet) {
                fileBodyMap.put(entry.getKey(),new FileBody(new File(entry.getValue())));
            }
            fileBodySet = fileBodyMap.entrySet();
        }
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        if (fileBodySet != null) {
            for (Map.Entry<String,FileBody> entry : fileBodySet) {
                builder.addPart(entry.getKey(),entry.getValue());
            }
        }
        if(stringBodySet != null) {
            for (Map.Entry<String,StringBody> entry : stringBodySet) {
                builder.addPart(entry.getKey(),entry.getValue());
            }
        }

        HttpEntity reEntity = builder.build();
        ((HttpPost)httpRequestBase).setEntity(reEntity);
    }

    public HttpResponse execute() {
        HttpResponse httpResponse = null;
        httpClient = HttpClients.createDefault();
        try {
            httpResponse = httpClient.execute(httpRequestBase);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK && statusCode != 221) {
                //打印出日志
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return httpResponse;
    }
}
