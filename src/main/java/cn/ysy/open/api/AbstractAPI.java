package cn.ysy.open.api;

import cn.ysy.open.constants.ServerConstant;
import cn.ysy.open.request.RequestInfo;

/**
 */
public  class AbstractAPI {
    public String accessToken;//连接密匙
    public String url;//连接的url
    public RequestInfo.Method method = RequestInfo.Method.POST;//请求方式
    public String host = ServerConstant.HOST;
    public String contentType = "application/x-www-form-urlencoded";


}
