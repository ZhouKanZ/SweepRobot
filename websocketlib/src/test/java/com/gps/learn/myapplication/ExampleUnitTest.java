package com.gps.learn.myapplication;

import com.alibaba.fastjson.JSONObject;
import com.gps.ros.response.ServiceResponse;
import com.gps.ros.response.SubscribeResponse;

import org.junit.Test;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    /**
     *  测试json的转换
     */
    @Test
    public void testJsonConvert(){

        String testStr1 = "{\n" +
                "    \"topic\": \"/cmd_vel\",\n" +
                "    \"msg\": {\n" +
                "        \"linear\": {\n" +
                "            \"y\": 0,\n" +
                "            \"x\": 0.25,\n" +
                "            \"z\": 0\n" +
                "        },\n" +
                "        \"angular\": {\n" +
                "            \"y\": 0,\n" +
                "            \"x\": 0,\n" +
                "            \"z\": 0.2863157894736843\n" +
                "        }\n" +
                "    },\n" +
                "    \"op\": \"publish\"\n" +
                "}";

        JSONObject jsonObject = (JSONObject) JSONObject.parse(testStr1);
        String op = jsonObject.getString("op");
        switch (op){
            case "publish":
                SubscribeResponse subscriberesponse = jsonObject.toJavaObject(SubscribeResponse.class);
                System.out.println("SubscribeResponse:"+JSONObject.toJSONString(subscriberesponse));
                break;
            case "service_response":
                ServiceResponse serviceresponse = jsonObject.toJavaObject(ServiceResponse.class);
                System.out.println("serviceresponse:"+JSONObject.toJSONString(serviceresponse));
                break;
            default:
                System.out.println("error");
                break;
        }

    }
}