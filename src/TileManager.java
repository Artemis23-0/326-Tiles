import java.util.*;
import java.awt.*;
import java.util.List;

/**
 * Created by: Kay Karman (754506)
 * Date: Jan. 23rd, 2023
 * Class: EGR326, Section A
 * Professor Hudnall
 *
 * Homework #1: Tiles
 *
 * This program develops all methods and behaviors for the Tile program, including
 * various methods of moving the tiles and deleteing them
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
     * Draws all tiles on the screen from bottom (beginning) to top (end)
     * @param g graphical pen to draw tiles
     */
    public void drawAll(Graphics g) {
        for (int i = 0; i < tiles.size(); i++) {
            tiles.get(i).draw(g);
        }
    }

    /**
     * If the (x,y) coordinates of a user-left click are contained by a Tile, the topmost Tile
     * should be moved to the beginning of the list
     * @param x X-coordinate of user click
     * @param y Y-coordinate of user click
     */
    public void raise(int x, int y) {
        ListIterator listIterator = tiles.listIterator(tiles.size());
        while (listIterator.hasPrevious()) {
            Tile currTile = (Tile) listIterator.previous();
            if (containsClick(currTile, x, y)) {
                tiles.add(currTile);
                tiles.remove(listIterator.previousIndex() + 1);
                break;
            }
        }
    }

    /**
     * If the (x,y) coordinates of a user shift-left-click are contained by a Tile,
     * the topmost Tile should be moved to the beginning of the list
     * @param x X-coordinate of user click
     * @param y Y-coordinate of user click
     */
    public void lower(int x, int y) {
        ListIterator listIterator = tiles.listIterator(tiles.size());
        while (listIterator.hasPrevious()) {
            Tile currTile = (Tile) listIterator.previous();
            if (containsClick(currTile, x, y)) {
                tiles.add(0, currTile);
                tiles.remove(listIterator.nextIndex() + 1);
                break;
            }
        }
    }

    /**
     * If the (x,y) coordinates of a user right-click are contained by a Tile,
     * the topmost Tile should be deleted
     * @param x X-coordinate of user click
     * @param y Y-coordinate of user click
     */
    public void delete(int x, int y) {
        ListIterator listIterator = tiles.listIterator(tiles.size());
        while (listIterator.hasPrevious()) {
            Tile currTile = (Tile) listIterator.previous();
            if (containsClick(currTile, x, y)) {
                listIterator.remove();
                break;
            }
        }
    }

    /**
     * If the (x,y) coordinates of a user shift-right-click is contained by any Tile,
     * the Tile(s) should be deleted
     * @param x X-coordinate of user click
     * @param y Y-coordinate of user click
     */
    public void deleteAll(int x, int y) {
        for (int i = tiles.size() - 1; i >= 0; i--) {
            if (containsClick(tiles.get(i), x, y)) {
                tiles.remove(i);
            }
        }
    }

    /**
     * Reorders the tiles in the list randomly and moves every Tile
     * to a new (x,y) position that are within the boundaries of the passed parameters.
     * (x,y) positions should be non-negative.
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
     * Helper method that determines if a user's click is contained in a Tile
     * @param t is the index of the current Tile
     * @param x X-coordinate of user click
     * @param y Y-coordinate of user click
     */
    public Boolean containsClick(Tile t, int x, int y) {
        Tile currTile = t;

        //Area bounds
        Boolean isInXAxis = (currTile.getX() <= x && currTile.getX() + currTile.getWidth() >= x);
        Boolean isInYAxis = (currTile.getY() <= y && currTile.getY() + currTile.getHeight() >= y);

        //Is contained in area
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