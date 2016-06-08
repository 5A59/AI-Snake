package mind;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

import map.Map;
import map.Point;
import map.Postion;
import snake.Snake;
import snake.Snake.Node;
import ui.DefaultDrawer;
import ui.Drawer;
import utils.Logger;

public class BFSMind implements Mind{
	private static final int NONE_PARENT = -1;

	private int width;
	private int height;
	private char[][] m;
	private int[][] parentMap;
	private Queue<Integer> queue;
	private Set<Integer> used;
	private Random random;

	public BFSMind() {
		queue = new LinkedList<>();
		used = new HashSet<>();
	}

	@Override
	public Postion nextStep(Map map, Snake snake) {
		Postion head = snake.getHead();
		Postion tail = snake.getTail();
		Postion food = getFood(map);
		int mapCount = (map.getHeight() - 2) * (map.getWidth() - 2);

		parentMap = new int[map.getWidth()][map.getHeight()];
		m = new char[map.getWidth()][];
		char[][] tmpM = map.getMap();
		for (int i = 0; i < map.getWidth(); i ++){
			m[i] = Arrays.copyOf(tmpM[i], map.getHeight());
		}

		Postion p = null;

		//先找到能通往食物的下一步
		refresh(map, head);
//		if (snake.getBodyLength() < mapCount / 2){
			p = nextStepToFood();
//		}
			if (p != null){
				Logger.d("first nextStepToFood is " + p.getX() + " " + p.getY());
			}else {
				Logger.d("first nextstepto food is null");
			}
		// 预测第二步
		if (p != null){
			char c = m[p.getX()][p.getY()];
			snake.LinkedList<Node> tmpL = snake.getBody();
			Node newTail = tmpL.getBeforeLast();
			if (newTail == null){
				newTail = new Node(tail.getX(), tail.getY());
			}

			m[p.getX()][p.getY()] = Map.SNAKE_NODE;
			if (c != Map.FOOD){
				m[tail.getX()][tail.getY()] = Map.NONE;
			}
			refresh(map, new Point(p.getX(), p.getY()));
//			Postion tmpPF = nextStepToTail(newTail);
			Postion tmpPF = nextStepToTail2(newTail, map, head, 0);
//			Postion tmpPF = longestPath(newTail, head, map);
			if (tmpPF != null){
				Logger.d("first nextStepTotail is " + tmpPF.getX() + " " + tmpPF.getY());
			}else {
				Logger.d("first nextStepTotail is null");
			}
			m[p.getX()][p.getY()] = c;
			m[tail.getX()][tail.getY()] = Map.SNAKE_NODE;
			if (tmpPF == null){
				p = null;
			}
		}

		if (p == null){
			Logger.d("longest to food start ");
//			p = nextStepToTail2(food, map, head, 0);
			p = longestPath(food, head, map);

			// 预测第二步
			if (p != null){
				char c = m[p.getX()][p.getY()];
				snake.LinkedList<Node> tmpL = snake.getBody();
				Node newTail = tmpL.getBeforeLast();
				if (newTail == null){
					newTail = new Node(tail.getX(), tail.getY());
				}

				m[p.getX()][p.getY()] = Map.SNAKE_NODE;
				if (c != Map.FOOD){
					m[tail.getX()][tail.getY()] = Map.NONE;
				}
				refresh(map, new Point(p.getX(), p.getY()));
	//			Postion tmpPF = nextStepToTail(newTail);
				Postion tmpPF = nextStepToTail2(newTail, map, head, 0);
//				Postion tmpPF = longestPath(newTail, head, map);
				m[p.getX()][p.getY()] = c;
				m[tail.getX()][tail.getY()] = Map.SNAKE_NODE;
				if (tmpPF == null){
					p = null;
				}
			}
		}

		if (p == null){
			// 寻找到尾巴的最长路径
			refresh(map, head);
//			p = nextStepToTail(tail);
			p = nextStepToTail2(tail, map, head, 0);
//			p = longestPath(tail, head, map);
		}

		if (p == null){
			refresh(map, head);
			Logger.d("can not find path to tail or food");
			p = wander(map, snake);
		}

		return p;
	}

