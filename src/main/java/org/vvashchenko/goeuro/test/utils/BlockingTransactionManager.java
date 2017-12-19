package org.vvashchenko.goeuro.test.utils;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.vvashchenko.goeuro.test.utils.interfaces.Transactionable;

@Component
@Qualifier("BlockingTransactionManager")
public class BlockingTransactionManager implements Transactionable {

    private final Object transactionMonitor = new Object();
    private volatile boolean isLoading = false;

    @Override
    public void endTransaction() {
        isLoading = false;
        synchronized (transactionMonitor) {
            transactionMonitor.notifyAll();
        }
    }

    @Override
    public void startTransaction() {
        isLoading = true;
    }

    @Override
    public void checkTransactionStatus() {
        if (loadingNewItems()) {
            synchronized (transactionMonitor) {
                try {
                    transactionMonitor.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException("Interrupted wait!");
                }
            }
        }
    }

    private boolean loadingNewItems() {
        return isLoading;
    }

}
