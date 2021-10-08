package com.zcrabblers.zcrabble.Tests;

import com.zcrabblers.zcrabble.Model.Dictionary;
import org.junit.Test;

import static org.junit.Assert.*;

public class DictionaryTests {
    @Test
    public void TestgetInstance(){
        Dictionary dictionary = Dictionary.getInstance();
        assertNotNull(dictionary.getDictArray());
        assertTrue(dictionary.checkWord("boat"));
    }
}
