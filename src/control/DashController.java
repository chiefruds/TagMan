package control;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import model.Dash;
import model.Game;

public class DashController implements Observer, Runnable {

	private ArrayList<Dash> dashArray;
	private Game game;

	public DashController(Game game) {
		this.game = game;
	}

	// an update from game.
	@Override
	public void update(Observable arg0, Object game) {
		this.game = (Game) game;
		dashArray = this.game.getDashArray();

	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			if (dashArray != null) {

				// making a a random dash fall.
				for (int i = 0; i < dashArray.size(); i++) {
					Random rand = new Random();
					int randomDash = rand.nextInt(10);

					while (dashArray.get(randomDash).isFalling()) {
						randomDash = rand.nextInt(10);
					}
					if (!dashArray.get(randomDash).isFalling()) {
						try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						dashArray.get(randomDash).setFalling(true);
					}
				}
			}
		}
	}

}
