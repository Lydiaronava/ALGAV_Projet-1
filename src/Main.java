import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;

import filebinomiale.*;




public class Main {

	public static void main(String[] args) {
		
		
		ArbreBinomial tst = new ArbreBinomial(4);
		ArbreBinomial tst2 = new ArbreBinomial(5);
		
		ArbreBinomial tst3 = new ArbreBinomial(9);
		ArbreBinomial tst4 = new ArbreBinomial(20);
		
		tst.union(tst2);
		tst3.union(tst4);
		tst.union(tst3);
		//System.out.println(tst.getCle());
		//tst.afficherArbre();
		
		FileBinomiale FB = new FileBinomiale(tst);
		ArbreBinomial t1 = new ArbreBinomial(40);
		ArbreBinomial t2 = new ArbreBinomial(50);
		t1.union(t2);
		//t1.afficherArbre();
		
		FB.ajoutMin(t1, FB);
		FB.ajoutMin(new ArbreBinomial(2), FB);	//ok
		//FB.afficherFileB();
		
		ArbreBinomial tmp = FB.degreMin();	//ok
		//tmp.afficherArbre();
				
		
		
		ArbreBinomial ab = new ArbreBinomial(100);
		ArbreBinomial ab2 = new ArbreBinomial(105);
		
		ArbreBinomial ab3 = new ArbreBinomial(109);
		ArbreBinomial ab4 = new ArbreBinomial(120);
		
		ab.union(ab2);
		ab3.union(ab4);
		ab.union(ab3);
		
		
		
		//ab = ab.union(tst);
		
		//ab.afficherArbre();
		FileBinomiale FB2 = new FileBinomiale(ab);
		
		//FB2.afficherFileB();		//ok
		
		//FB = FB.reste();		//ok
		//FB.afficherFileB();
		
		FileBinomiale FB3 = FB.union(FB,FB2);
		FB3.afficherFileB();
		
		
		/**
		
		/**représentation clé 128 bit, utilise ça pour l'instant 
		//mais ça changeras peut etre si j'ai envie de le faire (a la main)
		
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
	    
	}

}
