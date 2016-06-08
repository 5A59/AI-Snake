import judge.Judge;
import judge.Res;
import snake.LinkedList;
import snake.Snake;
import map.Postion;
import mind.Mind;
import map.Map;
import map.Point;
import ui.Drawer;
import utils.Logger;

public class Controller {

	private static Controller instance;
	private ControllerConfig config;

	private Controller() {
		
	}

	public static Controller getInstance() {
		if (instance == null){
			synchronized(Controller.class) {
				if (instance == null){
					instance = new Controller();
				}
			}
		}
		return instance;
	}

	public void init(ControllerConfig config) {
		if (this.config != null){
			return ;
		}
		this.config = config;
		this.config.getSnake().setMind(config.getMind());
	}

	public void run() throws UnsupportedOperationException {
		Snake snake = config.getSnake();
		Map map = config.getMap();
		Judge judge = config.getJudge();
		Drawer drawer = config.getDrawer();

		Postion n = snake.getHead();
		Postion t = snake.getTail();
		map.setMap(n, Map.SNAKE_NODE);
		map.setMap(t, Map.SNAKE_NODE);

		Postion fp = makeFood(config.getMap());
		map.setMap(fp, Map.FOOD);

		drawer.drawMap(map);

		boolean needMakeFood = false;
		while (true){
			if (needMakeFood){
				fp = makeFood(config.getMap());
				if (fp != null){
					map.setMap(fp, Map.FOOD);
				}
				needMakeFood = false;
				drawer.drawMap(map);
			}

			Postion p = snake.nextStep(map);
//			Logger.d("head is " + snake.getHead().getX() + " : " + snake.getHead().getY());
//			Logger.d("tail is " + snake.getTail().getX() + " : " + snake.getTail().getY());
//			Logger.d(snake.toString());
			if (p != null){
				Logger.d("nextStep is x : " + p.getX() + " y : " + p.getY());
			}
			Res res = judge.finish(map, p);

			if (res == Res.LOSE){
				drawer.drawLose();
				break;
			}

			if (res == Res.WIN){
				drawer.drawWin();
				break;
			}

			switch (map.getPosContent(p)){
			case E_SNAKE_NODE:
				throw new UnsupportedOperationException("unexcept E_SNAKE_NODE here");
			case E_FOOD:
				snake.addHead(p);
				map.setMap(p, Map.SNAKE_NODE);
				needMakeFood = true;
				break;
			case E_NONE:
				snake.addHead(p);
				map.setMap(p, Map.SNAKE_NODE);
				Postion pt = snake.removeTail();
				map.setMap(pt, Map.NONE);
				break;
			case E_WALL:
				throw new UnsupportedOperationException("unexcept E_WALL here");
			default:
				throw new UnsupportedOperationException("unexcept default here");
			}

			drawer.drawMap(map);
			pause();
		}
	}

	private Postion makeFood(Map map) {
		return config.getFoodMaker().makeFood(map);
	}

	private void pause() {
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

