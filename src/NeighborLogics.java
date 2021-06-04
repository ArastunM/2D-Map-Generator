import java.awt.*;


/**
 * NeighborLogics contains algorithms related to applying neighbor influence on individual pixels
 */
public class NeighborLogics {
    public NeighborLogics() {}

    /**
     * Applies the influence of neighboring pixels on pixel's color
     */
    public static void applyInfluence(Map map) {
        for (int x = 0; x < MapApp.resolution; x++) {
            for (int y = 0; y < MapApp.resolution; y++) {
                map.pixels[x][y].updateNeighborhood();
                map.pixels[x][y].setColor(getColor(map, x, y));
            }
        }
    }

    /**
     * Applies the influence of neighboring land pixels on pixel's color
     */
    public static void applyLandInfluence(Map map) {
        Color newColor;
        for (int x = 0; x < MapApp.resolution; x++) {
            for (int y = 0; y < MapApp.resolution; y++) {
                if (!map.pixels[x][y].getColor().equals(Map.seaColor)) {
                    map.pixels[x][y].updateNeighborhood();
                    newColor = map.pixels[x][y].isCoast() ? Map.sandColor : getLandColor(map, x, y);
                    map.pixels[x][y].setColor(newColor);
                }
            }
        }
    }

    /**
     * Applies the influence of neighboring high land pixels on pixel's color
     */
    public static void applyHighLandInfluence(Map map) {
        for (int x = 0; x < MapApp.resolution; x++) {
            for (int y = 0; y < MapApp.resolution; y++) {
                if (!(map.pixels[x][y].getColor().equals(Map.seaColor)
                        || map.pixels[x][y].getColor().equals(Map.sandColor))) {
                    map.pixels[x][y].updateNeighborhood();
                    map.pixels[x][y].setColor(getHighLandColor(map, x, y));
                }
            }
        }
    }

    /**
     * Calls and returns getColor with necessary parameters
     * for general neighborhood influenced color
     */
    private static Color getColor(Map map, int x, int y) {
        int bias = map.pixels[x][y].getNeighborhood() < 0 ? Map.landBias : Map.seaBias;
        return getColor(map.pixels[x][y].getNeighborhood(), Map.seaColor, Map.sandColor, bias);
    }
    /**
     * Calls and returns getColor with necessary parameters
     * for land only neighborhood influenced color
     */
    private static Color getLandColor(Map map, int x, int y) {
        int landBias = map.pixels[x][y].getLandNeighborhood() < 0 ? Map.plainsBias * 10 : Map.sandBias;
        return getColor(map.pixels[x][y].getLandNeighborhood(),
                Map.sandColor, Map.grassColor, landBias);
    }
    /**
     * Calls and returns getColor with necessary parameters
     * for high land only neighborhood influenced color
     */
    private static Color getHighLandColor(Map map, int x, int y) {
        int higherLandBias = map.pixels[x][y].getHighLandNeighborhood() < 0 ? Map.highLandBias : Map.grassBias;
        return getColor(map.pixels[x][y].getHighLandNeighborhood(),
                Map.grassColor, Map.rockColor, higherLandBias);
    }

    /**
     * @return color of the Pixel based on neighbor pixels
     */
    private static Color getColor(int neighborhood, Color color1, Color color2, int bias) {
        if (neighborhood == 0) return Map.getRandomColor(color1, color2);

        // biasIntensity: neighborhood, its influence and additional custom bias
        int biasIntensity = neighborhood * Map.neighborInfluence / bias;
        Color preference = neighborhood < 0 ? color1 : color2;
        Color alternative = neighborhood < 0 ? color2 : color1;

        return (Color) new Bias().biasedChoice(preference, alternative, biasIntensity);
    }
}
