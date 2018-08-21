package com.duke.mvp.util;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.duke.mvp.BuildConfig;
import com.wenming.library.save.imp.LogWriter;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * author : Duke
 * date   : 2018/8/21
 * explain   :
 * version: 1.0
 */
public class Lg {

    private static Context context;
    private static String pckName;

    public static class BaseConfig {
        protected int minimumLogLevel = BuildConfig.DEBUG ? Log.VERBOSE : Log.ASSERT;

        protected String packageName = "";

        protected String scope = "";

        protected BaseConfig() {

        }

        public BaseConfig(Application context) {
            try {
                packageName = context.getPackageName();
                scope = packageName.toUpperCase();

                Lg.d("Configuring Logging, minimum log level is %s", logLevelToString(minimumLogLevel));

            } catch (Exception e) {
                Log.e(packageName, "Error configuring logger", e);
            }
        }
    }

    /**
     * Default implementation logs to android.util.Log
     */
    public static class Print {
        public int println(int priority, String msg) {
            return Log.println(priority, getScope(5), processMessage(msg));
        }

        public int broadcast(int priority, String msg) {
            if (context != null) {
                try {
                    Intent i = new Intent();
                    i.setAction("com.gaopeng.wkd");
                    i.putExtra("package", pckName);
                    i.putExtra("priority", priority);
                    i.putExtra("msg", msg);
                    context.sendBroadcast(i);
                } catch (Throwable t) {
                }
            }
            return 0;
        }

        protected String processMessage(String msg) {
            if (config.minimumLogLevel <= Log.DEBUG)
                msg = String.format("%s %s", Thread.currentThread().getName(), msg);
            return msg;
        }

        protected static String getScope(int skipDepth) {
            if (config.minimumLogLevel <= Log.DEBUG) {
                StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
                if (skipDepth >= 0 && skipDepth < stackTrace.length) {
                    final StackTraceElement trace = stackTrace[skipDepth];
                    String fileName = trace.getFileName();
                    if (fileName == null || fileName.length() <= 0) {
                        fileName = trace.getClassName();
                    } else {
                        fileName = config.scope + "/" + fileName;
                    }

                    int lineNum = trace.getLineNumber();
                    String source = String.valueOf(lineNum);
                    if (lineNum < 0) {
                        source = trace.getMethodName();
                        if (source == null || source.length() <= 0) {
                            source = "Unknown Source";
                        }
                    }

                    return fileName + "(" + source + ")";
                }
            }

            return config.scope;
        }

    }

    /**
     * config is initially set to BaseConfig() with sensible defaults, then replaced by BaseConfig(ContextSingleton) during guice static injection pass.
     */
    protected static Lg.BaseConfig config = new Lg.BaseConfig();
    /**
     * print is initially set to Print(), then replaced by guice during static injection pass. This allows overriding where the log message is delivered to.
     */
    protected static Lg.Print print = new Lg.Print();

    private Lg() {
    }

    public static void setContext(Context context) {
        Lg.context = context.getApplicationContext();
        Lg.pckName = context.getPackageName();
    }

    public static int v(Throwable t) {
        return config.minimumLogLevel <= Log.VERBOSE ? print.println(Log.VERBOSE, Log.getStackTraceString(t)) : 0;
    }

    public static int v(Object format, Object... args) {
        if (config.minimumLogLevel > Log.VERBOSE)
            return 0;

        final String s = format.toString();
        final String message = args.length > 0 ? String.format(s, args) : s;
        return print.println(Log.VERBOSE, message);
    }

    public static int v(Throwable throwable, Object format, Object... args) {
        if (config.minimumLogLevel > Log.VERBOSE)
            return 0;

        final String s = format.toString();
        final String message = (args.length > 0 ? String.format(s, args) : s) + '\n' + Log.getStackTraceString(throwable);
        return print.println(Log.VERBOSE, message);
    }

    public static int d(Throwable t) {
        return config.minimumLogLevel <= Log.DEBUG
                ? print.println(Log.DEBUG, Log.getStackTraceString(t))
                : print.broadcast(Log.DEBUG, Log.getStackTraceString(t));
    }

    public static int d(Object format, Object... args) {
        if (config.minimumLogLevel > Log.DEBUG) {
            final String s = format.toString();
            final String message = args.length > 0 ? String.format(s, args) : s;
            return print.broadcast(Log.DEBUG, message);
        }

        final String s = format.toString();
        final String message = args.length > 0 ? String.format(s, args) : s;
        return print.println(Log.DEBUG, message);
    }

    public static int d(Throwable throwable, Object format, Object... args) {
        if (config.minimumLogLevel > Log.DEBUG) {
            final String s = format.toString();
            final String message = (args.length > 0 ? String.format(s, args) : s) + '\n' + Log.getStackTraceString(throwable);
            return print.broadcast(Log.DEBUG, message);
        }

        final String s = format.toString();
        final String message = (args.length > 0 ? String.format(s, args) : s) + '\n' + Log.getStackTraceString(throwable);
        return print.println(Log.DEBUG, message);
    }

