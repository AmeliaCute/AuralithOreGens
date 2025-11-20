package cute.ame.auralithoregens.Compat;

import cute.ame.auralithoregens.Registries.AttachementTypeRegistries;
import cute.ame.auralithoregens.Registries.DataComponentRegistries;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;

public class OreFinder extends Item
{
    public OreFinder(Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if(level.isClientSide || !(entity instanceof Player player)) return;
        BlockPos playerPos    = player.blockPosition();
        ChunkPos currentChunk = new ChunkPos(playerPos);

        if(!stack.has(DataComponentRegistries.CHUNK_X_DATA))
        {
            updateFinder(stack, (ServerLevel) level, currentChunk, player);
            return;
        }

        int storedChunkX = stack.get(DataComponentRegistries.CHUNK_X_DATA);
        int storedChunkY = stack.get(DataComponentRegistries.CHUNK_Y_DATA);

        if(currentChunk.x != storedChunkX || currentChunk.z != storedChunkY)
            updateFinder(stack, (ServerLevel) level, currentChunk, player);
    }

    private void updateFinder(ItemStack stack, ServerLevel level, ChunkPos pos, Player player)
    {
        ResourceLocation name = level.getChunk(pos.x, pos.z).getData(AttachementTypeRegistries.ORE_TYPE);
        stack.set(DataComponentRegistries.ORE_DATA, name.toString());
        stack.set(DataComponentRegistries.CHUNK_X_DATA, pos.x);
        stack.set(DataComponentRegistries.CHUNK_Y_DATA, pos.z);
    }
}
