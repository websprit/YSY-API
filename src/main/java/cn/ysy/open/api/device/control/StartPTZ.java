package cn.ysy.open.api.device.control;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpResponse;

import com.alibaba.fastjson2.JSON;

import cn.ysy.open.api.AbstractAPI;
import cn.ysy.open.constants.ServerConstant;
import cn.ysy.open.http.HttpPostMethod;
import cn.ysy.open.request.RequestInfo;
import cn.ysy.open.response.BaseDeviceResponse;
import cn.ysy.open.response.BasicResponse;
import cn.ysy.open.utils.HttpUtil;

/**
 */
public class StartPTZ extends AbstractAPI {
    // 设备序列号
    private String deviceSerial;

    // 通道编号
    private int channelNo = 1;
    // 操作命令：0-上，1-下，2-左，3-右，4-左上，5-左下，6-右上，7-右下，8-放大，9-缩小，10-近焦距，11-远焦距
    private int direction;
    // 云台速度：0-慢，1-适中，2-快
    private int speed;

    private HttpPostMethod httpPostMethod;// 请求方式

    public StartPTZ(String accessToken, String deviceSerial, int direction, int speed) {
        this(accessToken, deviceSerial, 1, direction, speed);
    }

    public StartPTZ(String accessToken, String deviceSerial, int channelNo, int direction, int speed) {
        this.url = ServerConstant.START_PTZ;
        this.deviceSerial = deviceSerial;
        this.accessToken = accessToken;
        this.direction = direction;
        this.speed = speed;
        this.method = RequestInfo.Method.POST;
        this.host = ServerConstant.HOST;
        this.contentType = "application/x-www-form-urlencoded";
        this.channelNo = channelNo;

        HttpUtil httpUtil = new HttpUtil();
        Map<String, Object> headMap = httpUtil.setHeadMap(host, contentType);
        httpPostMethod = new HttpPostMethod(method);
        httpPostMethod.setHeader(headMap);

        Map<String, Object> bodyMap = httpUtil.setBodyMap(accessToken, deviceSerial, null);
        bodyMap.put("channelNo", channelNo);
        bodyMap.put("direction", direction);
        bodyMap.put("speed", speed);

        httpPostMethod.setCompleteUrl(url, bodyMap);
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
