package com.vegasdevelopments.lightstream;

class LightStream<T> implements LightStreamBase<T> {
	final LightSink<T> sink;

	protected LightStream() {
		sink = new LightSink<T>();
	}

	protected LightStream(T value) {
		sink = new LightSink<T>(value);
	}

	private void isAlreadyClosed() {
		if (isClosed()) {
			throw new LightStreamException("You cannot add events to a closed LightStream");
		}

	}

	@Override
	public T getValue() {
		return sink.getValue();
	}

	@Override
	public void add(T event) {
		sink.add(event);
	}

	@Override
	public void addError(Object error) {
		sink.addError(error);
	}

	@Override
	public LightSubscriptionBase<T> listen(OnEventCallback<T> onEvent) {
		isAlreadyClosed();
		final LightSubscriptionBase<T> subscription = new LightSubscription<T>(sink.listener, onEvent, null, null);
		sink.listener.addSubscription(subscription);
		return subscription;
	}

	@Override
	public LightSubscriptionBase<T> listen(OnEventCallback<T> onEvent, OnErrorCallback onError) {
		isAlreadyClosed();
		final LightSubscriptionBase<T> subscription = new LightSubscription<T>(sink.listener, onEvent, onError, null);
		sink.listener.addSubscription(subscription);
		return subscription;
	}

	@Override
	public LightSubscriptionBase<T> listen(OnEventCallback<T> onEvent, OnDoneCallback onDone) {
		isAlreadyClosed();
		final LightSubscriptionBase<T> subscription = new LightSubscription<T>(sink.listener, onEvent, null, onDone);
		sink.listener.addSubscription(subscription);
		return subscription;
	}

	@Override
	public LightSubscriptionBase<T> listen(OnEventCallback<T> onEvent, OnErrorCallback onError, OnDoneCallback onDone) {
		isAlreadyClosed();
		final LightSubscriptionBase<T> subscription = new LightSubscription<T>(sink.listener, onEvent, onError, onDone);
		sink.listener.addSubscription(subscription);
		return subscription;
	}

	@Override
	public LightSinkBase<T> getSink() {
		return sink;
	}

	@Override
	public int length() {
		return sink.length();
	}

	@Override
	public boolean hasListeners() {
		return sink.hasListeners();
	}

	@Override
	public boolean isClosed() {
		return sink.isClosed();
	}

	@Override
	public Thread close() {
		return sink.close();
	}
}
