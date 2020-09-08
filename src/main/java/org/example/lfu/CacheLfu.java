package org.example.lfu;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Optional;


public class CacheLfu {

    /**
     * cache of values
     * Integer - it is a key
     * Object - it is a value
     */
    HashMap<Integer, Object> valuesCache;

    /** counters of using values
     * Integer - it is a key of value, that is used.
     * Integer - it is the number of times the key has been used*/
    HashMap<Integer, Integer> counters;

    /** the map quantity of requests for element of cache
     * Integer - it is a number of a counter
     * LinkedHashSet<Integer> - it is frequency of requests (item lists)*/
    HashMap<Integer, LinkedHashSet<Integer>> listsQuantityRequests;

    /** the capacity of a cache */
    int capacity;

    /**
     *
     */
    int firstKeyOfCounter = 1;

    int initialValueListQuantityRequests = -1;

    public CacheLfu(int capacity) {

        this.capacity = capacity;

        this.valuesCache = new HashMap<>();

        this.counters = new HashMap<>();

        this.listsQuantityRequests = new HashMap<>();

        int firstKey = 1;

        this.listsQuantityRequests.put(firstKey, new LinkedHashSet<>());
    }

    public Optional<Object> get(int key) {

        if (!valuesCache.containsKey(key)){

            return Optional.empty();

        }

        int count = counters.get(key);

        counters.put(key, count + 1);

        listsQuantityRequests.get(count).remove(key);


        if (count == initialValueListQuantityRequests && listsQuantityRequests.get(count).size() == 0){
            initialValueListQuantityRequests++;
        }

        if (!listsQuantityRequests.containsKey(count + 1)){
            listsQuantityRequests.put(count + 1, new LinkedHashSet<>());
        }

        listsQuantityRequests.get(count + 1).add(key);

        return Optional.of(valuesCache.get(key));
    }


    public void set(int key, Object value) {

        if (capacity <= 0) {
            return;
        }

        /**
         * if the key is the valuesCache then the key is rewritten
         */
        if (valuesCache.containsKey(key)) {

            valuesCache.put(key, value);

            return;
        }

        /**
         * if size of cacheValues is large than given capacity for cache,
         * then is happened eviction
         */
        if (valuesCache.size() >= capacity) {

            int eviction  = listsQuantityRequests
                    .get(initialValueListQuantityRequests)
                    .iterator().next();

            LinkedHashSet<Integer> listElementsIntoCache =
                    listsQuantityRequests.get(initialValueListQuantityRequests);

            listElementsIntoCache.remove(eviction);

            valuesCache.remove(eviction );

            counters.remove(eviction );
        }

        addEntryIntoCache(key, value);

    }

    /**
     * if an element is absent into cache then it is added
     * Also there is happened to create a counter for added element
     * @param key key of added element
     * @param value value of added element
     */
    private void addEntryIntoCache(int key, Object value){

        valuesCache.put(key, value);

        counters.put(key, 1);

        initialValueListQuantityRequests = 1;

        LinkedHashSet<Integer> firstElement =
                listsQuantityRequests.get(initialValueListQuantityRequests);

        firstElement.add(key);
    }
}