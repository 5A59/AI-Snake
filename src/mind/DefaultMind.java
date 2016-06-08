package mind;

import map.Map;
import map.Postion;
import snake.Snake;

public class DefaultMind implements Mind{


	public DefaultMind() {
		
	}

	@Override
	public Postion nextStep(Map map, Snake snake) {
		Postion head = snake.getHead();
		Point p = new Point(head.getX() + 1, head.getY());
		return p;
	}

	@Override
	public Postion wander(Map map, Snake snake) {
		return null;
	}

	public static class Point implements Postion{
		private int x;
		private int y;

		public Point() {
			
		}

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public void setX(int x) {
			this.x = x;
		}

		public void setY(int y) {
			this.y = y;
		}
	}

}
