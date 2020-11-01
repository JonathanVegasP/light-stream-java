package com.vegasdevelopments.lightstream;

import java.util.HashMap;
import java.util.Map;

class LightListener<T> implements LightListenerBase<T> {
	volatile Map<LightSubscription<T>, Void> subscriptions;

	protected LightListener() {
		subscriptions = new HashMap<LightSubscription<T>, Void>();
	}

	@Override
	public void addSubscription(LightSubscriptionBase<T> subscription) {
		subscriptions.put((LightSubscription<T>) subscription, null);
	}

	@Override
	public void removeSubscription(LightSubscriptionBase<T> subscription) {
		subscriptions.remove(subscription);
	}

	@Override
	public void notifyEvent(T event) {
		new Thread(() -> {
			subscriptions.forEach((subscription, __) -> {
				if (subscription.isPaused()) {
					return;
				}

				try {
					if (subscription._onEvent != null) {
						subscription._onEvent.apply(event);
					}
				} catch (Exception e) {
					if (subscription._onError != null) {
						subscription._onError.apply(e);
					}
				}
			});
		}).start();
	}

	@Override
	public void notifyError(Object error) {
		new Thread(() -> {
			subscriptions.forEach((subscription, __) -> {
				if (subscription.isPaused()) {
					return;
				}

				if (subscription._onError != null) {
					subscription._onError.apply(error);
				}
			});
		}).start();
	}

	@Override
	public Thread notifyDone() {
		final Thread thread = new Thread(() -> {
			subscriptions.forEach((subscription, __) -> {
				if (subscription.isPaused()) {
					return;
				}

				if (subscription._onDone != null) {
					subscription._onDone.apply();
				}
			});
		});
		thread.start();
		return thread;
	}

	@Override
	public int length() {
		return subscriptions.size();
	}

	@Override
	public boolean hasListeners() {
		return !subscriptions.isEmpty();
	}

	@Override
	public Thread close() {
		final Thread thread = new Thread(() -> {
			subscriptions.clear();
			subscriptions = null;
		});
		thread.start();
		return thread;
	}
}
