package org.thingml.utils;

public interface WSServerObserver {

    void onMessage(String message);
    void onOpen();
    void onClose();
    void onError(String errorMessage);

}
