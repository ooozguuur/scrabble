package com.gunes.enums;

public enum LetterScore {

    A('A', 1),
    B('B', 3),
    C('C', 4),
    CI('Ç', 4),
    D('D', 3),
    E('E', 1),
    F('F', 7),
    G('G', 5),
    GI('Ğ', 8),
    H('H', 5),
    I('I', 2),
    II('İ', 1),
    J('J', 10),
    K('K', 1),
    L('L', 1),
    M('M', 2),
    N('N', 1),
    O('O', 2),
    OI('Ö', 7),
    P('P', 5),
    R('R', 1),
    S('S', 2),
    SI('Ş', 4),
    U('U', 2),
    UI('Ü', 3),
    V('V', 7),
    Y('Y', 3),
    Z('Z', 4);

    private char letter;

    private int score;

    LetterScore(final char letter, final int score) {
        this.letter = letter;
        this.score = score;
    }

    public char getLetter() {
        return letter;
    }

    public int getScore() {
        return score;
    }

}
