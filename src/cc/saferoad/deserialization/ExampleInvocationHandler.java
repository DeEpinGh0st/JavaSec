package cc.saferoad.deserialization;/*
@auther S0cke3t
@date 2022-01-21
*/

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

public class ExampleInvocationHandler implements InvocationHandler {

    protected Map map;
     public ExampleInvocationHandler(Map map){
         this.map = map;
     }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(method.getName().compareTo("get") == 0){
            System.out.println("Hook method :" + method.getName());
            return "Hacked Object";
        }

        return method.invoke(this.map,args);
    }
}
