import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;


/**
 * AwarePixel is an extension of Pixel where individual pixel
 * is more aware of the composition of neighboring pixels
 */
public class AwarePixel extends Pixel {
    // neighborhood: ratio of (land - sea) neighboring pixels
    // e.g.; only sea (-8) -> shared (0) -> only land (8)
    private int neighborhood;
    // landNeighborhood: ratio of (grass - sand) neighboring pixels
    // e.g.; only sand (-8) -> shared (0) -> only grass (8)
    private int landNeighborhood;
    // highLandNeighborhood: ratio of (rock - grass) neighboring pixels
    // e.g.l only grass (-8) -> shared (0) -> only rock (8)
    private int highLandNeighborhood;

    /**
     * Constructing individual AwarePixel
     * @param x x coordinate of pixel
     * @param y y coordinate of pixel
     * @param parentPanel panel to add pixel to
     */
    public AwarePixel(int x, int y, Map parentMap, JPanel parentPanel) {
        super(x, y, parentMap, parentPanel);
        // assigning pixel neighborhood
        updateNeighborhood();
    }

    /**
     * Gets set surrounding, neighbor pixels
     * @return ArrayList of neighbor pixels
     */
    private ArrayList<AwarePixel> getNeighbors() {
        // defining neighbors ArrayList
        ArrayList<AwarePixel> neighbors = new ArrayList<>();
        int neighborX; int neighborY;

        // looping through coordinates of all possible neighbor pixels
        int distanceToStart = -1 * Map.neighborRange; int distanceToEnd = 2 * Map.neighborRange;
        for (int heightLevel = distanceToStart; heightLevel < distanceToEnd; heightLevel++) {
            for (int widthLevel = distanceToStart; widthLevel < distanceToEnd; widthLevel++) {
                neighborX = getX() + heightLevel; neighborY = getY() + widthLevel;

                // considering the neighbor if its coordinates are within Map panel,
                // it is not equal to current pixel and already set
                if (Map.isInsidePanel(neighborX, neighborY) && !(neighborX == getX() && neighborY == getY())
                        && getParentMap().pixels[neighborX][neighborY] != null)
                    neighbors.add(getParentMap().pixels[neighborX][neighborY]);
            }
        } return neighbors;
    }

    /**
     * update neighborhood of the pixel based on current neighbors
     */
    public void updateNeighborhood() {
        ArrayList<AwarePixel> neighbors = getNeighbors();
        setNeighborhood(0); // starting default neighborhood
        setLandNeighborhood(0);
        setHighLandNeighborhood(0);

        // water neighbor decreases neighborhood, land neighbor increases neighborhood
        for (AwarePixel neighbor : neighbors) {
            this.neighborhood += neighbor.getColor().equals(Map.seaColor) ? -1 : 1;

            if (!neighbor.getColor().equals(Map.seaColor)) {
                this.landNeighborhood += neighbor.getColor().equals(Map.sandColor) ? -1 : 1;

                if (!neighbor.getColor().equals(Map.sandColor)) {
                    this.highLandNeighborhood += neighbor.getColor().equals(Map.grassColor) ? -1 : 1;
                }
            }
        }
    }

    /**
     * @return true if pixel does not have same color neighbors, false otherwise
     */
    public boolean isAlone() {
        ArrayList<AwarePixel> neighbors = getNeighbors();

        for (AwarePixel neighbor : neighbors) {
            if (neighbor.getColor().equals(this.getColor()))
                return false;
        } return true;
    }

    /**
     * @return true if pixel has sea neighbors, false otherwise
     */
    public boolean isCoast() {
        ArrayList<AwarePixel> neighbors = getNeighbors();

        for (AwarePixel neighbor : neighbors) {
            if (neighbor.getColor().equals(Map.seaColor) && !neighbor.isAlone())
                return true;
        } return false;
    }

    /**
     * @return biased random boolean on whether the pixel can be considered a shore
     */
    public boolean isShore() {
        ArrayList<AwarePixel> neighbors = getNeighbors();
        if (!this.getColor().equals(Map.seaColor)) return false;

        for (AwarePixel neighbor : neighbors) {
            if (neighbor.getColor().equals(Map.sandColor)) {
                return new Bias().biasedChoice(Map.shoreBias * 10);
            }
        }
        for (AwarePixel neighbor : neighbors) {
            if (neighbor.getColor().equals(Map.shoreColor)) {
                return !new Bias().biasedChoice(Map.shoreBias * 10);
            }
        }
        return false;
    }

    /**
     * @return random neighboring pixel's color
     */
    public Color getNeighborsColor() {
        ArrayList<AwarePixel> neighbors = getNeighbors();
        AwarePixel randNeighbor = neighbors.get(new Random().nextInt(neighbors.size() - 1));
        return randNeighbor.getColor();
    }


    /**
     * @return neighborhood state of the pixel
     */
    public int getNeighborhood() { return neighborhood; }
    /**
     * @return landNeighborhood state of the pixel
     */
    public int getLandNeighborhood() { return landNeighborhood; }
    /**
     * @return highLandNeighborhood state of the pixel
     */
    public int getHighLandNeighborhood() { return highLandNeighborhood; }

    /**
     * setter method for neighborhood
     * @param neighborhood int to set neighborhood as
     */
    public void setNeighborhood(int neighborhood) { this.neighborhood = neighborhood; }
    /**
     * setter method for neighborhood
     * @param landNeighborhood int to set landNeighborhood as
     */
    public void setLandNeighborhood(int landNeighborhood) { this.landNeighborhood = landNeighborhood; }

    /**
     * setter method for neighborhood
     * @param highLandNeighborhood int to set highLandNeighborhood as
     */
    public void setHighLandNeighborhood(int highLandNeighborhood) { this.highLandNeighborhood = highLandNeighborhood; }
}
