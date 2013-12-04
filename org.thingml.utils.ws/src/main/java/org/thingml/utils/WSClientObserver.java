package org.thingml.utils;

public interface WSClientObserver {

    void onMessage(String message);
    void onOpen();
    void onClose();
    void onError(String errorMessage);

}
