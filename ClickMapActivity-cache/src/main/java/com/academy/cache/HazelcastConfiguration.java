package com.academy.cache;

import com.hazelcast.config.Config;
import com.hazelcast.config.XmlConfigBuilder;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileNotFoundException;

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
