package cn.ysy.open.api.device.manage;

import cn.ysy.open.api.AbstractAPI;
import cn.ysy.open.constants.ServerConstant;
import cn.ysy.open.http.HttpPostMethod;
import cn.ysy.open.request.RequestInfo;
import cn.ysy.open.response.BasicResponse;
import cn.ysy.open.response.CapturePicture;
import cn.ysy.open.utils.HttpUtil;
import com.alibaba.fastjson2.JSON;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.util.Map;

/**
 */
public class CapturePicApi extends AbstractAPI{
    private String deviceSerial;
    private int channelNo;
    private HttpPostMethod httpPostMethod;

    public CapturePicApi( String accessToken, String deviceSerial) {
        this.url = ServerConstant.CAPTURE_PICTURE;
        this.accessToken = accessToken;
        this.deviceSerial = deviceSerial;
        this.channelNo = 1;
        this.method = RequestInfo.Method.POST;
        this.host = ServerConstant.HOST;
        this.contentType = "application/x-www-form-urlencoded";

        //设置http的head
        HttpUtil httpUtil = new HttpUtil();
        Map<String,Object> headMap = httpUtil.setHeadMap(host,contentType);
        httpPostMethod = new HttpPostMethod(method);
        httpPostMethod.setHeader(headMap);

        //设置http的
        Map<String,Object> bodyMap = httpUtil.setBodyMap(accessToken,deviceSerial,null);
        if (channelNo == 1) {
            bodyMap.put("channelNo",1);
        }
        httpPostMethod.setCompleteUrl(url,bodyMap);
    }

    public BasicResponse<CapturePicture> executeApi() {
        BasicResponse response = null;
        HttpResponse httpResponse = httpPostMethod.execute();

        try {
            response = JSON.parseObject(httpResponse.getEntity().getContent(), BasicResponse.class);
            response.setJson(JSON.toJSONString(response));
            Object data = JSON.parseObject(JSON.toJSONString(response.getDataInternal()), CapturePicture.class);
            response.setData(data);
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
