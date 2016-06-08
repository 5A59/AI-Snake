package foodmaker;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import map.Map;
import map.Point;
import map.Postion;

public class DefaultFoodMaker implements FoodMaker{
	public static final int DEFAULT_COUNT = 100;
	private List<Point> posList;
	private List<Point> storeList;
	private Random random;

	public DefaultFoodMaker() {
		this(DEFAULT_COUNT);
	}

	public DefaultFoodMaker(int count) {
		posList = new ArrayList<>(count);
		storeList = new LinkedList<>();
		for (int i = 0; i < count; i ++){
			storeList.add(new Point(0, 0));
		}
		random = new Random();
	}

	@Override
	public Postion makeFood(Map map) {
		char[][] m = map.getMap();
		boolean needMake = true;
		for (int i = 0; i < map.getWidth(); i ++){
			for (int j = 0; j < map.getHeight(); j ++){
				if (m[i][j] == Map.NONE){
					Point p = getPoint(i, j);
					posList.add(p);
				}else if (m[i][j] == Map.FOOD){
					needMake = false;
				}
			}
		}

		Point p = null;
		if (needMake){
			int index = getRandom(posList.size());
			p = posList.get(index);
		}

		resetList();
		return p;
	}

	private int getRandom(int n) {
		return random.nextInt(n);
	}

	private Point getPoint(int x, int y) {
		Point p;
		if (storeList.isEmpty()){
			p = new Point(x, y);
		}else {
			p = storeList.remove(storeList.size() - 1);
			p.setX(x);
			p.setY(y);
		}
		return p;
	}

	private void resetList() {
		for (Point p : posList){
			storeList.add(p);
		}
		posList.clear();
	}

}
