package com.zcrabblers.zcrabble.Model;

/**
 * Enumerated letter used to specify our allowed characters to be played
 */
public enum Letter {
    A("A"),
    B("B"),
    C("C"),
    D("D"),
    E("E"),
    F("F"),
    G("G"),
    H("H"),
    I("I"),
    J("J"),
    K("K"),
    L("L"),
    M("M"),
    N("N"),
    O("O"),
    P("P"),
    Q("Q"),
    R("R"),
    S("S"),
    T("T"),
    U("U"),
    V("V"),
    W("W"),
    X("X"),
    Y("Y"),
    Z("Z");

    private final String letter;

    /**
     * creates an enum
     * @param letter the letter to be represented as an enum must be a english alphabetical letter
     */
    Letter(String letter) {
        this.letter = letter;
    }

    /**
     * @return returns the letter
     */
    public String getLetter(){
        return letter;
    }
}
