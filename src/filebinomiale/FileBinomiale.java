package filebinomiale;

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
		//System.out.println("entrée dans union");
		//Cas sans retenue
		/*
		System.out.println(" LA FILE F1");
		F1.afficherFileB();
		System.out.println(" LA FILE F2");
		F2.afficherFileB();
		System.out.println(" L'ARBRE RETENUE");
		ret.afficherArbre();
		System.out.println("THIS");
		this.afficherFileB();
		System.out.println("//////////////////////////////////////////////////////////////////");
		
		*/
		
		
		
		if(ret.estVide()) {	//System.out.println("	cas sans retenue");
			if(F1.estVide()) {
				System.out.println("la file F1 est vide \n");
				return F2;
			}
			if(F2.estVide()) {
				System.out.println("La file F2 est vide \n");
				return F1;
			}
			
			ArbreBinomial tmp1 = F1.degreMin();
			ArbreBinomial tmp2 = F2.degreMin();
			if(tmp1.getDegre() < tmp2.getDegre()) { 
				System.out.println("degreMin(F1) < degreMin(F2) \n");
				ajoutMin(tmp1, unionFile(F1.reste(), F2));
			}
			if(tmp2.getDegre() < tmp1.getDegre()) {
				ajoutMin(tmp2, unionFile(F2.reste(), F1));
			}
			if(tmp1.getDegre() == tmp2.getDegre()) {
				unionRetenue(F1.reste(), F2.reste(), tmp1.union(tmp2));
			}
		}
		else {		//System.out.println("	cas avec retenue");
			if(F1.estVide() && F2.estVide()) {
				ret.file();
			}
			if(F1.estVide())
				unionFile(ret.file(), F2);
			if(F2.estVide())
				unionFile(ret.file(), F1);
			
			ArbreBinomial tmp1 = F1.degreMin();
			ArbreBinomial tmp2 = F2.degreMin();
			if(ret.getDegre() < tmp1.getDegre() && ret.getDegre() < tmp2.getDegre())
				ajoutMin(ret, unionFile(F1,F2));
			if(ret.getDegre() == tmp1.getDegre() && ret.getDegre() == tmp2.getDegre())
				ajoutMin(ret, unionRetenue(F1.reste(), F2.reste(), tmp1.union(tmp2)));
			if(ret.getDegre() == tmp1.getDegre() && ret.getDegre() < tmp2.getDegre())
				unionRetenue(F1.reste(), F2, tmp1.union(ret));
			if(ret.getDegre() == tmp2.getDegre() && ret.getDegre() < tmp1.getDegre())
				unionRetenue(F2.reste(), F1, tmp2.union(ret));
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
			System.out.println("degre de A =  " + A.getDegre() + "     degre de minFB = " +FB.degreMin().getDegre() );
			if(A.getDegre() < FB.degreMin().getDegre()) {
				ArbreBinomial tmp = FB.tete;
				while(tmp.getFrere() != null) {
					tmp = tmp.getFrere();
				}
				tmp.setFrere(A);
				A.getRacine().setFrere(null);
				System.out.println("La nouvelle file après ajoutMin\n");
				FB.afficherFileB();
				System.out.println("fin ajout min \n");
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
	
	public void supprimerMin() {
		
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
