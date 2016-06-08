package mind;

import map.Postion;
import snake.Snake;
import map.Map;

public interface Mind {
	Postion nextStep(Map map, Snake snake);
	Postion wander(Map map, Snake snake);
}
