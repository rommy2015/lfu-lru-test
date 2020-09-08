package org.example.lru;

import java.util.HashMap;
import java.util.Optional;
import java.util.TreeMap;


public class CacheLruSecondVariant {

    /**Integer - it is a key of added value into cache,
     * Object - it is a value */
    HashMap<Integer, Object> cacheForValues;

    /**
     * Integer - a key for to made of request
     * Long - the time that have done Of request to the Key
     */
    HashMap<Integer, Long> quantityTimesOfRequestToKey;

    /**
     * time map for keys that were accessed
     * Long - the time that have done Of request to the Key
     * Integer - a key for to made of request
     */
    TreeMap<Long, Integer> timeMapForKeys;

    /**
     * The capacity is a cache for values
     */
    int capacity;


    public CacheLruSecondVariant(int capacity) {
        this.capacity = capacity;
        cacheForValues = new HashMap<>();
        quantityTimesOfRequestToKey = new HashMap<>();
        timeMapForKeys = new TreeMap<>();

    }

    public Optional<Object> get(int key) {

        if (!cacheForValues.containsKey(key)){
            return Optional.empty();
        }

        timeMapForKeys.remove(quantityTimesOfRequestToKey.get(key));
        long currentTime=System.nanoTime();

        quantityTimesOfRequestToKey.put(key, currentTime);

        timeMapForKeys.put(currentTime, key);

        return Optional.of(cacheForValues.get(key));
    }

    public void set(int key, Object value) {
        if (capacity <= 0)
            return;

        if (cacheForValues.containsKey(key)) {
            cacheForValues.put(key, value);
            return;
        }
        if (cacheForValues.size() >= capacity) {

            quantityTimesOfRequestToKey.remove(timeMapForKeys.firstEntry().getValue());
            cacheForValues.remove(timeMapForKeys.firstEntry().getValue());
            timeMapForKeys.remove(timeMapForKeys.firstEntry().getKey());


        }

        long currentTime=System.nanoTime();
        cacheForValues.put(key, value);
        quantityTimesOfRequestToKey.put(key, currentTime);
        timeMapForKeys.put(currentTime,key);
    }
}
