package cc.saferoad.rmi;/*
@auther S0cke3t
@date 2021-01-29
*/

import java.rmi.Naming;
import static cc.saferoad.rmi.RMIServer.RMI_NAME;
public class RMIClient {
    public static void main(String[] args) {
        try {
            // 查找远程RMI服务
            RMITestInterface rt = (RMITestInterface) Naming.lookup(RMI_NAME);

            // 调用远程接口RMITestInterface类的test方法
            String result = rt.test();

            // 输出RMI方法调用结果
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
