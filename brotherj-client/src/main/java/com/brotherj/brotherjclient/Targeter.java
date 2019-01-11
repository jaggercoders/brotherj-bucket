
package com.brotherj.brotherjclient;

import feign.Feign;
import feign.Target;

/**
 * description：
 *
 * @author brotherJ  Wang
 */

interface Targeter {
	<T> T target(FeignClientFactoryBean factory, Feign.Builder feign, FeignContext context,
                 Target.HardCodedTarget<T> target);
}
