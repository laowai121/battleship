package boyi.battleship.server.requests;

import java.util.List;

public class SubmitShipsRequest extends RequestWithPlayerToken {
    private List<String> ships;

    public SubmitShipsRequest(String playerToken) {
        setPlayerToken(playerToken);
    }

    public List<String> getShips() {
        return ships;
    }

    public void setShips(List<String> ships) {
        this.ships = ships;
    }
}
