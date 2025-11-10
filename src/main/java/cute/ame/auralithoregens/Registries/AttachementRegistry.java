package cute.ame.auralithoregens.Registries;

import com.mojang.serialization.Codec;
import cute.ame.auralithoregens.Auralithoregens;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class AttachementRegistry
{
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPE = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Auralithoregens.MODID);

    public static final Supplier<AttachmentType<ResourceLocation>> ORE_GENERATED = ATTACHMENT_TYPE.register(
            "ore_generated", () -> AttachmentType.builder(() -> ResourceLocation.withDefaultNamespace("diamond_ore")).build()
    );
}
