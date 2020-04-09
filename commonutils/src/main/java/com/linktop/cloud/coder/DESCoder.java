package com.linktop.cloud.coder;

import java.security.Key;  
import java.security.SecureRandom;  
  

import javax.crypto.Cipher;  
import javax.crypto.KeyGenerator;  
import javax.crypto.SecretKey;  
import javax.crypto.SecretKeyFactory;  
import javax.crypto.spec.DESKeySpec;  
import javax.crypto.spec.SecretKeySpec;
  
/** 
 * DES安全编码组件 
 *  
 * <pre> 
 * 支持 DES、DESede(TripleDES,就是3DES)、AES、Blowfish、RC2、RC4(ARCFOUR) 
 * DES                  key size must be equal to 56 
 * DESede(TripleDES)    key size must be equal to 112 or 168 
 * AES                  key size must be equal to 128, 192 or 256,but 192 and 256 bits may not be available 
 * Blowfish             key size must be multiple of 8, and can only range from 32 to 448 (inclusive) 
 * RC2                  key size must be between 40 and 1024 bits 
 * RC4(ARCFOUR)         key size must be between 40 and 1024 bits 
 * 具体内容 需要关注 JDK Document http://.../docs/technotes/guides/security/SunProviders.html 
 * </pre> 
 *  
 * @author 梁栋 
 * @version 1.0 
 * @since 1.0 
 */  
public abstract class DESCoder extends Coder {
    /** 
     * ALGORITHM 算法 <br> 
     * 可替换为以下任意一种算法，同时key值的size相应改变。 
     *  
     * <pre> 
     * DES                  key size must be equal to 56 
     * DESede(TripleDES)    key size must be equal to 112 or 168 
     * AES                  key size must be equal to 128, 192 or 256,but 192 and 256 bits may not be available 
     * Blowfish             key size must be multiple of 8, and can only range from 32 to 448 (inclusive) 
     * RC2                  key size must be between 40 and 1024 bits 
     * RC4(ARCFOUR)         key size must be between 40 and 1024 bits 
     * </pre> 
     *  
     * 在Key toKey(byte[] key)方法中使用下述代码 
     * <code>SecretKey secretKey = new SecretKeySpec(key, ALGORITHM);</code> 替换 
     * <code> 
     * DESKeySpec dks = new DESKeySpec(key); 
     * SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM); 
     * SecretKey secretKey = keyFactory.generateSecret(dks); 
     * </code> 
     */  
    private static String ALGORITHM = "DES";  
  
    public static String getALGORITHM() {
		return ALGORITHM;
	}

	public static void setALGORITHM(String aLGORITHM) {
		ALGORITHM = aLGORITHM;
	}

	/** 
     * 转换密钥<br> 
     *  
     * @param key 
     * @return 
     * @throws Exception 
     */  
    private static Key toKey(byte[] key) throws Exception {  
    	SecretKey secretKey = null;
    	if(ALGORITHM.equalsIgnoreCase("DES")) {
            DESKeySpec dks = new DESKeySpec(key);  
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);  
            secretKey = keyFactory.generateSecret(dks);  
    	} else {
            // 当使用其他对称加密算法时，如AES、Blowfish等算法时，用下述代码替换上述三行代码  
            secretKey = new SecretKeySpec(key, ALGORITHM);      		
    	}
  
        return secretKey;  
    }  

	/** 
     * 解密 
     *  
     * @param data 
     * @param key 
     * @return 
     * @throws Exception 
     */  
    public static byte[] decrypt(byte[] data, String key) throws Exception {  
        Key k = toKey(decryptBASE64(key));  
  
        Cipher cipher = Cipher.getInstance(ALGORITHM);  
        cipher.init(Cipher.DECRYPT_MODE, k);  
  
        return cipher.doFinal(data);  
    }

    public static byte[] decrypt2(byte[] data, String key) throws Exception {
        //Key k = toKey(decryptBASE64(key));
        //Cipher cipher = Cipher.getInstance(ALGORITHM);
        //cipher.init(Cipher.DECRYPT_MODE, k);

        //1.构造密钥生成器，指定为AES算法,不区分大小写
        KeyGenerator kgen = KeyGenerator.getInstance(ALGORITHM);
        //2.根据ecnodeRules规则初始化密钥生成器
        //生成一个128位的随机源,根据传入的字节数组
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(decryptBASE64(key));
        kgen.init(128, random);
        //3.产生原始对称密钥
        SecretKey secretKey = kgen.generateKey();
        //4.获得原始对称密钥的字节数组
        byte[] enCodeFormat = secretKey.getEncoded();
        //5.根据字节数组生成AES密钥
        SecretKeySpec keySpec = new SecretKeySpec(enCodeFormat, ALGORITHM);
        //6.根据指定算法AES自成密码器
        Cipher cipher = Cipher.getInstance(ALGORITHM);// 创建密码器
        //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
        cipher.init(Cipher.DECRYPT_MODE, keySpec);// 初始化

        return cipher.doFinal(data);
    }

    /** 
     * 加密 
     *  
     * @param data 
     * @param key 
     * @return 
     * @throws Exception 
     */  
    public static byte[] encrypt2(byte[] data, String key) throws Exception {
        //Key k = toKey(decryptBASE64(key));
        //Cipher cipher = Cipher.getInstance(ALGORITHM);
        //cipher.init(Cipher.ENCRYPT_MODE, k);

        //1.构造密钥生成器，指定为AES算法,不区分大小写
        KeyGenerator kgen = KeyGenerator.getInstance(ALGORITHM);
        //2.根据ecnodeRules规则初始化密钥生成器
        //生成一个128位的随机源,根据传入的字节数组
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(decryptBASE64(key));
        kgen.init(128, random);
        //3.产生原始对称密钥
        SecretKey secretKey = kgen.generateKey();
        //4.获得原始对称密钥的字节数组
        byte[] enCodeFormat = secretKey.getEncoded();
        //5.根据字节数组生成AES密钥
        SecretKeySpec keySpec = new SecretKeySpec(enCodeFormat, ALGORITHM);
        //6.根据指定算法AES自成密码器
        Cipher cipher = Cipher.getInstance(ALGORITHM);// 创建密码器
        //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);// 初始化
        return cipher.doFinal(data);  
    }


    public static byte[] encrypt(byte[] data, String key) throws Exception {
        Key k = toKey(decryptBASE64(key));
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, k);

        return cipher.doFinal(data);
    }

    /** 
     * 生成密钥 
     *  
     * @return 
     * @throws Exception 
     */  
    public static String initKey() throws Exception {  
        return initKey(null);  
    }  
  
    /** 
     * 生成密钥 
     *  
     * @param seed 
     * @return 
     * @throws Exception 
     */  
    public static String initKey(String seed) throws Exception {  
        SecureRandom secureRandom = null;  
  
        if (seed != null) {  
            secureRandom = new SecureRandom(decryptBASE64(seed));  
        } else {  
            secureRandom = new SecureRandom();  
        }  
  
        KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM);  
        kg.init(secureRandom);  
  
        SecretKey secretKey = kg.generateKey();  
  
        return encryptBASE64(secretKey.getEncoded());  
    }  
}  