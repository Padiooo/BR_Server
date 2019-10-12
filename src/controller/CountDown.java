package controller;

public class CountDown extends Thread {

	private final int TIMER_MAX = 5;
	private int timer = TIMER_MAX;
	private boolean holdTimer= true;
	private boolean isCountDownFinished= false;

	public CountDown() {
		
	}

	@Override
	public void run() {
		while (timer > 0) {
			if (!holdTimer) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				timer--;
			}
		}
		isCountDownFinished = true;
	}

	public boolean isCountDownFinished() {
		return isCountDownFinished;
	}

	public void holdTimer() {
		holdTimer = true;
	}

	public void resetTimer() {
		timer = TIMER_MAX;
		holdTimer = false;
	}
}
