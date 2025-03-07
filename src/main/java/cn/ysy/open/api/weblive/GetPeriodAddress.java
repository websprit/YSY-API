package cn.ysy.open.api.weblive;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpResponse;

import com.alibaba.fastjson2.JSON;

import cn.ysy.open.api.AbstractAPI;
import cn.ysy.open.constants.ServerConstant;
import cn.ysy.open.http.HttpPostMethod;
import cn.ysy.open.request.RequestInfo;
import cn.ysy.open.response.BasicResponse;
import cn.ysy.open.response.WebLiveAddress;
import cn.ysy.open.utils.HttpUtil;

/**
 * 获取指定有效期的直播地址 接口功能：
 *
 * 该接口适用于已经开通过直播的用户，用以根据设备序列号和通道号获取指定有效期的直播地址，可用于防盗链。（绑定地址）
 *
 * 请求地址
 *
 * https://open.ys7.com/api/lapp/live/address/limited
 *
 * 请求方式
 *
 * POST
 */
public class GetPeriodAddress extends AbstractAPI {

    private String deviceSerial;
    private int channelNo = 1;
    // 单位：秒，最小为300，单位秒数，最大默认62208000（即720天），最小默认300（即5分钟）
    private int expireTime = 300;

    private HttpPostMethod httpPostMethod;

    public GetPeriodAddress(String accessToken, String deviceSerial, int channelNo, int expireTime) {
        this.deviceSerial = deviceSerial;
        this.expireTime = expireTime;
        this.url = ServerConstant.WEB_LIVE_PERIOD_ADDRESS;
        this.method = RequestInfo.Method.POST;
        this.host = ServerConstant.HOST;
        this.channelNo = channelNo;
        this.contentType = "application/x-www-form-urlencoded";

        // 设置http的head
        HttpUtil httpUtil = new HttpUtil();
        Map<String, Object> headMap = httpUtil.setHeadMap(host, contentType);
        httpPostMethod = new HttpPostMethod(method);
        httpPostMethod.setHeader(headMap);

        // 设置http的body
        Map<String, Object> bodyMap = httpUtil.setBodyMap(accessToken, deviceSerial, null);
        if (this.expireTime > 300) {
            bodyMap.put("expireTime", expireTime);
        }
        bodyMap.put("channelNo", channelNo);

        httpPostMethod.setCompleteUrl(url, bodyMap);
    }

    public BasicResponse<WebLiveAddress> executeApi() {
        BasicResponse response = null;
        HttpResponse httpResponse = httpPostMethod.execute();

        try {
            response = JSON.parseObject(httpResponse.getEntity().getContent(), BasicResponse.class);
            response.setJson(JSON.toJSONString(response));

            // 将json对象转化成DeviceList对象数组ArrayList(DeviceListResponse)
            Object bs = response.getDataInternal();
            String stringJson = JSON.toJSONString(bs);
            WebLiveAddress webLiveAddress = JSON.parseObject(stringJson, WebLiveAddress.class);
            response.setData(webLiveAddress);
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
