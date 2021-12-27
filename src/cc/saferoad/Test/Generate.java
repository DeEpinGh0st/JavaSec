package cc.saferoad.Test;/*
@auther S0cke3t
@date 2021-12-27
*/

import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Generate {

    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("D:\\Projects\\Java\\Java-sec\\target\\classes\\cc\\saferoad\\Test\\FilterShellTest.class");
        byte[] bytes = toByteArray(fileInputStream);
        BASE64Encoder encoder = new BASE64Encoder();
        String B64Str = encoder.encode(bytes).replace("\n", "");
        System.out.println(B64Str);


    }

    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[10240];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }
}
