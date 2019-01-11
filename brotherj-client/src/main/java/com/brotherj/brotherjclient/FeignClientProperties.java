/*
 * Copyright 2013-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.brotherj.brotherjclient;

import feign.Contract;
import feign.Logger;
import feign.RequestInterceptor;
import feign.Retryer;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@ConfigurationProperties("feign.client")
public class FeignClientProperties {


	private String defaultConfig = "default";

	private Map<String, FeignClientConfiguration> config = new HashMap<>();

	public String getDefaultConfig() {
		return defaultConfig;
	}

	public void setDefaultConfig(String defaultConfig) {
		this.defaultConfig = defaultConfig;
	}

	public Map<String, FeignClientConfiguration> getConfig() {
		return config;
	}

	public void setConfig(Map<String, FeignClientConfiguration> config) {
		this.config = config;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		FeignClientProperties that = (FeignClientProperties) o;
		return Objects.equals(defaultConfig, that.defaultConfig) &&
				Objects.equals(config, that.config);
	}

	@Override
	public int hashCode() {
		return Objects.hash(defaultConfig, config);
	}

	public static class FeignClientConfiguration {

		private Logger.Level loggerLevel;

		private Integer connectTimeout;

		private Integer readTimeout;


		public Logger.Level getLoggerLevel() {
			return loggerLevel;
		}

		public void setLoggerLevel(Logger.Level loggerLevel) {
			this.loggerLevel = loggerLevel;
		}

		public Integer getConnectTimeout() {
			return connectTimeout;
		}

		public void setConnectTimeout(Integer connectTimeout) {
			this.connectTimeout = connectTimeout;
		}

		public Integer getReadTimeout() {
			return readTimeout;
		}

		public void setReadTimeout(Integer readTimeout) {
			this.readTimeout = readTimeout;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			FeignClientConfiguration that = (FeignClientConfiguration) o;
			return loggerLevel == that.loggerLevel &&
					Objects.equals(connectTimeout, that.connectTimeout) &&
					Objects.equals(readTimeout, that.readTimeout);
		}

		@Override
		public int hashCode() {
			return Objects.hash(loggerLevel, connectTimeout, readTimeout);
		}
	}

}
