package cc.saferoad.jndi;/*
@auther S0cke3t
@date 2021-11-18
*/

import cc.saferoad.rmi.RMITestInterface;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.rmi.RemoteException;
import java.util.Hashtable;
import static cc.saferoad.rmi.RMIServer.*;

public class JNDIRmi {
    public static void main(String[] args) {
        String providerURL = "rmi://" + RMI_HOST + ":" + RMI_PORT;
        // 创建环境变量对象
        Hashtable env = new Hashtable();
        // 设置JNDI初始化工厂类名
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.rmi.registry.RegistryContextFactory");
        // 设置JNDI提供服务的URL地址
        env.put(Context.PROVIDER_URL, providerURL);
        // 通过JNDI调用远程RMI方法测试
        try {
            // 创建JNDI目录服务对象
            DirContext context = new InitialDirContext(env);
            // 通过命名服务查找远程RMI绑定的RMITestInterface对象
            RMITestInterface testInterface = (RMITestInterface) context.lookup(RMI_NAME);
            // 调用远程的RMITestInterface接口的test方法
            String result = testInterface.test();
            System.out.println(result);
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
