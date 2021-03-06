package tas;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.Hashtable;
import java.util.Set;

import javax.xml.bind.DatatypeConverter;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import arbrerecherche.SearchTree;
import filebinomial.ArbreBinomial;
import filebinomial.FileBinomiale;
import tas.array.TasArray;

import tas.tree.TasMinTree;

public class Main {
	
	
	public static String[] fileToArray(File file) {
		BufferedReader reader = null;
		String[] bgs = new String[0];
		try {
			reader = new BufferedReader(new FileReader(file));
			String line;
			int cpt = 0;
			while ((line = reader.readLine()) != null) {
				cpt++;
			}
			bgs = new String[cpt];
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			reader = new BufferedReader(new FileReader(file));
			String line;
			int cpt = 0;
			while ((line = reader.readLine()) != null) {
				bgs[cpt] = line;
				cpt++;
			}
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		return bgs;
	}
	
	public static Hashtable<Integer, Double> resultConsIter(TasMinTree ts){

		Hashtable<Integer, Double> hash = new Hashtable<Integer, Double>(8);
		hash.put(100, 0.0);
		hash.put(1000, 0.0);
		hash.put(10000, 0.0);
		hash.put(200, 0.0);
		hash.put(20000, 0.0);
		hash.put(500, 0.0);
		hash.put(5000, 0.0);
		hash.put(50000, 0.0);



		File repertoire = new File("data/cles_alea/");
		File[] listeFichier = repertoire.listFiles();
		for(File file : listeFichier) {
			String str = file.getName();
			str = str.replaceAll("\\D+","");
			str = str.substring(1, str.length());
			int k = Integer.parseInt(str);
			
			BigInteger[] bgs = ts.fileToArray(file);
			ts = new TasMinTree();
			long time = System.currentTimeMillis();
			ts.consIter(bgs);
			time = System.currentTimeMillis() - time;
			hash.put(k, hash.get(k) + time);
			
		}
		
		hash.put(100, hash.get(100)/5);
		hash.put(1000, hash.get(1000)/5);
		hash.put(10000, hash.get(10000)/5);
		hash.put(200, hash.get(200)/5);
		hash.put(20000, hash.get(20000)/5);
		hash.put(500, hash.get(500)/5);
		hash.put(5000, hash.get(5000)/5);
		hash.put(50000, hash.get(50000)/5);
		return hash;
	}
	
	public static Hashtable<Integer, Double> resultConsIter(TasArray t){

		Hashtable<Integer, Double> hash = new Hashtable<Integer, Double>(8);
		hash.put(100, 0.0);
		hash.put(1000, 0.0);
		hash.put(10000, 0.0);
		hash.put(200, 0.0);
		hash.put(20000, 0.0);
		hash.put(500, 0.0);
		hash.put(5000, 0.0);
		hash.put(50000, 0.0);



		File repertoire = new File("data/cles_alea/");
		File[] listeFichier = repertoire.listFiles();
		for(File file : listeFichier) {
			String str = file.getName();
			str = str.replaceAll("\\D+","");
			str = str.substring(1, str.length());
			int k = Integer.parseInt(str);

			BigInteger[] bgs = t.fileToArray(file);
			t = new TasArray(bgs.length);
			long time = System.currentTimeMillis();
			t.consIter(bgs);
			time = System.currentTimeMillis() - time;
			hash.put(k, hash.get(k) + time);
			
		}
		
		hash.put(100, hash.get(100)/5);
		hash.put(1000, hash.get(1000)/5);
		hash.put(10000, hash.get(10000)/5);
		hash.put(200, hash.get(200)/5);
		hash.put(20000, hash.get(20000)/5);
		hash.put(500, hash.get(500)/5);
		hash.put(5000, hash.get(5000)/5);
		hash.put(50000, hash.get(50000)/5);
		return hash;
	}
	
	
	public static Hashtable<Integer, Double> resultUnion(TasArray t){

		Hashtable<Integer, Double> hash = new Hashtable<Integer, Double>(8);
		hash.put(100, 0.0);
		hash.put(1000, 0.0);
		hash.put(10000, 0.0);
		hash.put(200, 0.0);
		hash.put(20000, 0.0);
		hash.put(500, 0.0);
		hash.put(5000, 0.0);
		hash.put(50000, 0.0);

		File j1 = new File("data/cles_trié/jeu_1/");
		File j2 = new File("data/cles_trié/jeu_2/");
		File j3 = new File("data/cles_trié/jeu_3/");
		File j4 = new File("data/cles_trié/jeu_4/");
		File j5 = new File("data/cles_trié/jeu_5/");
		
		File[][] listejx = new File[5][1];
		listejx[0] = j1.listFiles();
		listejx[1] = j2.listFiles();
		listejx[2] = j3.listFiles();
		listejx[3] = j4.listFiles();
		listejx[4] = j5.listFiles();
		
		//je trie les fichiers par ordre lexicographique donc l'ordre est comme ça 
		/* 
		 * 100.txt
		 * 1000.txt
		 * 10000.txt
		 * 200.txt
		 * 20000.txt
		 * 500.txt
		 * 5000.txt
		 * 50000.txt
		 * 
		 * */
		for (File[] files : listejx) {
			Arrays.sort(files, new FileNameComparator());
		}
		for (File[] files : listejx) {
				BigInteger[] bgs = t.fileToArray(files[files.length/2]); // je prend le fichier du milieu comme pivot : 20000.txt
				t = new TasArray(bgs.length);
				t.consIter(bgs);
				
				for (File f : files) {
					BigInteger[] bgs2 = t.fileToArray(f);
					TasArray t2 = new TasArray(bgs2.length);
					t2.consIter(bgs2);
					String str = f.getName();
					str = str.replaceAll("\\D+","");
					str = str.substring(1, str.length());
					int k = Integer.parseInt(str);
					
					long time = System.currentTimeMillis();
					TasArray.union(t, t2);
					time = System.currentTimeMillis() - time;
					hash.put(k, hash.get(k) + time);
				}
			}
		
		hash.put(100, hash.get(100)/5);
		hash.put(1000, hash.get(1000)/5);
		hash.put(10000, hash.get(10000)/5);
		hash.put(200, hash.get(200)/5);
		hash.put(20000, hash.get(20000)/5);
		hash.put(500, hash.get(500)/5);
		hash.put(5000, hash.get(5000)/5);
		hash.put(50000, hash.get(50000)/5);

		return hash;
	}
	
	public static Hashtable<Integer, Double> resultUnion(TasMinTree t){

		Hashtable<Integer, Double> hash = new Hashtable<Integer, Double>(8);
		hash.put(100, 0.0);
		hash.put(1000, 0.0);
		hash.put(10000, 0.0);
		hash.put(200, 0.0);
		hash.put(20000, 0.0);
		hash.put(500, 0.0);
		hash.put(5000, 0.0);
		hash.put(50000, 0.0);

		File j1 = new File("data/cles_trié/jeu_1/");
		File j2 = new File("data/cles_trié/jeu_2/");
		File j3 = new File("data/cles_trié/jeu_3/");
		File j4 = new File("data/cles_trié/jeu_4/");
		File j5 = new File("data/cles_trié/jeu_5/");
		
		File[][] listejx = new File[5][1];
		listejx[0] = j1.listFiles();
		listejx[1] = j2.listFiles();
		listejx[2] = j3.listFiles();
		listejx[3] = j4.listFiles();
		listejx[4] = j5.listFiles();
		
		//je trie les fichiers par ordre lexicographique donc l'ordre est comme ça 
		/* 
		 * 100.txt
		 * 1000.txt
		 * 10000.txt
		 * 200.txt
		 * 20000.txt
		 * 500.txt
		 * 5000.txt
		 * 50000.txt
		 * 
		 * */
		for (File[] files : listejx) {
			Arrays.sort(files, new FileNameComparator());
		}
		for (File[] files : listejx) {
				BigInteger[] bgs = t.fileToArray(files[files.length/2]); // je prend le fichier du milieu comme pivot : 20000.txt
				t.consIter(bgs);
				
				for (File f : files) {
					BigInteger[] bgs2 = t.fileToArray(f);
					TasMinTree t2 = new TasMinTree();
					t2.consIter(bgs2);
					
					String str = f.getName();
					str = str.replaceAll("\\D+","");
					str = str.substring(1, str.length());
					int k = Integer.parseInt(str);
					
					BigInteger[] tasUnifie = TasMinTree.prepareTasUnion(t, t2);
					long time = System.currentTimeMillis();
					TasMinTree.union(tasUnifie);
					time = System.currentTimeMillis() - time;
					hash.put(k, hash.get(k) + time);
				}
			}
		
		hash.put(100, hash.get(100)/5);
		hash.put(1000, hash.get(1000)/5);
		hash.put(10000, hash.get(10000)/5);
		hash.put(200, hash.get(200)/5);
		hash.put(20000, hash.get(20000)/5);
		hash.put(500, hash.get(500)/5);
		hash.put(5000, hash.get(5000)/5);
		hash.put(50000, hash.get(50000)/5);

		return hash;
	}
	
	
	public static void creerGraphe(Hashtable<Integer, Double> hash, String title, String y, String x,String filename) {
		XYSeries series = new XYSeries("");
		Set<Integer> set = hash.keySet();		//
		for(Integer key : set) {			//
			//pour chacun de tes résultats tu ajoutes tes coordonnées grâce à ctte fonction
			series.add(key, hash.get(key));
		}										//
		//tu mets tes coordonnées dans un dataset
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series);
		
		//tu changes les titres par rapport à tes résultats
		//Titres du graphes , titre axe abscisses, titre axe ordonnées, ton dataset
		JFreeChart chart = ChartFactory.createXYLineChart(
				title,
				x, 
				y, 
				dataset);
		try {
			//là il faut juste changer le nom de ton fichier de sortie
			ChartUtilities.saveChartAsJPEG(new File(filename), chart, 500, 300);
		} catch (IOException e) {
			System.err.println("Problem occurred creating chart.");
		}
				
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//JEU DE TEST POUR L'IMPLEMENTATION DES TAS AVEC ARBRE
		
		//CONSTRUCTION ET UNION
		/*System.out.println("TAS MIN AVEC ARBRE\n");
		TasMinTree heap = new TasMinTree();
		TasMinTree heap2 = new TasMinTree();
		
		BigInteger k1 = new BigInteger("0x9c1f03a0d9cf510f2765bd0f226ff5dc".substring(2),16);
		BigInteger k2 = new BigInteger("0x10fd1015413104a2f26018d0ab77a727".substring(2),16);
		BigInteger k3 = new BigInteger("0x2e73d8ce4bd45923286e966bc8cf2d95".substring(2),16);
		BigInteger k4 = new BigInteger("0x767accd0c60c603f71a68be994019c7e".substring(2),16);
		BigInteger k5 = new BigInteger("0x34c63c08abab99722b945e57081288e7".substring(2),16);
		BigInteger k6 = new BigInteger("0x6d481adc2aeed025f0374a5982b5c23c".substring(2),16);
		
		//de la plus petite clé a la plus grande
		System.out.println("Données testées\n");
		System.out.println("clé de la plus petite a la plus grande\n");
		System.out.println(k2);
		System.out.println(k3);
		System.out.println(k5);
		System.out.println(k6);
		System.out.println(k4);
		System.out.println(k1);
		System.out.println("\n");
		
		heap.consIter(new BigInteger[] {k1,k2});
		System.out.println("AFFICHAGE PREFIX DE HEAP\n");
		heap.prefix();
		System.out.println("\n");
		
		heap.consIter(new BigInteger[] {k3,k4});
		System.out.println("AFFICHAGE PREFIX DE HEAP\n");
		heap.prefix();
		System.out.println("\n");
		
		heap2.consIter(new BigInteger[] {k5,k6});
		System.out.println("AFFICHAGE PREFIX DE HEAP 2\n");
		heap2.prefix();
		System.out.println("\n");
		
		System.out.println("AFFICHAGE PREFIX DE L'UNION DE HEAP ET HEAP 2\n");
		(TasMinTree.union(TasMinTree.prepareTasUnion(heap, heap2))).prefix();
		System.out.println("------------------");

		//SUPPRESSION DU MINIMUM
		
		System.out.println("Etat de heap avant suppression\n");
		heap.prefix();
		
		System.out.println("\nSuppression du minimum de heap : "+heap.delMin()+"\n");
		
		System.out.println("Etat de heap après cette suppression\n");
		heap.prefix();
		*/
		
		/*System.out.println("----------------------------------------------\n");
		System.out.println("TAS MIN AVEC TABLEAU\n");
		//JEU DE TEST POUR L'IMPLEMENTATION DES TAS AVEC TABLEAU
		
		TasArray tas = new TasArray(4);
		TasArray tas2 = new TasArray(4);
		
		//Affichage du tas array en arbre
		BigInteger p1 = new BigInteger("0x9c1f03a0d9cf510f2765bd0f226ff5dc".substring(2),16);
		BigInteger p2 = new BigInteger("0x10fd1015413104a2f26018d0ab77a727".substring(2),16);
		BigInteger p3 = new BigInteger("0x2e73d8ce4bd45923286e966bc8cf2d95".substring(2),16);
		BigInteger p4 = new BigInteger("0x767accd0c60c603f71a68be994019c7e".substring(2),16);
		BigInteger p5 = new BigInteger("0x34c63c08abab99722b945e57081288e7".substring(2),16);
		BigInteger p6 = new BigInteger("0x6d481adc2aeed025f0374a5982b5c23c".substring(2),16);

		System.out.println("Données testées");
		System.out.println("clé de la plus petite a la plus grande\n");
		System.out.println(p2);
		System.out.println(p3);
		System.out.println(p5);
		System.out.println(p6);
		System.out.println(p4);
		System.out.println(p1);
		System.out.println("\n");
		
		tas.consIter(new BigInteger[] {k1,k2,k3,k4});
		tas2.consIter(new BigInteger[]{k4,k5,k6});
		
		System.out.println("TAS 1 -------------------");
		System.out.println(tas);
		System.out.println("\nTAS 2 -------------------");
		System.out.println(tas2);
		
		System.out.println("\nETAT DE TAS1 APRES SUPPRESSION DU MINIMUM : "+tas.delMin());
		System.out.println("TAS 1 -------------------");
		System.out.println(tas);
		
		System.out.println("\nUNIFICATION DU TAS 1 ET 2");
		TasArray tasUnifie = TasArray.union(tas, tas2);
		System.out.println(tasUnifie);
		
		
		//JEU DE TEST AVEC ARBRE BINAIRE DE RECHERCHE
		System.out.println("\n------------------------------------------------------\n");
		System.out.println("ARBRE BINAIRE DE RECHERCHE");
		System.out.println("DONNÉES TESTÉES : data/cles_alea/jeu_1_nb_cles_100.txt\n");
		BufferedReader reader = null;
		SearchTree st = new SearchTree();

		try {
		    File file = new File("data/cles_alea/jeu_1_nb_cles_100.txt");
		    reader = new BufferedReader(new FileReader(file));

		    String line;
		    while ((line = reader.readLine()) != null) {
		    	BigInteger bi = new BigInteger(line.substring(2),16);
		        st.insert(bi);
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
		
		System.out.println("NOMBRE D'ÉLEMENT DE L'ARBRE BINAIRE : ");
		System.out.println(st.getNbElements());
		System.out.println("\nTEST D'APPARTENANCE DE 0x5eb1d3c4446ded76df352f88b1e44179 DANS L'ARBRE : ");
		System.out.println(st.search(new BigInteger("0x5eb1d3c4446ded76df352f88b1e44179".substring(2),16)));
		
		*/
		/*
		FileBinomiale FB = new FileBinomiale();
		for(int i = 0; i<20; i++) {
			FB = FB.ajouterArbre(new ArbreBinomial(i));
		}
		FB.afficherFileB();
		
		*/
		//tests sur des entiers pour visualiser la structure
		
	
		/*
		
		 
		try {
		    File file = new File("data/cles_alea/jeu_1_nb_cles_100.txt");
		    reader = new BufferedReader(new FileReader(file));

		    String line;
		    while ((line = reader.readLine()) != null) {
		    	BigInteger bi = new BigInteger(line.substring(2),16);
		        fb.ajouterArbre(new ArbreBinomial(bi));
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
		
		fb.afficherFileB();
		 */
		

		
		
		
		//EXPERIMENTATION ET CREATION DES GRAPHIQUE DE TEMPS POUR CONSTRUCTION ET UNION
		
		//construction filebinomiale 
		/*FileBinomiale FB = new FileBinomiale();
		Hashtable<Integer, Double> hash = new Hashtable<Integer, Double>(8);
		hash = FB.resultConsIter();
		FB.creerGraphe(hash);
		
		// A FAIRE : union filebinomiale.
		// A FAIRE : union filebinomiale.
		FileBinomiale FB2 = new FileBinomiale();
		//Hashtable<Integer, Double> hash2 = new Hashtable<Integer, Double>(8);
		hash = FB2.resultUnion();
		creerGraphe(hash, "Complexité union FileBinomiale", "ms", "Les autres files", "fileb_union.jpg");
		
		//construction tas min tableau
		TasArray tasminarray = new TasArray();
		Hashtable<Integer, Double> hash3 = new Hashtable<Integer, Double>(8);
		hash3 = resultConsIter(tasminarray);
		creerGraphe(hash3,"Complexité consIter Tasmin (tableau)","ms","Nb de clés","tas_array_consiter.jpg");
		
		//construction tas min arbre
		TasMinTree tasmintree = new TasMinTree();
		Hashtable<Integer, Double> hash2 = new Hashtable<Integer, Double>(8);
		hash2 = resultConsIter(tasmintree);
		creerGraphe(hash2,"Complexité consIter Tasmin (arbre)","ms","Nb de clés","tas_tree_consiter.jpg");
		
		//Union tas array
		Hashtable<Integer, Double> hash4 = new Hashtable<Integer, Double>(8);
		hash4 = resultUnion(new TasArray());
		creerGraphe(hash4, "Complex union tasMinArray avec un tas initial 20K clé", "ms", "les autre tas","tas_array_union.jpg");
		
		//Union tas arbre
		Hashtable<Integer, Double> hash5 = new Hashtable<Integer, Double>(8);
		hash4 = resultUnion(new TasMinTree());
		creerGraphe(hash4, "Complex union tasMinTree avec un tas initial 20K clé", "ms", "les autre tas","tas_tree_union.jpg");
		*/
		
		SearchTree st = new SearchTree();
		File shakespear = new File("data/Shakespeare/");
		File[] allWords = shakespear.listFiles();
		MessageDigest md;
		ArrayList<String> wordsDistinct =  new ArrayList<String>();
		ArrayList<BigInteger> wordsDistinctBG =  new ArrayList<BigInteger>();
		try {
			md = MessageDigest.getInstance("MD5");
			for (File file : allWords) {
				int ligne = 0;
				
				//System.out.println(fileToArray(file).length+" "+file.getName());
				for (String word : fileToArray(file)) {
					ligne++;
					md.update(word.getBytes());
				    byte[] digest = md.digest();
				    String myHash = DatatypeConverter
				      .printHexBinary(digest).toUpperCase();
				    BigInteger hash = new BigInteger(myHash,16);
				    if(st.search(hash))
				    	;
				    else {
				    	//System.out.println("Premiere occurence du mot : "+word+" dans le fichier : "+file.getName()+" ligne : "+ligne);
				    	st.insert(new BigInteger(myHash,16));
				    	wordsDistinct.add(word);
				    	wordsDistinctBG.add(new BigInteger(myHash,16));
				    }
				}
			}
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println(st.getNbElements());
		System.out.println(wordsDistinct.size());

		BigInteger[] words = new BigInteger[wordsDistinctBG.size()];
		for (int i = 0; i < words.length; i++) {
			words[i] = wordsDistinctBG.get(i);
		}
		
		//Comparaisons des 4 fonctions principale de tas/file binomiale
		
		//ConsIter
		TasArray tas = new TasArray(words.length);
		FileBinomiale fb = new FileBinomiale();
		Hashtable<Integer, Double> hash = new Hashtable<Integer, Double>(8);
		hash.put(50, 0.0);
		hash.put(100, 0.0);
		
		double time = System.currentTimeMillis();
		tas.consIter(words);
		time = System.currentTimeMillis() - time;
		hash.put(50, hash.get(50)+time);
		
		double newtime = System.currentTimeMillis();
		fb=fb.consIter(words);
		newtime = System.currentTimeMillis() - newtime;
		hash.put(100, hash.get(50)+newtime);
		creerGraphe(hash, "temps d'execution ConsIter sur mot distinct Shakespeare", "ms", "tas min (gauche) et file binomiale (droite)", "consiter_SP_tas_array.jpg");
		
		//Supprmin
		//FileBinomiale fb = new FileBinomiale();
		hash = new Hashtable<Integer, Double>(8);
		BigInteger[] container = new BigInteger[wordsDistinct.size()];
		hash.put(50, 0.0);
		hash.put(100, 0.0);
		double time2 = System.currentTimeMillis();
		for (int i = 0; i < wordsDistinct.size(); i++) {
			container[i] = tas.delMin();
		}
		time2 = System.currentTimeMillis() - time2;
		hash.put(50, time2);
		double newtime2 = System.currentTimeMillis();
		for(int i = 0; i < wordsDistinct.size();i++) {
			fb=fb.supprimerMin();
		}
		newtime2 = System.currentTimeMillis() - newtime2;
		
		hash.put(100, newtime2);
		creerGraphe(hash, "temps d'execution supprMin sur mot distinct Shakespeare", "ms", "tas min (gauche) et file binomiale (droite)", "supprmin_SP_tas_array.jpg");
		
		//Ajout
		FileBinomiale fb2 = new FileBinomiale();
		hash = new Hashtable<Integer, Double>(8);
		hash.put(50, 0.0);
		hash.put(100, 0.0);
		double time3 = System.currentTimeMillis();
		for (int i = 0; i < container.length; i++) {
			tas.insert(container[i]);
		}
		time3 = System.currentTimeMillis() - time3;
		hash.put(50, time3);
		
		double newtime3 = System.currentTimeMillis();
		for(int i = 0; i < container.length; i++) {
			fb2 = fb2.ajouterArbre(new ArbreBinomial(container[i]));
		}
		newtime3 = System.currentTimeMillis() - newtime3;
		hash.put(100, newtime3);
		creerGraphe(hash, "temps d'execution ajout sur mot distinct Shakespeare", "ms", "tas min (gauche) et file binomiale (droite)", "ajout_SP_tas_array.jpg");
		
		//Union (séparer le tableau des mots en deux tas et unir les deux
		//FileBinomiale fb = new FileBinomiale();
		TasArray tasq = new TasArray(container.length/2);
		TasArray tasp = new TasArray(container.length/2);
		hash = new Hashtable<Integer, Double>(8);
		hash.put(50, 0.0);
		hash.put(100, 0.0);
		for (int i = 0; i < container.length/2; i++) {
			tasq.insert(container[i]);
		}
		for (int i = container.length/2; i < container.length; i++) {
			tasp.insert(container[i]);
		}
		double time4 = System.currentTimeMillis();
		TasArray.union(tasq, tasp);
		time4 = System.currentTimeMillis() - time4;
		hash.put(50, hash.get(50)+time4);
		
		FileBinomiale fleft = new FileBinomiale();
		FileBinomiale fright = new FileBinomiale();
		for (int i = 0; i < container.length/2; i++) {
			fleft = fleft.ajouterArbre(new ArbreBinomial(container[i]));
		}
		for (int i = container.length/2; i < container.length; i++) {
			fright = fright.ajouterArbre(new ArbreBinomial(container[i]));
		}
		double newtime4 = System.currentTimeMillis();
		fleft.unionFile(fleft, fright);
		newtime4 = System.currentTimeMillis() - newtime4;
		hash.put(100, newtime4);
		
		
		creerGraphe(hash, "temps d'execution union sur mot distinct Shakespeare", "ms", "tas min (gauche) et file binomiale (droite)", "union_SP_tas_array.jpg");
	}
	
}
