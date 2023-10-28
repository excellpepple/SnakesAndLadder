import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Tiles {
   private int tileNumber;
    private int newPosition;
    private Player player;
    private final ArrayList<Player> players = new ArrayList<>();

    public Tiles(int tileNumber) {
        setTileNumber(tileNumber);
        setNewPosition(tileNumber);
    }

    public Tiles(int tileNumber, Player[] players) {
        setTileNumber(tileNumber);
        setPlayers(players);
        setNewPosition(tileNumber);
    }

    public void setPlayers(Player[] players) {
        for (Player p : players){
            if (p.getPosition() == this.getTileNumber()){
                this.players.add(p);
            }
        }
    }

    public int getTileNumber() {
        return tileNumber;
    }

    public void setTileNumber(int tileNumber) {
        this.tileNumber = tileNumber;
    }

    public int getNewPosition() {
        return newPosition;
    }

    public void setNewPosition(int newPosition) {
        this.newPosition = newPosition;
    }

    @Override
    public String toString() {
        return "\t" + tileNumber + " <" + this.players + ">";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tiles tiles)) return false;
        return getTileNumber() == tiles.getTileNumber() && getNewPosition() == tiles.getNewPosition() && player.equals(tiles.player) && players.equals(tiles.players);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTileNumber(), getNewPosition(), player, players);
    }

    
}
