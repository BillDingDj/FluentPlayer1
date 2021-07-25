package com.example.fluentplayer.utils;

public class JniUtils {
    static {
        System.loadLibrary("main_cpp");
    }

    public static String GetFFmpegVersion() {
        return native_GetFFmpegVersion();
    }

    private static native String native_GetFFmpegVersion();
}