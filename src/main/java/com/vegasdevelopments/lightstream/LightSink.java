package com.vegasdevelopments.lightstream;

class LightSink<T> implements LightSinkBase<T> {
	protected LightListenerBase<T> listener = new LightListener<T>();
	
	private T value;

	protected LightSink() {
	}

	protected LightSink(T value) {
		this.value = value;
	}

	private void canBeClosed() {
		if (isClosed()) {
			throw new LightStreamException("You cannot close a closed LightStream");
		}
	}

	private void isAlreadyClosed() {
		if (isClosed()) {
			throw new LightStreamException("You cannot add events to a closed LightStream");
		}
	}

	protected T getValue() {
		return value;
	}

	@Override
	public void add(T event) {
		isAlreadyClosed();
		value = event;
		listener.notifyEvent(event);
	}

	@Override
	public void addError(Object error) {
		isAlreadyClosed();
		listener.notifyError(error);
	}

	@Override
	public int length() {
		return listener != null ? listener.length() : 0;
	}

	@Override
	public boolean hasListeners() {
		return listener != null && listener.hasListeners();
	}

	@Override
	public boolean isClosed() {
		return listener == null;
	}

	@Override
	public Thread close() {
		canBeClosed();
		final Thread thread = new Thread(() -> {
			try {
				listener.notifyDone().join();
				listener.close().join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				listener = null;
				value = null;
			}
		});
		thread.start();
		return thread;
	}
}
