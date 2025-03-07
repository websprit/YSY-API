package cn.ysy.open.response;

import com.alibaba.fastjson2.annotation.JSONField;

/**
 * @author wumu
 */
public class AccessToken {

    @JSONField(name="accessToken")
    public String accessToken;
    @JSONField(name = "expireTime")
    public String expireTime;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }
}
