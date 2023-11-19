// Property.java

import java.util.*;

class Player {
    String name;
    int money;

    Player(String name, int money) {
        this.name = name;
        this.money = money;
    }
}

class BoardSpace {
    String name;

    BoardSpace(String name) {
        this.name = name;
    }

    void performAction(Player player) {
        System.out.println(player.name + " landed on " + name);
    }
}

class PropertySpace extends BoardSpace {
    int cost;
    int rent;
    Player owner;

    PropertySpace(String name, int cost, int rent) {
        super(name);
        this.cost = cost;
        this.rent = rent;
    }

    @Override
    void performAction(Player player) {
        super.performAction(player);
        if (owner == null && player.money >= cost) {
            owner = player;
            player.money -= cost;
            System.out.println(player.name + " bought " + name + " for $" + cost);
        } else if (owner != null && owner != player) {
            player.money -= rent;
            owner.money += rent;
            System.out.println(player.name + " paid $" + rent + " rent to " + owner.name);
        }
    }

    void payRent(Player player) {
        if (owner != null && owner != player) {
            int totalRent = calculateRent();
            player.money -= totalRent;
            owner.money += totalRent;
            System.out.println(player.name + " paid $" + totalRent + " rent to " + owner.name);
        }
    }

    private int calculateRent() {
        // You can implement more complex rent calculation logic here if needed
        return rent;
    }
}

class ChanceSpace extends BoardSpace {
    ChanceSpace(String name) {
        super(name);
    }

    @Override
    void performAction(Player player) {
        super.performAction(player);
        // Implement chance action, e.g., draw a chance card
        System.out.println("Drawing a chance card for " + player.name);
    }
}

class EmptySpace extends BoardSpace {
    EmptySpace(String name) {
        super(name);
    }
}

class StartSpace extends BoardSpace {
    StartSpace(String name) {
        super(name);
    }

    @Override
    void performAction(Player player) {
        super.performAction(player);
        System.out.println(player.name + " passed Go and collected $2");
        player.money += 2;
    }
}

class JailSpace extends BoardSpace {
    JailSpace(String name) {
        super(name);
    }

    @Override
    void performAction(Player player) {
        super.performAction(player);
        System.out.println(player.name + " is in jail");
        // You can add more logic related to being in jail if needed
    }
}

class VisitJailSpace extends BoardSpace {
    VisitJailSpace(String name) {
        super(name);
    }

    @Override
    void performAction(Player player) {
        super.performAction(player);
        System.out.println(player.name + " visited the jail");
        // You can add more logic related to visiting the jail if needed
    }
}

class FreeParkingSpace extends BoardSpace {
    FreeParkingSpace(String name) {
        super(name);
    }

    @Override
    void performAction(Player player) {
        super.performAction(player);
        System.out.println(player.name + " is in Free Parking");
        // You can add more logic related to Free Parking if needed
    }
}

class GoToJailSpace extends BoardSpace {
    GoToJailSpace(String name) {
        super(name);
    }

    @Override
    void performAction(Player player) {
        super.performAction(player);
        System.out.println(player.name + " goes to jail!");
        // You can add more logic related to going to jail if needed
    }
}

class MonopolyBoard {
    List<BoardSpace> spaces;

    MonopolyBoard(List<BoardSpace> spaces) {
        this.spaces = spaces;
    }

    void displayBoard() {
        System.out.println("Monopoly Board:");
        for (BoardSpace space : spaces) {
            System.out.println(space.name);
        }
    }

    BoardSpace getSpace(int index) {
        return spaces.get(index);
    }
}

class MonopolyGame {
    List<Player> players;
    MonopolyBoard board;

    MonopolyGame() {
        players = new ArrayList<>();
        board = new MonopolyBoard(
                List.of(
                        new StartSpace("Start"),
                        new PropertySpace("GATEKJØKKENET BURGERBAREN", 1, 2),
                        new PropertySpace("PIZZAHUSET PIZZERIAET", 1, 2),
                        new ChanceSpace("Chance"),
                        new PropertySpace("GODTEBUTIKKEN SLIKBUTIKKEN", 1, 2),
                        new PropertySpace("ISKIOSKEN ISKIOSKEN", 1, 2),
                        new JailSpace("Jail/VISIT JAIL"),
                        new PropertySpace("MUSEET MUSEET", 2, 4),
                        new PropertySpace("BIBLIOTEKET", 2, 4),
                        new ChanceSpace("Chance"),
                        new PropertySpace("RULLEBRETTPARKEN SKATERPARKEN", 2, 4),
                        new PropertySpace("SVØMMEBASSENGET SWIMMINGPOOLEN", 2, 4),
                        new FreeParkingSpace("Free Parking"),
                        new PropertySpace("SPILLEHALLEN SPILLEHALLEN", 3, 6),
                        new PropertySpace("KINOEN BIOGRAFEN", 3, 6),
                        new ChanceSpace("Chance"),
                        new PropertySpace("LEKETØYSBUTIKKEN LEGETØJSBUTIKKEN", 3, 6),
                        new PropertySpace("DYREBUTIKKEN DYREHANDLEN", 3, 6),
                        new GoToJailSpace("Go to Jail"),
                        new PropertySpace("BOWLINGHALLEN BOWLINGHALLEN", 4, 8),
                        new PropertySpace("ZOOLOGISK HAGE ZOO", 4, 8),
                        new ChanceSpace("Chance"),
                        new PropertySpace("VANNLANDET VANDLANDET", 5, 10),
                        new PropertySpace("STRANDPROMENADEN STRANDPROMENADEN", 5, 10)
                )
        );
    }

    void initializeGame() {
        // Add players
        players.add(new Player("Player 1", 20));
        players.add(new Player("Player 2", 20));
        players.add(new Player("Player 3", 20));
        players.add(new Player("Player 4", 20));
    }

    void playGame() {
        initializeGame();

        // Game loop
        Scanner scanner = new Scanner(System.in);

        while (true) {
            for (Player currentPlayer : players) {
                System.out.println("\n" + currentPlayer.name + "'s turn");

                // Display available options
                System.out.println("1. Roll Dice");
                System.out.println("2. End Turn");

                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        // Simulate dice roll
                        int diceRoll = (int) (Math.random() * 6) + 1;
                        System.out.println(currentPlayer.name + " rolled a " + diceRoll);

                        // Calculate the new position on the board
                        int currentPosition = players.indexOf(currentPlayer);
                        int newPosition = (currentPosition + diceRoll) % board.spaces.size();

                        // Perform the action on the new board space
                        board.getSpace(newPosition).performAction(currentPlayer);
                        break;

                    case 2:
                        // End turn
                        break;

                    default:
                        System.out.println("Invalid choice");
                }

                // Simulate rent payment
                for (BoardSpace space : board.spaces) {
                    if (space instanceof PropertySpace) {
                        PropertySpace property = (PropertySpace) space;
                        property.payRent(currentPlayer);
                    }
                }
            }
        }
    }
}

public class Property {
    public static void main(String[] args) {
        MonopolyGame game = new MonopolyGame();
        game.playGame();
    }
}
