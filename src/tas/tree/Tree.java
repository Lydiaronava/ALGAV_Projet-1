package tas.tree;

import java.util.ArrayList;

import tas.tree.Node;

public class Tree<E> {
	
	protected Node<E> root;
	protected int nbelements = 0;
	protected ArrayList<Integer> binaryRepresentation = new ArrayList<Integer>();
	
	
	public Tree() {
		// TODO Auto-generated constructor stub
		root = null;
	}

	public Tree(Node<E> r) {
		// TODO Auto-generated constructor stub
		root = r;
		nbelements = getNbElements(root);
	}
	
	public ArrayList<Integer> getBinaryRepresentation(){
		return binaryRepresentation;
	}

	public int getNbElements() {
		return getNbElements(root);
	}
	public int getNbElements(Node<E> root) {
		if(root == null)
			return 0;
		return getNbElements(root.getLeftson()) + 1 + getNbElements(root.getRightson());
	}
	//représentation binaire d'un entier, dans un tableau
	public void binaryTransform(int d) {
		int tmp = d;
		if(binaryRepresentation.size()>0)
			binaryRepresentation.clear();
		while(tmp != 0) {
			int r = tmp%2;
			binaryRepresentation.add(r);
			tmp = (tmp-r)/2;
		}
		binaryRepresentation.remove(binaryRepresentation.size()-1);
	}
	
	//les deux parcours de base d'un arbre pour facilité les test et débbug
	
	public void infix() {
		infix(root);
	}
	public void infix(Node<E> r) {
		if(r.getLeftson() != null)
			infix(r.getLeftson());
		System.out.println(r.getElement());
		if(r.getRightson() != null) {
			infix(r.getRightson());
		}
	}
	
	public void prefix() {
		prefix(root);
	}
	public void prefix(Node<E> r) {
		System.out.println(r.getElement());
		if(r.getLeftson() != null) {
			prefix(r.getLeftson());
		}
		if(r.getRightson() != null) {
			prefix(r.getRightson());
		}
			
	}
	

}
