package com.linlu.wms.util;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA是一种非对称加密算法，它使用两个不同的密钥进行加密和解密。
 * 字符串格式的密钥在未在特殊说明情况下都为BASE64编码格式
 * 由于非对称加密速度极其缓慢，一般文件不使用它来加密而是使用对称加密
 * 非对称加密算法可以用来对对称加密的密钥加密，这样保证密钥的安全也就保证了数据的安全
 */
public class RSAUtil {
    /**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";

    /**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    /**
     * 获取公钥的key
     */
    private static final String PUBLIC_KEY = "RSAPublicKey";

    /**
     * 获取私钥的key
     */
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * RSA 位数 如果采用2048 上面最大加密和最大解密则须填写:  245 256
     */
    private static final int INITIALIZE_LENGTH = 1024;

    /**
     * 生成公钥和私钥
     *
     * @return 密钥对
     * @throws NoSuchAlgorithmException 生成失败
     */
    public static Map<String, Object> geneKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGenerator.initialize(INITIALIZE_LENGTH);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<>();
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /**
     * 用私钥信息生成签名
     *
     * @param data       源数据
     * @param privateKey 私钥
     * @return 签名
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static String sign(byte[] data, String privateKey)
            throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey pkcsPrivateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(pkcsPrivateKey);
        signature.update(data);
        return Base64.encodeBase64String(signature.sign());
    }

    /**
     * 校验数字签名
     *
     * @param data      源数据
     * @param publicKey 公钥
     * @param sign      签名
     * @return true: 签名一致, false: 签名不一致
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static boolean verify(byte[] data, String publicKey, String sign)
            throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey x509PublicKey = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(x509PublicKey);
        signature.update(data);
        return signature.verify(Base64.decodeBase64(sign));
    }

    /**
     * 私钥加密
     *
     * @param data       源数据
     * @param privateKey 私钥(Base64编码)
     * @return 加密后的数据
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws IOException
     */
    public static String encryptByPrivateKey(byte[] data, String privateKey)
            throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey pkcs8PrivateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, pkcs8PrivateKey);
        int inputLength = data.length;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // 数据分段解密
        int offset = 0;
        byte[] cache;
        int i = 0;
        while (inputLength - offset > 0) {
            if (inputLength - offset > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offset, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offset, inputLength - offset);
            }
            byteArrayOutputStream.write(cache, 0, cache.length);
            i++;
            offset = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();
        return Base64.encodeBase64String(encryptedData);
    }

    /**
     * 公钥解密
     *
     * @param data      源数据
     * @param publicKey 公钥(Base64编码)
     * @return 加密后的数据
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws IOException
     */
    public static String encryptByPublicKey(byte[] data, String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey x509PublicKey = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, x509PublicKey);
        // 数据分段解密
        int inputLength = data.length;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        while (inputLength - offset > 0) {
            if (inputLength - offset > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offset, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offset, inputLength - offset);
            }
            byteArrayOutputStream.write(cache, 0, cache.length);
            i++;
            offset = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();
        return Base64.encodeBase64String(encryptedData);
    }

    /**
     * 私钥解密
     *
     * @param encryptedData 加密的数据(Base64编码)
     * @param privateKey    私钥(Base64编码)
     * @return 解密后的数据
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws IOException
     */
    public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        encryptedData = Base64.decodeBase64(encryptedData);
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey pkcs8PrivateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, pkcs8PrivateKey);
        int inputLength = encryptedData.length;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // 数据分段解密
        int offset = 0;
        byte[] cache;
        int i = 0;
        while (inputLength - offset > 0) {
            if (inputLength - offset > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offset, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offset, inputLength - offset);
            }
            byteArrayOutputStream.write(cache, 0, cache.length);
            i++;
            offset = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();
        return decryptedData;
    }

    /**
     * 公钥解密
     *
     * @param encryptedData 加密的数据
     * @param publicKey     加密的公钥
     * @return 解密后的数据
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws IOException
     */
    public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey x509PublicKey = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, x509PublicKey);
        // 数据分段解密
        int inputLength = encryptedData.length;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        while (inputLength - offset > 0) {
            if (inputLength - offset > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offset, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offset, inputLength - offset);
            }
            byteArrayOutputStream.write(cache, 0, cache.length);
            i++;
            offset = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();
        return decryptedData;
    }

    /**
     * 获取私钥
     * @param keyMap 密钥对
     * @return 私钥(Base64编码)
     */
    public static String getPrivateKey(Map<String, Object> keyMap) {
        PrivateKey privateKey = (PrivateKey) keyMap.get(PRIVATE_KEY);
        return Base64.encodeBase64String(privateKey.getEncoded());
    }

    /**
     * 获取公钥
     * @param keyMap 密钥对
     * @return 公钥(Base64编码)
     */
    public static String getPublicKey(Map<String, Object> keyMap) {
        PublicKey publicKey = (PublicKey) keyMap.get(PUBLIC_KEY);
        return Base64.encodeBase64String(publicKey.getEncoded());
    }


}
