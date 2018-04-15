package boyi.battleship.core.battlefield;

public class BattleFieldCoordinate {
    private int row;
    private int col;

    public BattleFieldCoordinate(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public boolean isValid() {
        return row >= 0 && row < 10 && col >= 0 && col < 10;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public boolean equals(Object obj) {
        boolean equals = false;

        if (obj instanceof BattleFieldCoordinate) {
            BattleFieldCoordinate other = (BattleFieldCoordinate) obj;
            equals = getRow() == other.getRow() && getCol() == other.getCol();
        }

        return equals;
    }

    @Override
    public String toString() {
        return "[" + row + ", " + col + "]";
    }
}
