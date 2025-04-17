package ru.akvine.dbvisor.services.dto.connection;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ConnectionPoolStore extends ConcurrentHashMap<String, VisorConnectionDataSource> implements Runnable {
    private final long supposedLifeTimeMs;
    private final long checkTimeMs;

    public ConnectionPoolStore(long supposedLifeTimeMs, long checkTimeMs) {
        this.supposedLifeTimeMs = supposedLifeTimeMs;
        this.checkTimeMs = checkTimeMs;
    }

    /**
     * Чтобы правильно вызвать этот метод нужно создать новый Daemon Thread и вызвать у него start()
     * Пример:
     * @EventListener(ApplicationReadyEvent.class)
     *      public void runAfterStartup() {
     *          Thread connectionPoolListenerThread = new Thread(new ConnectionPoolStore(...));
     *          connectionPoolListenerThread.setDaemon(true);
     *          connectionPoolListenerThread.setPriority(Thread.MIN_PRIORITY);
     *          connectionPoolListenerThread.start();
     *      }
     */
    @Override
    public void run() {
        log.info("Start to find non-active DB Visor datasources with life time {} (ms)", supposedLifeTimeMs);
        while (true) {
            try {
                TimeUnit.MILLISECONDS.sleep(checkTimeMs);
            } catch (InterruptedException exception) {
                log.error(exception.getMessage());
            }

            long currentTime = System.currentTimeMillis();

            for (String key : keySet()) {
                VisorConnectionDataSource dataSource = get(key);
                long deltaTime = currentTime - dataSource.getLastUsedTime();
                if (deltaTime >= supposedLifeTimeMs && dataSource.isAvailableToClose()) {
                    VisorConnectionDataSource removedDataSource = super.remove(key);
                    removedDataSource.close();
                    log.debug("DB Visor data source with id = {} was removed and closed", key);
                }
            }
        }
    }
}
