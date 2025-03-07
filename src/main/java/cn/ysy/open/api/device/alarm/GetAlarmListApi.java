package cn.ysy.open.api.device.alarm;

import cn.ysy.open.api.AbstractAPI;
import cn.ysy.open.constants.ServerConstant;
import cn.ysy.open.http.HttpPostMethod;
import cn.ysy.open.response.BasicResponse;
import cn.ysy.open.response.device.alarm.AlarmMessage;
import cn.ysy.open.utils.HttpUtil;
import com.alibaba.fastjson2.JSON;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.util.Map;

public class GetAlarmListApi extends AbstractAPI {

    private Long startTime;
    private Long endTime;
    private Integer alarmType =-1;
    private Integer status =2;
    private Integer pageStart = 0;
    private Integer pageSize = 10;

    private HttpPostMethod httpMethod;//请求方式

    public GetAlarmListApi () {
        this.url = ServerConstant.GET_ALARM_LIST;
    }



    public BasicResponse<AlarmMessage> executeApi() {
        HttpUtil httpUtil = new HttpUtil();
        Map<String,Object> headMap = httpUtil.setHeadMap(host,contentType);
        httpMethod = new HttpPostMethod(method);
        httpMethod.setHeader(headMap);
        Map<String,Object> bodyMap = httpUtil.setBodyMap(accessToken,null,null);
        bodyMap.put("startTime",this.startTime);
        bodyMap.put("endTime",this.endTime);
        bodyMap.put("alarmType",this.alarmType);
        bodyMap.put("status",this.status);
        bodyMap.put("pageStart",this.pageStart);
        bodyMap.put("pageSize",this.pageSize);
        httpMethod.setCompleteUrl(url,bodyMap);

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


    public static final class Builder {
        public String accessToken;//连接密匙
        private Long startTime;
        private Long endTime;
        private Integer alarmType =-1;
        private Integer status =2;
        private Integer pageStart = 0;
        private Integer pageSize = 10;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder accessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public Builder startTime(Long startTime) {
            this.startTime = startTime;
            return this;
        }

        public Builder endTime(Long endTime) {
            this.endTime = endTime;
            return this;
        }

        public Builder alarmType(Integer alarmType) {
            this.alarmType = alarmType;
            return this;
        }

        public Builder status(Integer status) {
            this.status = status;
            return this;
        }

        public Builder pageStart(Integer pageStart) {
            this.pageStart = pageStart;
            return this;
        }

        public Builder pageSize(Integer pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public GetAlarmListApi build() {
            GetAlarmListApi getAlarmListApi = new GetAlarmListApi();
            getAlarmListApi.pageSize = this.pageSize;
            getAlarmListApi.status = this.status;
            getAlarmListApi.endTime = this.endTime;
            getAlarmListApi.pageStart = this.pageStart;
            getAlarmListApi.startTime = this.startTime;
            getAlarmListApi.alarmType = this.alarmType;
            return getAlarmListApi;
        }
    }
}
