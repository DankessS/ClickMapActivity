package com.academy.cache;

import com.hazelcast.core.HazelcastInstance;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ConcurrentMap;

/**
 * Created by Daniel Palonek on 2016-09-17.
 */
public class AbstractCacheSupplier {

    @Autowired
    private HazelcastInstance hazelcastInstance;

    protected ConcurrentMap<String,Object> getUserContext() {
        return hazelcastInstance.getUserContext();
    }
}
