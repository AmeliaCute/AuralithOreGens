package cute.ame.auralithoregens.Compat;

import cute.ame.auralithoregens.Gen.ChunkGenerationHandler;
import cute.ame.auralithoregens.Gen.OreDictionary;
import cute.ame.auralithoregens.Gen.OreType;
import cute.ame.auralithoregens.Registries.DataComponentRegistries;
import cute.ame.auralithoregens.Renderer.OreFinderRenderer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;

import java.util.Random;
import java.util.function.Consumer;

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
            // update
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
        OreType selectedOre = OreDictionary.getOre(pos, level.getSeed());
        stack.set(DataComponentRegistries.ORE_DATA, selectedOre.name().toString());
        stack.set(DataComponentRegistries.CHUNK_X_DATA, pos.x);
        stack.set(DataComponentRegistries.CHUNK_Y_DATA, pos.z);

        if(player.getInventory().contains(stack))
        {
            player.displayClientMessage(
                    Component.literal("Chunk [")
                            .withStyle(ChatFormatting.YELLOW)
                            .append(Component.literal(pos.x + ", " + pos.z + "] contains: ")
                                    .withStyle(ChatFormatting.YELLOW))
                            .append(Component.literal(selectedOre.name() + " Ore")
                                    .withStyle(ChatFormatting.AQUA)),
                true
            );
        }
    }
}
