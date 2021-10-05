package com.zcrabblers.zcrabble.Utils;

import java.time.*;

/**
 * Enum representing a seed that is to be used when utilising the Random class.
 * Seed gets the current date and converts it to a long.
 */
public enum RandomSeed {
    INSTANCE;
    long seed = Long.parseLong(LocalDate.now().toString().replace("-",""));

    /**
     * Getter for long seed.
     * @return a long representing a seed.
     */
    public long getSeed(){
        return seed;
    }

}
