package cute.ame.auralithoregens;

import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.Arrays;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    static final ModConfigSpec SPEC;

    public static ModConfigSpec.ConfigValue<Integer> MIN_VEINS;
    public static ModConfigSpec.ConfigValue<Integer> MAX_VEINS;

    public static ModConfigSpec.ConfigValue<Integer> MIN_VEIN_SIZE;
    public static ModConfigSpec.ConfigValue<Integer> MAX_VEIN_SIZE;

    public static ModConfigSpec.ConfigValue<Integer> MIN_RANGE;
    public static ModConfigSpec.ConfigValue<Integer> MAX_RANGE;

    public static ModConfigSpec.ConfigValue<java.util.List<? extends java.lang.String>> BLACKLIST;

    static
    {
        BUILDER.push("Server settings");

        MIN_RANGE = BUILDER.defineInRange("minRange", -62, -64, 0);
        MAX_RANGE = BUILDER.defineInRange("maxRange", 64, 0, 120);

        MIN_VEINS = BUILDER.define("minVeins", 2);
        MAX_VEINS = BUILDER.define("maxVeins", 3);

        MIN_VEIN_SIZE = BUILDER.define("minVeinSize", 8);
        MAX_VEIN_SIZE = BUILDER.define("maxVeinSize", 16);

        BLACKLIST = BUILDER.defineListAllowEmpty("blackList",
                Arrays.asList(
                        "mysticalagriculture:soulium_ore",
                        "modern_industrialization:titanium_ore"
                ), () -> "", o -> (o instanceof String) && !((String)o).isBlank());

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
