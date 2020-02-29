package com.lzf.applianceafter_salesservicesystem.util;

import android.util.Log;

public class LogUtil {
    public static void logV(String tag, String msg) {
        Log.v(tag, "===|");
        Log.v(tag, "===【" + msg + "】===");
        Log.v(tag, "===|");
    }

    public static void logE(String tag, String msg) {
        Log.e(tag, "===|");
        Log.e(tag, "===|" + msg + "|===");
        Log.e(tag, "===|");
    }
}
