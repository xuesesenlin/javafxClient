package org.fx.urils;

import java.text.SimpleDateFormat;

/**
 * @author ld
 * @name
 * @table
 * @remarks
 */
public class LoggerUtil {

    private String className;
    private String methodName;
    private String message;
    private String date;

    public String getClassName() {
        return className;
    }

    public void setClassName(Class className) {
        this.className = className.getSimpleName();
        setDate(System.currentTimeMillis());
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(long date) {
        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-DD H24:mm:ss");
        this.date = format.format((date / 1000) + "");
    }

    public LoggerUtil() {
        super();
    }

    public LoggerUtil(Class className, String methodName, String message) {
        this.className = className.getSimpleName();
        this.methodName = methodName;
        this.message = message;
        this.date = date;
    }

    @Override
    public String toString() {
        return "LoggerUtil{" +
                "className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", message='" + message + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
