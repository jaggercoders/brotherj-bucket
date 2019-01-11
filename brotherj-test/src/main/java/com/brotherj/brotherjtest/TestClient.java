package com.brotherj.brotherjtest;

import com.brotherj.brotherjclient.FeignClient;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

/**
 * description：
 *
 * @author brotherJ  Wang
 */
@FeignClient(name = "brotherj",url = "${brotherj.server.url}",path = "/test")
public interface TestClient {

    @RequestLine(value = "GET /tt")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    String test();
}
