package dataStructures;

import java.io.IOException;
import java.io.Serializable;


/**
 * @author Tomás Sousa (68302) tsm.sousa@campus.fct.unl.pt
 * @author Lourenço Beato (68461) lm.beato@campus.fct.unl.pt
 */

/**
 * Doubly linked list Implementation 
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 * 
 */
public class DoubleList<E> implements List<E>
{   

	/**
	 * Serial Version UID of the Class
	 */
	static final long serialVersionUID = 0L;

	/**
	 * Double List Node Implementation 
	 * @author AED  Team
	 * @version 1.0
	 * @param <E> Generic Element
	 * 
	 */
	static class DoubleListNode<E> implements Serializable
	{

		/**
		 * Serial Version UID of the Class
		 */
		static final long serialVersionUID = 0L;

		/**
		 * Element stored in the node.
		 */
		private E element;

		/**
		 * (Pointer to) the previous node.
		 */
		private DoubleListNode<E> previous;

		/**
		 * (Pointer to) the next node.
		 */
		private DoubleListNode<E> next;

		/**
		 * 
		 * @param theElement - The element to be contained in the node
		 * @param thePrevious - the previous node
		 * @param theNext - the next node
		 */
		public DoubleListNode( E theElement, DoubleListNode<E> thePrevious, 
				DoubleListNode<E> theNext )
		{
			element = theElement;
			previous = thePrevious;
			next = theNext;
		}

		/**
		 * 
		 * @param theElement to be contained in the node
		 */
		public DoubleListNode( E theElement )
		{
			this(theElement, null, null);
		}


		/**
		 * 
		 * @return the element contained in the node
		 */
		public E getElement( )
		{
			return element;
		}


		/**
		 * 
		 * @return the previous node
		 */
		public DoubleListNode<E> getPrevious( )
		{
			return previous;
		}


		/**
		 * 
		 * @return the next node
		 */
		public DoubleListNode<E> getNext( )
		{
			return next;
		}


		/**
		 * 
		 * @param newElement - New element to replace the current element
		 */
		public void setElement( E newElement )
		{
			element = newElement;
		}


		/**
		 * 
		 * @param newPrevious - node to replace the current previous node
		 */
		public void setPrevious( DoubleListNode<E> newPrevious )
		{
			previous = newPrevious;
		}


		/**
		 * 
		 * @param newNext - node to replace the next node
		 */
		public void setNext( DoubleListNode<E> newNext )
		{
			next = newNext;
		}

	}
	/**
	 *  Node at the head of the list.
	 */
	protected DoubleListNode<E> head;

	/**
	 * Node at the tail of the list.
	 */
	protected DoubleListNode<E> tail;

	/**
	 * Number of elements in the list.
	 */
	protected int currentSize;

	/**
	 * Constructor of an empty double linked list.
	 * head and tail are initialized as null.
	 * currentSize is initialized as 0.
	 */
	public DoubleList( )
	{
		head = null;
		tail = null;
		currentSize = 0;
	}


	@Override
	public boolean isEmpty( )
	{  
		return currentSize == 0;
	}


	@Override
	public int size( )
	{
		return currentSize;
	}


	@Override
	public Iterator<E> iterator( )
	{
		return new DoubleListIterator<E>(head, tail);
	}


	@Override
	public E getFirst( ) throws EmptyListException
	{  
		if ( this.isEmpty() )
			throw new EmptyListException();

		return head.getElement();
	}


	@Override
	public E getLast( ) throws EmptyListException
	{  
		return tail.getElement();
	}


	/**
	 * Returns the node at the specified position in the list.
	 * Pre-condition: position ranges from 0 to currentSize-1.
	 * @param position - position of list element to be returned
	 * @return DoubleListNode<E> at position
	 */
	protected DoubleListNode<E> getNode( int position ) 
	{
		DoubleListNode<E> node;

		if ( position <= ( currentSize - 1 ) / 2 )
		{
			node = head;
			for ( int i = 0; i < position; i++ )
				node = node.getNext();
		}
		else
		{
			node = tail;
			for ( int i = currentSize - 1; i > position; i-- )
				node = node.getPrevious();

		}
		return node;
	}


	@Override    
	public E get( int position ) throws InvalidPositionException
	{
		if ( position < 0 || position >= currentSize )
			throw new InvalidPositionException();

		return this.getNode(position).getElement();

	}


	@Override
	public int find( E element )
	{
		DoubleListNode<E> node = head;
		int position = 0;
		while ( node != null && !node.getElement().equals(element) )
		{
			node = node.getNext();
			position++;
		}
		if ( node == null )
			return -1;
		else
			return position;
	}


	@Override
	public void addFirst( E element )
	{
		DoubleListNode<E> newNode = new DoubleListNode<E>(element, null, head);
		if ( this.isEmpty() )
			tail = newNode;
		else
			head.setPrevious(newNode);
		head = newNode;
		currentSize++;
	}