	@Override
	public Postion wander(Map map, Snake snake) {
		Logger.d("wander start");
		Postion head = snake.getHead();
		Postion food = getFood(map);

//		Postion p = nextStepToTail(food);
		Postion p = nextStepToTail2(food, map, head, 0);
		if (p != null){
			return p;
		}

		snake.LinkedList<Node> tmpL = snake.getBody();
		for (int i = 0; i < tmpL.getLength(); i ++){
			Postion tmpP = tmpL.getFromLast(i);
			p = nextStepToTail2(tmpP, map, head, 0);
			if (p != null){
				return p;
			}
		}


		char[][] m = map.getMap();
		int x = head.getX();
		int y = head.getY();
		List<Postion> list = new ArrayList<>();
		char c = m[x - 1][y];
		if (c == Map.NONE || c == Map.FOOD){
			list.add(new Point(x - 1, y));
		}
		c = m[x][y + 1];
		if (c == Map.NONE || c == Map.FOOD){
			list.add(new Point(x, y + 1));
		}
		c = m[x + 1][y];
		if (c == Map.NONE || c == Map.FOOD){
			list.add(new Point(x + 1, y));
		}
		c = m[x][y - 1];
		if (c == Map.NONE || c == Map.FOOD){
			list.add(new Point(x, y - 1));
		}
		if (!list.isEmpty()){
			return list.get(getRandom(list.size()));
		}
		return null;
	}

	private Postion nextStepToFood() {
		int number;
		int x;
		int y;
		HandleRes res = new HandleRes();
		while (!queue.isEmpty()){
			number = queue.poll();
			x = getXByNumber(number);
			y = getYByNumber(number);
			boolean r = 
				handlePoint(number, x - 1, y, res) ||
				handlePoint(number, x, y + 1, res) ||
				handlePoint(number, x + 1, y, res) ||
				handlePoint(number, x, y - 1, res);
			if (r && res.res){
				Point p = getStartParent(parentMap, res.x, res.y, null); // 获取下一步坐标
				return p;
			}
		}
		return null;
	}

	private Postion longestPath(Postion end, Postion start, Map map) {
		int w = map.getWidth();
		int h = map.getHeight();
		width = w;
		height = h;
		int[][] m = new int[w][h];
		char[][] cm = this.m;

		for (int i = 0; i < w; i ++){
			for (int j = 0; j < h; j ++){
				if (i == end.getX() && j == end.getY()){
					m[i][j] = 0;
					continue;
				}
				if (cm[i][j] == Map.SNAKE_NODE || cm[i][j] == Map.WALL || cm[i][j] == Map.FOOD){
					m[i][j] = -2;
				}else {
					m[i][j] = -1;
				}
			}
		}
		Queue<Integer> queue = new LinkedList<>();
		queue.add(getNumber(end.getX(), end.getY()));
		int x = 0;
		int y = 0;
		while (!queue.isEmpty()){
			int num = queue.poll();
			x = getXByNumber(num);
			y = getYByNumber(num);

			if (m[x + 1][y] == -1){
				m[x + 1][y] = m[x][y] + 1;
				queue.add(getNumber(x + 1, y));
			}
			if (m[x - 1][y] == -1){
				m[x - 1][y] = m[x][y] + 1;
				queue.add(getNumber(x - 1, y));
			}
			if (m[x][y + 1] == -1){
				m[x][y + 1] = m[x][y] + 1;
				queue.add(getNumber(x, y + 1));
			}
			if (m[x][y - 1] == -1){
				m[x][y - 1] = m[x][y] + 1;
				queue.add(getNumber(x, y - 1));
			}
		}

		x = start.getX();
		y = start.getY();
		int sx = x;
		int sy = y;
		int max = m[x + 1][y];
		sx = x + 1;
		if (m[x - 1][y] > max){
			max = m[x - 1][y];
			sx = x - 1;
			sy = y;
		}
		if (m[x][y + 1] > max){
			max = m[x][y + 1];
			sx = x;
			sy = y + 1;
		}
		if (m[x][y - 1] > max){
			max = m[x][y - 1];
			sx = x;
			sy = y - 1;
		}

		Point rp = new Point(sx, sy);
		return rp;
	}

