package cn.ysy.open.api.account;

import cn.ysy.open.api.AbstractAPI;
import cn.ysy.open.constants.ServerConstant;
import cn.ysy.open.http.HttpPostMethod;
import cn.ysy.open.response.BaseDeviceResponse;
import cn.ysy.open.response.BasicResponse;
import cn.ysy.open.utils.HttpUtil;
import cn.ysy.open.utils.PasswordUtil;
import com.alibaba.fastjson2.JSON;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.util.Map;

public class UpdatePasswordApi extends AbstractAPI {

    private String accountId;
    private String oldPassword;
    private String newPassword;
    private HttpPostMethod httpMethod;//请求方式


    public UpdatePasswordApi(String accessToken,String accountId, String  oldPassword, String  newPassword) {
        this.url = ServerConstant.GET_ACCOUNT_LIST;
        this.accessToken = accessToken;
        this.accountId = accountId;
        this.oldPassword = PasswordUtil.generate(oldPassword);
        this.newPassword = PasswordUtil.generate(newPassword);
        HttpUtil httpUtil = new HttpUtil();
        Map<String,Object> headMap = httpUtil.setHeadMap(host,contentType);
        httpMethod = new HttpPostMethod(method);
        httpMethod.setHeader(headMap);

        Map<String,Object> bodyMap = httpUtil.setBodyMap(accessToken,null,null);
        bodyMap.put("accountId",this.accountId);
        bodyMap.put("oldPassword",this.oldPassword);
        bodyMap.put("newPassword",this.newPassword);
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