    public static int i(Throwable t) {
        return config.minimumLogLevel <= Log.INFO
                ? print.println(Log.INFO, Log.getStackTraceString(t))
                : print.broadcast(Log.INFO, Log.getStackTraceString(t));
    }

    public static int i(Object format, Object... args) {
        if (config.minimumLogLevel > Log.INFO) {
            final String s = format.toString();
            final String message = args.length > 0 ? String.format(s, args) : s;
            return print.broadcast(Log.INFO, message);
        }

        final String s = format.toString();
        final String message = args.length > 0 ? String.format(s, args) : s;
        return print.println(Log.INFO, message);
    }

    public static int i(Throwable throwable, Object format, Object... args) {
        if (config.minimumLogLevel > Log.INFO) {
            final String s = format.toString();
            final String message = (args.length > 0 ? String.format(s, args) : s) + '\n' + Log.getStackTraceString(throwable);
            return print.broadcast(Log.INFO, message);
        }

        final String s = format.toString();
        final String message = (args.length > 0 ? String.format(s, args) : s) + '\n' + Log.getStackTraceString(throwable);
        return print.println(Log.INFO, message);
    }

    public static int w(Throwable t) {
        return config.minimumLogLevel <= Log.WARN ? print.println(Log.WARN, Log.getStackTraceString(t)) : 0;
    }

    public static int w(Object format, Object... args) {
        if (config.minimumLogLevel > Log.WARN)
            return 0;

        final String s = format.toString();
        final String message = args.length > 0 ? String.format(s, args) : s;
        return print.println(Log.WARN, message);
    }

    public static int w(Throwable throwable, Object format, Object... args) {
        if (config.minimumLogLevel > Log.WARN)
            return 0;

        final String s = format.toString();
        final String message = (args.length > 0 ? String.format(s, args) : s) + '\n' + Log.getStackTraceString(throwable);
        return print.println(Log.WARN, message);
    }

    public static int e(Throwable t) {
        return config.minimumLogLevel <= Log.ERROR
                ? print.println(Log.ERROR, Log.getStackTraceString(t))
                : print.broadcast(Log.ERROR, Log.getStackTraceString(t));
    }

    public static int e(Object format, Object... args) {
        if (config.minimumLogLevel > Log.ERROR) {
            final String s = format.toString();
            final String message = args.length > 0 ? String.format(s, args) : s;
            return print.broadcast(Log.ERROR, message);
        }

        final String s = format.toString();
        final String message = args.length > 0 ? String.format(s, args) : s;
        return print.println(Log.ERROR, message);
    }

    public static int e(Throwable throwable, Object format, Object... args) {
        if (config.minimumLogLevel > Log.ERROR) {
            final String s = format.toString();
            final String message = (args.length > 0 ? String.format(s, args) : s) + '\n' + Log.getStackTraceString(throwable);
            return print.broadcast(Log.ERROR, message);
        }

        final String s = format.toString();
        final String message = (args.length > 0 ? String.format(s, args) : s) + '\n' + Log.getStackTraceString(throwable);
        return print.println(Log.ERROR, message);
    }

    /**
     * 如果是activity的文件，就传入当前的activity的tag,不然不好区分是哪个activity
     *
     * @param tag
     * @param content
     */
    public static void writeLog(String tag, String content) {
        if (BuildConfig.DEBUG) {
            LogWriter.writeLog(tag, print.processMessage(content));
        }
    }

    public static void writeLog(String content) {
        if (BuildConfig.DEBUG) {
            LogWriter.writeLog(print.getScope(5), print.processMessage(content));
        }
    }

    public static int json(String json) {
        return i(formatJSON(json));
    }

    public static String formatJSON(String json) {
        String formattedString = json;
        if (json == null || json.trim().length() == 0) {
            throw new RuntimeException("JSON empty.");
        }
        try {
            if (json.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(json);
                formattedString = jsonObject.toString(4);
            } else if (json.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(json);
                formattedString = jsonArray.toString(4);
            } else {
                Lg.e("JSON should start with { or [, but found " + json);
            }
        } catch (Exception e) {
            Lg.e(e);
        }
        return formattedString;
    }

    public static boolean isDebugEnabled() {
        return config.minimumLogLevel <= Log.DEBUG;
    }

    public static boolean isVerboseEnabled() {
        return config.minimumLogLevel <= Log.VERBOSE;
    }

    public static Lg.BaseConfig getConfig() {
        return config;
    }

    public static String logLevelToString(int loglevel) {
        switch (loglevel) {
            case Log.VERBOSE:
                return "VERBOSE";
            case Log.DEBUG:
                return "DEBUG";
            case Log.INFO:
                return "INFO";
            case Log.WARN:
                return "WARN";
            case Log.ERROR:
                return "ERROR";
            case Log.ASSERT:
                return "ASSERT";
        }

        return "UNKNOWN";
    }

}
