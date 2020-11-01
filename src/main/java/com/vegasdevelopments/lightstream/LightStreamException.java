package com.vegasdevelopments.lightstream;

class LightStreamException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private static String getExceptionName(String message) {
		final StringBuilder builder = new StringBuilder();
		builder.append("LightStreamException: ");
		builder.append(message);
		return builder.toString();
	}
	
	public LightStreamException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(getExceptionName(message), cause, enableSuppression, writableStackTrace);
	}

	public LightStreamException(String message, Throwable cause) {
		super(getExceptionName(message), cause);
	}

	public LightStreamException(String message) {
		super(getExceptionName(message));
	}

	public LightStreamException(Throwable cause) {
		super(cause);
	}
}
