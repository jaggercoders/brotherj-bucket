package com.brotherj.brotherjclient;

import feign.Logger;

/**
 * Allows an application to use a custom Feign {@link Logger}.
 *
 */
public interface FeignLoggerFactory {

	/**
	 * Factory method to provide a {@link Logger} for a given {@link Class}.
	 *
	 * @param type the {@link Class} for which a {@link Logger} instance is to be created
	 * @return a {@link Logger} instance
	 */
	Logger create(Class<?> type);

}
