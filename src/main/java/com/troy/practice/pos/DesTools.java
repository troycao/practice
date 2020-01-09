package com.troy.practice.pos;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

public class DesTools {

    /** The Constant KEY_ALGORITHM. */
    public static final String KEY_ALGORITHM = "DESede";

    /** The Constant CIPHER_ALGORITHM. */
    public static final String CIPHER_ALGORITHM = "DESede/ECB/NoPadding";

    /** The Constant CIPHER_ALGORITHM. */
    public static final String SINGLE_CIPHER_ALGORITHM = "DES";

    public static final String SINGLE_CIPHER_ALGORITHM_FOR_UP = "DES/ECB/NoPadding";

    public static final String ALGORITHM_TYPE = "BC";

    static {
        BouncyCastleProvider bcProvider = new BouncyCastleProvider();
        java.security.Security.addProvider(bcProvider);
    }

    /**
     * 3des加密.
     *
     * @param data
     *            the data
     * @param key
     *            the key
     * @return the byte[]
     * @throws Exception
     *             the exception
     */
    public static byte[] threeEncrypt(byte[] data, byte[] key) {
        try {
            Key k = toKey(key);
            Cipher cipher = Cipher
                    .getInstance(CIPHER_ALGORITHM, ALGORITHM_TYPE);
            cipher.init(Cipher.ENCRYPT_MODE, k);
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 密钥字节转Key.
     *
     * @param key
     *            the key
     * @return the key
     * @throws Exception
     *             the exception
     */
    private static Key toKey(byte[] key) throws Exception {
        // DESedeKeySpec dks = new DESedeKeySpec(key);
        // SecretKeyFactory keyFactory =
        // SecretKeyFactory.getInstance(KEY_ALGORITHM,"BC");
        // return keyFactory.generateSecret(dks);
        SecretKey deskey = new SecretKeySpec(key, KEY_ALGORITHM);
        return deskey;
    }
}
