package com.upaxer.serverless.serviceRepository;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Service;

import com.upaxer.serverless.idaoService.IService;
import com.upaxer.serverless.models.DataRequest;
@Service
public class ServiceImplement implements IService{
    private static String secretKey = "Up@X3R_4LpH4?53Cr3T#W0rD_d3s417.";//

	@Override
	public String request(String request) throws Exception {
		String opj= ServiceImplement.encode(request);
		System.out.println("data-encode "+opj);
		return opj;
	}

	@Override
	public DataRequest response(DataRequest request) throws Exception {
		DataRequest opj= ServiceImplement.decode(request.getData());
		System.out.println("data-decode "+opj);
		return opj;
	}
	
	 public static String encode(String txt) {
	        try {
	            byte[] clientKeyBytes = secretKey.getBytes();
	            //SecretKey se genera con los bytes de la password
	            SecretKeySpec clientKey = new SecretKeySpec(clientKeyBytes, 0, clientKeyBytes.length, "AES");
	            Cipher encCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	            encCipher.init(Cipher.ENCRYPT_MODE, clientKey);
	            byte[] ivBytes = encCipher.getIV();
	            byte[] dataBytes = encCipher.doFinal(txt.getBytes("UTF-8"));
	            byte[] concat = new byte[ivBytes.length + dataBytes.length];
	            System.arraycopy(ivBytes, 0, concat, 0, ivBytes.length);
	            System.arraycopy(dataBytes, 0, concat, ivBytes.length, dataBytes.length);
	            return DatatypeConverter.printBase64Binary(concat);
	        } catch (NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | UnsupportedEncodingException | BadPaddingException | NoSuchPaddingException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	 
	 public static DataRequest decode(String txt) throws UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
	    	byte[] dataBytes;
	    	 Cipher pwdcipher;
	            byte[] data;
	            DataRequest opj=new DataRequest();
	    	try {
	            
	            dataBytes = DatatypeConverter.parseBase64Binary(txt);
	            byte[] IV;
	            IV = Arrays.copyOfRange(dataBytes, 0, 16);
	            data = Arrays.copyOfRange(dataBytes,16, dataBytes.length);
	            byte[] clientKeyBytes;
	            clientKeyBytes = secretKey.getBytes();
	            SecretKeySpec clientKey;
	            clientKey = new SecretKeySpec(clientKeyBytes, 0, clientKeyBytes.length,"AES");
	           

	            pwdcipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	            pwdcipher.init(Cipher.DECRYPT_MODE, clientKey, new IvParameterSpec(IV));
	            opj.setData(new String(pwdcipher.doFinal(data), "UTF-8"));
	            return opj.getData();
	        } catch (NoSuchAlgorithmException ex) {
	            System.out.println(ex);
	        }
			return null;
	    }

}
