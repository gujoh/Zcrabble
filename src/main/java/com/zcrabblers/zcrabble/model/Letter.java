package com.zcrabblers.zcrabble.model;

/**
 * Letter is an enum that represents a letter.
 * @author Niklas Axelsson
 *
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

    Letter(String letter) {
        this.letter = letter;
    }

    /**
     * Returns a letter.
     * @return a letter formatted as a String.
     */
    public String getLetter(){
        return letter;
    }
}
