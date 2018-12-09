import java.math.BigInteger;

public class HachageMD5 {
	
	
	BigInteger hashMD5(/*un fichier*/) {
		
		////////////////
		/// initialisations
		////////////////
		
		//s et k deux tableaux de taille 64 --> tjs les mêmes valeurs dedans
		
		int r[] = { 7, 12, 17, 22,  7, 12, 17, 22,  7, 12, 17, 22,  7, 12, 17, 22, 
		5,  9, 14, 20,  5,  9, 14, 20,  5,  9, 14, 20,  5,  9, 14, 20, 
		4, 11, 16, 23,  4, 11, 16, 23,  4, 11, 16, 23,  4, 11, 16, 23, 
		 6, 10, 15, 21,  6, 10, 15, 21,  6, 10, 15, 21,  6, 10, 15, 21 };
		
		int k[] = {0xd76aa478, 0xe8c7b756, 0x242070db, 0xc1bdceee,
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
		int a0 = 0x67452301;
		int b0 = 0xefcdab89;
		int c0 = 0x98badcfe;
		int d0 = 0x10325476;
		
		///////////
		/// préparation du message
		///////////
		
		// ajouter le bit 1 à la fin du fichier
		
		//tant que taille(fichier) != 448mod512
			//ajouter un bit 0
		
		//coder la taille du fichier en 64bits (il faut absolument que ce soit codé en little-endian!!)
		
		//ajouter la taille en 64bits à la fin du fichier
			//--> on doit avoir un fichier dont la taille est un multiple de 512
		
		
		
		
		////////////////
		////algo
		///////////////
		
		int a = a0, 
			b = b0, 
			c = c0, 
			d = d0;		//init des valeurs de hachage
			
		//pour chaque bloc de 512 bits
			//créer un tableau w de 16 cases --> chaque case contient 32 bits du bloc (encore en little-endian) 
			int[] w = new int[16];
			
			
			int f=0, g=0;
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
				
				int temp = d;
				d = c;
				c = b;
				int tempb = ((a + f + k[i] + w[g]));
				b = Integer.rotateLeft(tempb,r[i]) + b;
				a = temp;
			}
			a0 += a;
			b0 += b;
			c0 += c;
			d0 += d;
		//fin pour
			
		//résultat = a0 concaténé à a1 concaténé à a2 concaténé à a3  (en little endian)
		
			
		
	}
			

}