	private Postion nextStepToTail2(Postion tail, Map map, Postion head, int next) {
		int number;
		int x;
		int y;
		List<Integer> tmpPath = new ArrayList<>();
		HandleRes res = new HandleRes();
		int count = 0;
		while (!queue.isEmpty()){
			number = queue.poll();
			x = getXByNumber(number);
			y = getYByNumber(number);
			boolean r = 
				handleTailPoint2(null, number, x - 1, y, tail, res, count) ||
				handleTailPoint2(null, number, x, y + 1, tail, res, count) ||
				handleTailPoint2(null, number, x + 1, y, tail, res, count) ||
				handleTailPoint2(null, number, x, y - 1, tail, res, count);
			count ++;
			if (r && res.res){
				Point p = getStartParent(parentMap, res.x, res.y, tmpPath); // 获取下一步坐标
				Postion np = null;
				if (next < 5 && p != null){
					for (Integer i : tmpPath){
						m[getXByNumber(i)][getYByNumber(i)] = Map.SNAKE_NODE;
					}
					refresh(map, head);
					np = nextStepToTail2(p, map, head, next + 1);
					for (Integer i : tmpPath){
						m[getXByNumber(i)][getYByNumber(i)] = Map.NONE;
					}
				}
				return np != null ? np : p;
			}
		}
		return null;
	}

	private Postion nextStepToTail(Postion tail) {
		int number = 0;
		int x = 0;
		int y = 0;
		HandleRes res = new HandleRes();
		List<Integer> path = new ArrayList<>();
		List<Integer> tmpPath = new ArrayList<>();
		Point p = null;
		Point tmpP = null;

		used.add(queue.peek());
		used.add(getNumber(tail.getX(), tail.getY()));
		while (!queue.isEmpty()){
			number = queue.poll();
			x = getXByNumber(number);
			y = getYByNumber(number);
//			boolean r = 
				handleTailPoint(parentMap, number, x - 1, y, tail, res);
				handleTailPoint(parentMap, number, x, y + 1, tail, res);
				handleTailPoint(parentMap, number, x + 1, y, tail, res);
				handleTailPoint(parentMap, number, x, y - 1, tail, res);
			if (res.res){
				tmpPath.clear();
				tmpP = getStartParent(parentMap, res.x, res.y, tmpPath); // 获取下一步坐标
				Logger.d("tmpPath size " + tmpPath.size());
				if (path.size() < tmpPath.size()){
					path.clear();
					path.addAll(tmpPath);
					p = (tmpP == null ? p : tmpP);
				}
			}
		}
		return p;
	}

	private int getRandom(int n) {
		if (random == null){
			random = new Random();
		}
		return random.nextInt(n);
	}

	private Postion getFood(Map map) {
		Point p = null;
		for (int i = 0; i < width; i ++){
			for (int j = 0; j < height; j ++){
				if (map.getMap()[i][j] == Map.FOOD){
					p = new Point(i, j);
				}
			}
		}
		return p;
	}

	private void refresh(Map map, Postion head) {
		width = map.getWidth();
		height = map.getHeight();
		queue.clear();
		used.clear();	

		for (int i = 0; i < width; i ++){
			Arrays.fill(parentMap[i], NONE_PARENT);
		}

		queue.add(getNumber(head.getX(), head.getY()));
		parentMap[head.getX()][head.getY()] = NONE_PARENT;
	}

