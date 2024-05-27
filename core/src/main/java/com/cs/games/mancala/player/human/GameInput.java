package com.cs.games.mancala.player.human;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Reads commands from system input
 */
public class GameInput {

    private BufferedReader reader;

    public GameInput() {
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * The next command inputed by user
     *
     * @return
     */
    public String getCommand() {
        try {
            return reader.readLine();
        } catch (IOException ioe) {
            return ioe.getMessage();
        }
    }

    public int getInteger(int min, int max) {
        int num = min - 1;
        do {
            System.out.println("Input number ["+min+" - "+max+"]:");
            try {
                num = Integer.parseInt(getCommand());
            }
            catch(NumberFormatException e) {
                System.out.println("Must be a number");
            }
        } while(num < min && num > max);
        return num;
    }
}
