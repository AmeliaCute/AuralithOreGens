package cute.ame.auralithoregens.Gen;

import cute.ame.auralithoregens.Config;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class OreDictionary
{
    private static List<OreType> availableOres = null;

    public static void scanForOres()
    {
        availableOres = new ArrayList<>();

        for(Map.Entry<ResourceKey<Block>, Block> entry : BuiltInRegistries.BLOCK.entrySet())
        {
            ResourceLocation id = entry.getKey().location();
            if(Config.BLACKLIST.get().contains(id.toString())) continue;

            String path = id.getPath();

            if(path.contains("_ore") && !path.contains("raw") && !path.contains("deepslate_") && !path.contains("nether") && !path.contains("end"))
            {
                ResourceLocation deepTest = ResourceLocation.fromNamespaceAndPath(id.getNamespace(),"deepslate_" + id.getPath());

                Block oreDeep = BuiltInRegistries.BLOCK.get(deepTest);
                availableOres.add(new OreType(id, entry.getValue(), oreDeep != null ? oreDeep : entry.getValue()));
            }
        }
    }

    public static OreType getOre(ChunkPos pos, long seed)
    {
        long combinedSeed = pos.x + pos.z + seed;
        return availableOres.get(new Random(combinedSeed).nextInt(availableOres.size()));
    }
}
