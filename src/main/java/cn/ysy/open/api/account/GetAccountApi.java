package cn.ysy.open.api.account;

import cn.ysy.open.api.AbstractAPI;
import cn.ysy.open.constants.ServerConstant;
import cn.ysy.open.http.HttpPostMethod;
import cn.ysy.open.response.BasicResponse;
import cn.ysy.open.response.account.AccountInfoResponse;
import cn.ysy.open.utils.HttpUtil;
import com.alibaba.fastjson2.JSON;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.util.Map;

public class GetAccountApi extends AbstractAPI {

    private String accountName; //账号名称
    private String accountId; // 账号id
    private HttpPostMethod httpMethod;//请求方式


    public GetAccountApi ( String accessToken, String accountName,String accountId) {
        this.url = ServerConstant.GET_ACCOUNT_INFO;
        this.accessToken = accessToken;
        this.accountName = accountName;
        this.accountId = accountId;
        if(accountName == null && accountId == null){
            throw new NullPointerException("账号名称和账号id不能同时为空");
        }
        HttpUtil httpUtil = new HttpUtil();
        Map<String,Object> headMap = httpUtil.setHeadMap(host,contentType);
        httpMethod = new HttpPostMethod(method);
        httpMethod.setHeader(headMap);

        Map<String,Object> bodyMap = httpUtil.setBodyMap(accessToken,null,null);
        bodyMap.put("accountName",this.accountName);
        bodyMap.put("accountId",this.accountId);
        httpMethod.setCompleteUrl(url,bodyMap);
    }

    public BasicResponse<AccountInfoResponse> executeApi() {
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
