package auction_system;

import javafx.application.Platform;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeManager {
    private boolean isRealTime = true;
    private Thread realTimeThread;
    private LocalDateTime simulatedTime;

    public String getCurrentTime() {
        LocalDateTime now = (isRealTime) ? LocalDateTime.now() : simulatedTime;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }

    public void startRealTime(Runnable updateUI) {
        stopRealTime();
        isRealTime = true;
        realTimeThread = new Thread(() -> {
            while (isRealTime) {
                try {
                    Thread.sleep(1000);
                    Platform.runLater(updateUI);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        realTimeThread.setDaemon(true); // Ensure thread stops with the app
        realTimeThread.start();
    }

    public void stopRealTime() {
        if (realTimeThread != null && realTimeThread.isAlive()) {
            isRealTime = false;
            realTimeThread.interrupt();
        }
    }

    public void setSimulatedTime(LocalDate date, int hour, int minute, int second) {
        simulatedTime = LocalDateTime.of(date, LocalTime.of(hour, minute, second));
        isRealTime = false;
    }
}
