package org.example.lfu;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;


class CacheLfuTest {

    CacheLfu cacheLfu;

    int capacity = 2;

    int keyFirst = 1;
    int keySecond = 2;
    int keyThird = 3;

    @BeforeEach
    void setUp() {

        cacheLfu = new CacheLfu(capacity);
        cacheLfu.set(keyFirst, new Object());
        cacheLfu.set(keySecond, new Object());
    }

    @AfterEach
    void tearDown() {
        cacheLfu = null;
    }

    @Test
    void get() {
        assertNotNull(cacheLfu.valuesCache.get(keyFirst));
        assertNotNull(cacheLfu.valuesCache.get(keySecond));
    }

    @Test
    void set() {

        assertEquals(cacheLfu.valuesCache.size(), capacity);
    }

    @Test
    void testCapacity() {
        cacheLfu.set(keyThird, new Object());
        assertNull(cacheLfu.valuesCache.get(keyFirst));
        assertEquals(cacheLfu.valuesCache.size(), keySecond);

    }

    @Test
    void testEvictionStrategy() {

        Object objectFirst = cacheLfu.get(keyFirst);

        assertNotNull(objectFirst);

        cacheLfu.set(keyThird, new Object());

        assertNull(cacheLfu.valuesCache.get(keySecond));

    }

}