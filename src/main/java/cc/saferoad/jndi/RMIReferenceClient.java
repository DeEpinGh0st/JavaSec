package cc.saferoad.jndi;/*
@auther S0cke3t
@date 2021-11-18
*/

import javax.naming.InitialContext;
import static cc.saferoad.jndi.RMIReferenceServer.RMI_NAME;
public class RMIReferenceClient {
    public static void main(String[] args) {
        try{
            //初始化Context对象
            InitialContext context = new InitialContext();
            //查询远程RMI绑定对象
            Object o = context.lookup(RMI_NAME);
            System.out.println(o);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
