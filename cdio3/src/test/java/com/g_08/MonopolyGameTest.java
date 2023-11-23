package com.g_08;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MonopolyGameTest {

    private MonopolyGame game;

    @Before
    public void setUp() {
        game = new MonopolyGame();
    }

    @Test
    public void testInitializeGame() {
        game.initializeGame();
        assertEquals(4, game.getPlayers().size());
    }

    @Test
    public void testHasProperties() {
        Player player = new Player("TestPlayer", 20);
        assertFalse(game.hasProperties(player));

        // Assuming you have a PropertySpace
        PropertySpace property = new PropertySpace("TestProperty", 1, 2);
        property.owner = player;
        game.getBoard().spaces.add(property);

        assertTrue(game.hasProperties(player));
    }

    // Add more tests for other methods and functionalities as needed

}
