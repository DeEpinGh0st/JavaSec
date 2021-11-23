package cc.saferoad.agent;/*
@auther S0cke3t
@date 2021-11-23
*/

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CrackLicenseTest {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static boolean checkExpiry(String expireDate) {
        try {
            Date date = DATE_FORMAT.parse(expireDate);

            // 检测当前系统时间早于License授权截至时间
            if (new Date().before(date)) {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return true;
    }
    static class Mythread extends Thread  {

        @Override
        public void run() {
            final String expireDate = "2020-10-01 00:00:00";
            while (true) {
                try {
                    String time = "[" + DATE_FORMAT.format(new Date()) + "] ";

                    // 检测license是否已经过期
                    if (checkExpiry(expireDate)) {
                        System.err.println(time + "您的授权已过期，请重新购买授权！");
                    } else {
                        System.out.println(time + "您的授权正常，截止时间为：" + expireDate);
                    }

                    // sleep 5秒
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void main(String[] args) {
        Mythread mythread = new Mythread();
        mythread.start();
    }
}
