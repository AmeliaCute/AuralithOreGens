package cute.ame.auralithoregens.Registries;

import com.mojang.serialization.Codec;
import cute.ame.auralithoregens.Auralithoregens;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class DataComponentRegistries
{
    public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Auralithoregens.MODID);

    public static final Codec<String> STRING_CODEC = Codec.STRING;

    public static final  DeferredHolder<DataComponentType<?>, DataComponentType<String>> ORE_DATA = DATA_COMPONENTS.registerComponentType("ore_data",
            (DataComponentType.Builder<String> builder) ->
                    builder.persistent(Codec.STRING)
                            .networkSynchronized(ByteBufCodecs.STRING_UTF8)
    );

    public static final  DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> CHUNK_X_DATA = DATA_COMPONENTS.registerComponentType("chunk_x_data",
            (DataComponentType.Builder<Integer> builder) ->
                    builder.persistent(Codec.INT)
                            .networkSynchronized(ByteBufCodecs.INT)
    );

    public static final  DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> CHUNK_Y_DATA = DATA_COMPONENTS.registerComponentType("chunk_y_data",
            (DataComponentType.Builder<Integer> builder) ->
                    builder.persistent(Codec.INT)
                            .networkSynchronized(ByteBufCodecs.INT)
    );

}
