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

    @Test
    public void testPayRent(){
        Player owner = new Player("Owner", 10);
        Player visitor = new Player("Visitor", 10);

        PropertySpace testProperty = new PropertySpace("TestProperty",10,5);

        testProperty.owner=owner;

        testProperty.payRent(visitor);

        assertEquals("Owner should recieve rent",15,owner.money);

        assertEquals("Visitor should pay rent",5,visitor.money);
    }

}
