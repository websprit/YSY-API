package cn.ysy.open.api.device.manage;

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

/**
 */
public class AddDeviceApi extends AbstractAPI{
    //private String accessToken;//授权过程获取的access_token
    private String deviceSerial;//设备的序列号
    private String validateCode;//设备验证码，设备机身上的六位大写字母
    private HttpPostMethod httpMethod;//请求方式


    public AddDeviceApi ( String accessToken, String deviceSerial,String validateCode) {
        this.url = ServerConstant.ADD_DEVICE;
        this.accessToken = accessToken;
        this.deviceSerial = deviceSerial;
        this.validateCode = validateCode;

        HttpUtil httpUtil = new HttpUtil();
        Map<String,Object> headMap = httpUtil.setHeadMap(host,contentType);
        httpMethod = new HttpPostMethod(method);
        httpMethod.setHeader(headMap);

        Map<String,Object> bodyMap = httpUtil.setBodyMap(accessToken,deviceSerial,validateCode);
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
