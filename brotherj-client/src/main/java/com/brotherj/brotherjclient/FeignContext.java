package com.brotherj.brotherjclient;

import org.springframework.cloud.context.named.NamedContextFactory;

/**
 * description：
 *
 * @author brotherJ  Wang
 */
public class FeignContext extends NamedContextFactory<FeignClientSpecification> {


    public FeignContext() {
        super(FeignClientsConfiguration.class, "feign", "feign.client.name");
    }

}