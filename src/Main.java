import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import md5.HachageMD5;


public class Main {

	public static void main(String[] args) {
		
		// TODO Auto-generated method stub
		/**représentation clé 128 bit, utilise ça pour l'instant 
		//mais ça changeras peut etre si j'ai envie de le faire (a la main)
		**/
		//clé identique donc compare to renvoie 0
		String key1 = "0x9c1f03a0d9cf510f2765bd0f226ff5dc";
		String key2 = "0x9c1f03a0d9cf510f2765bd0f226ff5dc";
		
		// on retire le '0x' qui est juste un format pour dire que c'est de l'héxa, 
		//et on précise a biginteger que c'est de l'héxa avec 16
	/*
		BigInteger k1 = new BigInteger(key1.substring(2),16); 
		BigInteger k2 = new BigInteger(key2.substring(2),16);
		
		System.out.println("k1 == k2 : "+k1.compareTo(k2));
		
		key2 = "0x9c1f03a0d9cf510f2765bd0f226ff5dd"; // pareil que key1 sauf la derniere lettre, d > c 
													//donc key1 < key2 donc compare to renvoie -1
		k2 = new BigInteger(key2.substring(2),16);
		System.out.println("k1 < k2 : "+k1.compareTo(k2));
		
		key2 = "0x9c1f03a0d9cf510f2765bd0f226ff5db"; // pareil que key1 sauf la derniere lettre, b < c 
		//donc key1 > key2 donc compare to renvoie 1
		k2 = new BigInteger(key2.substring(2),16);
		System.out.println("k1 > k2 : "+k1.compareTo(k2));
		
		BigInteger k1hex = new BigInteger(k1.toString(),10);
		System.out.println("hey valeur de k1 : "+k1+" string de k1 : "+key1+" base 10 "+k1hex);
		// Récupération de toute les lignes du fichier.
		BufferedReader reader = null;

		try {
		    File file = new File("data/cles_alea/jeu_1_nb_cles_100.txt");
		    reader = new BufferedReader(new FileReader(file));

		    String line;
		    while ((line = reader.readLine()) != null) {
		        System.out.println(line);
		    }

		} catch (IOException e) {
		    e.printStackTrace();
		} finally {
		    try {
		        reader.close();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}
		*/
		
		
		System.out.println(DatatypeConverter
			      .printHexBinary(HachageMD5.hashMD5("hello").toByteArray()).toUpperCase());
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update("hello".getBytes());
			System.out.println(DatatypeConverter
				      .printHexBinary(md5.digest()).toUpperCase());
		}catch(NoSuchAlgorithmException e){
			
		}
		
		
		
	    
	}

}
