package com.vegasdevelopments.lightstream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface LightStreamBase<T> {
	static <T> LightStreamBase<T> newStream() {
		return new LightStream<T>();
	}

	static <T> LightStreamBase<T> newStream(T value) {
		return new LightStream<T>(value);
	}

	@Nullable
	T getValue();

	void add(@Nullable final T event);

	void addError(@Nullable final Object error);

	LightSinkBase<T> getSink();

	LightSubscriptionBase<T> listen(@Nullable OnEventCallback<T> onEvent);

	LightSubscriptionBase<T> listen(@Nullable OnEventCallback<T> onEvent, @Nullable OnErrorCallback onError);

	LightSubscriptionBase<T> listen(@Nullable OnEventCallback<T> onEvent, @Nullable OnDoneCallback onDone);

	LightSubscriptionBase<T> listen(@Nullable OnEventCallback<T> onEvent, @Nullable OnErrorCallback onError,
			@Nullable OnDoneCallback onDone);

	@Nonnull
	int length();

	@Nonnull
	boolean hasListeners();

	@Nonnull
	boolean isClosed();

	@Nonnull
	Thread close();
}
