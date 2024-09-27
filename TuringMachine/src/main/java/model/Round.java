package model;

/**
 * The {@code Round} class represents a round in the Turing Machine game.
 * It keeps track of the proposed code, the score, and whether the round has started.
 */
public class Round {

    /** The proposed code for the round. */
    private Code proposedCode;

    /** The score achieved in the round. */
    private int score;

    /** A flag indicating whether the round has started. */
    private boolean roundStarted;

    /**
     * Constructs a new {@code Round} object with an initial score of 0 and the round not started.
     */
    public Round() {
        score = 0;
        roundStarted = false;
    }

    /**
     * Increments the score by 1.
     */
    public void incrementScore() {
        score++;
    }

    /**
     * Decrements the score by 1.
     */
    public void decrementScore() {
        score--;
    }

    /**
     * Checks if the round has started.
     *
     * @return {@code true} if the round has started, {@code false} otherwise.
     */
    public boolean isRoundStarted() {
        return !roundStarted;
    }

    /**
     * Sets the proposed code for the round and marks the round as started.
     *
     * @param proposedCode The proposed code for the round.
     */
    public void setProposedCode(Code proposedCode) {
        this.proposedCode = proposedCode;
        roundStarted = true;
    }

    /**
     * Unsets the proposed code, marking the round as not started.
     */
    public void unSetProposedCode() {
        proposedCode = null;
        roundStarted = false;
    }

    /**
     * Gets the proposed code for the round.
     *
     * @return The proposed code, or {@code null} if the code is not set.
     */
    public Code getProposedCode() {
        if (proposedCode == null) {
            return null;
        }
        return new Code(proposedCode);
    }

    /**
     * Gets the score achieved in the round.
     *
     * @return The score for the round.
     */
    public int getScore() {
        return score;
    }
}

