package cn.ysy.open.api.account;

import cn.ysy.open.api.AbstractAPI;
import cn.ysy.open.constants.ServerConstant;
import cn.ysy.open.http.HttpPostMethod;
import cn.ysy.open.response.BaseDeviceResponse;
import cn.ysy.open.response.BasicResponse;
import cn.ysy.open.utils.HttpUtil;
import com.alibaba.fastjson2.JSON;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.util.Map;

public class DeleteStatementApi extends AbstractAPI {

    private String deviceSerial ;
    private String accountId ;
    private HttpPostMethod httpMethod;//请求方式


    public DeleteStatementApi(String accessToken, String accountId, String deviceSerial) {
        this.url = ServerConstant.DELETE_STATEMENT;
        this.accessToken = accessToken;
        this.accountId = accountId;
        this.deviceSerial = deviceSerial;
        HttpUtil httpUtil = new HttpUtil();
        Map<String,Object> headMap = httpUtil.setHeadMap(host,contentType);
        httpMethod = new HttpPostMethod(method);
        httpMethod.setHeader(headMap);

        Map<String,Object> bodyMap = httpUtil.setBodyMap(accessToken,null,null);
        bodyMap.put("accountId",this.accountId);
        bodyMap.put("deviceSerial", this.deviceSerial);
        httpMethod.setCompleteUrl(url,bodyMap);
    }

    public BasicResponse<BaseDeviceResponse> executeApi() {
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
