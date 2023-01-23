import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by: Kay Karman (754506)
 * Date: Jan. 23rd, 2023
 * Class: EGR326, Section A
 * Professor Hudnall
 *
 * Homework #1: Tiles
 *
 * This program tests all methods inside TileManager (excluding drawAll) using JUnit5
 */
public class TileManagerTest {

    /**
     * Timeout rule for all tests
     */
    @Rule
    public Timeout globalTimeout = Timeout.seconds(5);

    /**
     * Instructor provided test that ensures that an empty list is
     * initialized
     */
    @Test
    public void constructorTest() { //Example test provided by the instructor
        TileManager tileManager = new TileManager();
        List<Tile> tileList = tileManager.getTiles(); //use getTiles method to get the state of your tiles
        assertTrue("The constructor should initialize an empty list", tileList.isEmpty());
    }

    /**
     * Instructor provided test that ensures tiles are added to the end of the list
     */
    @Test
    public void addTest() { //Example test provided by instructor (uncomment below after you implement addTile method)
        TileManager tileManager = new TileManager();
        Tile tile = new Tile(100, 100, 50, 20, Color.RED);
        Tile tile2 = new Tile(35, 50, 20, 20, Color.blue);
        Tile tile3 = new Tile(45, 170, 30, 90, Color.DARK_GRAY);
        List<Tile> tileList = tileManager.getTiles();

        tileManager.addTile(tile);
        assertEquals("Tiles should be added to the end of the list", tile, tileList.get(0));
        tileManager.addTile(tile2);
        assertEquals("Tiles should be added to the end of the list", tile2, tileList.get(1));
        tileManager.addTile(tile3);
        assertEquals("Tiles should be added to the end of the list", tile3, tileList.get(2));
    }

