package cc.saferoad.agent;/*
@auther S0cke3t
@date 2021-11-24
*/

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

public class CrackAgent {
    /**
     * 需要被Hook的类
     */
    private static final String HOOK_CLASS = "cc.saferoad.agent.CrackLicenseTest";

    public static void main(String[] args) {

    }

    public static void premain(String args, final Instrumentation inst) {

        //创建ClassFileTransformer对象
        ClassFileTransformer classFileTransformer = createClassFileTransformer();
        //设置是否允许agent retransform
        inst.addTransformer(classFileTransformer,true);
        //获取所有被JVM加载的类
        Class[] loaderClass = inst.getAllLoadedClasses();
        for (Class clazz : loaderClass){
            String className = clazz.getName();
            if(inst.isModifiableClass(clazz)){
                if(className.equals(HOOK_CLASS)){
                    try{
                        inst.retransformClasses(clazz);
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static ClassFileTransformer createClassFileTransformer(){
        return new ClassFileTransformer() {
            //重写transform
            @Override
            public byte[] transform(ClassLoader classLoader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
                //将Instrumentation格式的classname替换为普通java classname
                className = className.replace('/','.');
                //判断是否是要处理的类
                if(className.equals(HOOK_CLASS)){
                    try{
                        ClassPool classPool = ClassPool.getDefault();
                        //使用javassist将类二进制解析成Ctclass
                        CtClass ctClass = classPool.makeClass(new ByteArrayInputStream(classfileBuffer));
                        //获取要修改的方法 checkExpiry
                        CtMethod ctMethod = ctClass.getDeclaredMethod(
                                "checkExpiry", new CtClass[]{classPool.getCtClass("java.lang.String")}
                        );
                        //在方法执行前插入代码 $1表示方法的第一个参数，以此类推
                        ctMethod.insertBefore("System.out.println(\" Agent插入代码---> License到期时间：\" + $1);");
                        ctMethod.insertBefore("System.out.println(\" Agent插入代码---> 修改返回值\");");
                        //修改方法返回值 注意最后的分号
                        ctMethod.insertAfter("return false;");
                        classfileBuffer = ctClass.toBytecode();
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                }
                return classfileBuffer;
            }
        };
    }
}
