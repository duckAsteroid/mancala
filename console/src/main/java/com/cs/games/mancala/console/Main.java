package com.cs.games.mancala.console;

import com.cs.games.mancala.model.*;
import com.cs.games.mancala.player.HumanPlayer;
import com.cs.games.mancala.player.MoveSupplier;
import com.cs.games.mancala.player.ai.ComputerPlayer;
import com.cs.games.mancala.player.ai.RandomPlayer;
import com.cs.games.mancala.player.human.GameInput;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Command line mancala game
 */
public class Main {

    public enum PlayerType {
        HUMAN, RANDOM, COMPUTER
    }

    public static void main(String[] args) {
        GameInput input = new GameInput();

        System.out.println("Player 1: Please select a player type?");
        PlayerType p1Type = getType(input);
        MoveSupplier p1Supplier = moveSupplier(p1Type, input);
        String p1Name;
        if (p1Type == PlayerType.HUMAN) {
            System.out.println("Player 1: Enter name?");
            p1Name = input.getCommand();
        } else {
            p1Name = p1Supplier.getDisplayName();
        }


        System.out.println("Player 2: Please select a player type?");
        PlayerType p2Type = getType(input);
        MoveSupplier p2Supplier = moveSupplier(p2Type, input);
        String p2Name;
        if (p2Type == PlayerType.HUMAN) {
            System.out.println("Player 2: Enter name?");
            p2Name = input.getCommand();
        } else {
            p2Name = p2Supplier.getDisplayName();
        }

        Game game = new Game(p1Supplier, p2Supplier);
        game.run();

        System.out.println("Final scores:");
        System.out.println("\t P1: "+game.getBoard().getScore(Player.ONE));
        System.out.println("\t P2: "+game.getBoard().getScore(Player.TWO));

        Player winner = game.getBoard().getLeader();
        if (winner == null) {
            System.out.println("The game was a tie!");
        }
        else {
            String winnerName = winner.number == 0 ? p1Name : p2Name;
            System.out.println(winnerName +" won by "+game.getBoard().getLead(winner));
        }
        System.out.println("Thanks for playing!");
    }

    public static PlayerType getType(GameInput input) {
        PlayerType[] playerTypes = PlayerType.values();
        for(int i=0; i < playerTypes.length; i++) {
            System.out.println("\t["+i+"] "+playerTypes[i].name());
        }
        int selected = input.getInteger(0, playerTypes.length - 1);
        return playerTypes[selected];
    }

    public static void render(Board board) {
        System.out.println("--------------Player one----------------");
        System.out.println("|    |  0 |  1 |  2 |  3 |  4 |  5 |    |");
        System.out.println("|    |-----------------------------|    |");
        renderCupRow(board, Player.ONE);
        System.out.println("|    |-----------------------------|    |");
        renderCupRow(board, Player.TWO);
        System.out.println("|    |-----------------------------|    |");
        System.out.println("|    |  5 |  4 |  3 |  2 |  1 |  0 |    |");
        System.out.println("--------------Player two----------------");
    }

    private static void renderCupRow(Board board, Player p) {
        Iterable<Cup> cups;
        if (p == Player.ONE) {
            System.out.print  ("|    ");
            cups = Cup.playerCups(p);
        }
        else {
            cups = new Iterable<Cup>() {
                @Override
                public Iterator<Cup> iterator() {
                    return new LinkedList<Cup>(StreamSupport.stream(Cup.playerCups(p).spliterator(), false)
                            .collect(Collectors.toList())).descendingIterator();
                }
            };
        }

        for(Cup c : cups)
        {
            System.out.print("|"+renderCup(board, c));
        }
        if (p == Player.TWO) {
            System.out.println("|    |");
        }
        else {
            System.out.println("|");
        }
    }

    private static String renderCup(Board board, Cup c){
        int cupCount = board.getBeadCount(c);
        String value = Integer.toString(cupCount);
        if (value.length() < 2) {
            value = " " + value;
        }
        return " "+value+" ";
    }

    public static MoveSupplier moveSupplier(PlayerType type, GameInput input) {
        switch (type) {
            default:
            case HUMAN:
                return new HumanPlayer(input);
            case RANDOM:
                return new RandomPlayer();
            case COMPUTER:
                return new ComputerPlayer();
        }
    }

}
