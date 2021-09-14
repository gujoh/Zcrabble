package Model;

import java.util.ArrayList;
import java.util.List;

public class Rack {
    private List<Tile> playerTiles = new ArrayList<Tile>();

    public Tile getTile(int index) {
        return playerTiles.get(index);
    }
}
