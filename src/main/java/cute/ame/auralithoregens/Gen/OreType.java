package cute.ame.auralithoregens.Gen;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public record OreType(ResourceLocation name, Block stoneVariant, Block deepVariant)
{
}
