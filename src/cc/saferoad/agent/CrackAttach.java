package cc.saferoad.agent;/*
@auther S0cke3t
@date 2021-11-24
*/

import java.io.File;
import java.lang.instrument.Instrumentation;
import java.net.URL;
import java.util.List;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

public class CrackAttach {
    /**
     * 需要被Hook的类
     */
    private static final String HOOK_CLASS = "cc.saferoad.agent.CrackLicenseTest";

    public static void main(String[] args) {
        if (args.length == 0) {
            List<VirtualMachineDescriptor> list = VirtualMachine.list();

            for (VirtualMachineDescriptor desc : list) {
                System.out.println("进程ID：" + desc.id() + "，进程名称：" + desc.displayName());
            }

            return;
        }

        // Java进程ID
        String pid = args[0];

        try {
            // 注入到JVM虚拟机进程
            VirtualMachine vm = VirtualMachine.attach(pid);

            // 获取当前Agent的jar包路径
            URL agentURL = CrackAttach.class.getProtectionDomain().getCodeSource().getLocation();
            String agentPath = new File(agentURL.toURI()).getAbsolutePath();

            // 注入Agent到目标JVM
            vm.loadAgent(agentPath);
            vm.detach();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void agentmain(String args, final Instrumentation inst) {
        CrackAgent.premain(args,inst);
    }
}
