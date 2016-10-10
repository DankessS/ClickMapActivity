package com.academy.cache;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ConcurrentMap;

/**
 * Created by Daniel Palonek on 2016-09-17.
 */
public abstract class AbstractCacheSupplier {

    @Autowired
    private HazelcastInstance hazelcastInstance;

    protected ConcurrentMap<String,Object> getUserContext() {
        return hazelcastInstance.getUserContext();
    }

    protected IMap getMap(String mapName) {
        return hazelcastInstance.getMap(mapName);
    }
}
