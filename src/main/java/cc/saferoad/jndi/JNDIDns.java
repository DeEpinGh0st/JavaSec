package cc.saferoad.jndi;/*
@auther S0cke3t
@date 2021-11-18
*/

import javax.naming.Context;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;

public class JNDIDns {
    public static void main(String[] args) {
        //创建hashtable用于存放环境变量
        Hashtable env = new Hashtable();
        //初始化使用DnsContextFactory的JNDI工厂类
        env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.dns.DnsContextFactory");
        //设置JNDI服务的URL,可以设置为DNS服务器地址
        env.put(Context.PROVIDER_URL,"dns://223.6.6.6/");
        try{
            //创建JNDI目录服务对象
            DirContext dirContext = new InitialDirContext(env);
            //获取指定解析域名的记录
            Attributes attributeBaiDu = dirContext.getAttributes("baidu.com", new String[]{"A"});
            System.out.println(attributeBaiDu);
            Attributes attributeQQ = dirContext.getAttributes("qq.com", new String[]{"A"});
            System.out.println(attributeQQ);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
