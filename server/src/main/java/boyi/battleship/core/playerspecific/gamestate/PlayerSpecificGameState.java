package boyi.battleship.core.playerspecific.gamestate;

public enum PlayerSpecificGameState {
    // objective
    AWAITING_A,
    AWAITING_B,
    AWAITING_SHIPS,
    AWAITING_A_SHIPS,
    AWAITING_B_SHIPS,
    AWAITING_A_ATTACK,
    AWAITING_B_ATTACK,
    A_WON,
    B_WON,

    // subjective
    AWAITING_ME,
    AWAITING_OPPONENT,
    AWAITING_MY_SHIPS,
    AWAITING_OPPONENT_SHIPS,
    AWAITING_MY_ATTACK,
    AWAITING_OPPONENT_ATTACK,
    I_WON,
    I_LOST,

    UNKNOWN
}
