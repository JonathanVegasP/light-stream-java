package com.vegasdevelopments.lightstream;

interface LightListenerBase<T> {
	void addSubscription(LightSubscriptionBase<T> subscription);
	
	void removeSubscription(LightSubscriptionBase<T> subscription);
	
	void notifyEvent(T event);

	void notifyError(Object error);

	Thread notifyDone();

	int length();

	boolean hasListeners();

	Thread close();
}
