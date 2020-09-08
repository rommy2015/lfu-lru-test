package org.example.lru;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CacheLruTest {

    CacheLru cacheLru;
    List <Integer> list;

    @BeforeClass
    public static void globalSetUp() {
        System.out.println("Initial setup...");
    }

    @Before
    public void setUp() {
        cacheLru = new CacheLru(4);
        list = new ArrayList(Arrays.asList(1, 2, 3, 1, 4, 5));
    }

    @Test
    public void run() {

        for (Integer element : list){
            cacheLru.referOntoKeyIntoCacheLru(element);
        }

        cacheLru.displayContainsOfCache();
    }

}
