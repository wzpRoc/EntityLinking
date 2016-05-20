package org.ailab.wimfra.util;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;
import java.util.regex.Pattern;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESCryptographer  {
    private static String CIPHER_TRANSFORMATION = "AES/CTR/NoPadding";
    private static String KEY_TYPE = "AES";
    private static int KEY_SIZE_BITS = 128;

    private Charset plainTextEncoding = Charset.forName("UTF-8");
    private SecretKey key;
    private Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
    private byte[] ivBytes = new byte[KEY_SIZE_BITS/8];

    public AESCryptographer() throws NoSuchAlgorithmException, NoSuchPaddingException, NoSuchProviderException, InvalidParameterSpecException, InvalidKeyException, InvalidAlgorithmParameterException{
        KeyGenerator kgen = KeyGenerator.getInstance(KEY_TYPE);
        kgen.init(KEY_SIZE_BITS);
        key = kgen.generateKey();
        cipher.init(Cipher.ENCRYPT_MODE, key);
        ivBytes = cipher.getParameters().getParameterSpec(IvParameterSpec.class).getIV();
    }

    public String getIVAsHex(){
        return byteArrayToHexString(ivBytes);
    }

    public String getKeyAsHex(){
        return byteArrayToHexString(key.getEncoded());
    }

    public void setCrtKey(String keyText) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException{
        byte[] bText = keyText.getBytes();
        SecretKey secretKey = new SecretKeySpec(bText, "AES");
        Cipher c2 = Cipher.getInstance("AES/ECB/NoPadding");
        c2.init(Cipher.ENCRYPT_MODE, secretKey);
        bText = c2.doFinal(bText);
        key = new SecretKeySpec(bText, "AES");
    }

    public void setStringToKey(String keyText) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        setKey(keyText.getBytes());
    }

    public void setHexToKey(String hexKey){
        setKey(hexStringToByteArray(hexKey));
    }

    private void setKey(byte[] bArray){
        byte[] bText = new byte[KEY_SIZE_BITS/8];
        int end = Math.min(KEY_SIZE_BITS/8, bArray.length);
        System.arraycopy(bArray, 0, bText, 0, end);
        key = new SecretKeySpec(bText, KEY_TYPE);
    }

    public void setStringToIV(String ivText){
        setIV(ivText.getBytes());
    }

    public void setHexToIV(String hexIV){
        setIV(hexStringToByteArray(hexIV));
    }

    private void setIV(byte[] bArray){
        byte[] bText = new byte[KEY_SIZE_BITS/8];
        int end = Math.min(KEY_SIZE_BITS/8, bArray.length);
        System.arraycopy(bArray, 0, bText, 0, end);
        ivBytes = bText;
    }

    public String encryptCRT(String message) throws InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException,
            InvalidAlgorithmParameterException {
        String hexMessage = encrypt(message);
        return byteArrayToHexString(ivBytes).concat(hexMessage.substring(2));
    }

    public String encrypt(String message) throws InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException,
            InvalidAlgorithmParameterException {
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(ivBytes));
        byte[] encrypted = cipher.doFinal(message.getBytes(plainTextEncoding));
        String result = byteArrayToHexString(encrypted);
        return result;
    }

    public String decryptCrt(String hexCipherText) throws InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException{
        byte[] ciphertextBytes = hexStringToByteArray(hexCipherText);
        ivBytes = Arrays.copyOf(Arrays.copyOf(ciphertextBytes, 8), 16);
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(ivBytes));
        byte[] recoveredCleartext = cipher.doFinal(ciphertextBytes, 8, ciphertextBytes.length - 8);
        return new String(recoveredCleartext);
    }


    public String decrypt(String hexCiphertext)
            throws IllegalBlockSizeException, BadPaddingException,
            InvalidKeyException, InvalidAlgorithmParameterException,
            UnsupportedEncodingException {
        byte[] dec = hexStringToByteArray(hexCiphertext);
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(ivBytes));
        byte[] decrypted = cipher.doFinal(dec);
        return new String(decrypted, plainTextEncoding);
    }

    private static String byteArrayToHexString(byte[] raw) {
        StringBuilder sb = new StringBuilder(2 + raw.length * 2);
        sb.append("0x");
        for (int i = 0; i < raw.length; i++) {
            sb.append(String.format("%02X", Integer.valueOf(raw[i] & 0xFF)));
        }
        return sb.toString();
    }

    private static byte[] hexStringToByteArray(String hex) {
        Pattern replace = Pattern.compile("^0x");
        String s = replace.matcher(hex).replaceAll("");

        byte[] b = new byte[s.length() / 2];
        for (int i = 0; i < b.length; i++){
            int index = i * 2;
            int v = Integer.parseInt(s.substring(index, index + 2), 16);
            b[i] = (byte)v;
        }
        return b;
    }

    public Charset getPlainTextEncoding() {
        return plainTextEncoding;
    }

    public void setPlainTextEncoding(String plainTextEncoding) {
        this.plainTextEncoding = Charset.forName(plainTextEncoding);
    }

    @Test
    public void testEncryptionData() throws Exception
    {
        AESCryptographer aes = new AESCryptographer();
        String key = "0123456789abcdef";
        System.out.println("密钥 :"+key);
        aes.setCrtKey(key);
        aes.setPlainTextEncoding("UTF-8");
//        aes.setPlainTextEncoding("GBK");

        String data = "wzp.newsML.test AES Encryption !@#$%^&~超毅数字";
        System.out.println("明文 :"+data);
//        String encrypted = aes.encrypt(data);
        String encrypted = aes.encryptCRT(data);
        System.out.println("加密后:"+encrypted);
//        String decrypted = aes.decrypt(encrypted);
        String decrypted = aes.decryptCrt(encrypted);
        System.out.println("解密后:"+decrypted);
    }

    @Test
    public void testDecryptionData() throws Exception
    {
        AESCryptographer aes = new AESCryptographer();
        String key = "0123456789abcdef";
        System.out.println("密钥 :"+key);
        aes.setCrtKey(key);
        aes.setPlainTextEncoding("UTF-8");
//        aes.setPlainTextEncoding("GBK");

//        String encrypted = "0xD693D159DCD61F7C8F8A9BA3B77128124E8AD4524F0EA0AE029108F8EFFD9B49095D75189FFB550C09A3D648AEAB197358719B38BE9589DE7C30D485E0C693D05082D19A6C89371DA32664303BB06021AA11BC381018A8ECB45F4217CBF21AF376B07709425AEC905BBF453B7270AA7D2A5435A3B7D04E39363ACDA1337849C487F5499A9ACE1B2B42070D6FE51097F94AD739124049B66CADB5472F2201C607923DE171FEAB10761D173E2573A24611601DB5066121F0B31865FFCD59BA2F1D86D3D15DBF6F7F54F987A740DE59";
        String encrypted = "0x19EDBDA110202C7114BE042361D4FD2125FE180E2F7EE64D76D5828D19F83C02DA82FEC8ED5624C2";
        System.out.println("加密后:"+encrypted);
        String decrypted = aes.decrypt(encrypted);
        System.out.println("解密后:"+decrypted);
    }

}

