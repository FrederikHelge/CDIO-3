package com.g_08;

import java.util.*;

class Player {
    String name;
    int money;
    int position;

    Player(String name, int money) {
        this.name = name;
        this.money = money;
        this.position = 0;
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
            System.out.println("Property not owned: " + name);
            System.out.println("Do you wish to buy the property for $" + cost + "?(1: Yes, 2: No)");

            var buyChoice = new Scanner(System.in);
            int buyDecision = 0;

            try{
                buyDecision = buyChoice.nextInt();
            } catch (InputMismatchException e){
                System.out.println("Invalid input. Please enter a number");
                buyChoice.next();
            }

            if(buyDecision==1){
                owner = player;
                player.money -= cost;
                System.out.println(player.name + " bought " + name + " for $" + cost); 
            } else {
                System.out.println(player.name + " decided not to buy " + name);
            }

        } else if (owner != null && !owner.equals(player)) {
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
        return rent;
    }
}

class ChanceSpace extends BoardSpace {
    private MonopolyBoard board;

    ChanceSpace(String name, MonopolyBoard board) {
        super(name);
        this.board = board;
    }

    void setBoard(MonopolyBoard board) {
        this.board = board;
    }

    @Override
    void performAction(Player player) {
        super.performAction(player);
        drawChanceCard(player);
    }

    private void drawChanceCard(Player player) {
        int cardNumber = (int) (Math.random() * 10) + 1;

        switch (cardNumber) {
            case 1:
                moveUpToThreeSpaces(player);
                break;
            case 2:
                moveToStart(player);
                break;
            case 3:
                moveUpToFiveSpaces(player);
                break;
            case 4:
                moveUpToThreeSpaces(player);
                break;
            case 5:
                moveOneSpaceOrExtraCard(player);
                break;
            case 6:
                getMoneyForHouses(player);
                break;
            case 7:
                moveUpToFiveSpaces(player);
                break;
            case 8:
                moveToStart(player);
                break;
            case 9:
                goToJail(player);
                break;
            case 10:
                breakOutOfJail(player);
                break;
        }
    }

    private void moveToStart(Player player) {
        System.out.println("Move to Start and get $2.");
        player.money += 2;
        player.position = 0;
        board.getSpace(player.position).performAction(player);
    }

    private void moveUpToFiveSpaces(Player player) {
        int spaces = (int) (Math.random() * 5) + 1;
        System.out.println("Move up to " + spaces + " spaces forward.");
        player.position = (player.position + spaces) % board.spaces.size();
        board.getSpace(player.position).performAction(player);
    }

    private void moveUpToThreeSpaces(Player player) {
        int spaces = (int) (Math.random() * 3) + 1;
        System.out.println("Move up to " + spaces + " spaces forward.");
        int newPosition = (player.position + spaces) % board.spaces.size();
        if (board.getSpace(newPosition) instanceof PropertySpace) {
            PropertySpace property = (PropertySpace) board.getSpace(newPosition);
            if (property.owner == null) {
                System.out.println("You get it for free!");
                property.owner = player;
            } else {
                System.out.println("Someone owns it, you have to pay the owner.");
                property.payRent(player);
            }
        }
        player.position = newPosition;
    }

    private void moveOneSpaceOrExtraCard(Player player) {
        int choice = (int) (Math.random() * 2);

        if (choice == 0) {
            System.out.println("Move one space forward.");
            player.position = (player.position + 1) % board.spaces.size();
            board.getSpace(player.position).performAction(player);
        } else {
            System.out.println("Take an extra chance card.");
            drawChanceCard(player);
        }
    }

    private void goToJail(Player player) {
        System.out.println("Go to Jail!");
        player.position = board.spaces.indexOf(board.spaces.stream().filter(space -> space instanceof JailSpace).findFirst().orElse(null));
        board.getSpace(player.position).performAction(player);
    }

    private void breakOutOfJail(Player player) {
        System.out.println("Break out of Jail!");
    }

    private void getMoneyForHouses(Player player) {
        System.out.println("Your houses have been making that mulla, get $10 free!");
        player.money += 10;
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
    }
}

class GoToJailSpace extends BoardSpace {
    private MonopolyBoard board;

    GoToJailSpace(String name) {
        super(name);
    }

