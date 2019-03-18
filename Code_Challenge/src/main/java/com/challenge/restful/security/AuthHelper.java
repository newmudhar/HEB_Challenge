package com.challenge.restful.security;

import com.challenge.restful.model.Token;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.Timestamp;

/**
 * Created by newmudhar on 11/4/2018.
 */
public class AuthHelper {

    public static String encrypt(String strClearText,String strKey) throws Exception{
        String strData;

        try {
            SecretKeySpec sKeySpec=new SecretKeySpec(strKey.getBytes(),"Blowfish");
            Cipher cipher=Cipher.getInstance("Blowfish");
            cipher.init(Cipher.ENCRYPT_MODE, sKeySpec);
            byte[] encrypted=cipher.doFinal(strClearText.getBytes());
            strData=new String(encrypted);

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return strData;
    }

    public static String decrypt(String strEncrypted,String strKey) throws Exception{
        String strData;

        try {
            SecretKeySpec sKeySpec=new SecretKeySpec(strKey.getBytes(),"Blowfish");
            Cipher cipher=Cipher.getInstance("Blowfish");
            cipher.init(Cipher.DECRYPT_MODE, sKeySpec);
            byte[] decrypted=cipher.doFinal(strEncrypted.getBytes());
            strData=new String(decrypted);

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return strData;
    }

    public static LogonValidationEnum isValidToken(EntityManager em, String username, String encodedToken){

        String queryString = "Select t\n" +
                "from Token t\n" +
                "where t.username = :username";

        Query query = em.createQuery(queryString);
        query.setParameter("username", username);
        Token token = (query.getResultList().isEmpty())?new Token():(Token) query.getResultList().get(0);
        System.out.println("Mudhar token retrieved: " + token.getToken());
        System.out.println("Mudhar token passed   : " + encodedToken);

        if (token.getToken().equals(encodedToken)){
            Timestamp now = new Timestamp(System.currentTimeMillis());
            Timestamp after30Minutes = new Timestamp(token.getCreationDate().getTime() + 1800000);
            System.out.println("Mudhar times: " + now.toString() + "->" + after30Minutes.toString());
            if (now.before(after30Minutes)){
                return LogonValidationEnum.ACCESS_GRANTED;
            }
            else{
                return LogonValidationEnum.TOKEN_EXPIRED;
            }

        }
        return LogonValidationEnum.ACCESS_DENIED;

    }

}
