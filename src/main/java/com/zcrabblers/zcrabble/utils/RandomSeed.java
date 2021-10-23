package com.zcrabblers.zcrabble.utils;

import java.time.*;
import java.util.Random;

/**
 * Enum representing a Random object with a seed that changes each day.
 * Seed gets the current date and converts it to a long.
 * Can be used for testing purposes, for instance.
 */
public enum RandomSeed {
    INSTANCE;
    long seed = Long.parseLong(LocalDate.now().toString().replace("-",""));
    Random random = new Random(seed);

    /**
     * Getter for long seed.
     * @return a long representing a seed.
     */
    public Random getRandom(){
        return random;
    }

    /**
     * Returns the seed.
     * @return the seed.
     */
    public long getSeed(){
        return seed;
    }

}
