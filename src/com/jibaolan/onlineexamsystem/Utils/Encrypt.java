package com.jibaolan.onlineexamsystem.Utils;

import com.alee.utils.encryption.Base64;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encrypt {
    public static String Base64Encode(String s){
        return Base64.encode(s.getBytes());
    }
    public static String Base64Decode(String s){
        return new String(Base64.decode(s));
    }
    public static String JSSEncode(String s)throws IllegalStateException{
        String ol="/\\\r\t\n{}'\":<>";
        String ne="／＼ｒｔｎ｛｝＇＂：＜＞";
        char[] os=ol.toCharArray();
        char[] ns=ne.toCharArray();
        for (int i = 0; i < os.length; i++){
            char o=os[i];
            char n=ns[i];
            if(s.contains(String.valueOf(n)))
                throw new IllegalStateException("The given String contains \""+ n +"\" ,JSS Encrypt will destroy those information!");
            s=s.replace(o,n);
        }
        return s;
    }
    public static String JSSDecode(String s){
        String ne="/\\\r\t\n{}'\":<>";
        String ol="／＼ｒｔｎ｛｝＇＂：＜＞";
        char[] os=ol.toCharArray();
        char[] ns=ne.toCharArray();
        for (int i = 0; i < os.length; i++){
            char o=os[i];
            char n=ns[i];
            s=s.replace(o,n);
        }
        return s;
    }
    public static String MD5(String s){
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(s.getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static void main(String[] args) {
        String s1="TEST000";
        //9e82ea23e92449f0e11fb63b5736c40d
        String s2="TEST111";
        //87918a9a546ef6f7b78718eea0cd027a
        String s3="1";
        //c4ca4238a0b923820dcc509a6f75849b

    }

}