    /**
     * Tests that no null tile may be added to a list
     */
    @Test
    public void addNotNullTest() {
        try {
            TileManager tileManager = new TileManager();
            Tile tile = null;
            tileManager.addTile(tile);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests that the bottom tile is raised to the end of the list and the rest of the
     * tiles are properly ordered
     */
    @Test
    public void raisePositionTest() {
        //Given:

        //Creating new a new list of tiles
        TileManager tileManager = new TileManager();
        List tileList = createTileList(tileManager);

        Tile tile = (Tile) tileList.get(0);
        Tile tile2 = (Tile) tileList.get(1);
        Tile tile3 = (Tile) tileList.get(2);

        //When:
        tileManager.raise(125, 110);

        //Then:

        //Assert that the right tile was moved
        assertAll("Testing movement",
                ()-> assertEquals("Clicked tile should be at the end of the list", tile, tileList.get(2)),
                ()-> assertEquals("Positions of other tiles should shift accordingly", tile2, tileList.get(0))
        );

        //Assert that size of tileList is unchanged
        assertEquals("ArrayList size should not change", 3, tileList.size());
    }

    /**
     * Tests that no tiles are raised if the click is already on the top, or no
     * tile is clicked
     */
    @Test
    public void dontRaisePositionTest() {
        //Given:

        //Creating new a new list of tiles
        TileManager tileManager = new TileManager();
        List tileList = createTileList(tileManager);

        Tile tile = (Tile) tileList.get(0);
        Tile tile2 = (Tile) tileList.get(1);
        Tile tile3 = (Tile) tileList.get(2);

        //When:
        tileManager.raise(80, 70);
        tileManager.raise(10, 10);

        //Then:

        //Assert that the right tile was moved
        assertAll("Testing that there is no movement",
                ()-> assertEquals("Clicked tile should be at the end of the list", tile3, tileList.get(2)),
                ()-> assertEquals("Clicked tile should be at the end of the list", tile2, tileList.get(1)),
                ()-> assertEquals("Positions of other tiles should shift accordingly", tile, tileList.get(0))
        );

        //Assert that size of tileList is unchanged
        assertEquals("ArrayList size should not change", 3, tileList.size());
    }

    /**
     * Tests that the topmost tile is lowered to the beginning of the list and the rest of the
     * tiles are properly ordered
     */
    @Test
    public void lowerPositionTest() {
        //Given:

        //Creating new a new list of tiles
        TileManager tileManager = new TileManager();
        List tileList = createTileList(tileManager);

        Tile tile = (Tile) tileList.get(0);
        Tile tile2 = (Tile) tileList.get(1);
        Tile tile3 = (Tile) tileList.get(2);

        //When:
       tileManager.lower(80, 70);

        //Then:

        //Assert that the right tile was moved
        assertAll("Testing movement",
                ()-> assertEquals("Clicked tile should be at the beginning of the list", tile3, tileList.get(0)),
                ()-> assertEquals("Positions of other tiles should shift accordingly", tile2, tileList.get(2))
        );

        //Assert that size of tileList is unchanged
        assertEquals("ArrayList size should not change", 3, tileList.size());
    }

    /**
     * Tests that no tiles are lowered if the click is already on the bottom, or no
     * tile is clicked
     */
    @Test
    public void dontLowerPositionTest() {
        //Given:

        //Creating new a new list of tiles
        TileManager tileManager = new TileManager();
        List tileList = createTileList(tileManager);

        Tile tile = (Tile) tileList.get(0);
        Tile tile2 = (Tile) tileList.get(1);
        Tile tile3 = (Tile) tileList.get(2);

        //When:
        tileManager.lower(120, 120);
        tileManager.lower(10, 10);

        //Then:

        //Assert that the right tile was moved
        assertAll("Testing that there is no movement",
                ()-> assertEquals("Tiles should not move", tile3, tileList.get(2)),
                ()-> assertEquals("Tiles should not move", tile2, tileList.get(1)),
                ()-> assertEquals("Tiles should not move",  tile, tileList.get(0))
        );

        //Assert that size of tileList is unchanged
        assertEquals("ArrayList size should not change", 3, tileList.size());
    }

    /**
     * Tests that the clicked tile is deleted from the list and the
     * proper order of the remaining tiles is retained
     */
    @Test
    public void deleteTest() {
        //Given:

        //Creating new a new list of tiles
        TileManager tileManager = new TileManager();
        List tileList = createTileList(tileManager);

        Tile tile = (Tile) tileList.get(0);
        Tile tile2 = (Tile) tileList.get(1);
        Tile tile3 = (Tile) tileList.get(2);

        //When:
        tileManager.delete(60, 80);

        //Then:

        //Assert that one correct item was deleted
        assertAll("Testing that there is no movement/deletion",
                ()-> assertEquals("Tiles should not move", tile2, tileList.get(1)),
                ()-> assertEquals("Tiles should not move", tile, tileList.get(0))
        );

        //Assert that size of tileList is altered
        assertEquals("ArrayList size should change", 2, tileList.size());
    }

    /**
     * Tests that no tiles are deleted from the list if the point
     * clicked is not contained by any of them
     */
    @Test
    public void dontDeleteTest() {
        //Given:

        //Creating new a new list of tiles
        TileManager tileManager = new TileManager();
        List tileList = createTileList(tileManager);

        Tile tile = (Tile) tileList.get(0);
        Tile tile2 = (Tile) tileList.get(1);
        Tile tile3 = (Tile) tileList.get(2);

        //When:
        tileManager.delete(60, 30);

        //Then:

        //Assert that nothing was deleted
        assertAll("Testing that there is no movement/deletion",
                ()-> assertEquals("Tiles should not move", tile3, tileList.get(2)),
                ()-> assertEquals("Tiles should not move", tile2, tileList.get(1)),
                ()-> assertEquals("Tiles should not move", tile, tileList.get(0))
        );

        //Assert that size of tileList is unchanged
        assertEquals("ArrayList size should not change", 3, tileList.size());
    }

    /**
     * Tests that all tiles that contain the point of a click (and none
     * that don't) are deleted from the list
     */
    @Test
    public void deleteAllTest() {
        //Given:

        //Creating new a new list of tiles
        TileManager tileManager = new TileManager();
        List tileList = createTileList(tileManager);

        Tile tile = (Tile) tileList.get(0);
        Tile tile2 = (Tile) tileList.get(1);
        Tile tile3 = (Tile) tileList.get(2);

        //When:
        tileManager.deleteAll(102, 110);

        //Then:

        //Assert that correct items were deleted
        assertEquals("Remaining tile should be in correct position", tile2, tileList.get(0));

        //Assert that size of tileList is altered
        assertEquals("ArrayList size should change", 1, tileList.size());
    }

    /**
     * Tests that the order of the list after it is shuffled is not the same
     * (or is randomized)
     */
    @Test
    public void shuffleOrderTest() {
        //Given:

        //Creating new a new list of tiles
        TileManager tileManager = new TileManager();
        List tileList = createTileList(tileManager);

        List tileListCopy = new ArrayList<>(tileList);

        //When:
        tileManager.shuffle(200,300);

        //Then:
        assertNotEquals("Lists should not be equal", tileList, tileListCopy);
    }

    /**
     * Tests that the x,y coordinate values that are generated by the shuffle method
     * are within the parameter bounds
     */
    @Test
    public void shuffleBoundsTest() {
        //Given:

        //Creating new a new list of tiles
        TileManager tileManager = new TileManager();
        Tile tile = new Tile(70, 30, 10, 25, Color.GRAY);
        tileManager.addTile(tile);

        //When:
        tileManager.shuffle(200,300);

        //Then:
        assertAll("Testing x,y bounds",
                () -> assertTrue("x value must be within bounds", tile.getX() <= 190),
                () -> assertTrue("y value must be within bounds", tile.getY() <= 275)
        );
    }

    /**
     * Tests that if a point/click is contained by a tile, the method
     * will return true
     */
    @Test
    public void containsTest() {
        //Given:

        //Creating new a new list of tiles
        TileManager tileManager = new TileManager();
        Tile tile = new Tile(70, 30, 10, 25, Color.GRAY);
        tileManager.addTile(tile);

        //Then:
        assertTrue("Should return true if point is contained", tileManager.containsClick(tile, 75, 40));
    }

    //helper

    /**
     * Generates a list of tiles to use for testing
     * @param m is the tile manager object with behaviors
     * @return a list of three tiles with known dimensions
     */
    public List<Tile> createTileList(TileManager m) {
        TileManager tileManager = m;
        Tile tile = new Tile(100, 100, 50, 20, Color.RED);
        Tile tile2 = new Tile(35, 50, 20, 20, Color.blue);
        Tile tile3 = new Tile(45, 45, 60, 90, Color.DARK_GRAY);

        List<Tile> tileList = tileManager.getTiles();

        tileManager.addTile(tile);
        tileManager.addTile(tile2);
        tileManager.addTile(tile3);

        return tileList;
    }

}