package com.vegasdevelopments.lightstream;

class LightSubscription<T> implements LightSubscriptionBase<T> {
	final LightListenerBase<T> listener;

	protected volatile OnEventCallback<T> _onEvent;

	protected volatile OnErrorCallback _onError;

	protected volatile OnDoneCallback _onDone;

	private volatile boolean paused = false;

	protected LightSubscription(LightListenerBase<T> listener, OnEventCallback<T> onEvent, OnErrorCallback onError,
			OnDoneCallback onDone) {
		this.listener = listener;
		_onEvent = onEvent;
		_onError = onError;
		_onDone = onDone;
	}

	@Override
	public void onEvent(OnEventCallback<T> handleEvent) {
		_onEvent = handleEvent;
	}

	@Override
	public void onError(OnErrorCallback handleError) {
		_onError = handleError;
	}

	@Override
	public void onDone(OnDoneCallback handleDone) {
		_onDone = handleDone;
	}

	@Override
	public boolean isPaused() {
		return paused;
	}

	@Override
	public void resume() {
		new Thread(() -> {
			paused = false;
		}).start();
	}

	@Override
	public void pause() {
		new Thread(() -> {
			paused = true;
		}).start();
	}

	@Override
	public Thread cancel() {
		final Thread thread = new Thread(() -> {
			listener.removeSubscription(this);
			_onEvent = null;
			_onError = null;
			_onDone = null;
		});
		thread.start();
		return thread;
	}
}
