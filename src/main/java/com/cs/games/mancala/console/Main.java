package com.cs.games.mancala.console;

import com.cs.games.mancala.model.Game;
import com.cs.games.mancala.model.Move;
import com.cs.games.mancala.model.Player;
import com.cs.games.mancala.player.HumanPlayer;
import com.cs.games.mancala.player.MoveSupplier;
import com.cs.games.mancala.player.ai.ComputerPlayer;
import com.cs.games.mancala.player.ai.RandomPlayer;
import com.cs.games.mancala.player.human.GameInput;

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
        while(!game.getBoard().isGameOver()) {
            // TODO dump board

            // ask for next move
            MoveSupplier player = game.getPlayer();
            Move move = player.selectFrom(game.getBoard());
            if (move == null) {
                System.out.println("Undo!");
                game.undo();
            }
            else {
                System.out.println("Move: "+move.getCup());
                game.doMove(move);
            }
        }
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
