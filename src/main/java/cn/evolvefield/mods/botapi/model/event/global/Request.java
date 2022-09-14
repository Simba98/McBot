package cn.evolvefield.mods.botapi.model.event.global;

/**
 * Description: 请求类型适用
 * Author: cnlimiter
 * Date: 2022/9/14 16:41
 * Version: 1.0
 */

public class Request extends Message {
    private String requestType;//请求类型

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestType='" + requestType + '\'' +
                "} " + super.toString();
    }
}
