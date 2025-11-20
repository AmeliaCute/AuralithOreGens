package cute.ame.auralithoregens.Gen;

import cute.ame.auralithoregens.Config;
import cute.ame.auralithoregens.Registries.AttachementTypeRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.ChunkEvent;

import java.util.*;

@EventBusSubscriber
public class ChunkGenerationHandler
{
    private static Random random = null;

    @SubscribeEvent
    public static void onChunkGenerate(ChunkEvent.Load event)
    {
        if(!(event.getLevel() instanceof ServerLevel serverLevel) || serverLevel.dimension() != Level.OVERWORLD) return;
        ChunkAccess chunk = event.getChunk();
        ChunkPos chunkPos = chunk.getPos();

        if(chunk.getData(AttachementTypeRegistries.PROCESSED.get()) || !chunk.getPersistedStatus().isOrAfter(ChunkStatus.FEATURES)) return;
        if(random == null) random = new Random(serverLevel.getSeed() ^ ((long) chunkPos.x << 32 | chunkPos.z & 0xFFFFFFFFL));

        OreType selectedOre = OreDictionary.getOre(chunkPos, serverLevel.getSeed());
        int veinCount = Config.MIN_VEINS.get() + random.nextInt(Config.MAX_VEINS.get() - Config.MIN_VEINS.get() + 1);
        generateOreVeins(chunk, selectedOre, veinCount, chunkPos);

        chunk.setData(AttachementTypeRegistries.PROCESSED.get(), true);
        chunk.setData(AttachementTypeRegistries.ORE_TYPE.get(), selectedOre.name());
    }


    private static void generateOreVeins(ChunkAccess chunk, OreType oreType, int veinCount, ChunkPos chunkPos)
    {
        for(int i = 0; i < veinCount; ++i)
        {
            int x = random.nextInt(16);
            int z = random.nextInt(16);
            int y = Config.MIN_RANGE.get() + random.nextInt(Math.max(1, Config.MAX_RANGE.get() + Config.MIN_RANGE.get()*-1));

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

            Block oreBlock = pos.getY() < 0 ? oreType.deepVariant() : oreType.stoneVariant();
            chunk.setBlockState(pos, oreBlock.defaultBlockState(), false);
            placedBlocks.add(pos);

            for(BlockPos neighbor : List.of(pos.north(), pos.south(), pos.east(), pos.west(), pos.above(), pos.below()))
                if(random.nextFloat() < 0.65f) toProcess.add(neighbor);
        }
    }

}
