package com.vegasdevelopments.lightstream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface LightSubscriptionBase<T> {
	void onEvent(@Nullable OnEventCallback<T> handleEvent);
	
	void onError(@Nullable OnErrorCallback handleError);
	
	void onDone(@Nullable OnDoneCallback handleDone);
	
	@Nonnull
	boolean isPaused();
	
	void resume();
	
	void pause();
	
	Thread cancel();
}
