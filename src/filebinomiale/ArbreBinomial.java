package filebinomiale;

//import java.math.BigInteger;

public class ArbreBinomial {
	
	
	//structure d'un noeud
	private ArbreBinomial frere;
	private ArbreBinomial fils;
	private ArbreBinomial pere;
	private int degre;
	private int cle;
	
	//pointeur sur la racine
	private ArbreBinomial racine;
	
	public ArbreBinomial(){
		frere = null;
		fils = null;
		pere = null;
		degre = -1;
		cle = -1;
		racine = null;
	}
	
	public ArbreBinomial(int cle) {
		frere = null;
		fils = null;
		pere = null;
		degre = 0;
		this.cle = cle;
		racine = this;
	}
	
	public boolean estVide() {
		if(racine == null)
			return true;
		return false;
	}
	
	public int degre() {
		return racine.getDegre();
	}
	
	//union de deux arbres de même degré
	//utilise unionFrere pour ne pas avoir à écrire le même code pour les deux cas
	//doit retourner un ArbreBinomial pour l'utiliser dans union des FB
	public ArbreBinomial union(ArbreBinomial A2)  {
		if(degre() == A2.degre() && this != null && A2 != null) {
			if(racine.getCle() < A2.racine.getCle()) {	//clé(this) < clé(A2)  --> la racine du nouvel arbre est la racine de this
				unionFrere(A2);
				incremDegre();
				return this;
			}
			else {
				A2.unionFrere(this);
				A2.incremDegre();
				return A2;
			}
			
		}
		return null;
	}
	
	//fonction qui s'occupe de mettre les bons pointeurs
	private void unionFrere(ArbreBinomial A2) {		//pour clé(this) > cle(A2)
		if(degre() > 0 && A2.degre() > 0) {		//inutile pour les arbres à un noeud
			ArbreBinomial tmp1 = fils;
			ArbreBinomial tmp2 = A2.racine;
			ArbreBinomial tmp3 = tmp2;
			
			while(tmp1 != null && tmp2 != null ) {
				while(tmp3.frere != null)
					tmp3 = tmp3.frere;
				tmp3.frere = tmp1;
				tmp1 = tmp1.fils;
				tmp2 = tmp2.fils;
				tmp3 = tmp2;
			}
		}
		
		//A2.frere = fils;
		fils = A2.racine;
		A2.pere = this.racine;
		
	}
	
	public FileBinomiale decapiter() {
		ArbreBinomial tmp = this.getFils();
		FileBinomiale FB = new FileBinomiale();
		while(tmp != null) {
			FB.ajouterArbre(tmp.getFrere());
			tmp = tmp.getFrere();
		}
		return FB;
	}
	
	public FileBinomiale file() {		//renvoie une file binomiale qui contient un seul arbre
		this.racine.frere = null;
		return new FileBinomiale(this);
	}
	
	
	public void afficherArbre() {
		if(racine != null) {
			ArbreBinomial tmpFils = racine;
			System.out.println("La racine de degré "+ tmpFils.getDegre() + ": " + tmpFils.getCle());
			while(tmpFils.getFils() != null) {
				tmpFils = tmpFils.getFils();
				ArbreBinomial tmpFrere = tmpFils;
				System.out.print("Noeud de degré " + tmpFils.getDegre() + " : " + tmpFils.getCle());
				
				while(tmpFrere.getFrere() != null) {
					tmpFrere = tmpFrere.getFrere();
					System.out.print(" " + tmpFrere.getCle());
				}
				System.out.println();

			}
		}
	}

	public ArbreBinomial getFrere() {
		return frere;
	}

	public void setFrere(ArbreBinomial frere) {
		this.frere = frere;
	}

	public ArbreBinomial getFils() {
		return fils;
	}

	public void setFils(ArbreBinomial fils) {
		this.fils = fils;
	}

	public ArbreBinomial getPere() {
		return pere;
	}

	public void setPere(ArbreBinomial pere) {
		this.pere = pere;
	}

	public int getDegre() {
		return degre;
	}

	public void setDegre(int degre) {
		this.degre = degre;
	}

	public int getCle() {
		return cle;
	}

	public void setCle(int cle) {
		this.cle = cle;
	}
	
	public ArbreBinomial  getRacine() {
		return racine;
	}
	
	public void incremDegre() {
		degre+=1;
	}
	
	public void decremDegre() {
		degre-=1;
	}
	

}
