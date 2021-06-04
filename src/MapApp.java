import javax.swing.*;
import java.awt.*;


/**
 * Sets basic configurations of the MapApp
 */
public class MapApp {
    // frame: main JFrame of the app
    public static JFrame frame = new JFrame();
    // panel: main JPanel of the app
    public static JPanel panel = new JPanel();

    // resolution: number of pixels in generated map
    public static int resolution = 100; // recommended 100
    // windowSize: size of the frame / window
    public static int windowSize = 800;

    /**
     * Constructor for MapApp.
     * Main frame and SidePanel are constructed with given parameters
     */
    public MapApp() {
        SidePanel sidePanel = new SidePanel();

        frame.add(sidePanel.panel, BorderLayout.NORTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Map Generator");
        frame.pack();
        frame.setVisible(true);
        frame.setSize(windowSize, windowSize);
    }

    /**
     * Generates a new Map on main panel
     */
    public static void generateNewMap() {
        frame.remove(panel);
        panel = new JPanel();
        panel.setLayout(new GridLayout(resolution, resolution));

        Map map = new Map();
        System.out.println(getCoverageOf(map, Map.seaColor));

        frame.add(panel, BorderLayout.CENTER);
        pageRefresh();
    }

    /**
     * Static method used to refresh the game panel
     */
    public static void pageRefresh() {
        panel.revalidate();
        panel.repaint();
    }

    /**
     * @return % land coverage in Map
     */
    public static int getCoverageOf(Map map, Color color) {
        double totalCount = Math.pow(MapApp.resolution, 2);
        int landPixelCount = 0;

        for (int x = 0; x < MapApp.resolution; x++) {
            for (int y = 0; y < MapApp.resolution; y++) {
                if (map.pixels[x][y].getColor().equals(color))
                    landPixelCount++;
            }
        } return (int) ((landPixelCount / totalCount) * 100);
    }

    /**
     * App launcher
     * @param args empty
     */
    public static void main(String[] args) {
        new MapApp();
    }


}
