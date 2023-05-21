package it.dani.provacifratura;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Base64;

public class Symmetric {

    private static byte[] createInitializationVector(@Range(from = 1, to = Long.MAX_VALUE) int dim)
    {
        byte[] result = new byte[dim];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(result);

        return result;
    }

    public static @NotNull Key createAESKey(@Range(from = 1, to = Long.MAX_VALUE) int dim)
    {
        byte[] raw = createInitializationVector(dim);
        return new SecretKeySpec(raw, "AES");
    }

    public static byte[] encrypt(@NotNull Key key, @NotNull byte[] value) throws BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException, InvalidKeyException {
        Cipher cipher = null;
        try { cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); }
        catch (NoSuchAlgorithmException | NoSuchPaddingException e) { e.printStackTrace(); }

        byte[] initVector = new byte[cipher.getBlockSize()];

        IvParameterSpec ivParameterSpec = new IvParameterSpec(initVector);
        cipher.init(Cipher.ENCRYPT_MODE, key,ivParameterSpec);

        return cipher.doFinal(value);
    }

    public static String encrypt(@NotNull Key key, @NotNull String value) throws BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException, InvalidKeyException {
        return Base64.getEncoder().encodeToString(encrypt(key,Base64.getDecoder().decode(value)));
    }

    public static byte[] decrypt(@NotNull Key key, @NotNull byte[] value) throws InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = null;
        try { cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); }
        catch (NoSuchAlgorithmException | NoSuchPaddingException e) { e.printStackTrace(); }

        byte[] initVector = new byte[cipher.getBlockSize()];

        IvParameterSpec ivParamsSpec = new IvParameterSpec(initVector);
        cipher.init(Cipher.DECRYPT_MODE, key, ivParamsSpec);

        return cipher.doFinal(value);
    }

    public static String decrypt(@NotNull Key key, @NotNull String value) throws InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        return Base64.getEncoder().encodeToString(decrypt(key, Base64.getDecoder().decode(value)));
    }
}
