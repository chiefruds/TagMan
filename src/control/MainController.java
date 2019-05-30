package control;

import model.Game;
import view.GameView;
import view.MainFrame;
import view.PlayView;
import view.TimeView;

public class MainController {

	private Game game;
	private TimeController timeController;
	private DashController dashController;
	private Thread timeThread;
	private Thread gameThread;
	private Thread dashThread;
	private TimeView timeView;
	private GameView gameView;
	private PlayView playView;

	public MainController() {
		game = new Game();
		timeController = new TimeController();
		dashController = new DashController(game);

		timeView = new TimeView();
		gameView = new GameView();
		playView = new PlayView(this);
		new MainFrame(timeView, gameView, playView);

		// timeController is a thread because it holds the time.
		timeController.addObserver(timeView);
		timeThread = new Thread(timeController);
		timeThread.start();

		// dashController is a thread because playView would stop with the thread.sleep
		// from dashControler.
		dashThread = new Thread(dashController);
		dashThread.start();

		// adding the observers to game.
		game.addObserver(gameView);
		game.addObserver(playView);
		game.addObserver(dashController);

		// game is a thread because it needs to update the locations of objects.
		gameThread = new Thread(game);
		gameThread.start();

	}

	public int getTime() {
		return timeController.getTime();
	}

	public void startTimer() {
		timeController.startTimer();
	}

	public void stopTimer() {
		timeController.stopTimer();
	}

}
