package mind;

import map.Map;
import map.Point;
import map.Postion;
import snake.Snake;

public class RoundMind implements Mind{

	private int i = 0;

	public RoundMind() {

	}

	@Override
	public Postion nextStep(Map map, Snake snake) {
		Postion head = snake.getHead();
		Point p;
		int x = 0;
		int y = 0;

		if (i < 2 || i == 8){
			x = head.getX() + 1;
			y = head.getY();
			if (i == 8){
				i = 0;
			}
		}else if (i < 4){
			x = head.getX();
			y = head.getY() + 1;
		}else if (i < 6){
			x = head.getX() - 1;
			y = head.getY();
		}else if (i < 8){
			x = head.getX();
			y = head.getY() - 1;
		}else {
			i = -1;
			x = 0;
			y = 0;
		}
		i ++;

		p = new Point(x, y);
		return p;
	}

	@Override
	public Postion wander(Map map, Snake snake) {
		return null;
	}

}
