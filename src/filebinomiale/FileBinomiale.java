package filebinomiale;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Set;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.sun.corba.se.impl.oa.poa.ActiveObjectMap.Key;

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

	//fonction qui ajoute les clés une par une, directement depuis le fichier
	public FileBinomiale consIter(File file) {
		BufferedReader reader = null;
		FileBinomiale FB = new FileBinomiale();

		try {
			reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null) {
				BigInteger key = new BigInteger(line.substring(2), 16);
				//System.out.println(key);
				FB = FB.ajouterArbre(new ArbreBinomial(key));
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
		return FB;
	}

	public void supprimerMin() {
		ArbreBinomial A = this.tete;
		if(A.getFrere() == null) {
			this.tete = null;
		}
		while(A.getFrere().getFrere() != null) {
			A = A.getFrere();
		}
		A.setFrere(null);
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
			FB = FB.consIter(file);
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
	
	public void creerGraphe(Hashtable<Integer, Double> hash) {
		XYSeries series = new XYSeries("");
		Set<Integer> set = hash.keySet();		//
		for(Integer key : set) {				//
			series.add(key, hash.get(key));
		}										//
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series);
		
		JFreeChart chart = ChartFactory.createXYLineChart(
				"Complexité ConsIter sur FileBinomiale", 
				"Nombre de clés", 
				"Complexité en ms", 
				dataset);
		try {
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
