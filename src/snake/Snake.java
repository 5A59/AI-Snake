package snake;

import map.Map;
import map.Postion;
import mind.Mind;

public class Snake {

	private LinkedList<Node> body;
	private Mind mind;
	private Towards towards;

	public Snake() {
		this(null, null);
	}

	public Snake(Mind mind, Towards towards) {
		body = new LinkedList<>();
		this.mind = mind;
		this.towards = towards;
	}

	public void setMind(Mind mind) {
		if (this.mind != null){
			return ;
		}
		this.mind = mind;
	}

	public void setTowards(Towards towards) {
		if (this.towards == null){
			return ;
		}
		this.towards = towards;
	}

	public Postion getHead() {
		return body.getFirst();
	}

	public Postion getTail() {
		return body.getLast();
	}

	public void addHead(Postion p) {
		Node used = body.getFirst();
		body.addFirst(new Node(p.getX(), p.getY()));
		Node now = body.getFirst();

		if (used == null){
			return ;
		}

		if (used.getX() == now.getX()){
			if (used.getY() < now.getY()){
				towards = Towards.DOWN;
			}else if (used.getY() > now.getY()){
				towards = Towards.UP;
			}
		}else {
			if (used.getX() < now.getX()){
				towards = Towards.RIGHT;
			}else if (used.getX() > now.getX()){
				towards = Towards.LEFT;
			}
		}
	}

	public Postion removeTail() {
		return body.removeLast();
	}

	public Postion nextStep(Map map) {
		return mind.nextStep(map, this);
	}

	public LinkedList<Node> getBody() {
		return body;
	}

	public int getBodyLength() {
		return body.getLength();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < body.getLength(); i ++){
			builder.append("node").append(i).append("  x: ")
				.append(body.get(i).x).append("  y: ").append(body.get(i).y).append("  ");
		}
		return builder.toString();
	}

	public static class Node implements Postion{
		private int x;
		private int y;

		public Node() {
			
		}

		public Node(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public void setX(int x) {
			this.x = x;
		}

		public void setY(int y) {
			this.y = y;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}
	}

	public enum Towards {
		UP,
		DOWN,
		LEFT,
		RIGHT;
	}
}
