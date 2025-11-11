package cute.ame.auralithoregens;

import cute.ame.auralithoregens.Gen.OreDictionary;
import cute.ame.auralithoregens.Registries.AttachementTypeRegistries;
import cute.ame.auralithoregens.Registries.DataComponentRegistries;
import cute.ame.auralithoregens.Registries.ItemRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(Auralithoregens.MODID)
public class Auralithoregens
{
    public static final String MODID = "auralithoregens";

    public Auralithoregens(IEventBus modEventBus, ModContainer modContainer)
    {
        modContainer.registerConfig(ModConfig.Type.SERVER, Config.SPEC);

        DataComponentRegistries.DATA_COMPONENTS.register(modEventBus);
        AttachementTypeRegistries.ATTACHMENT_TYPES.register(modEventBus);

        ItemRegistries.ITEMS.register(modEventBus);

        modEventBus.addListener(this::onConfigLoad);
    }

    private void onConfigLoad(ModConfigEvent event)
    {
        if (event.getConfig().getSpec() == Config.SPEC)
            OreDictionary.scanForOres();
    }

}
