package com.vegasdevelopments.lightstream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface LightSinkBase<T> {
	void add(@Nullable final T event);

	void addError(@Nullable final Object error);

	@Nonnull
	int length();

	@Nonnull
	boolean hasListeners();

	@Nonnull
	boolean isClosed();

	@Nonnull
	Thread close();
}
