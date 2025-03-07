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

    private String source;
    private HttpPostMethod httpPostMethod;

    public OpenLive(String accessToken, String source) {
        this.accessToken = accessToken;
        this.source = source;
        this.url = ServerConstant.WEB_LIVE_OPEN_LIVE;

        HttpUtil httpUtil = new HttpUtil();
        Map<String, Object> headMap = httpUtil.setHeadMap(host, contentType);
        httpPostMethod = new HttpPostMethod(method);
        httpPostMethod.setHeader(headMap);

        Map<String, Object> bodyMap = httpUtil.setBodyMap(accessToken, null, null);
        bodyMap.put("source", source);

        httpPostMethod.setCompleteUrl(url, bodyMap);
    }

    public BasicResponse<List<OpenLiveResponse>> executeApi() {
        BasicResponse response = null;
        HttpResponse httpResponse = httpPostMethod.execute();

        try {
            response = JSON.parseObject(httpResponse.getEntity().getContent(), BasicResponse.class);
            response.setJson(JSON.toJSONString(response));

            // 将json对象转化成DeviceList对象数组ArrayList(DeviceListResponse)
            Object bs = response.getDataInternal();
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
