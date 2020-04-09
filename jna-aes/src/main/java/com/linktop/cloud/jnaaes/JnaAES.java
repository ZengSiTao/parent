package com.linktop.cloud.jnaaes;


import java.io.File;
import java.security.Key;


import com.sun.jna.*;

import javax.crypto.Cipher;

public class JnaAES {
    private interface Function extends Library {

        String JDKBits = System.getProperty("sun.arch.data.model");
        String linuxFilePath = System.getProperty("user.dir") + File.separator + "ext-lib" + File.separator + "linux-" + JDKBits + File.separator + "libaes.so";
        String winFilePath = System.getProperty("user.dir") + File.separator + "ext-lib" + File.separator + "win-" + JDKBits + File.separator + "aes.dll";
        String filePath = (System.getProperty("os.name").toLowerCase().indexOf("win") != -1) ? winFilePath : linuxFilePath;

        String linuxLibPath = System.getProperty("user.dir") + File.separator + "ext-lib" + File.separator + "linux-" + JDKBits;
        String winLibPath = System.getProperty("user.dir") + File.separator + "ext-lib" + File.separator + "win-" + JDKBits;
        String libPath = (System.getProperty("os.name").toLowerCase().indexOf("win") != -1) ? winLibPath : linuxLibPath;

        String strSetDebugLoad = System.setProperty("jna.debug_load", "true");
        String strSetLibPath = System.setProperty("jna.library.path", libPath);
        String strSetPlatformLibPath = System.setProperty("jna.platform.library.path", libPath);


        Function instanceDll  = (Function)Native.loadLibrary(filePath,Function.class);

        public void AES_ECB_encrypt(byte[] input, byte[] key, byte[] output, int length);
        public void AES_ECB_decrypt(byte[] input, byte[] key, byte[] output, int length);


    }

    public static byte[] encrypt(byte[] data, byte[] key) {
        //Completion
        int completionSize = 0;
        if (data.length % 16 != 0)
            completionSize = 16 - (data.length % 16);

        int llen = data.length + completionSize;
        byte[] originCharCompletion = new byte[llen];
        System.arraycopy(data, 0, originCharCompletion, 0, data.length);
        if (data.length % 16 != 0) {
            for (int i = 0; i < completionSize; i++) {
                originCharCompletion[data.length + i] = (byte)completionSize;
            }
        }

        //分段加密
        int datapart = llen / 16;
        byte [] out_buffer = new byte[llen];
        byte [] outpart = new byte[16];
        byte [] inpart = new byte[16];
        for (int i = 0; i < datapart; ++i) {
            System.arraycopy(originCharCompletion,  i * 16, inpart, 0, 16);
            Function.instanceDll.AES_ECB_encrypt(inpart, key, outpart, 16);
            System.arraycopy(outpart,  0, out_buffer, i * 16, 16);
        }

        return out_buffer;
    }


    public static byte[] encrypt(String data, String key) {
        return encrypt(charToByteArray(data.toCharArray()), charToByteArray(key.toCharArray()));
    }

    public static byte[] decrypt(byte[] data, byte[] key) {
        //分段解密
        int datapart = data.length / 16;
        byte[] out_buffer = new byte[data.length];
        byte [] outpart = new byte[16];
        byte [] inpart = new byte[16];
        for (int i = 0; i < datapart; ++i) {
            System.arraycopy(data,  i * 16, inpart, 0, 16);
            Function.instanceDll.AES_ECB_decrypt(inpart, key, outpart, 16);
            System.arraycopy(outpart,  0, out_buffer, i * 16, 16);
        }
        byte padding = out_buffer[data.length - 1];
        if(padding < 16 && padding > 0) {
            byte[] temp = new byte[data.length - padding];
            System.arraycopy(out_buffer,  0, temp, 0, data.length - padding);
            return temp;
        }
        return out_buffer;
    }

    public static byte[] decrypt(String data, String key) {
        return decrypt(charToByteArray(data.toCharArray()), charToByteArray(key.toCharArray()));
    }

    public static byte[] charToByteArray(char[] charArray) {

        byte[] input = new byte[charArray.length];
        int i = 0;
        for (char ch : charArray) {
            input[i] = (byte) ch;
            i++;
        }
        return input;
    }
}
