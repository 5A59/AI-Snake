package judge;

import map.Map;
import map.Postion;

public class DefaultJudge implements Judge{

	@Override
	public Res finish(Map map, Postion p) {
		switch (map.getPosContent(p)){
		case E_WALL:
		case E_SNAKE_NODE:
		case E_NO_RES:
			return Res.LOSE;
		default:
			break;
		}

		return Res.RUNNING;
	}

}
