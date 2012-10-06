package network;

import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;


public class Crypter {



		private final static String cleSecrete = "monksdescailloux";
		private final static String algo = "RC4";
		private final static Key cle = new SecretKeySpec(cleSecrete.getBytes(), algo);



		public static String encrypte(String texteEnClair)
		{
			Cipher cipher = null;
			try
			{
				cipher = Cipher.getInstance(algo);
			}
			catch (NoSuchAlgorithmException e) {e.printStackTrace();}
			catch (NoSuchPaddingException e) {e.printStackTrace();}

			try
			{
				cipher.init(Cipher.ENCRYPT_MODE, cle);
			}
			catch (InvalidKeyException e) {e.printStackTrace();}

			byte[] texteCrypte = null;
			try
			{
				texteCrypte = cipher.doFinal(texteEnClair.getBytes());
			}
			catch (IllegalBlockSizeException e) {e.printStackTrace();}
			catch (BadPaddingException e) {e.printStackTrace();}

			return new String(texteCrypte);
		}

		public static String decrypte(String texteCrypte)
		{
			Cipher cipher = null;
			try
			{
				cipher = Cipher.getInstance(algo);
			}
			catch (NoSuchAlgorithmException e) {e.printStackTrace();}
			catch (NoSuchPaddingException e) {e.printStackTrace();}

			try
			{
				cipher.init(Cipher.DECRYPT_MODE, cle);
			}
			catch (InvalidKeyException e) {e.printStackTrace();}

			byte[] texteEnClair = null;

			try
			{
				texteEnClair = cipher.doFinal(texteCrypte.getBytes());
			}
			catch (IllegalBlockSizeException e)	{e.printStackTrace();}
			catch (BadPaddingException e) {e.printStackTrace();}

			return new String(texteEnClair);
		}
		
}