	/**
	 * 
	 * @param map
	 * @param parent
	 * @param x
	 * @param y
	 * @param tail
	 * @param handleRes
	 * @return
	 */
	private boolean handleTailPoint(int[][] map, int parent, int x, int y, 
			Postion tail, HandleRes handleRes) {
		boolean res = false;
		if (m[x][y] == Map.NONE){
			if (addNumber(x, y)){
				setParent(x, y, parent);
			}
		}else if (m[x][y] == Map.SNAKE_NODE && x == tail.getX() && y == tail.getY()){
//			if (addNumber(x, y)){
				setParent(x, y, parent);
//			}
			res = true;
			handleRes.res = true;
			handleRes.x = x;
			handleRes.y = y;
		}
		return res;
	}

	private boolean handleTailPoint2(int[][] map, int parent, int x, int y, 
			Postion tail, HandleRes handleRes, int count) {
		boolean res = false;
		if (m[x][y] == Map.NONE){
			if (addNumber(x, y)){
				setParent(x, y, parent);
			}
		}else if (m[x][y] == Map.SNAKE_NODE && x == tail.getX() && y == tail.getY() && count > 1){
//			if (addNumber(x, y)){
				setParent(x, y, parent);
//			}
			res = true;
			handleRes.res = true;
			handleRes.x = x;
			handleRes.y = y;
		}
		return res;
	}

	/**
	 * 处理到 food 的节点
	 * @param map
	 * @param parent
	 * @param x
	 * @param y
	 * @param handleRes 结果回调
	 * @return 是否找到food
	 */
	private boolean handlePoint(int parent, int x, int y, HandleRes handleRes) {
		boolean res = false;
		if (m[x][y] == Map.NONE){
			if (addNumber(x, y)){
				setParent(x, y, parent);
			}
		}else if (m[x][y] == Map.FOOD){
			if (addNumber(x, y)){
				setParent(x, y, parent);
			}
			res = true;
			handleRes.res = true;
			handleRes.x = x;
			handleRes.y = y;
		}
		return res;
	}

	private Point getStartParent(int[][] parentMap, int x, int y, List<Integer> path) {
		int tmpX = x;
		int tmpY = y;
		int lastX = x;
		int lastY = y;
		int number;
		while ((number = parentMap[tmpX][tmpY]) != NONE_PARENT){
			parentMap[tmpX][tmpY] = NONE_PARENT;
			lastX = tmpX;
			lastY = tmpY;
			tmpX = getXByNumber(number);
			tmpY = getYByNumber(number);
			if (path != null){
				path.add(number);
			}
		}
		return new Point(lastX, lastY);
	}

	private boolean addNumber(int x, int y) {
		int number = getNumber(x, y);
		if (!used.contains(number)){
			queue.add(number);
			used.add(number);
			return true;
		}
		return false;
	}

	private void setParent(int x, int y, int parent) {
		parentMap[x][y] = parent;
	}

	private int getNumber(int x, int y) {
		return x * height + y;
	}

	private int getXByNumber(int number) {
		return number / height;
	}

	private int getYByNumber(int number) {
		return number % height;
	}

	private static class HandleRes {
		boolean res;
		int x;
		int y;

		public HandleRes() {
			this(false, -1, -1);
		}

		public HandleRes(boolean res, int x, int y) {
			this.res = res;
			this.x = x;
			this.y = y;
		}
	}

	public static void main(String[] args) {
		BFSMind mind = new BFSMind();
		Map map = new Map(10, 10);
		Snake snake = new Snake();
		Drawer drawer = new DefaultDrawer();
		Postion head = new Point(1, 3);
		Postion node = new Point(1, 2);
		Postion tail = new Point(1, 1);
		Postion food = new Point(2, 4);
		snake.addHead(tail);
//		snake.addHead(node);
//		snake.addHead(head);

//		map.setMap(head, Map.SNAKE_NODE);
//		map.setMap(node, Map.SNAKE_NODE);
		map.setMap(tail, Map.SNAKE_NODE);
		map.setMap(food, Map.FOOD);
		drawer.drawMap(map);

		Postion p = mind.nextStep(map, snake);
//		Postion p = mind.longestPath(food, tail, map);
		Logger.d("x : " + p.getX() + " y : " + p.getY());
	}
}
