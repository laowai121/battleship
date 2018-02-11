package boyi.battleship.server.game;

public enum GameState {
    AWAITING_A,
    AWAITING_B,
    AWAITING_A_SHIPS,
    AWAITING_B_SHIPS,
    A_WON,
    B_WON;

    public boolean isFinished() {
        return this == A_WON || this == B_WON;
    }

    public boolean isAwaitingPlayers() {
        return this == AWAITING_A || this == AWAITING_B;
    }
}
