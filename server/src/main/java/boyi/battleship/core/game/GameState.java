package boyi.battleship.core.game;

public enum GameState {
    AWAITING_A,
    AWAITING_B,
    AWAITING_SHIPS, // neither A nor B submitted ships
    AWAITING_A_SHIPS, // B has submitted ships
    AWAITING_B_SHIPS, // A has submitted ships
    AWAITING_A_ATTACK,
    AWAITING_B_ATTACK,
    A_WON,
    B_WON;

    public boolean isFinished() {
        return this == A_WON || this == B_WON;
    }

    public boolean isAwaitingPlayers() {
        return this == AWAITING_A || this == AWAITING_B;
    }
}
