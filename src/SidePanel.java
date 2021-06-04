import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;


/**
 * SidePanel of MapApp used to manage user interactions with GUI
 */
public class SidePanel implements ActionListener {
    // panel: main panel of the SidePanel
    public JPanel panel = new JPanel();

    // typeList: contains button names for types of the map
    private final String[] typeList = new String[] {"Islands", "Peninsula", "Lakes"};
    // resolutionList: contains button names for resolution of the map
    private final String[] resolutionList = new String[] {"100x100", "150x150", "200x200"};

    // type: dropdown button used to select type of the map
    private final JComboBox<String> typeBtn = new JComboBox<>(typeList);
    // resolution: dropdown button used to select resolution of the map
    private final JComboBox<String> resolutionBtn = new JComboBox<>(resolutionList);

    // generateBtn: button used to generate a new map with selected settings
    private final JButton generateBtn = new JButton("GENERATE");
    // saveBtn: button used to save the generated map as an image
    private final JButton saveBtn = new JButton("SAVE");

    // COLUMN_SIZE: column size of the SidePanel
    public static final int COLUMN_SIZE = 4;

    /**
     * Constructs the SidePanel, requested by MapApp
     */
    public SidePanel() {
        Dimension panelDimension = new Dimension
                ((int) (MapApp.windowSize * 0.05), (int) (MapApp.windowSize * 0.05));
        panel.setPreferredSize(panelDimension);
        panel.setLayout(new GridLayout(1, COLUMN_SIZE));

        typeBtn.addActionListener(this);
        resolutionBtn.addActionListener(this);
        generateBtn.addActionListener(this);
        saveBtn.addActionListener(this);

        panel.add(typeBtn, Component.CENTER_ALIGNMENT);
        panel.add(resolutionBtn, Component.CENTER_ALIGNMENT);
        panel.add(generateBtn, Component.CENTER_ALIGNMENT);
        panel.add(saveBtn, Component.CENTER_ALIGNMENT);
    }

    /**
     * Handles Map type selection
     * @param selectedIndex index of the current selection
     */
    private static void typeSelection(int selectedIndex) {
        switch (selectedIndex) {
            case 0 -> TypePacks.applyPack(TypePacks.islandPack);
            case 1 -> TypePacks.applyPack(TypePacks.peninsulaPack);
            case 2 -> TypePacks.applyPack(TypePacks.lakePack);
        }
    }

    /**
     * Handles Map resolution selection
     * @param selectedIndex index of the selected resolution
     */
    private static void resolutionSelection(int selectedIndex) {
        switch (selectedIndex) {
            case 0 -> MapApp.resolution = 100;
            case 1 -> MapApp.resolution = 150;
            case 2 -> MapApp.resolution = 200;
        }
    }

    /**
     * Handles button selection
     * @param buttonText text of button pressed
     */
    private static void buttonSelection(String buttonText) {
        switch (buttonText) {
            case "GENERATE" -> MapApp.generateNewMap();
            case "SAVE" -> {
                String pathName = JOptionPane.showInputDialog(null,
                        "Enter custom path name", null);
                if (MapApp.panel.getSize().width == 0 || pathName == null) return;
                saveScreenShot(pathName);
            }
        }
    }

    /**
     * Saves the screen shot of the main panel
     * @param pathName file path to store screen shot at
     */
    private static void saveScreenShot(String pathName) {
        BufferedImage bufImage = new BufferedImage(MapApp.panel.getSize().width,
                MapApp.panel.getSize().height,BufferedImage.TYPE_INT_RGB);
        MapApp.panel.paint(bufImage.createGraphics());
        File imageFile = new File(pathName + ".png");

        try { ImageIO.write(bufImage, "png", imageFile); }
        catch (Exception ignored) {}
    }

    /**
     * Handles GUI interactions
     * @param e performed action / event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton)
            buttonSelection(((JButton) e.getSource()).getText());
        else if (e.getSource() == typeBtn && typeBtn.getSelectedItem() instanceof String)
            typeSelection(typeBtn.getSelectedIndex());
        else if (e.getSource() == resolutionBtn && resolutionBtn.getSelectedItem() instanceof String)
            resolutionSelection(resolutionBtn.getSelectedIndex());
    }
}
