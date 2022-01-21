package cc.saferoad.deserialization;/*
@auther S0cke3t
@date 2022-01-21
*/

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class InvocationApp {
    public static void main(String[] args) {
        InvocationHandler handler = new ExampleInvocationHandler(new HashMap());
        Map proxyMap = (Map) Proxy.newProxyInstance(Map.class.getClassLoader(),new Class[]{Map.class},handler);
        proxyMap.put("hello","world");
        String result = (String) proxyMap.get("hello");
        System.out.println(result);
    }
}
