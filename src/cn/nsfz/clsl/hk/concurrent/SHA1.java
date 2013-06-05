package cn.nsfz.clsl.hk.uuid;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class SHA1 {
    
    private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
         

    
    public static void main(String[] args) throws NoSuchAlgorithmException{
        MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
        messageDigest.update("test".getBytes());
        System.out.println(messageDigest.digest());
        System.out.println(getFormattedText(messageDigest.digest()));
    }

    
    private static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        // 把密文转换成十六进制的字符串形式
        for (int index = 0; index < len; index++) {        
            buf.append(HEX_DIGITS[(bytes[index] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[index] & 0x0f]);
        }     
        return buf.toString();
 
    }
    
}
