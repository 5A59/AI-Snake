package snake;

public class LinkedList<T> {

	private Node<T> first;
	private Node<T> last;

	private int len;

	public LinkedList() {
		len = 0;
		first = null;
		last = null;
	}

	public T get(int index) {
		T tmp;
		Node<T> n = first;
		for (int i = 0; i < index; i ++){
			n = n.next;
		}
		return n.content;
	}

	public T getFromLast(int index) {
		T tmp;
		Node<T> n = last;
		for (int i = 0; i < index; i ++){
			if (n == null){
				break;
			}
			n = n.pre;
		}
		return n != null ? n.content : null;
	}

	public void addFirst(T data) {
		Node<T> node = new Node<T>(null, first, data);
		if (first == null){
			first = node;
			last = first;
		}else {
			first.pre = node;
			first = node;
		}
		addHandle();
	}

	public void addLast(T data) {
		Node<T> node = new Node<T>(last, null, data);
		if (last == null){
			first = node;
			last = node;
		}else {
			last.next = node;
			last = node;
		}
		addHandle();
	}

	public T removeFist() {
		if (first == null){
			return null;
		}
		Node<T> tmp = first;
		first = first.next;
		if (first != null){
			first.pre = null;
		}
		removeHandle();
		return tmp.content;
	}

	public T removeLast() {
		if (last == null){
			return null;
		}
		Node<T> tmp = last;
		last = last.pre;
		if (last != null){
			last.next = null;
		}
		removeHandle();
		return tmp.content;
	}

	public T getFirst() {
		if (first == null){
			return null;
		}
		return first.content;
	}

	public T getLast() {
		if (last == null){
			return null;
		}
		return last.content;
	}

	public T getBeforeLast() {
		if (last == null || last.pre == null){
			return null;
		}
		return last.pre.content;
	}

	public int getLength() {
		return len;
	}

	private void addHandle() {
		len ++;
	}

	private void removeHandle() {
		len --;
	}

	private static class Node<T>{
		T content;
		Node<T> next;
		Node<T> pre;

		public Node(T content) {
			this.content = content;
			next = null;
			pre = null;
		}

		public Node(Node<T> pre, Node<T> next, T content) {
			this.pre = pre;
			this.next = next;
			this.content = content;
		}
	}
}
