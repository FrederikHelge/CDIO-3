package com.g_08;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

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
        MonopolyGame game = new MonopolyGame();
        List<Player> players = new ArrayList<>(game.getPlayers());
        
        players.add(new Player("TestPlayer", 100));
        
    }

}
