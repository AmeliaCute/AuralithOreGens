package cute.ame.auralithoregens;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    static final ModConfigSpec SPEC;

    public static ModConfigSpec.ConfigValue<Integer> MIN_VEINS;
    public static ModConfigSpec.ConfigValue<Integer> MAX_VEINS;

    public static ModConfigSpec.ConfigValue<Integer> MIN_VEIN_SIZE;
    public static ModConfigSpec.ConfigValue<Integer> MAX_VEIN_SIZE;

    public static ModConfigSpec.ConfigValue<Integer> MIN_RANGE;
    public static ModConfigSpec.ConfigValue<Integer> MAX_RANGE;

    static
    {
        BUILDER.push("Server settings");

        MIN_RANGE = BUILDER.defineInRange("minRange", -62, -64, 0);
        MAX_RANGE = BUILDER.defineInRange("maxRange", 64, 0, 120);

        MIN_VEINS = BUILDER.define("minVeins", 2);
        MAX_VEINS = BUILDER.define("maxVeins", 3);

        MIN_VEIN_SIZE = BUILDER.define("minVeinSize", 8);
        MAX_VEIN_SIZE = BUILDER.define("maxVeinSize", 16);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
