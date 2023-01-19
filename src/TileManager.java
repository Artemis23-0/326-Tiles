import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.awt.*;
import java.util.Random;

/**
 * Add your own comments here
 */
public class TileManager {
    private List<Tile> tiles; //DO NOT MODIFY THIS LINE

    /**
     * Default constructor
     * Initially stores no tiles
     */
    public TileManager() {
        tiles = new ArrayList<>();
    }

    /**
     * Adds given Tile to the end of the list of Tiles
     * @param rect is the Tile to add, should not be null
     */
    public void addTile(Tile rect) {
        //Error handling
        if (rect == null) {
            throw new IllegalArgumentException("Cannot add null tiles");
        }

        tiles.add(rect);
    }

    /**
     * Draws all tiles on the screen from bottom to top
     * @param g graphical pen to draw tiles
     */
    public void drawAll(Graphics g) {
        for (int i = 0; i < tiles.size(); i++) {
            tiles.get(i).draw(g);
        }
    }

    /**
     * If the (x,y) coordinates are contained by a Tile, the topmost Tile
     * should be moved to the end of the list
     * @param x X-coordinate of user click
     * @param y Y-coordinate of user click
     */
    public void raise(int x, int y) {
        Boolean isTopmost = false;
        for (int i = tiles.size() - 1; i >= 0; i--) {
            Tile currTile = tiles.get(i);
            if (containsClick(i, x, y) && !isTopmost) {
                isTopmost = true;
                tiles.add(currTile);
                tiles.remove(i);
            }
        }
    }

    /**
     * If the (x,y) coordinates are contained by a Tile, the topmost Tile
     * should be moved to the beginning of the list
     * @param x X-coordinate of user click
     * @param y Y-coordinate of user click
     */
    public void lower(int x, int y) {
        Boolean isTopmost = false;
        for (int i = tiles.size() - 1; i >= 0; i--) {
            Tile currTile = tiles.get(i);
            if (containsClick(i, x, y) && !isTopmost) {
                isTopmost = true;
                tiles.add(0, currTile);
                tiles.remove(i + 1);
            }
        }
    }

    /**
     * If the (x,y) coordinates are contained by a Tile, the topmost Tile
     * should be deleted
     * @param x X-coordinate of user click
     * @param y Y-coordinate of user click
     */
    public void delete(int x, int y) {
        Boolean isTopmost = false;
        for (int i = tiles.size() - 1; i >= 0; i--) {
            if (containsClick(i, x, y) && !isTopmost) {
                tiles.remove(i);
                isTopmost = true;
            }
        }
    }

    /**
     * If the (x,y) coordinates is contained by any Tile, the Tile(s)
     * should be deleted
     * @param x X-coordinate of user click
     * @param y Y-coordinate of user click
     */
    public void deleteAll(int x, int y) {
        for (int i = tiles.size() - 1; i >= 0; i--) {
            if (containsClick(i, x, y)) {
                tiles.remove(i);
            }
        }
    }

    /**
     * Reorders the tiles in the list randomly and moves every Tile
     * to a new (x,y) position.
     * (x,y) positions should be non-negative
     * @param width is the width of pixel area
     * @param height is the height of the pixel area
     */
    public void shuffle(int width, int height) {
        //Shuffling
        Collections.shuffle(tiles);

        //New positions
        for (int i = 0; i < tiles.size(); i++) {
            Tile currTile = tiles.get(i);

            //For random coordinates
            Random rand = new Random();

            //Plus 1 for inclusive bounds
            currTile.setX(rand.nextInt(width - currTile.getWidth() + 1));
            currTile.setY(rand.nextInt(height - currTile.getHeight() + 1));
        }
    }

    //Helper Methods

    /**
     * Determines if the current tile contains the passed (x,y) values
     * @param i is the index of the current Tile
     * @param x X-coordinate of user click
     * @param y Y-coordinate of user click
     */
    public Boolean containsClick(int i, int x, int y) {
        Tile currTile = tiles.get(i);

        //Contained in the area
        Boolean isInXAxis = (currTile.getX() <= x && currTile.getX() + currTile.getWidth() >= x);
        Boolean isInYAxis = (currTile.getY() <= y && currTile.getY() + currTile.getHeight() >= y);

        if (isInXAxis && isInYAxis) {
            return true;
        }
        return false;
    }

    //*** FOR TESTING PURPOSE ONLY ****//
    //SHOULD USE THIS METHOD ONLY IN J-UNIT TEST CODE
    //DO NOT MODIFY THIS METHOD
    public List<Tile> getTiles() {
        return tiles;
    }
}