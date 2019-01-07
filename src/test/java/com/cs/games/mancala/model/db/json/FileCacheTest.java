package com.cs.games.mancala.model.db.json;

import com.cs.games.mancala.model.Board;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class FileCacheTest {

    private static final String EXPECTED_COUNTS = "4,4,4,4,4,4,0,4,4,4,4,4,4,0";
    private static final String EXPECTED_MOVES = "A0:A1:A2:A3:A4:A5";

    private static final String EXPECTED_BOARD = "A\n24\n24\n" + EXPECTED_COUNTS +"\n" + EXPECTED_MOVES + "\n";

    private Board testBoard = Board.initialBoard();

    @Test
    public void testCupCounts() {
        assertEquals(EXPECTED_COUNTS, FileCache.cupCounts(testBoard));
        assertArrayEquals(testBoard.cloneBeads(), FileCache.parseCupCounts(EXPECTED_COUNTS));
    }

    @Test
    public void testMoveFormat() {
        assertEquals(EXPECTED_MOVES, FileCache.format(testBoard.nextMoves()));
        assertEquals(testBoard.nextMoves().stream().map(move -> move.getCup()).collect(Collectors.toList()), FileCache.parseNextMoves(EXPECTED_MOVES));
    }

    @Test
    public void testWrite() throws UnsupportedEncodingException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream testStream = new PrintStream(out, true, StandardCharsets.UTF_8.name());
        FileCache.writeTo(testStream, testBoard);
        assertEquals(EXPECTED_BOARD, new String(out.toByteArray(), StandardCharsets.UTF_8).replace("\r", ""));
    }

    @Test
    public void testRead() throws IOException {
        BufferedReader reader = new BufferedReader(new StringReader(EXPECTED_BOARD));
        Board board = FileCache.readFrom(reader);
        assertNotNull(board);
        assertEquals(testBoard, board);
    }

}