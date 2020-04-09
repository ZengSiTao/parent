package com.linktop.cloud.entranceguardcore.util;

import java.math.BigInteger;

/**
 * @创建人 baojielei
 * @创建时间 2020/3/27
 * @描述
 */
public class NumberConvertUtil {
    /**
     * number   要转换的数
     * from     原数的进制
     * to       要转换成的进制
     */
    //将某数转换为二进制的数并填充满8位
    public static  String numberChangeToBinNumber(String number, int from, int to) {
        //将原进制数转为二进制数
         String binNumer = new BigInteger(number, from).toString(to);
         //二进制数的长度
        int binlen = binNumer.length();
        //二进制的数填充满8位
        String zero ="";
        if (binlen <= 8){
            for(int i = 0; i<8-binlen;i++){
                zero += "0";
            }
        }else {
            return "原数据超过16进制FF(二进制的11111111)";
        }
        String newbin =  zero + binNumer ;

         return newbin;
    }

    /**
     * number   要转换的数
     * from     原数的进制
     * to       要转换成的进制
     */
    public static  String numberChangeToNumber(String number, int from, int to) {
        return new BigInteger(number, from).toString(to);
    }

}
