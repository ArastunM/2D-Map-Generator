import javax.swing.*;
import java.awt.*;


/**
 * Pixel represents individual pixels in the generated Map
 */
public class Pixel {
    // parentMap: reference to the Map pixel is being mapped to
    private Map parentMap;
    // self: reference to the pixel represented on MapApp panel
    private JLabel self;
    // color: color of the pixel
    private Color color;
    // x: height, x coordinate of the pixel on Map
    private int x;
    // y: width, y coordinate of the pixel on the Map
    private int y;

    /**
     * Constructing the individual Pixel
     * @param x x coordinate of pixel
     * @param y y coordinate of pixel
     * @param parentPanel panel to add pixel to
     */
    public Pixel(int x, int y, Map parentMap, JPanel parentPanel) {
        // assigning parent Map
        setParentMap(parentMap);
        // initiating and assigning self
        setSelf(new JLabel(), parentPanel);
        // assigning the pixel color
        setColor(Map.getRandomColor(Map.seaColor, Map.sandColor));
        // assigning pixel position
        setX(x); setY(y);
    }

    /**
     * @return parent Map of the pixel
     */
    public Map getParentMap() { return parentMap; }
    /**
     * @return private self instance of the pixel
     */
    public JLabel getSelf() { return self; }
    /**
     * @return color of the pixel
     */
    public Color getColor() { return color; }
    /**
     * @return x coordinate of the pixel
     */
    public int getX() { return x; }
    /**
     * @return y coordinate of the pixel
     */
    public int getY() { return y; }

    /**
     * setter method for parentMap
     * @param parentMap Map to set as parentMap
     */
    public void setParentMap(Map parentMap) { this.parentMap = parentMap; }
    /**
     * setter method for self
     * @param self JLabel to set self as
     */
    public void setSelf(JLabel self) {
        self.setOpaque(true);
        this.self = self;
    }
    /**
     * setter method for self
     * @param self JLabel to set self as
     * @param parentPanel panel to add self to
     */
    public void setSelf(JLabel self, JPanel parentPanel) {
        setSelf(self);
        parentPanel.add(self);
    }
    /**
     * setter method for color
     * @param color Color to set color as
     */
    public void setColor(Color color) {
        this.color = color;
        this.self.setBackground(color);
    }
    /**
     * setter method for x
     * @param x int to set x as
     */
    public void setX(int x) { this.x = x; }
    /**
     * setter method for y
     * @param y int to set y as
     */
    public void setY(int y) { this.y = y; }
}
