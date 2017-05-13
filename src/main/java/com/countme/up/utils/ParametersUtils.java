package com.countme.up.utils;

/**
 * This utility class is used to operate different functions on method's parameters.
 * 
 * @author ahamouda
 * 
 */
public final class ParametersUtils {

	private ParametersUtils() {
	}

	/**
	 * This static method is used to check if any of the given parameters is <b>null</b>.
	 * 
	 * @param parameters
	 *            array of parameters
	 * @throws NullPointerException
	 *             if any of the given parameter is <b>null</b>.
	 */
	public static void checkNullParameters(Object... parameters) {
		synchronized (parameters) {
			if (parameters != null) {
				for (Object o : parameters) {
					if (o == null) {
						throw new NullPointerException();
					}
				}
			}
		}
	}
}
