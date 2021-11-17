package cc.saferoad.apachecc;/*
@auther S0cke3t
@date 2021-01-28
*/
import org.apache.commons.collections.functors.InvokerTransformer;

public class InvokeTransformerTest {

    public static void main(String[] args) {
        // 定义需要执行的本地系统命令
        String cmd = "calc.exe";
        // 构建transformer对象
        InvokerTransformer transformer = new InvokerTransformer(
                "exec", new Class[]{String.class}, new Object[]{cmd}
        );
        // 传入Runtime实例，执行对象转换操作
        transformer.transform(Runtime.getRuntime());
    }
}


