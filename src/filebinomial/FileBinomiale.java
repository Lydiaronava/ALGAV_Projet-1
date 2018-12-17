package filebinomial;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Set;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import tas.FileNameComparator;

public class FileBinomiale {

	private ArbreBinomial tete;
	private ArbreBinomial min;

	public FileBinomiale() {
		tete = null;
		min = null;
	}

	public FileBinomiale(ArbreBinomial A) {
		tete = A;
		min = A;
	}

	public FileBinomiale ajouterArbre(ArbreBinomial A) {
		if(!estVide()) {
			if(min.getCle().compareTo(A.getCle()) > 0) {
				min = A;
			}
			return unionFile(this, new FileBinomiale(A));
		}
		else 	
			return new FileBinomiale(A);
	}

	public FileBinomiale unionFile(FileBinomiale F1, FileBinomiale F2) {
		//F1.afficherFileB();
		//F2.afficherFileB();
		//System.out.println("//////////////////////////////////////////////////////////////////");
		return unionRetenue(F1, F2, new ArbreBinomial());
	}

	//algo slides 24 et 25 du chapitre 1
	private FileBinomiale unionRetenue(FileBinomiale F1, FileBinomiale F2, ArbreBinomial ret) {
		if(ret.estVide()) {	
			if(F1.estVide()) {
				return F2;
			}
			if(F2.estVide()) {
				return F1;
			}

			ArbreBinomial tmp1 = F1.degreMin();
			ArbreBinomial tmp2 = F2.degreMin();
			if(tmp1.getDegre() < tmp2.getDegre()) { 
				return ajoutMin(tmp1, unionFile(F1.reste(), F2));
			}
			if(tmp2.getDegre() < tmp1.getDegre()) {
				return ajoutMin(tmp2, unionFile(F2.reste(), F1));
			}
			if(tmp1.getDegre() == tmp2.getDegre()) {
				return unionRetenue(F1.reste(), F2.reste(), tmp1.union(tmp2));
			}
		}
		else {		
			if(F1.estVide() && F2.estVide()) {
				return ret.file();
			}
			if(F1.estVide())
				return unionFile(ret.file(), F2);
			if(F2.estVide())
				return unionFile(ret.file(), F1);

			ArbreBinomial tmp1 = F1.degreMin();
			ArbreBinomial tmp2 = F2.degreMin();
			if(ret.getDegre() < tmp1.getDegre() && ret.getDegre() < tmp2.getDegre())
				return ajoutMin(ret, unionFile(F1,F2));
			if(ret.getDegre() == tmp1.getDegre() && ret.getDegre() == tmp2.getDegre())
				return ajoutMin(ret, unionRetenue(F1.reste(), F2.reste(), tmp1.union(tmp2)));
			if(ret.getDegre() == tmp1.getDegre() && ret.getDegre() < tmp2.getDegre())
				return unionRetenue(F1.reste(), F2, tmp1.union(ret));
			if(ret.getDegre() == tmp2.getDegre() && ret.getDegre() < tmp1.getDegre())
				return unionRetenue(F2.reste(), F1, tmp2.union(ret));
		}
		return this;
	}




	public boolean estVide() {
		return tete == null;
	}

	public ArbreBinomial degreMin() {		//renvoie l'arbre avec le degre min --> l'arbre le plus à droite
		ArbreBinomial tmp = this.tete;
		if(tete != null) {
			while(tmp.getFrere() != null) {
				tmp = tmp.getFrere();
			}
			return tmp;
		}
		return null;
	}


	//supprime l'arbre de degre minimum càd le plus à droite 
	public FileBinomiale reste() {		
		if(tete == null)
			return this;
		if(tete.getFrere() == null) {
			tete = null;
			return this;
		}
		ArbreBinomial tmp = tete;
		while(tmp.getFrere().getFrere() != null) {
			//this.afficherFileB();
			tmp = tmp.getFrere();
		}
		tmp.setFrere(null);
		return this;


	}

	public FileBinomiale ajoutMin(ArbreBinomial A, FileBinomiale FB) {
		if(FB.estVide()) {
			FB.tete = A;
		}
		if(!A.estVide()) {
			//System.out.println("degre de A =  " + A.getDegre() + "     degre de minFB = " +FB.degreMin().getDegre() );
			if(A.getDegre() < FB.degreMin().getDegre()) {
				ArbreBinomial tmp = FB.tete;
				while(tmp.getFrere() != null) {
					tmp = tmp.getFrere();
				}
				tmp.setFrere(A);
				A.getRacine().setFrere(null);
				return FB;
			}
			else {
				System.out.println("impossible d'ajouter		l'arbre que vous avez essayé d'ajouter :");
				A.afficherArbre();
				System.out.println("la FB que vous essayer de compléter");
				FB.afficherFileB();
				return FB;
			}
		}
		return null;
	}

