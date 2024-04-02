package io.netty.util;

public class LogUtil {
    public static void log(String taskName) {
        System.out.println("I am a task[== " + taskName + "==], work in thread: [::" + Thread.currentThread().getName() + "::]");
    }
}
