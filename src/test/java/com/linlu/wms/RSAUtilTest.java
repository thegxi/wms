package com.linlu.wms;

import com.linlu.wms.util.RSAUtil;
import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

public class RSAUtilTest {
    @Test
    public void rsaTest() throws NoSuchAlgorithmException {
        Map<String, Object> keyMap = RSAUtil.geneKeyPair();
        String publicKey = RSAUtil.getPublicKey(keyMap);
        String privateKey = RSAUtil.getPrivateKey(keyMap);
        System.out.println("publicKey = " + publicKey);
        System.out.println("privateKey = " + privateKey);
    }

    @Test
    public void encryptData() throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, IOException, InvalidKeyException {
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCEXPkAUNuKMMpEh04okup3FCxSxjuRaixF6roe+CXre/JQCjKA9n3vO780blwe8kB7npktE5buW3Sw0MEuPrjAQodcjjUkHEHoxbXTcYDJFs4kH2a56W3YMxWE1LiHFWmip0mvEltwoB9ulkPG1vXMFxE3+UuSI5zI8IgsFZgxkwIDAQAB";
        String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAIRc+QBQ24owykSHTiiS6ncULFLGO5FqLEXquh74Jet78lAKMoD2fe87vzRuXB7yQHuemS0Tlu5bdLDQwS4+uMBCh1yONSQcQejFtdNxgMkWziQfZrnpbdgzFYTUuIcVaaKnSa8SW3CgH26WQ8bW9cwXETf5S5IjnMjwiCwVmDGTAgMBAAECgYAEGCV5bnik6uL/uhyfXjslWWAQuaIohT8GAxTdOXzuWUYp5MUURbZS4DzONypfb5MjuKwU+InSjJQeedTXC7MxRMK9iCKpvjnFJpYf+PN1GkPztkwE2YzUVZSp1e4EKVc793iG45X2xVpm36xPtRD9YESlGtUCWz7j9Acnrdl6mQJBAKz0jnI5p3D095Yq5CUpzqGy81/6ivf9rpayHk7d7QmDY1FruTnRRRPSxGSIpD13eF3i8HbBFZ0gn6uPrGhYaIcCQQDD6uJmb4M8XGKVUY+C6Lpl0b1dj4Jw+wQqdHO1HaVBI9yig3SamKlTfkPBuWra2N7za/vV2BVdD8lurAJkJ42VAkA6vWxLmw0lLe2QkMyfWo2VGWPS6xKbmwran2/vtSu2GWt30bA81LgzPVf6FPZC8fptWI/2pSkkDlpsOAWEAtKtAkEAiLiW7FNvqyTxqzByzTFqOvoGg49NwVh7TMFdqgwZMf87zrGcIvLcX6bi1AN4PWdRF2DJKy+ToREOSkyBkrFDjQJBAJO8QW8YVok3yGWDA73hgNd7mGYzm4QGGFdujJQRzxKYYINMRMQC8zbTNuZ/v6UFZY6XRDHU2G2bVSnjHlA/sN0=";
        String data = "I have to take a shower before going out because the sweat had made my skin sticky";
        String encryptedData = RSAUtil.encryptByPublicKey(data.getBytes(StandardCharsets.UTF_8), publicKey);
        System.out.println("encrypt = " + encryptedData);
        byte[] decryptedData = RSAUtil.decryptByPrivateKey(encryptedData.getBytes(StandardCharsets.UTF_8), privateKey);
        System.out.println("decrypt = " + new String(decryptedData));
    }
}
