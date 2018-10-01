package com.parabird.uvds.common.utils;

import com.google.common.hash.Hashing;

public class CodecUtils {
    public static String generateUID(byte[] byteArray) {
        return Hashing.sha256().hashBytes(byteArray).toString();
    }
}
