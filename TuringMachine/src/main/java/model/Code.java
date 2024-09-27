package model;

/**
 * The {@code Code} class represents a three-digit code used in the game.
 * It consists of three digits, each ranging from 1 to 5.
 */
public class Code {

    /** The first digit of the code. */
    protected final int first;

    /** The second digit of the code. */
    protected final int second;

    /** The third digit of the code. */
    protected final int third;

    /**
     * Constructs a {@code Code} object with the specified three-digit code.
     * Throws an exception if the code is invalid.
     *
     * @param code The three-digit code.
     * @throws TurningMachineException If the code is invalid.
     */
    public Code(int code) {
        int centaine = (code / 100) % 10;
        int dizaine = (code / 10) % 10;
        int unite = code % 10;

        if (!verifyCode(centaine, dizaine, unite)) {
            throw new TurningMachineException("The code proposed is invalid.");
        }

        first = centaine;
        second = dizaine;
        third = unite;
    }

    /**
     * Constructs a {@code Code} object with the same digits as the specified code.
     *
     * @param code The code to copy.
     */
    public Code(Code code) {
        first = code.getFirst();
        second = code.getSecond();
        third = code.getThird();
    }

    /**
     * Verifies if the specified digits form a valid code.
     *
     * @param centaine The hundreds digit.
     * @param dizaine  The tens digit.
     * @param unite    The units digit.
     * @return {@code true} if the code is valid, {@code false} otherwise.
     */
    private boolean verifyCode(int centaine, int dizaine, int unite) {
        return centaine >= 1 && dizaine >= 1 && unite >= 1 && centaine <= 5 && dizaine <= 5 && unite <= 5;
    }

    /**
     * Gets the first digit of the code.
     *
     * @return The first digit.
     */
    public int getFirst() {
        return first;
    }

    /**
     * Gets the second digit of the code.
     *
     * @return The second digit.
     */
    public int getSecond() {
        return second;
    }

    /**
     * Gets the third digit of the code.
     *
     * @return The third digit.
     */
    public int getThird() {
        return third;
    }

    /**
     * Checks if the specified code is equal to this code.
     *
     * @param code The code to compare.
     * @return {@code true} if the codes are equal, {@code false} otherwise.
     */
    public boolean equals(Code code) {
        return code.getFirst() == first && code.getSecond() == second && code.getThird() == third;
    }
}
