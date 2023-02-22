package com.example.authgateway;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.annotation.ConnectMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
@Slf4j
public class TestController {

    private final RSocketRequester.Builder requester;

    @ConnectMapping
    public Mono<Void> sampleConnect(){
        return Mono.fromRunnable(() -> log.info("RSocket Connection Success"));
    }

    @MessageMapping("RSocket.sample")
    public Mono<String> getTest(String sample){
        log.info(sample);
        return Mono.just(sample+"\n RSocket은 받았어요");
    }


    @RequestMapping("/path")
    public Mono<String> gwSample(/*@PathVariable("path") */@RequestBody String sample){
        log.info(sample + " : 요청 잘받았어용");
        return Mono.just(sample);
    }

    @RequestMapping("/api/{service}/{endpoint}")
    public Mono<String> gw(@PathVariable("service") String service,
                           @PathVariable("endpoint") String endpoint){
        return Mono.just(service + " - " + endpoint);
    }

//    @Data
//    private class Sample{
//        private
//    }


}
