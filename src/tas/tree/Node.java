package tas.tree;

public class Node<E> {
	private E element;
	private Node<E> father, leftson, rightson;
	
	public Node() {
		setElement(null);
		setLeftson(null);
		setRightson(null);
		setFather(null);
	}
	
	public Node(E el) {
		setElement(el);
		setLeftson(null);
		setRightson(null);
		setFather(null);
	}
	
	public Node(Node<E> fath,E el, Node<E> left, Node<E> right) {
		setElement(el);
		setLeftson(left);
		setRightson(right);
		setFather(fath);
	}
	

	public Node<E> getLeftson() {
		return leftson;
	}

	public void setLeftson(Node<E> leftson) {
		this.leftson = leftson;
	}

	public Node<E> getRightson() {
		return rightson;
	}

	public void setRightson(Node<E> rightson) {
		this.rightson = rightson;
	}

	public E getElement() {
		return element;
	}

	public void setElement(E element) {
		this.element = element;
	}

	public Node<E> getFather() {
		return father;
	}

	public void setFather(Node<E> father) {
		this.father = father;
	}

}
