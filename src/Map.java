import java.awt.*;
import java.util.Random;


/**
 * Map represents generated 2D Map containing Map Parameters and all pixels in an ArrayList
 */
public class Map {
    // MAPPING COLORS
    public static Color seaColor = new Color(0, 0, 255);
    public static Color shoreColor = new Color(114,206,221);
    public static Color sandColor = new Color(232, 202, 2);
    public static Color grassColor = new Color(8, 130, 42);
    public static Color rockColor = new Color(121, 121, 121, 255);

    // PARAMETERS
    public static int terrainSmooth = MapApp.resolution / 15; // default (resolution / 15)
    public static int grassSmooth = MapApp.resolution / 15; // default (resolution / 15)
    public static int rockSmooth = 3; // default 3


    // neighborInfluence: influence of a neighbor on a pixels color
    public static int neighborInfluence = 20; // default 20
    // neighborRange: defines what is considered a neighbor
    public static int neighborRange = 1; // default 1

    // BIASES
    public static int seaBias = 3; // default 3
    public static int landBias = 1; // default 1

    public static int sandBias = 1; // default 1
    public static int plainsBias = 3; // default 3

    public static int grassBias = 7; // default 7
    public static int highLandBias = 1; // default 1

    public static int shoreBias = 5; // default 5

    // pixels: Pixels of MapApp panel
    public AwarePixel[][] pixels = new AwarePixel[MapApp.resolution][MapApp.resolution];


    /**
     * Constructs the Map
     */
    public Map() {
        initTerrain();
        for (int i = 0; i < terrainSmooth; i++) { NeighborLogics.applyInfluence(this); }

        addGrass();
        for (int i = 0; i < grassSmooth; i++) { NeighborLogics.applyLandInfluence(this); }

        addRock();
        for (int i = 0; i < rockSmooth; i++) { NeighborLogics.applyHighLandInfluence(this); }

        clearLonePixels();
        addShore();
    }

    /**
     * Constructs a random basic Terrain (sea and land)
     */
    private void initTerrain() {
        // looping through all layout places adding pixels to the panel and ArrayList
        for (int x = 0; x < MapApp.resolution; x++) {
            for (int y = 0; y < MapApp.resolution; y++) {
                pixels[x][y] = new AwarePixel(x, y, this, MapApp.panel);
            }
        }
    }

    /**
     * Adds a grass layer on existing land
     */
    private void addGrass() {
        for (int x = 0; x < MapApp.resolution; x++) {
            for (int y = 0; y < MapApp.resolution; y++) {
                if (pixels[x][y].getColor().equals(sandColor) && !pixels[x][y].isCoast()) {
                    pixels[x][y].setColor(getRandomColor(sandColor, grassColor));
                }
            }
        }
    }

    /**
     * Adds a rock layer on existing grass land
     */
    private void addRock() {
        for (int x = 0; x < MapApp.resolution; x++) {
            for (int y = 0; y < MapApp.resolution; y++) {
                if (pixels[x][y].getColor().equals(grassColor)) {
                    pixels[x][y].setColor(getRandomColor(grassColor, rockColor));
                }
            }
        }
    }

    /**
     * Adds a shore layer in coastal areas
     */
    private void addShore() {
        for (int x = 0; x < MapApp.resolution; x++) {
            for (int y = 0; y < MapApp.resolution; y++) {
                if (pixels[x][y].isShore())
                    pixels[x][y].setColor(shoreColor);
            }
        }
    }

    /**
     * Loops through Map clearing any lone pixels
     */
    private void clearLonePixels() {
        for (int x = 0; x < MapApp.resolution; x++) {
            for (int y = 0; y < MapApp.resolution; y++) {
                if (pixels[x][y].isAlone())
                    pixels[x][y].setColor(pixels[x][y].getNeighborsColor());
            }
        }
    }

    /**
     * @return randomly selected Color among available
     */
    public static Color getRandomColor(Color option1, Color option2) {
        // choosing a random color (of 2 available currently)
        return new Random().nextBoolean() ? option1 : option2;
    }

    /**
     * @param x x coordinate
     * @param y y coordinate
     * @return true if x and y are within MapApp panel, false otherwise
     */
    public static boolean isInsidePanel(int x, int y) {
        return !(x < 0 || x > MapApp.resolution - 1 || y < 0 || y > MapApp.resolution - 1);
    }
}
