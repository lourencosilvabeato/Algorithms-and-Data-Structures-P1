package dataStructures;

import java.io.IOException;

public class OrderedList<E extends Comparable<E>> extends DoubleList<E> implements List<E> {

	private static final long serialVersionUID = 1L;

	public OrderedList() {
		super();
	}

	@Override
	public void add(int position, E element) {
		if (position < 0 || position > currentSize) {
			throw new InvalidPositionException();
		}

		DoubleListNode<E> newNode = new DoubleListNode<>(element, null, null);

		if (head == null) {
			head = newNode;
			tail = newNode;
		} else {
			DoubleListNode<E> current = head;

			while (current != null && current.getElement().compareTo(element) < 0)
				current = current.getNext();

			if (current == null) {
				tail.setNext(newNode);
				newNode.setPrevious(tail);
				tail = newNode;
			} else if (current == head) {
				newNode.setNext(head);
				head.setPrevious(newNode);
				head = newNode;
			} else {
				newNode.setNext(current);
				newNode.setPrevious(current.getPrevious());
				current.getPrevious().setNext(newNode);
				current.setPrevious(newNode);
			}
		}
		currentSize++;
	}


	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
       
        out.defaultWriteObject();
        out.writeInt(currentSize);

        DoubleListNode<E> node = head;
        while (node != null) {
            out.writeObject(node.getElement());
            node = node.getNext();
        }
    }
	
	
	  @SuppressWarnings("unchecked")
		private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		   
		    in.defaultReadObject();

		  
		    currentSize = in.readInt();

		
		    head = null;
		    tail = null;

		    for (int i = 0; i < currentSize; i++) {
		        
		        E element = (E) in.readObject();

		        if (head == null) {
		            DoubleListNode<E> newNode = new DoubleListNode<>(element, null, null);
		            head = newNode;
		            tail = newNode;
		        } else {
		            // Add subsequent nodes
		            DoubleListNode<E> newNode = new DoubleListNode<>(element, tail, null);
		            tail.setNext(newNode);
		            tail = newNode;
		        }
		    }
		}
}
