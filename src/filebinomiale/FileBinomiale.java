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
			return union(this, new FileBinomiale(A));
		}
		else 	
			return new FileBinomiale(A);
	}
	
	public FileBinomiale union(FileBinomiale F1, FileBinomiale F2) {
		F1.afficherFileB();
		F2.afficherFileB();
		System.out.println("//////////////////////////////////////////////////////////////////");
		return unionRetenue(F1, F2, new ArbreBinomial());
	}
	
	//algo slides 24 et 25 du chapitre 1
	private FileBinomiale unionRetenue(FileBinomiale F1, FileBinomiale F2, ArbreBinomial ret) {
		//System.out.println("entrée dans union");
		//Cas sans retenue
		if(ret.estVide()) {	//System.out.println("	cas sans retenue");
			if(F1.estVide())
				return F2;
			if(F2.estVide())
				return F1;
			
			ArbreBinomial tmp1 = F1.degreMin();
			ArbreBinomial tmp2 = F2.degreMin();
			if(tmp1.getDegre() < tmp2.getDegre())
				return ajoutMin(tmp1, union(F1.reste(), F2));
			if(tmp2.getDegre() < tmp1.getDegre())
				return ajoutMin(tmp2, union(F2.reste(), F1));
			if(tmp1.getDegre() == tmp2.getDegre())
				return unionRetenue(F1.reste(), F2.reste(), tmp1.union(tmp2));
		}
		else {		//System.out.println("	cas avec retenue");
			if(F1.estVide() && F2.estVide()) {
				return ret.file();
			}
			if(F1.estVide())
				return union(ret.file(), F2);
			if(F2.estVide())
				return union(ret.file(), F1);
			
			ArbreBinomial tmp1 = F1.degreMin();
			ArbreBinomial tmp2 = F2.degreMin();
			if(ret.getDegre() < tmp1.getDegre() && ret.getDegre() < tmp2.getDegre())
				return ajoutMin(ret, union(F1,F2));
			if(ret.getDegre() == tmp1.getDegre() && ret.getDegre() == tmp2.getDegre())
				return ajoutMin(ret, unionRetenue(F1.reste(), F2.reste(), tmp1.union(tmp1)));
			if(ret.getDegre() == tmp1.getDegre() && ret.getDegre() < tmp2.getDegre())
				return unionRetenue(F1.reste(), F2, tmp1.union(ret));
			if(ret.getDegre() == tmp2.getDegre() && ret.getDegre() < tmp1.getDegre())
				return unionRetenue(F2.reste(), F1, tmp2.union(ret));
		}
		return null;
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
		
		if(FB.estVide())
			return new FileBinomiale(A);
		if(!A.estVide()) {
			if(A.getDegre() < FB.degreMin().getDegre()) {
				ArbreBinomial tmp = FB.tete;
				while(tmp.getFrere() != null) {
					tmp = tmp.getFrere();
				}
				tmp.setFrere(A);
				A.getRacine().setFrere(null);
				return this;
			}
			else {
				System.out.println("impossible d'ajouter");
				return this;
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
			}
		}
		else {
			System.out.println("La file binomiale est vide");
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
