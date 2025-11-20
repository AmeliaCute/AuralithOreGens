package cute.ame.auralithoregens.Registries;

import com.mojang.serialization.Codec;
import cute.ame.auralithoregens.Auralithoregens;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class AttachementTypeRegistries
{
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Auralithoregens.MODID);

    public static final Supplier<AttachmentType<Boolean>> PROCESSED = ATTACHMENT_TYPES.register(
            "isprocessed", () -> AttachmentType.builder(() -> false).serialize(Codec.BOOL).build());

    public static final Supplier<AttachmentType<ResourceLocation>> ORE_TYPE = ATTACHMENT_TYPES.register(
            "ore_type", () -> AttachmentType.builder(() -> ResourceLocation.fromNamespaceAndPath("minecraft", "iron_ore")).serialize(ResourceLocation.CODEC).build());
}
