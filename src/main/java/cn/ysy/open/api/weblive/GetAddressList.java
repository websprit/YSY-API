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
import cn.ysy.open.request.RequestInfo;
import cn.ysy.open.response.BasicResponse;
import cn.ysy.open.response.WebLiveAddress;
import cn.ysy.open.utils.HttpUtil;

/**
 * 获取用户下直播视频列表 接口功能
 *
 * 该接口适用于已经开通过直播的用户，用以获取账号下的视频地址列表。（绑定地址）
 *
 * 请求地址
 *
 * https://open.ys7.com/api/lapp/live/video/list
 *
 * 请求方式
 */
public class GetAddressList extends AbstractAPI {

    private String deviceSerial;

    private int pageStart = 0;

    private int pageSize = 10;

    private HttpPostMethod httpPostMethod;

    public GetAddressList(String accessToken) {
        this(accessToken, 0, 10);
    }

    public GetAddressList(String accessToken, int pageStart, int pageSize) {
        this.pageSize = pageSize;
        this.pageStart = pageStart;
        this.url = ServerConstant.WEB_LIVE_ADDRESS_LIST;
        this.method = RequestInfo.Method.POST;
        this.host = ServerConstant.HOST;
        this.contentType = "application/x-www-form-urlencoded";

        // 设置http的head
        HttpUtil httpUtil = new HttpUtil();
        Map<String, Object> headMap = httpUtil.setHeadMap(host, contentType);
        httpPostMethod = new HttpPostMethod(method);
        httpPostMethod.setHeader(headMap);

        // 设置http的body
        Map<String, Object> bodyMap = httpUtil.setBodyMap(accessToken, null, null);
        bodyMap.put("pageStart", pageStart);
        bodyMap.put("pageSize", pageSize);

        httpPostMethod.setCompleteUrl(url, bodyMap);
    }

    public BasicResponse<List<WebLiveAddress>> executeApi() {
        BasicResponse response = null;
        HttpResponse httpResponse = httpPostMethod.execute();

        try {
            response = JSON.parseObject(httpResponse.getEntity().getContent(), BasicResponse.class);
            response.setJson(JSON.toJSONString(response));

            // 将json对象转化成DeviceList对象数组ArrayList(DeviceListResponse)
            Object bs = response.getDataInternal();
            String stringJson = JSON.toJSONString(bs);
            List<WebLiveAddress> webLiveAddressList =
                JSON.parseObject(stringJson, new TypeReference<ArrayList<WebLiveAddress>>() {});
            response.setData(webLiveAddressList);
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