	//fonction qui ajoute les clés une par une
	public FileBinomiale consIter(BigInteger[] tab) {
		FileBinomiale FB = new FileBinomiale();
		for(int i = 0; i < tab.length; i++){
			FB = FB.ajouterArbre(new ArbreBinomial(tab[i]));
		}
		return FB;
	}

	public BigInteger[] fileToArray(File file) {
		BufferedReader reader = null;
		BigInteger[] bgs = new BigInteger[0];
		try {
			reader = new BufferedReader(new FileReader(file));
			String line;
			int cpt = 0;
			while ((line = reader.readLine()) != null) {
				cpt++;
			}
			bgs = new BigInteger[cpt];
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
				bgs[cpt] = new BigInteger(line.substring(2), 16);
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

	public FileBinomiale supprimerMin() {
		//ArbreBinomial minVal = min;
		FileBinomiale decap = min.decapiter();
		
		//supprimer l'arbre du min dans la FB
		ArbreBinomial temp = tete;
		ArbreBinomial prec = temp;
		while(temp.getFrere() != null) {
			if(temp.getCle().compareTo(min.getCle()) == 0) {
				prec.setFrere(temp.getFrere());
				break;
			}
			prec = temp;
			temp = temp.getFrere();
		}
			return this.unionFile(decap, this);

	}

	public Hashtable<Integer, Double> resultConsIter(){

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
			FileBinomiale FB = new FileBinomiale();
			String str = file.getName();
			str = str.replaceAll("\\D+","");
			str = str.substring(1, str.length());
			int k = Integer.parseInt(str);
			long time = System.currentTimeMillis();
			//long time = System.nanoTime();
			BigInteger[] tab = fileToArray(file);
			FB = FB.consIter(tab);
			time = System.currentTimeMillis() - time;
			//time = System.nanoTime();
			hash.put(k, hash.get(k) + time);

		}
		System.out.println(hash);

		hash.put(100, hash.get(100)/5);
		hash.put(1000, hash.get(1000)/5);
		hash.put(10000, hash.get(10000)/5);
		hash.put(200, hash.get(200)/5);
		hash.put(20000, hash.get(20000)/5);
		hash.put(500, hash.get(500)/5);
		hash.put(5000, hash.get(5000)/5);
		hash.put(50000, hash.get(50000)/5);
		System.out.println(hash);
		return hash;
	}

	public Hashtable<Integer, Double> resultUnion(){
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

		for (File[] files : listejx) {
			Arrays.sort(files, new FileNameComparator());
		}

		for(File[] files : listejx) {
			File filepivot = files[files.length/2];
			BigInteger[] reftab = fileToArray(filepivot);

			for(File f : files) {
				FileBinomiale FB = new FileBinomiale();
				FB = FB.consIter(reftab);
				FileBinomiale FB2 = new FileBinomiale();
				BigInteger[] tab = fileToArray(f);
				FB2=FB2.consIter(tab);
				String str = f.getName();
				str = str.replaceAll("\\D+","");
				str = str.substring(1, str.length());
				int k = Integer.parseInt(str);

				long time = System.currentTimeMillis();
				FB2.unionFile(FB, FB2);
				time = System.currentTimeMillis() - time;
				hash.put(k, hash.get(k) + time);
			}
		}
		System.out.println(hash);
		hash.put(100, hash.get(100)/5);
		hash.put(1000, hash.get(1000)/5);
		hash.put(10000, hash.get(10000)/5);
		hash.put(200, hash.get(200)/5);
		hash.put(20000, hash.get(20000)/5);
		hash.put(500, hash.get(500)/5);
		hash.put(5000, hash.get(5000)/5);
		hash.put(50000, hash.get(50000)/5);
		System.out.println(hash);

		return hash;
	}

	public void creerGraphe(Hashtable<Integer, Double> hash) {
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
				"Complexité ConsIter sur FileBinomiale", 
				"Nombre de clés", 
				"Complexité en ms", 
				dataset);
		try {
			//là il faut juste changer le nom de ton fichier de sortie
			ChartUtilities.saveChartAsJPEG(new File("FBcomplexConsIter.jpg"), chart, 500, 300);
		} catch (IOException e) {
			System.err.println("Problem occurred creating chart.");
		}

	}

	public void afficherFileB() {
		if(!estVide()) {
			ArbreBinomial tmp = tete.getRacine();
			int i = 1;
			while(tmp != null) {
				System.out.println("****** Le " + i + "e arbre de la file ******");
				tmp.afficherArbre();
				i++;
				System.out.println();
				tmp = tmp.getFrere();
				System.out.println("\n");
			}
		}
		else {
			System.out.println("	La file binomiale est vide");
		}
	}

	public ArbreBinomial getTete() {
		return tete;
	}

	public void setTete(ArbreBinomial tete) {
		this.tete = tete;
	}
	public ArbreBinomial getMin() {
		return min;
	}
	public void setMin(ArbreBinomial min) {
		this.min = min;
	}




}
