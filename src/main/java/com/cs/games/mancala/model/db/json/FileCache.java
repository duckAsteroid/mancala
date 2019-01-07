package com.cs.games.mancala.model.db.json;

import com.cs.games.mancala.model.*;

import java.io.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class FileCache implements BoardCache {
    private final File directory;

    public FileCache(File directory) {
        this.directory = directory;
    }

    @Override
    public boolean contains(List<Move> moves) {
        File f = new File(directory, format(moves) + ".board");
        return f.exists();
    }

    @Override
    public void put(List<Move> moves, Board board) {
        final String movesString = format(moves);
        final File boardFile = new File(directory, movesString + ".board");
        try (PrintStream printStream = new PrintStream(new FileOutputStream(boardFile))) {
            writeTo(printStream, board);
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void writeTo(PrintStream printStream, Board board) {
        printStream.println(board.getNextPlayer().id);
        printStream.println(board.getScore(Player.ONE));
        printStream.println(board.getScore(Player.TWO));
        printStream.println(cupCounts(board));
        printStream.println(format(board.nextMoves()));
    }

    static String cupCounts(Board board) {
        StringBuilder sb = new StringBuilder();
        Iterator<Cup> iter = Cup.iterator();
        while(iter.hasNext()) {
            Cup cup = iter.next();
            sb.append(board.getBeadCount(cup));
            if (iter.hasNext()) {
                sb.append(',');
            }
        }
        return sb.toString();
    }

    static String format(List<Move> moves) {
        StringBuilder sb = new StringBuilder();
        Iterator<Move> iter = moves.iterator();
        while (iter.hasNext()) {
            Move move = iter.next();
            sb.append(move.getCup().toString());
            if (iter.hasNext()) {
                sb.append(':');
            }
        }
        return sb.toString();
    }

    @Override
    public Board get(List<Move> moves) {
        final File boardFile = new File(directory, format(moves) + ".board");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(boardFile))))
        {
            return readFrom(reader);
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    static Board readFrom(BufferedReader reader) throws IOException {
        char playerId = reader.readLine().charAt(0);
        int[] scores = new int[]{
                Integer.parseInt(reader.readLine()),
                Integer.parseInt(reader.readLine()),
        };
        int[] beads = parseCupCounts(reader.readLine());
        List<Cup> nextMoves = parseNextMoves(reader.readLine());
        return new Board(beads, Player.parse(playerId), scores, nextMoves);
    }

    static List<Cup> parseNextMoves(String line) {
        String[] split = line.split(":");
        return Arrays.asList(split).stream()
                .map(s -> Cup.parse(s))
                .collect(Collectors.toList());
    }

    static int[] parseCupCounts(String line) {
        String[] split = line.split(",");
        int[] beads = new int[split.length];
        for(int i=0; i < 14; i++) {
            beads[i] = Integer.parseInt(split[i]);
        }
        return beads;
    }
}
