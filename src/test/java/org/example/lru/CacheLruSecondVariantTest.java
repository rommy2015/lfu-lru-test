package org.example.lru;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CacheLruSecondVariantTest {

    CacheLruSecondVariant cacheLruSecondVariant;

    int keyFirst = 1;
    int keySecond = 2;
    int keyThird = 3;

    @BeforeEach
    void setUp() {
        int capacity = 2;
        cacheLruSecondVariant = new CacheLruSecondVariant(capacity);
        cacheLruSecondVariant.set(keyFirst, new Object());
        cacheLruSecondVariant.set(keySecond, new Object());
    }

    @AfterEach
    void tearDown() {
        cacheLruSecondVariant = null;
    }

    @Test
    void get() {
        assertNotNull(cacheLruSecondVariant.cacheForValues.get(keyFirst));
        assertNotNull(cacheLruSecondVariant.cacheForValues.get(keySecond));
    }

    @Test
    void set() {
        assertEquals(cacheLruSecondVariant.cacheForValues.size(),keySecond);
    }

    @Test
    void testCapacity(){
        cacheLruSecondVariant.set(keyThird, new Object());
        assertEquals(cacheLruSecondVariant.cacheForValues.size(),keySecond);

    }

    @Test
    void testEvictionStrategy(){

        cacheLruSecondVariant.set(keyThird, new Object());

        Object objectFound = cacheLruSecondVariant.get(keyThird).get();

        assertNotNull(objectFound);

        assertThrows(NoSuchElementException.class, ()
                -> cacheLruSecondVariant.get(keyFirst).get());

        assertNull(cacheLruSecondVariant.cacheForValues.get(keyFirst));
    }
}