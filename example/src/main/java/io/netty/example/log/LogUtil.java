package io.netty.example.log;

public class LogUtil {
    public static void log(String taskName) {
        System.out.printf("I am a task[== " + taskName + "==], work in thread: [::" + Thread.currentThread().getName() + "::]");
    }
}
