package util;

import javax.swing.SwingUtilities;

public class TimerThread extends Thread {
    public interface TimerListener {
        void onTick(int remainingSeconds);
        void onTimeUp();
    }

    private int remainingSeconds;
    private boolean running;
    private final TimerListener listener;

    public TimerThread(int durationMinutes, TimerListener listener) {
        this.remainingSeconds = durationMinutes * 60;
        this.running = true;
        this.listener = listener;
    }

    @Override
    public void run() {
        while (running && remainingSeconds > 0) {
            try {
                Thread.sleep(1000);
                remainingSeconds--;
                int seconds = remainingSeconds;
                SwingUtilities.invokeLater(() -> listener.onTick(seconds));
            } catch (InterruptedException e) {
                running = false;
                interrupt();
            }
        }

        if (running && remainingSeconds <= 0) {
            SwingUtilities.invokeLater(listener::onTimeUp);
        }
    }

    public void stopTimer() {
        running = false;
    }
}
