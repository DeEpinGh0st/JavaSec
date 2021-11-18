package cc.saferoad.jndi;/*
@auther S0cke3t
@date 2021-11-18
*/
import javax.naming.InitialContext;

import static cc.saferoad.jndi.LDAPReferenceServer.LDAP_URL;
public class LDAPReferenceClient {
    public static void main(String[] args) {
        try{
            //初始化context
            InitialContext context = new InitialContext();
            //查询ldap服务的对象
            Object o = context.lookup(LDAP_URL);
            System.out.println(o);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
