import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;

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
		
		BigInteger k1 = new BigInteger(key1.substring(2),16); 
		BigInteger k2 = new BigInteger(key2.substring(2),16);
		
		System.out.println("k1 == k2 : "+k1.compareTo(k2));
		
		key2 = "0x9c1f03a0d9cf510f2765bd0f226ff5dd"; // pareil que key1 sauf la derniere lettre, d > c 
													//donc key1 < key2 donc compare to renvoie -1
		k2 = new BigInteger(key2.substring(2),16);
		System.out.println("k1 < k2 : "+k1.compareTo(k2));
		
		// Récupération de fichier.
		
		FileInputStream in = null;

	    try {
	    	try {
				in = new FileInputStream(".txt");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         
	    	int c;
	    	try {
				while ((c = in.read()) != -1) {
					System.out.println(c);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }finally {
	    	if (in != null) {
	    		try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	    }
	}

}
