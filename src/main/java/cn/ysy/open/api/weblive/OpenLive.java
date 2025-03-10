package cn.ysy.open.api.weblive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;

import cn.ysy.open.api.AbstractAPI;
import cn.ysy.open.constants.ServerConstant;
import cn.ysy.open.http.HttpPostMethod;
import cn.ysy.open.response.BasicResponse;
import cn.ysy.open.response.OpenLiveResponse;
import cn.ysy.open.utils.HttpUtil;

/**
 * 开通直播功能 接口功能：
 *
 * 该接口用于根据序列号和通道号批量开通直播功能（只支持可观看视频的设备）。
 *
 * 请求地址
 *
 * https://open.ys7.com/api/lapp/live/video/open
 *
 * 请求方式
 *
 * POST
 *
 * 子账户token请求所需最小权限
 */
public class OpenLive extends AbstractAPI {

    private String deviceSerial;
    private HttpPostMethod httpPostMethod;
    private Integer protocol =4;

    /**
     *
     * @param accessToken
     * @param deviceSerial
     * @param protocol 流播放协议，1-ezopen、2-hls、3-rtmp、4-flv，默认为1
     */
    public OpenLive(String accessToken, String deviceSerial,Integer protocol) {
        this.accessToken = accessToken;
        this.deviceSerial = deviceSerial;
        if(protocol!=null){
            this.protocol = protocol;
        }
        this.url = ServerConstant.WEB_LIVE_OPEN_LIVE;

        HttpUtil httpUtil = new HttpUtil();
        Map<String, Object> headMap = httpUtil.setHeadMap(host, contentType);
        httpPostMethod = new HttpPostMethod(method);
        httpPostMethod.setHeader(headMap);

        Map<String, Object> bodyMap = httpUtil.setBodyMap(accessToken, deviceSerial, null);
        bodyMap.put("protocol", protocol);

        httpPostMethod.setCompleteUrl(url, bodyMap);
    }

    public BasicResponse<List<OpenLiveResponse>> executeApi() {
        BasicResponse response = null;
        HttpResponse httpResponse = httpPostMethod.execute();

        try {
            response = JSON.parseObject(httpResponse.getEntity().getContent(), BasicResponse.class);
            response.setJson(JSON.toJSONString(response));

            // 将json对象转化成DeviceList对象数组ArrayList(DeviceListResponse)
            Object bs = response.getData();
            String stringJson = JSON.toJSONString(bs);
            List<OpenLiveResponse> openLiveResponses =
                JSON.parseObject(stringJson, new TypeReference<ArrayList<OpenLiveResponse>>() {});
            response.setData(openLiveResponses);
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
