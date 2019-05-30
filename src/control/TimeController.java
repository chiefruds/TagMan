package control;

import java.util.Observable;

public class TimeController extends Observable implements Runnable {

	private int time;
	private boolean gameRunning;

	public TimeController() {
		this.gameRunning = false;
	}

	@Override
	public void run() {
		//a while statement to make sure the threat doesn't stop.
		while (true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
			//making the timer and notifying the observers.
			while (time > 0 && gameRunning == true) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
				if (gameRunning) {
					time--;
				}
				setChanged();
				notifyObservers(time);

			}
		}

	}

	public void stopTimer() {
		gameRunning = false;
		time = 30;
	}

	public void startTimer() {
		gameRunning = true;
		time = 30;
	}
	
	public int getTime() {
		return time;
	}

}
