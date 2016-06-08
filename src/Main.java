import map.Map;
import mind.BFSMind;

public class Main {
	public static void main(String[] args) {
		System.out.println(System.getProperty("java.library.path"));
		ControllerConfig config = new ControllerConfig.Builder()
				.setMap(new Map(10,30))
				.setMind(new BFSMind())
				.create();
		Controller controller = Controller.getInstance();
		controller.init(config);
		controller.run();
	} 
}
