import snake.Snake;
import snake.Snake.Towards;
import foodmaker.DefaultFoodMaker;
import foodmaker.FoodMaker;
import judge.DefaultJudge;
import judge.Judge;
import judge.Res;
import map.Map;
import map.Point;
import map.Postion;
import mind.DefaultMind;
import mind.Mind;
import ui.DefaultDrawer;
import ui.Drawer;

public class ConfigFactory {

	public static Drawer getDefaultDrawer() {
		return new DefaultDrawer();
	}

	public static Snake getDefaultSnake() {
		Snake s = new Snake();
		s.addHead(new Point(1, 1));
		s.addHead(new Point(1, 2));
//		s.addHead(new Postion(){
//			@Override
//			public int getX() {
//				return 2;
//			}
//
//			@Override
//			public int getY() {
//				return 1;
//			}
//		});
//		s.addHead(new Postion(){
//			@Override
//			public int getX() {
//				return 3;
//			}
//
//			@Override
//			public int getY() {
//				return 1;
//			}
//		});
		s.setTowards(Towards.RIGHT);
		return s;
	}

	public static Map getDefaultMap() {
		return new Map();
	}

	public static Judge getDefaultJudge() {
		return new DefaultJudge();
	}

	public static Mind getDefaultMind() {
		return new DefaultMind();
	}

	public static FoodMaker getDefaultFoodMaker() {
		return new DefaultFoodMaker();
	}
}
