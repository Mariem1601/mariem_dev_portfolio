package model;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void testGameInitializationWithSpecificProblem() {
        Game game = new Game(1);

        assertEquals(0, game.getTotalScore());
        assertEquals(0, game.getRoundNumber());
        assertNotNull(game.getProblem());
        assertNotNull(game.getValidators());
    }

    @Test
    void testGameInitializationWithRandomProblem() {
        Game game = new Game();

        assertEquals(0, game.getTotalScore());
        assertEquals(0, game.getRoundNumber());
        assertNotNull(game.getProblem());
        assertNotNull(game.getValidators());
    }

    @Test
    void testChooseCode() {
        Game game = new Game();
        int code = 123;

        Code code1 = new Code(code);
        game.chooseCode(code);

        assertFalse(game.getActualRound().isRoundStarted());
        assertTrue(code1.equals(game.getActualRound().getProposedCode()));
    }

    @Test
    void testUnChooseCode() {
        Game game = new Game();

        game.chooseCode(123);
        game.unChooseCode();

        assertTrue(game.getActualRound().isRoundStarted());
        assertNull(game.getActualRound().getProposedCode());
    }

    @Test
    void testNextRound() {
        Game game = new Game();
        game.chooseCode(123);

        assertThrows(TurningMachineException.class, game::nextRound);

    }

    @Test
    void testPrecedentRound() {
        Game game = new Game();
        game.chooseCode(123);
        game.verifyValidator(game.getProblem().getValidators().get(0));

        Map<Integer, String> initialValidators = game.getValidators();

        game.nextRound();
        game.precedentRound(initialValidators);

        assertEquals(1, game.getRoundNumber());
        assertEquals(1, game.getActualRound().getScore());
        assertEquals(initialValidators, game.getValidators());
    }
/*
    @Test
    void testCheckCode() {
        Game game = new Game();
         secretCode = game.getProblem().getSecretCode();

        assertTrue(game.checkCode(secretCode));
        assertFalse(game.checkCode(secretCode + 1));
    }*/

    @Test
    void testUnselectValidator() {
        Game game = new Game();
        game.chooseCode(123);
        int nbValidator = game.getProblem().getValidators().get(0);

        game.verifyValidator(nbValidator);
        int initialScore = game.getTotalScore();

        game.unselectValidator(nbValidator);

        assertEquals("white", game.getValidators().get(nbValidator));
        assertEquals(initialScore - 1, game.getTotalScore());
    }

    @Test
    void testNumberProblem() {
        Game game = new Game(1);

        assertEquals(1, game.getProblem().getNumberProblem());
    }
}
