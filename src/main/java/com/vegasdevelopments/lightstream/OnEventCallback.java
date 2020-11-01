package com.vegasdevelopments.lightstream;

public interface OnEventCallback<T> {
	void apply(final T event);
}
