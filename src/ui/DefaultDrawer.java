package ui;

import java.io.IOException;

import map.Map;

public class DefaultDrawer implements Drawer{

	static {
		System.loadLibrary("jniclear");
	}

	public native void jniclear();

	@Override
	public void drawMap(Map map) {
		clear();
		char[][] m = map.getMap();
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < map.getWidth(); i ++){
			s.setLength(0);
			s.append(m[i]);
			System.out.println(s.toString());
		}
	}

	@Override
	public void drawWin() {
		System.out.println("win");
	}

	@Override
	public void drawLose() {
		System.out.println("lose");
	}

	private void clear() {
//		jniclear();
		try {
			Runtime.getRuntime().exec("clear");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
