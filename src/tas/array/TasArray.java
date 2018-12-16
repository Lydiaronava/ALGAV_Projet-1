package tas.array;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;

public class TasArray {
	private int currentSize = 1; // je commence a 1 dans le tableau c'est + efficace pour les calcul d'indice des fils, a retenir pour tout le reste du code
	private int capacity;
	private BigInteger[] elements;
	
	public int size() {
		return currentSize-1;
	}
	public int capacity() {
		return capacity;
	}
	
	public TasArray() {
		// TODO Auto-generated constructor stub
	}
	
	public TasArray(int cap) {
		capacity = cap;
		elements = new BigInteger[capacity];
	}
	
	public TasArray(int cap, BigInteger[] elts) {
		capacity = cap;
		currentSize = elts.length;
		elements = new BigInteger[capacity];
		for (int i = 0; i < elts.length; i++) {
			elements[i] = elts[i];
		}
		
	}
	
	//tas dynamique donc je double a chaque fois qu'on a atteins la capacité max.
	@SuppressWarnings("unchecked")
	private void doubleCapacity() {
		capacity*=2;
		BigInteger[] newElts = new BigInteger[capacity];
		for (int i = 0; i < currentSize; i++) {
			newElts[i] = elements[i];
		}
		elements = newElts;
	}
	
	//j'insère bètement a la fin, et je lance percolate up pour maintenir la propriété de tas min.
	public void insert(BigInteger el) {
		if(currentSize == capacity)
			doubleCapacity();
		elements[currentSize] = el;
		percolate_up();
		currentSize++;
	}
	
	//idem tasmintree
	public void percolate_up() {
		BigInteger el = elements[currentSize];
		int placeEl = currentSize;
		while(elements[1] != el && el.compareTo(elements[placeEl/2]) == -1) {
				BigInteger father = elements[placeEl/2];
				elements[placeEl/2] = el;
				elements[placeEl] = father;
				placeEl = placeEl/2;
		}
	}

	//idem tasmintreee
	private void percolate_down(int currentId) {
		int imax = currentId;
		int leftson = currentId*2;
		int rightson = leftson+1;
		
		if(leftson < currentSize && elements[leftson].compareTo(elements[imax]) < 0) {
			imax = leftson;
		}
		if(rightson < currentSize && elements[rightson].compareTo(elements[imax]) < 0) {
			imax = rightson;
		}
		if(imax != currentId) {
			BigInteger tmp = elements[imax];
			elements[imax] = elements[currentId];
			elements[currentId] = tmp;
			percolate_down(imax);
		}
}
	//meme principe que tasmintree pour etre en O(n)
	public void consIter(BigInteger[] elts) {
		for (int i = 0; i < elts.length; i++) {
			if(currentSize == capacity)
				doubleCapacity();
			elements[currentSize] = elts[i];
			currentSize++;
		}
		for (int i = currentSize/2; i > 0; i--) {
			percolate_down(i);
		}
	}
	
	public BigInteger delMin() {
		if(currentSize > 0) {
			BigInteger root = elements[1];
			elements[1] = elements[size()];
			currentSize--;
			percolate_down(1);
			return root;
		}
		return null;
	}

	
	public static TasArray union(TasArray t1, TasArray t2) {
		TasArray t3 = new TasArray(t1.capacity+t2.capacity);
		t3.consIter(Arrays.copyOfRange(t1.elements, 1, t1.size())); // on commence a insérer a 1 donc ici on prend le tableau privé de la première case vide
		t3.consIter(Arrays.copyOfRange(t2.elements, 1, t2.size()));
		return t3;
	}
	
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuilder str = new StringBuilder();
		str.append("[");
		for (int i = 1; i < currentSize; i++) {
			if(i == (currentSize- 1))
				str.append(elements[i]);
			else
				str.append(elements[i]+":");
		}
		str.append("]");
		return str.toString();
		
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