	@Override
	public void addLast(E element) {
		DoubleListNode<E> newNode = new DoubleListNode<>(element, tail, null);

		if (this.isEmpty()) {
			head = newNode;
		} else {
			tail.setNext(newNode);
		}

		tail = newNode;
		currentSize++;
	}



	/**
	 * Inserts the specified element at the specified position in the list.
	 * Pre-condition: position ranges from 1 to currentSize-1.
	 * @param position - middle position for insertion of element
	 * @param element - element to be inserted at middle position
	 */
	protected void addMiddle( int position, E element )
	{
		DoubleListNode<E> prevNode = this.getNode(position - 1);
		DoubleListNode<E> nextNode = prevNode.getNext();
		DoubleListNode<E> newNode = new DoubleListNode<E>(element, prevNode, nextNode);

		prevNode.setNext(newNode);
		nextNode.setPrevious(newNode);
		currentSize++;
	}



	@Override
	public void add( int position, E element ) throws InvalidPositionException
	{
		if ( position < 0 || position > currentSize )
			throw new InvalidPositionException();

		if ( position == 0 )
			this.addFirst(element);
		else if ( position == currentSize )
			this.addLast(element);
		else
			this.addMiddle(position, element);
	}


	/**
	 * Removes the first node in the list.
	 * Pre-condition: the list is not empty.
	 */
	protected void removeFirstNode( )
	{
		head = head.getNext();
		if ( head == null )
			tail = null;
		else
			head.setPrevious(null);
		currentSize--;
	}


	@Override
	public E removeFirst( ) throws EmptyListException
	{
		if ( this.isEmpty() )
			throw new EmptyListException();

		E element = head.getElement();
		this.removeFirstNode();
		return element;
	}


	/**
	 * Removes the last node in the list.
	 * Pre-condition: the list is not empty.
	 */
	protected void removeLastNode( )
	{
		tail = tail.getPrevious();
		if ( tail == null )
			head = null;
		else
			tail.setNext(null);
		currentSize--;
	}


	@Override
	public E removeLast( ) throws EmptyListException
	{
		if ( this.isEmpty() )
			throw new EmptyListException();

		E element = tail.getElement();
		this.removeLastNode();
		return element;
	}


	/**
	 * Removes the specified node from the list.
	 * Pre-condition: the node is neither the head nor the tail of the list.
	 * @param node - middle node to be removed
	 */
	protected void removeMiddleNode( DoubleListNode<E> node )
	{
		DoubleListNode<E> prevNode = node.getPrevious();
		DoubleListNode<E> nextNode = node.getNext();

		prevNode.setNext(nextNode);
		nextNode.setPrevious(prevNode);
		currentSize--;
	}


	@Override
	public E remove( int position ) throws InvalidPositionException
	{
		if ( position < 0 || position >= currentSize )
			throw new InvalidPositionException();

		if ( position == 0 )
			return this.removeFirst();
		else if ( position == currentSize - 1 )
			return this.removeLast();
		else 
		{
			DoubleListNode<E> currentNode = getNode(position);
			removeMiddleNode(currentNode);
			return currentNode.getElement();
		}
	}


	/**
	 * Returns the node with the first occurrence of the specified element
	 * in the list, if the list contains the element.
	 * Otherwise, returns null.
	 * @param element - element to be searched
	 * @return DoubleListNode<E> where element was found, null if not found 
	 */
	protected DoubleListNode<E> findNode( E element )
	{
		if( this.find(element) == -1){
			return null;
		}
		else{
			int position = this.find(element);
			return this.getNode(position);
		}
	}


	@Override
	public boolean remove( E element )
	{
		DoubleListNode<E> node = this.findNode(element);
		if ( node == null )
			return false;
		else
		{
			if ( node == head )
				this.removeFirstNode();
			else if ( node == tail )
				this.removeLastNode();
			else
				this.removeMiddleNode(node);
			return true;
		}
	}
	
	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        // Escreve o número de elementos na lista
        out.defaultWriteObject();
        out.writeInt(currentSize);

        // Serializa os elementos da lista, na ordem
        DoubleListNode<E> node = head;
        while (node != null) {
            out.writeObject(node.getElement());
            node = node.getNext();
        }
    }

	  @SuppressWarnings("unchecked")
	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
	    // Read the default object state (non-transient fields)
	    in.defaultReadObject();

	    // Read the size of the list
	    currentSize = in.readInt();

	    // Reconstruct the list from the serialized data
	    head = null;
	    tail = null;

	    for (int i = 0; i < currentSize; i++) {
	        // Suppress unchecked cast warning since we are dealing with generics
	        E element = (E) in.readObject();

	        // If the list is empty, add the first node
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
