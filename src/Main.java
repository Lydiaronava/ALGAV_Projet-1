import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Hashtable;

import filebinomiale.*;




public class Main {
	static String paddedInt32(String str) {
		int N =32 - str.length();
		String s = "";
		s+=str;
		for(int i=0; i < N; i++) {
			s += "0";
		}
		//s+=str;
		//s = new StringBuilder(s).reverse().toString();
		return s;
	}



	static BigInteger hashMD5(File filein) {

		////////////////
		/// initialisations
		////////////////

		//s et k deux tableaux de taille 64 --> tjs les mêmes valeurs dedans

		long r[] = { 7, 12, 17, 22,  7, 12, 17, 22,  7, 12, 17, 22,  7, 12, 17, 22, 
				5,  9, 14, 20,  5,  9, 14, 20,  5,  9, 14, 20,  5,  9, 14, 20, 
				4, 11, 16, 23,  4, 11, 16, 23,  4, 11, 16, 23,  4, 11, 16, 23, 
				6, 10, 15, 21,  6, 10, 15, 21,  6, 10, 15, 21,  6, 10, 15, 21 };

		long k[] = {0xd76aa478, 0xe8c7b756, 0x242070db, 0xc1bdceee,
				0xf57c0faf, 0x4787c62a, 0xa8304613, 0xfd469501, 
				0x698098d8, 0x8b44f7af, 0xffff5bb1, 0x895cd7be, 
				0x6b901122, 0xfd987193, 0xa679438e, 0x49b40821, 
				0xf61e2562, 0xc040b340, 0x265e5a51, 0xe9b6c7aa, 
				0xd62f105d, 0x02441453, 0xd8a1e681, 0xe7d3fbc8, 
				0x21e1cde6, 0xc33707d6, 0xf4d50d87, 0x455a14ed, 
				0xa9e3e905, 0xfcefa3f8, 0x676f02d9, 0x8d2a4c8a, 
				0xfffa3942, 0x8771f681, 0x6d9d6122, 0xfde5380c, 
				0xa4beea44, 0x4bdecfa9, 0xf6bb4b60, 0xbebfbc70, 
				0x289b7ec6, 0xeaa127fa, 0xd4ef3085, 0x04881d05, 
				0xd9d4d039, 0xe6db99e5, 0x1fa27cf8, 0xc4ac5665, 
				0xf4292244, 0x432aff97, 0xab9423a7, 0xfc93a039, 
				0x655b59c3, 0x8f0ccc92, 0xffeff47d, 0x85845dd1, 
				0x6fa87e4f, 0xfe2ce6e0, 0xa3014314, 0x4e0811a1, 
				0xf7537e82, 0xbd3af235, 0x2ad7d2bb, 0xeb86d391 };

		//valeurs d'initialisations de A, B, C et D
		long a0 = 0x67452301;
		long b0 = 0xefcdab89;
		long c0 = 0x98badcfe;
		long d0 = 0x10325476;

		///////////
		/// préparation du message
		///////////

		String name = filein.getName().replaceFirst("[.][^.]+$", "");
		File fileout = new File(name + "bit.txt");
		try {
			FileInputStream fis = new FileInputStream(filein);
			FileOutputStream fos = new FileOutputStream(fileout);
			char current; String bitChar;
			while(fis.available() > 0) {
				current = (char) fis.read();
				if(current == '\n' || current == ' ') {
					continue;
				}
				bitChar = Integer.toBinaryString(current);
				fos.write(bitChar.getBytes());
				//System.out.println(Integer.toBinaryString(current)+ "   " + current);
			}

			//System.out.println(fileout.length() + " la taille");
			fis.close();
			String un = "1";
			String zero = "0";
			fos.write(un.getBytes());

			int length = (int)fileout.length();
			int n = 448 - length%512;
			//System.out.println(length + " nb de bit à ajouter " + n);

			for(int i = 0; i < n ; i++) {
				fos.write(zero.getBytes());
			}

			String tmp = new StringBuilder(Long.toBinaryString(fileout.length())).reverse().toString();
			//System.out.println(tmp.length() + tmp);
			fos.write(tmp.getBytes());
			for(int i = 0; i < (64-tmp.length()) ; i++) {
				fos.write(zero.getBytes());
			}


			if(fileout.length()%512 == 0){
				//System.out.println("bien joué ça marche " + fileout.length());
			}

			fos.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//le message est prêt

		////////////////
		////algo
		///////////////

		long a = a0, 
				b = b0, 
				c = c0, 
				d = d0;		//init des valeurs de hachage

		try {
			FileInputStream fis = new FileInputStream(fileout);
			int nbite = (int)fileout.length()/512;

			String[] wstr = new String[16];

			for(int n = 0; n < nbite; n++) {
				for(int j = 0; j < 16; j++) {
					String str = new String();
					for(int l = 0; l<32; l++) {
						str += (char) fis.read();
					}
					str = new StringBuilder(str).reverse().toString();
					wstr[j] = str;
				}

				long[] w = new long[16];
				for(int j = 0; j < 16; j++) {
					w[j] = Long.parseLong(wstr[j],2);		//string to int en base 2
				}



				long f=0;
				int g=0;
				for(int i=0; i<64; i++) {

					if(i >= 0 && i < 16) {
						f = (b & c) | (~d & c);
						g = i;
					}
					if(i >= 16 && i < 32) {
						f = (d & b) | (~d & c );
						g = (5*i + 1) % 16;
					}
					if(i >= 32 && i < 48) {
						f = b ^ c ^ d;
						g = (3*i + 5) % 16;
					}
					if(i >= 48 && i < 64) {
						f = c ^(b | ~d);
						g = (7*i) % 16;
					}

					long temp = d;
					d = c;
					c = b;
					long tempb = ((a + f + k[i] + w[g]));
					b = Integer.rotateLeft((int)tempb,(int)r[i]) + b;
					a = temp;
				}
				a0 += a;
				b0 += b;
				c0 += c;
				d0 += d;

			}

			String h0 = paddedInt32(Integer.toBinaryString((int) a0));
			String h1 = paddedInt32(Integer.toBinaryString((int) b0));
			String h2 = paddedInt32(Integer.toBinaryString((int) c0));
			String h3 = paddedInt32(Integer.toBinaryString((int) d0));
			
			h0 = h0 + h1 + h2 + h3;
			System.out.println(h0.length() + "LA TAILLE DE h0");
			return new BigInteger(h0,2);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//résultat = a0 concaténé à a1 concaténé à a2 concaténé à a3  (en little endian)

		return new BigInteger("0");

	}




	public static void main(String[] args) {




		//représentation clé 128 bit, utilise ça pour l'instant 
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

		/*	BufferedReader reader = null;

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
		 */	    

		/*		FileBinomiale FB = new FileBinomiale();

		Hashtable<Integer, Double> hash = new Hashtable<Integer, Double>(8);
		hash = FB.resultConsIter();
		FB.creerGraphe(hash);

		 */

		File file = new File("test.txt");
		BigInteger result = hashMD5(file);
		System.out.println(result );
		System.out.println(result.bitLength());


	}

}
