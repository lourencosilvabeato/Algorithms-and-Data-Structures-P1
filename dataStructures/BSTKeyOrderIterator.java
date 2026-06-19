package dataStructures;

class BSTKeyOrderIterator<K,V> implements Iterator<Entry<K,V>> {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected BSTNode<Entry<K,V>> root;

	protected Stack<BSTNode<Entry<K,V>>> p;


	BSTKeyOrderIterator(BSTNode<Entry<K,V>> root){
		this.root=root;
		rewind();
	}
	
	private void pushPathToMinimum(BSTNode<Entry<K,V>> node) {
		 while (node != null) {
		        p.push(node); // Push current node onto the stack
		        node = node.getLeft(); // Move to the left child
		    }
	}

	//O(1) para todos os casos
	public boolean hasNext(){
		 return !p.isEmpty();
	 }


	public Entry<K,V> next() throws NoSuchElementException {
	    if (!hasNext()) {
	        throw new NoSuchElementException();
	    }

	    // Pop the top node
	    BSTNode<Entry<K,V>> node = p.pop();

	    // If the node has a right child, push its leftmost path onto the stack
	    if (node.getRight() != null) {
	        pushPathToMinimum(node.getRight());
	    }

	    // Return the element stored in the popped node
	    return node.getElement();
	}


    public void rewind( ){
		p = new StackInList<BSTNode<Entry<K,V>>>();
    	pushPathToMinimum(root);
    }
}
