/*
 * Autor: Miguel Angel Cedeno Garciduenas
 * Email: miguecg@gmail.com 
 */

package mx.taxi.util;

/**
 * @author miguel
 */
import java.io.Serializable;
import javax.crypto.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CifraMD5 implements Serializable {

   
    /**
     *
     * Crear hash aleatorio.
     */
    public static String hashAleatorio() {
        String hash = null;
        try {

            KeyGenerator kg = KeyGenerator.getInstance("HmacMD5");
            SecretKey sk = kg.generateKey();
            Mac mac = Mac.getInstance("HmacMD5");
            mac.init(sk);
            byte[] result = mac.doFinal(("&%sdsa$gadsdsadw345=(&2s/$wsmrm654ff655sdasqw2493%").getBytes());

            hash = convert(result);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return hash;
    }

    public static String hashPassword(String passwd) {
        char[] pwd = passwd.toCharArray();
        byte[] pwdb = new byte[pwd.length];
        byte[] crypt = null;

        try {

            String algo = "MD5";
            MessageDigest md = MessageDigest.getInstance(algo);
            md.reset();

            for (int b = 0; b < pwd.length; b++) {
                pwdb[b] = (byte) pwd[b];
            }

            crypt = md.digest(pwdb);
            smudge(pwdb);
        } catch (NoSuchAlgorithmException c) {
            System.out.println(c.getMessage());
        }
        //  return new String(Base64.encode(crypt));
        return (convert(crypt));
    }

    /*------------------------------------------------------------------------*/
    /**
     * El asunto que aqui meto este metodo para convertir de bytes a string.
     *
     */
    private static String convert(byte bytes[]) {

        StringBuffer sb = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            sb.append(convertDigit((int) (bytes[i] >> 4)));
            sb.append(convertDigit((int) (bytes[i] & 0x0f)));
        }
        return (sb.toString());

    }

    /**
     * Y este metodo que convierte a decimales los hexadecimales.
     */
    private static char convertDigit(int value) {

        value &= 0x0f;
        if (value >= 10) {
            return ((char) (value - 10 + 'a'));
        } else {
            return ((char) (value + '0'));
        }

    }

    private static void smudge(char[] pwd) {
        if (pwd != null) {
            for (int b = 0; b < pwd.length; b++) {
                pwd[b] = 0;
            }
        }
    }

    private static void smudge(byte[] pwd) {
        if (pwd != null) {
            for (int b = 0; b < pwd.length; b++) {
                pwd[b] = 0;
            }
        }
    }
}