    @Override
    void performAction(Player player) {
        super.performAction(player);
        System.out.println(player.name + " goes to jail!");
        player.position = board.spaces.indexOf(board.spaces.stream().filter(space -> space instanceof JailSpace).findFirst().orElse(null));
        board.getSpace(player.position).performAction(player);
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

public class MonopolyGame {
    private List<Player> players;
    private MonopolyBoard board;

    public MonopolyGame() {
        players = new ArrayList<>();
        // Initialize the Monopoly board
        board = new MonopolyBoard(
                List.of(
                        new StartSpace("Start"),
                        new PropertySpace("CRACKDEN", 1, 2),
                        new PropertySpace("GUNSTORE", 1, 2),
                        new ChanceSpace("Chance", board),
                        new PropertySpace("HENNY BULLOVARD", 1, 2),
                        new PropertySpace("REDLIGHT DISTRICT", 1, 2),
                        new JailSpace("Jail/VISIT JAIL"),
                        new PropertySpace("CORNER STORE", 2, 4),
                        new PropertySpace("BRITNEYS PLACE", 2, 4),
                        new ChanceSpace("Chance", board),
                        new PropertySpace("GRAVEYARD", 2, 4),
                        new PropertySpace("DICE CORNER", 2, 4),
                        new FreeParkingSpace("Free Parking"),
                        new PropertySpace("GANGSTER GEAR", 3, 6),
                        new PropertySpace("THE FUN PALACE", 3, 6),
                        new ChanceSpace("Chance", board),
                        new PropertySpace("Univisity of New York", 3, 6),
                        new PropertySpace("JOHNNY THE DEALERS SPOT", 3, 6),
                        new GoToJailSpace("Go to Jail"),
                        new PropertySpace("BOOZE STORE", 4, 8),
                        new PropertySpace("DRIVE BY ALLEY", 4, 8),
                        new ChanceSpace("Chance", board),
                        new PropertySpace("POST OFFICE", 5, 10),
                        new PropertySpace("COMPTON STATION", 5, 10)
                )
        );

        for (BoardSpace space : board.spaces) {
            if (space instanceof ChanceSpace) {
                ((ChanceSpace) space).setBoard(board);
            }
        }

    }

    public void initializeGame() {
        // Add players
        players.add(new Player("Pimp", 20));
        players.add(new Player("Hoe", 20));
        players.add(new Player("Junkie", 20));
        players.add(new Player("Officerjim", 20));
    }

    boolean hasProperties(Player player) {
        for (BoardSpace space : board.spaces) {
            if (space instanceof PropertySpace) {
                PropertySpace property = (PropertySpace) space;
                if (property.owner == player) {
                    return true;
                }
            }
        }
        return false;
    }

    void playGame() {
        initializeGame();

        // Game loop
        Scanner scanner = new Scanner(System.in);

        while (players.size() > 1) {
            for (Iterator<Player> iterator = players.iterator(); iterator.hasNext();) {
                Player currentPlayer = iterator.next();
                System.out.println("\n" + currentPlayer.name + "'s turn");

                // Display available options
                System.out.println("1. Roll Dice");
                System.out.println("2. End Turn");

                int choice;

                try {
                    choice = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number");
                    scanner.next();
                    continue;
                }

                switch (choice) {
                    case 1:
                        // Simulate dice roll
                        int diceRoll = (int) (Math.random() * 6) + 1;
                        System.out.println(currentPlayer.name + " rolled a " + diceRoll);

                        // Calculate the new position on the board
                        int currentPosition = currentPlayer.position;
                        int newPosition = (currentPosition + diceRoll) % board.spaces.size();

                        // Perform the action on the new board space
                        board.getSpace(newPosition).performAction(currentPlayer);
                        currentPlayer.position = newPosition; // Update player's position1

                        // Check if the player is out of the game
                        if (currentPlayer.money <= 0) {
                            System.out.println(currentPlayer.name + " is out of the game!");
                            iterator.remove(); // Remove the player from the game
                        }
                        break;

                    case 2:
                        // End turn
                        break;

                    default:
                        System.out.println("Invalid choice");
                }
            }
        }

        // Declare the winner
        if (!players.isEmpty()) {
            Player winner = players.get(0);
            System.out.println(winner.name + " wins the game!");
        } else {
            System.out.println("No winner. The game ended in a draw.");
        }
        scanner.close();
    }

    public static void main(String[] args) {
        MonopolyGame game = new MonopolyGame();
        game.playGame();
    }
}
