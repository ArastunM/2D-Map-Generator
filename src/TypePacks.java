/**
 * TypePacks contains custom Map type packs
 */
public class TypePacks {
    public static Integer[] islandPack = new Integer[]
            { MapApp.resolution / 15, MapApp.resolution / 15, 3, 3, 1, 1, 3, 7, 1, 5 };
    public static Integer[] peninsulaPack = new Integer[]
            { (int) (MapApp.resolution * 0.8), (int) (MapApp.resolution * 0.8), 3, 1, 1, 5, 1, 3, 1, 15 };
    public static Integer[] lakePack = new Integer[]
            { MapApp.resolution / 10, MapApp.resolution / 10, 3, 1, 3, 1, 3, 1, 2, 5 };

    /**
     * Applies the given pack to Map settings
     * @param pack pack type to apply
     */
    public static void applyPack(Integer[] pack) {
        Map.terrainSmooth = pack[0];
        Map.grassSmooth = pack[1];
        Map.rockSmooth = pack[2];

        Map.seaBias = pack[3];
        Map.landBias = pack[4];
        Map.sandBias = pack[5];
        Map.plainsBias = pack[6];
        Map.grassBias = pack[7];
        Map.highLandBias = pack[8];
        Map.shoreBias = pack[9];
    }
}
