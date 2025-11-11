package cute.ame.auralithoregens.Registries;

import cute.ame.auralithoregens.Auralithoregens;
import cute.ame.auralithoregens.Compat.OreFinder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ItemRegistries
{
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Auralithoregens.MODID);

    public static final Supplier<Item> ORE_FINDER = ITEMS.registerItem("ore_finder", OreFinder::new);
}
