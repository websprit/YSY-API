package cn.ysy.open.response;

import com.alibaba.fastjson2.annotation.JSONField;

/**
 * @author wumu
 */
public class CapturePicture {

    @JSONField(name="picUrl")
    public String picUrl;

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
