package com.academy.web.config.cache;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Daniel Palonek on 2016-09-05.
 */
@Configuration
public class HazelcastConfiguration {
    @Bean
    public HazelcastInstance config() {
        return Hazelcast.newHazelcastInstance();
    }
}
