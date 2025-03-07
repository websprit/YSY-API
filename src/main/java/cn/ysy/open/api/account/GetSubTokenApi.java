package cn.ysy.open.api.account;

import cn.ysy.open.api.AbstractAPI;
import cn.ysy.open.constants.ServerConstant;
import cn.ysy.open.http.HttpPostMethod;
import cn.ysy.open.response.AccessToken;
import cn.ysy.open.response.BasicResponse;
import cn.ysy.open.utils.HttpUtil;
import com.alibaba.fastjson2.JSON;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.util.Map;

public class GetSubTokenApi extends AbstractAPI {

    private String accountId;
    private HttpPostMethod httpMethod;//请求方式


    public GetSubTokenApi ( String accessToken, String accountId) {
        this.url = ServerConstant.GET_SUB_TOKEN;
        this.accessToken = accessToken;
        this.accountId = accountId;
        HttpUtil httpUtil = new HttpUtil();
        Map<String,Object> headMap = httpUtil.setHeadMap(host,contentType);
        httpMethod = new HttpPostMethod(method);
        httpMethod.setHeader(headMap);

        Map<String,Object> bodyMap = httpUtil.setBodyMap(accessToken,null,null);
        bodyMap.put("accountId",this.accountId);
        httpMethod.setCompleteUrl(url,bodyMap);
    }

    public BasicResponse<AccessToken> executeApi() {
        BasicResponse response = null;
        HttpResponse httpResponse = httpMethod.execute();

        try {
            response = JSON.parseObject(httpResponse.getEntity().getContent(),BasicResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            httpMethod.httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
