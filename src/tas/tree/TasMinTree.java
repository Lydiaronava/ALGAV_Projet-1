package tas.tree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;


public class TasMinTree extends Tree<BigInteger> {
	
	private Node<BigInteger> lastEl;
	
	public TasMinTree() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	public TasMinTree(Node<BigInteger> el) {
		super(el);
	}
	
	//Algo en O(n), d'abord j'insère tout les éléments sans reflechir, et ensuite j'appel percolateDown sur l'arbre en partant des derniers père vers la racine.
	//a la fin evidemment j'oublie pas de recalculer ma représentation binaire, et d'aller chercher le dernier élément de l'arbre pour facilité la suppression.
	public void consIter(BigInteger[] els) {
		for (int i = 0; i < els.length; i++) {
			nbelements++;
			binaryTransform(nbelements);
			root = insert(null,root,els[i],binaryRepresentation.size()-1);
		}
		for (int i = getNbElements()/2; i > 0; i--) {
			if(i == 1)
				percolateDown(root);
			else {
				binaryTransform(i);
				Node<BigInteger> t = getNode(root, binaryRepresentation.size()-1);
				percolateDown(t);
			}
		}
		binaryTransform(nbelements);
		lastEl = getNode(root, binaryRepresentation.size()-1);
	}
	public void insert(BigInteger el) {
		nbelements++;
		binaryTransform(nbelements);
		root = insert(null,root,el,binaryRepresentation.size()-1);
		lastEl = getNode(root, binaryRepresentation.size()-1);
		percolateUp(lastEl);
		
	}
	
	
	//Methode qui me sort les elements de l'arbre sous forme de liste, avec un arbre c'est chiant avec la recursion et les indices du tableau.
	public ArrayList<BigInteger> toList() {
		return toList(root,new ArrayList<BigInteger>());
	}
	private ArrayList<BigInteger> toList(Node<BigInteger> node,ArrayList<BigInteger> container) {
		container.add(node.getElement());
		if(node.getLeftson() != null) {
			toList(node.getLeftson(), container);
		}
		if(node.getRightson() != null) {
			toList(node.getRightson(), container);
		}
		return container;
	}
	//preparation du tas qui va contenir l'union des deux en parametre
	public static BigInteger[] prepareTasUnion(TasMinTree t1,TasMinTree t2) {
		Object[] t1arr = t1.toList().toArray(); 
		Object[] t2arr = t2.toList().toArray();
		BigInteger[] t3 = new BigInteger[t1arr.length+t2arr.length];
		for (int i = 0; i < t1arr.length; i++) {
			t3[i] = (BigInteger) t1arr[i];
		}
		for (int i = 0; i < t2arr.length; i++) {
			t3[t1arr.length+i] = (BigInteger) t2arr[i];
		}
		return t3;
	}
	
	//L'union : je transforme les deux arbres en liste, puis je prend les tableau de ses listes, 
	//que je fusionne en un seul tableau et que je balance a construction d'un troisieme arbre
	public static TasMinTree union(BigInteger[] b) {
		TasMinTree tree = new TasMinTree();
		tree.consIter(b);
		return tree;
		
	}
	
