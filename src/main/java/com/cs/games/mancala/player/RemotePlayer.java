package com.cs.games.mancala.player;

import com.cs.games.mancala.model.Board;
import com.cs.games.mancala.model.Move;

public class RemotePlayer implements MoveSupplier
{

    @Override
    public Move selectFrom(Board board) {
        // FIXME listen on a socket for a remote player commands
        return null;
    }

    @Override
    public String getDisplayName() {
        return "Remote player";
    }
}
