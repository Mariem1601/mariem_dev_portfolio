package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoundTest {

    @Test
    void testRoundInitialization() {
        Round round = new Round();

        assertEquals(0, round.getScore());
        assertNull(round.getProposedCode());
        assertTrue(round.isRoundStarted());
    }

    @Test
    void testSetProposedCode() {
        Round round = new Round();
        Code code = new Code(123);

        round.setProposedCode(code);

        assertFalse(round.isRoundStarted());
        assertTrue(code.equals(round.getProposedCode()));
    }

    @Test
    void testUnSetProposedCode() {
        Round round = new Round();
        Code code = new Code(123);

        round.setProposedCode(code);
        round.unSetProposedCode();

        assertTrue(round.isRoundStarted());
        assertNull(round.getProposedCode());
    }

    @Test
    void testIncrementScore() {
        Round round = new Round();

        round.incrementScore();

        assertEquals(1, round.getScore());
    }

    @Test
    void testDecrementScore() {
        Round round = new Round();

        round.decrementScore();

        assertEquals(-1, round.getScore());
    }

    @Test
    void testGetScore() {
        Round round = new Round();

        assertEquals(0, round.getScore());

        round.incrementScore();
        round.incrementScore();

        assertEquals(2, round.getScore());

        round.decrementScore();

        assertEquals(1, round.getScore());
    }

    @Test
    void testGetCodeProposal() {
        Round round = new Round();
        Code code = new Code(123);

        round.setProposedCode(code);

        Code retrievedCode = round.getProposedCode();

        assertNotNull(retrievedCode);
        assertNotSame(code, retrievedCode);
        assertEquals(code.getFirst(), retrievedCode.getFirst());
        assertEquals(code.getSecond(), retrievedCode.getSecond());
        assertEquals(code.getThird(), retrievedCode.getThird());
    }

    @Test
    void testIsRoundStarted() {
        Round round = new Round();

        assertTrue(round.isRoundStarted());

        round.setProposedCode(new Code(123));

        assertFalse(round.isRoundStarted());

        round.unSetProposedCode();

        assertTrue(round.isRoundStarted());
    }

}