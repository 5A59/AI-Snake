package map;

import map.Postion;

public class Map {
	public final static char SNAKE_NODE = '*';
	public final static char FOOD = '@';
	public final static char NONE = ' ';
	public final static char WALL = '#';
	public final static char DEFAULT_WIDTH = 8;

	private int width;
	private int height;

	private char[][] map;

	public Map() {
		this(DEFAULT_WIDTH, DEFAULT_WIDTH);
	}

	public Map(int width, int height) {
		this.width = width;
		this.height = height;
		initMap(width, height);
	}

	private void initMap(int w, int h) {
		map = new char[w][h];
		// 初始化 墙壁
		for (int i = 0; i < w; i ++){
			map[i][0] = WALL;
			map[i][h - 1] = WALL;
		}

		for (int i = 0; i < h; i ++){
			map[0][i] = WALL;
			map[w - 1][i] = WALL;
		}

		// 初始化 地图内部
		for (int i = 1; i < w - 1; i ++){
			for (int j = 1; j < h - 1; j ++){
				map[i][j] = NONE;
			}
		}
	}

	public PosContent getPosContent(Postion p) {
		PosContent tmp = null;
		if (p == null){
			return PosContent.E_NO_RES;
		}
		switch (map[p.getX()][p.getY()]){
		case SNAKE_NODE:
			tmp = PosContent.E_SNAKE_NODE;
			break;
		case FOOD:
			tmp = PosContent.E_FOOD;
			break;
		case NONE:
			tmp = PosContent.E_NONE;
			break;
		case WALL:
			tmp = PosContent.E_WALL;
			break;
		}
		return tmp;
	}

	public char[][] getMap() {
		return map;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setMap(Postion p, char data) {
		map[p.getX()][p.getY()] = data;
	}

	public static enum PosContent {
		E_SNAKE_NODE,
		E_FOOD,
		E_NONE,
		E_WALL,
		E_NO_RES;
	}
}
