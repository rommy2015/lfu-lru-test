package org.example.lru;

import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public class CacheLru {


    /** to save keys of cache*/
    static Deque<Integer> storeKeysOfCache;

    /**
     * to store references for Key into cache
     */
    static HashSet<Integer> StoreReferencesForKeysIntoCache;

    /**
     * max capacity of the cache
     */
    private int maxSizeCache;

    public CacheLru(int maxSizeCache) {
        this.maxSizeCache = maxSizeCache;
    }

   static  {

        storeKeysOfCache = new LinkedList<>();

        StoreReferencesForKeysIntoCache = new HashSet<>();

    }



    /*Относится к ключу x в кеше LRU */

    /**
     *
     * @param key key, that is been processing now
     */
    public void referOntoKeyIntoCacheLru(int key) {

        if (!StoreReferencesForKeysIntoCache.contains(key)) {

            if (storeKeysOfCache.size() == maxSizeCache) {

                int last = storeKeysOfCache.removeLast();

                StoreReferencesForKeysIntoCache.remove(last);

            }

        } else {

            /* Найденная страница не всегда может быть последним элементом, даже если она

            промежуточный элемент, который необходимо удалить и добавить в начало

            очереди */

            int index = 0;
            int i = 0;

            for (Integer integer : storeKeysOfCache) {

                if (integer == key) {

                    index = i;

                    break;

                }

                i++;

            }

            storeKeysOfCache.remove(index);

        }

        storeKeysOfCache.push(key);

        StoreReferencesForKeysIntoCache.add(key);

    }



    public void displayContainsOfCache() {

        Iterator<Integer> itr = storeKeysOfCache.iterator();
        System.out.println();

        while (itr.hasNext()) {
            System.out.print(itr.next() + " ");
        }

        System.out.println();

    }

}
