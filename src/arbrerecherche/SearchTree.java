package arbrerecherche;

import java.math.BigInteger;

import tas.tree.Node;
import tas.tree.Tree;

public class SearchTree extends Tree<BigInteger>{
	
	public SearchTree() {
		// TODO Auto-generated constructor stub
		super();
	}

	public SearchTree(Node<BigInteger> root) {
		// TODO Auto-generated constructor stub
		super(root);
	}
	
	public Boolean search(BigInteger el) {
		return search(el,root);
	}
	
	public void insert(BigInteger el) {
		nbelements++;
		root = insert(el, root);
	}
	
	private Node<BigInteger> insert(BigInteger el, Node<BigInteger> root) {
		if(root == null)
			return new Node<BigInteger>(null, el, null, null);
		else if((el.compareTo(root.getElement()) >0)) {
			root.setRightson(insert(el,root.getRightson()));
		}
			
		else if((el.compareTo(root.getElement()) <0)){
			root.setLeftson(insert(el,root.getLeftson()));
		}
			
		return root;
	}
	
	private Boolean search(BigInteger el, Node<BigInteger> root) {
		if(root == null)
			return false;
		if((el.compareTo(root.getElement())) == 0)
			return true;
		else if((el.compareTo(root.getElement()) >0))
			return search(el,root.getRightson());
		else if((el.compareTo(root.getElement()) <0))
			return search(el,root.getLeftson());
		return false;
			
	}

}