	//Tout le systeme de tas codé en arbre je l'ai basé sur une chose : la représentation binaire des élements
	// Exemple : je veux insérer un element dans un arbre a 3 elements, je vais d'abord calculer la représentation binaire de 4 (3 + 1 le nouvel element)
	// (je fais ça dans la classe Tree) ça me donne un tableau [0,0,1] (c'est a l'envers, normal) donc je remove le dernier element du tableau, et ça me donne
	//le chemin a emprunté pour insérer le prochain élément.
	public Node<BigInteger> insert(Node<BigInteger> father, Node<BigInteger> r, BigInteger el, int id) {
		if(root == null) {
			return new Node<BigInteger>(father,el,null,null);
		}
		else {
			if(r == null) {
				return new Node<BigInteger>(father,el,null,null);
			}
			else {
				if(binaryRepresentation.get(id) == 0) {
					r.setLeftson(insert(r,r.getLeftson(),el,id-1));
				}
				else {
					r.setRightson(insert(r,r.getRightson(),el,id-1));
				}
			}
		}
		return r;
	}
	//algo pour me donner le noeud de mon choix, au pire Log(n) en partant de la racine pour une feuille
	public Node<BigInteger> getNode(Node<BigInteger> r,int id) {
		if(id < 0) {
			return r;
		}
		if(binaryRepresentation.get(id) == 0) {
			return getNode(r.getLeftson(),id-1);
		}
		else if(binaryRepresentation.get(id) == 1) {
			return getNode(r.getRightson(),id-1);
		}
		return r;
	}
	//d'abord je retire la racine, puis je transpose le dernier élément dans la racine, et je supprime le dernier noeud qui contenait le dernier element
	// en appelant le deuxieme del min.
	//a la fin, je doit toujours recalculer la représentation binaire pour avoir le prochain chemin a emprunté en cas d'insertion/suppression
	// et je stock aussi le dernier élément de l'arbre pour facilité l'échange entre le celui ci et la racine quand je doit supprimer le minimum.
	//percolateDown/Up sont les algos qui servent a maintenir la propriété de tas min.
	public BigInteger delMin() {
		if(root == null)
			return null;
		if(root.getElement().compareTo(lastEl.getElement()) == 0)
			return root.getElement();
		
		BigInteger tmp = root.getElement();
		root = delMin(root,binaryRepresentation.size()-1);
		root.setElement(lastEl.getElement());
		nbelements--;
		binaryTransform(nbelements);
		percolateDown(root);
		lastEl = getNode(root,binaryRepresentation.size()-1);
		return tmp;
	}
	public Node<BigInteger> delMin(Node<BigInteger> r, int id) {
			if(id >= 0) {
				if(binaryRepresentation.get(id) == 0) {
					r.setLeftson(delMin(r.getLeftson(),id-1));
				}
				else if(binaryRepresentation.get(id) == 1) {
					r.setRightson(delMin(r.getRightson(),id-1));
				}
			}
			else {
				return null;
			}
			return r;
	}
	//je me compare aux père et si il est plus grand, j'échange avec lui
	public void percolateUp(Node<BigInteger> currentEl) {
		if(currentEl.getFather() == null)
			;
		else if(currentEl.getElement().compareTo(currentEl.getFather().getElement()) < 0) {
			BigInteger father = currentEl.getFather().getElement();
			currentEl.getFather().setElement(currentEl.getElement());
			currentEl.setElement(father);
			percolateUp(currentEl.getFather());
		}
	}
	//si l'un de mes fils est plus petit que moi, j'échange avec lui, si mes deux fils sont plus petit que moi, j'échange avec min(filsg,filsd)
	public void percolateDown(Node<BigInteger> currentNode) {
		Node<BigInteger> tmpNode = null;
		if(currentNode.getLeftson()!= null && currentNode.getLeftson().getElement().compareTo(currentNode.getElement()) < 0) {
			if(currentNode.getRightson()!= null && currentNode.getRightson().getElement().compareTo(currentNode.getElement()) < 0) {
				if(currentNode.getLeftson().getElement().compareTo(currentNode.getRightson().getElement()) > 0)
					tmpNode = currentNode.getRightson();
				else
					tmpNode = currentNode.getLeftson();
			}
			else
				tmpNode = currentNode.getLeftson();		
		}
		if(currentNode.getRightson()!= null && currentNode.getRightson().getElement().compareTo(currentNode.getElement()) < 0) {
			if(currentNode.getLeftson()!= null && currentNode.getLeftson().getElement().compareTo(currentNode.getElement()) < 0) {
				if(currentNode.getRightson().getElement().compareTo(currentNode.getLeftson().getElement()) > 0)
					tmpNode = currentNode.getLeftson();
				else
					tmpNode = currentNode.getRightson();
			}
			else
				tmpNode = currentNode.getRightson();
		}
		
		if(tmpNode != null) {
			BigInteger son = tmpNode.getElement();
			tmpNode.setElement(currentNode.getElement());
			currentNode.setElement(son);
			percolateDown(tmpNode);
		}
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
}
