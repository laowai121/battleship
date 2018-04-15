package boyi.battleship.core.gamestate;

public enum GameState {
    AWAITING_A,         // none of the players joined the game yet
    AWAITING_B,         // awaiting the second player
    AWAITING_SHIPS,     // neither A nor B submitted ships
    AWAITING_A_SHIPS,   // B has submitted ships
    AWAITING_B_SHIPS,   // A has submitted ships
    AWAITING_A_ATTACK,  // it's A's turn now
    AWAITING_B_ATTACK,  // it's B's turn now
    A_WON,              // end of game. A won
    B_WON,              // end of game. B won

    UNKNOWN;            // something went wrong

    public boolean isFinished() {
        return this == A_WON || this == B_WON;
    }

    public boolean isAwaitingPlayers() {
        return this == AWAITING_A || this == AWAITING_B;
    }
}
