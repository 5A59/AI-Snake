import snake.Snake;
import foodmaker.FoodMaker;
import judge.Judge;
import map.Map;
import mind.Mind;
import ui.Drawer;

public class ControllerConfig {
	private Mind mind;
	private Judge judge;
	private Drawer drawer;
	private Snake snake;
	private Map map;
	private FoodMaker foodMaker;

	private ControllerConfig() {
		
	}

	public Mind getMind() {
		return mind;
	}

	public Judge getJudge() {
		return judge;
	}

	public Drawer getDrawer() {
		return drawer;
	}

	public Snake getSnake() {
		return snake;
	}

	public Map getMap() {
		return map;
	}

	public FoodMaker getFoodMaker() {
		return foodMaker;
	}

	public static class Builder {
		private Mind mind;
		private Judge judge;
		private Drawer drawer;
		private Snake snake;
		private Map map;
		private FoodMaker foodMaker;

		public Builder setMind(Mind mind) {
			this.mind = mind;
			return this;
		}

		public Builder setJudge(Judge judge) {
			this.judge = judge;
			return this;
		}

		public Builder setDrawer(Drawer drawer) {
			this.drawer = drawer;
			return this;
		}

		public Builder setSnake(Snake snake) {
			this.snake = snake;
			return this;
		}

		public Builder setMap(Map map) {
			this.map = map;
			return this;
		}

		public Builder setFoodMaker(FoodMaker foodMaker) {
			this.foodMaker = foodMaker;
			return this;
		}

		private void apply(ControllerConfig config) {
			if (drawer != null){
				config.drawer = drawer;
			}else {
				config.drawer = ConfigFactory.getDefaultDrawer();
			}

			if (judge != null){
				config.judge = judge;
			}else {
				config.judge = ConfigFactory.getDefaultJudge();
			}

			if (mind != null){
				config.mind = mind;
			}else {
				config.mind = ConfigFactory.getDefaultMind();
			}

			if (snake != null){
				config.snake = snake;
			}else {
				config.snake = ConfigFactory.getDefaultSnake();
			}

			if (map != null){
				config.map = map;
			}else {
				config.map = ConfigFactory.getDefaultMap();
			}

			if (foodMaker != null){
				config.foodMaker = foodMaker;
			}else {
				config.foodMaker = ConfigFactory.getDefaultFoodMaker();
			}
		}

		public ControllerConfig create() {
			ControllerConfig config = new ControllerConfig();
			apply(config);
			return config;
		}
	}
}
