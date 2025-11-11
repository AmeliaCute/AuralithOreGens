package cute.ame.auralithoregens.Client;

import cute.ame.auralithoregens.Auralithoregens;
import cute.ame.auralithoregens.Registries.ItemRegistries;
import cute.ame.auralithoregens.Renderer.OreFinderRenderer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

@EventBusSubscriber(modid = Auralithoregens.MODID, value = Dist.CLIENT)
public class ClientSetup
{
    @SubscribeEvent
    public static void onRegisterClientExtensions(RegisterClientExtensionsEvent event)
    {
        event.registerItem(
            new IClientItemExtensions()
            {
                private final BlockEntityWithoutLevelRenderer renderer = new OreFinderRenderer();

                @Override
                public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                    return renderer;
                }
            },
            ItemRegistries.ORE_FINDER.get()
        );
    }
}
