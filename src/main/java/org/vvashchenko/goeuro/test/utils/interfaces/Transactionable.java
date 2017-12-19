package org.vvashchenko.goeuro.test.utils.interfaces;

public interface Transactionable {

    void startTransaction();

    void endTransaction();

    void checkTransactionStatus();
}
