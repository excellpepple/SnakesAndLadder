import java.util.Objects;

public class Player {
    private String name;
    private int position;
    private int diceValue = 0;

    public Player(String name) {
        this.setName(name);
        this.setPosition(0);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean hasPlayerWon() {
        return this.getPosition() == 100;
    }

    public int getDiceValue() {
        return this.diceValue;
    }

    public void setDiceValue(int diceValue) {
        this.diceValue = Math.max(diceValue, 0);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player player)) return false;
        return getPosition() == player.getPosition() && getDiceValue() == player.getDiceValue() && getName().equals(player.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getPosition(), getDiceValue());
    }


}
