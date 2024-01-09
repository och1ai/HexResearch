package name.dashkal.minecraft.hexresearch.interop;

/**
 * Utility class for holding an instance to each mod interop object.
 */
public class Interop {
    private static final HexGloopInterOp hexgloop = new HexGloopInterOp();

    /** Call to force the static initializer. */
    public static void init() {
        // Forces static initializer
    }

    /** Returns a handle to the HexGloop interop object. */
    public static HexGloopInterOp getHexGloop() {
        return hexgloop;
    }
}
