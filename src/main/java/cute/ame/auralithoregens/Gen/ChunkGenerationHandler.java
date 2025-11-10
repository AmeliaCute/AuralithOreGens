package cute.ame.auralithoregens.Gen;

import cute.ame.auralithoregens.Config;
import cute.ame.auralithoregens.Registries.AttachementRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.level.ChunkEvent;

import java.util.*;

@EventBusSubscriber
public class ChunkGenerationHandler
{
    private record OreType(ResourceLocation name, Block stoneVariant, Block deepVariant){};
    private static List<OreType> avaibleOres = null;
    private static Random random = null;


    @SubscribeEvent
    public static void onChunkGenerate(ChunkEvent.Load event)
    {
        if(!(event.getLevel() instanceof ServerLevel serverLevel) || serverLevel.dimension() != Level.OVERWORLD) return;

        ChunkAccess chunk = event.getChunk();
        ChunkPos chunkPos = chunk.getPos();

        if(!chunk.getPersistedStatus().isOrAfter(ChunkStatus.FEATURES))
            return;

        if(avaibleOres == null)
            scanForOres();

        if(random == null)
            random = new Random(serverLevel.getSeed() ^ (chunkPos.x << 32 | chunkPos.z & 0xFFFFFFFFL));

        OreType selectedOre = selectOreForChunk(chunkPos);
        int veinCount = Config.MIN_VEINS.get() + random.nextInt(Config.MAX_VEINS.get() - Config.MIN_VEINS.get() + 1);
        generateOreVeins(chunk, selectedOre, veinCount, chunkPos);

        //TODO: Push chunk info to client here
        chunk.setData(AttachementRegistry.ORE_GENERATED, selectedOre.name);
    }

    private static void scanForOres()
    {
        avaibleOres = new ArrayList<>();

        for(Map.Entry<ResourceKey<Block>, Block> entry : BuiltInRegistries.BLOCK.entrySet())
        {
            ResourceLocation id = entry.getKey().location();
            String path = id.getPath();


            if(path.contains("_ore") && !path.contains("raw") && !path.contains("nether") && !path.contains("end"))
            {
                ResourceLocation deepTest = ResourceLocation.fromNamespaceAndPath(id.getNamespace(),"deepslate_" + id.getPath());

                Block oreDeep = BuiltInRegistries.BLOCK.get(deepTest);
                avaibleOres.add(new OreType(id, entry.getValue(), oreDeep != null ? oreDeep : entry.getValue()));
            }
        }

    }

    private static OreType selectOreForChunk(ChunkPos pos)
    {
        return avaibleOres.get(random.nextInt(avaibleOres.size()));
    }

    private static void generateOreVeins(ChunkAccess chunk, OreType oreType, int veinCount, ChunkPos chunkPos)
    {
        for(int i = 0; i < veinCount; ++i)
        {
            int x = random.nextInt(15);
            int z = random.nextInt(15);
            int y = -60 + random.nextInt(Math.max(1, Config.MAX_RANGE.get() + Config.MIN_RANGE.get()*-1));

            BlockPos startPos = chunkPos.getBlockAt(x, y, z);

            int veinSize = Config.MIN_VEIN_SIZE.get() + random.nextInt(Config.MAX_VEIN_SIZE.get() - Config.MIN_VEIN_SIZE.get() + 1);
            generateVein(chunk, startPos, oreType, veinSize, chunkPos);

        }
    }

    private static void generateVein(ChunkAccess chunk, BlockPos start, OreType oreType, int size, ChunkPos chunkPos)
    {
        Set<BlockPos> placedBlocks = new HashSet<>();
        Queue<BlockPos> toProcess = new LinkedList<>();
        toProcess.add(start);

        while(!toProcess.isEmpty() && placedBlocks.size() < size)
        {
            BlockPos pos = toProcess.poll();

            if(placedBlocks.contains(pos)) continue;
            if(!((pos.getX() >> 4 == chunkPos.x) && (pos.getZ() >> 4 == chunkPos.z))) continue;

            BlockState currentState = chunk.getBlockState(pos);
            if(!(currentState.is(Blocks.STONE) || currentState.is(Blocks.DEEPSLATE))) continue;

            Block oreBlock = pos.getY() < 0 ? oreType.stoneVariant : oreType.deepVariant;
            chunk.setBlockState(pos, oreBlock.defaultBlockState(), false);
            placedBlocks.add(pos);

            System.out.println("Vein "+ start.toString() +" placed "+oreType.name.toString()+" blocks");

            for(BlockPos neighbor : List.of(pos.north(), pos.south(), pos.east(), pos.west(), pos.above(), pos.below()))
                if(random.nextFloat() < 0.65f)
                    toProcess.add(neighbor);
        }
    }

}
