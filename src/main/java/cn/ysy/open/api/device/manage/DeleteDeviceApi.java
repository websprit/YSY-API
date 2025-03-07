package cn.ysy.open.api.device.manage;

import cn.ysy.open.api.AbstractAPI;
import cn.ysy.open.constants.ServerConstant;
import cn.ysy.open.http.HttpPostMethod;
import cn.ysy.open.request.RequestInfo;
import cn.ysy.open.response.BaseDeviceResponse;
import cn.ysy.open.response.BasicResponse;
import cn.ysy.open.utils.HttpUtil;
import com.alibaba.fastjson2.JSON;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.util.Map;

/**
 */
public class DeleteDeviceApi extends AbstractAPI {

    //private String accessToken;
    private String deviceSerial;
    private HttpPostMethod httpPostMethod;

    public DeleteDeviceApi(String accessToken,String deviceSerial) {
        this.url = ServerConstant.DELETE_DEVICE;
        this.accessToken = accessToken;
        this.deviceSerial = deviceSerial;
        this.method = RequestInfo.Method.POST;
        this.host = ServerConstant.HOST;
        this.contentType = "application/x-www-form-urlencoded";

        //设置http的header
        HttpUtil httpUtil = new HttpUtil();
        Map<String,Object> headMap = httpUtil.setHeadMap(host,contentType);
        httpPostMethod = new HttpPostMethod(method);
        httpPostMethod.setHeader(headMap);

        //设置http的body部分
        Map<String,Object> bodyMap = httpUtil.setBodyMap(accessToken,deviceSerial,null);

        //设置完整的url地址
        httpPostMethod.setCompleteUrl(url,bodyMap);
    }

    public BasicResponse<BaseDeviceResponse> executeApi() {
        BasicResponse response = null;
        HttpResponse httpResponse = httpPostMethod.execute();

        try {
            response = JSON.parseObject(httpResponse.getEntity().getContent(), BasicResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            httpPostMethod.httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
