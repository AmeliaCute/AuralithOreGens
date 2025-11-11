package cute.ame.auralithoregens.Renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import cute.ame.auralithoregens.Client.OreFinderModel;
import cute.ame.auralithoregens.Registries.DataComponentRegistries;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

public class OreFinderRenderer extends BlockEntityWithoutLevelRenderer
{

    public static OreFinderModel oreFinderModel = null;

    public OreFinderRenderer()
    {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay)
    {
        poseStack.popPose();
        poseStack.pushPose();

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        if (oreFinderModel == null)
            oreFinderModel = new OreFinderModel();
        BakedModel model = oreFinderModel;
        model = model.applyTransform(displayContext, poseStack, displayContext == ItemDisplayContext.FIRST_PERSON_LEFT_HAND || displayContext == ItemDisplayContext.THIRD_PERSON_LEFT_HAND);
        switch (displayContext) {
            case THIRD_PERSON_LEFT_HAND, THIRD_PERSON_RIGHT_HAND -> {
                poseStack.translate(-0.25, 0, 0);
                poseStack.scale(0.5f, 0.5f, 0.5f);
            }
            case FIRST_PERSON_LEFT_HAND, FIRST_PERSON_RIGHT_HAND -> {
                Quaternionf quaternion = new Quaternionf();
                quaternion.rotationXYZ((float) Math.toRadians(-12), (float) Math.toRadians(-30), (float) Math.toRadians(0));
                poseStack.mulPose(quaternion);
                poseStack.translate(-0.25, 0, 0);
                poseStack.scale(0.5f, 0.5f, 0.5f);
            }
            case GUI -> {
                Quaternionf quaternion = new Quaternionf();
                quaternion.rotationXYZ((float) Math.toRadians(-20), (float) Math.toRadians(19), (float) Math.toRadians(7));
                poseStack.mulPose(quaternion);
                poseStack.translate(-0.5f, -0.4f, -0.5f);
            }
            case GROUND, FIXED -> {
                poseStack.scale(0.5f, 0.5f, 0.5f);
                poseStack.translate(-0.5f, -0.1f, -0.5f);
            }
            default -> {}
        }
        itemRenderer.renderModelLists(model, stack, packedLight, packedOverlay, poseStack, buffer.getBuffer(Sheets.translucentItemSheet()));

        if(stack.has(DataComponentRegistries.ORE_DATA))
        {
            String id = stack.get(DataComponentRegistries.ORE_DATA);
            Block oreBlock = BuiltInRegistries.BLOCK.get(ResourceLocation.parse(id));
            poseStack.pushPose();
            poseStack.translate(0.435f, 0.6f, 0.51);
            poseStack.scale(0.15f, 0.15f, 0);
            Minecraft.getInstance().getBlockRenderer().renderSingleBlock(oreBlock.defaultBlockState(), poseStack, buffer, 0xF000F0, packedOverlay, ModelData.EMPTY, null);
            poseStack.popPose();
            poseStack.pushPose();
            float centerX = 110f;
            float centerY = 140f;
            String name = oreBlock.getName().getString();
            float textWidth = Minecraft.getInstance().font.width(name);
            poseStack.translate(0, 0, 0.51f);
            poseStack.scale( 0.005f, 0.005f, 0);
            poseStack.translate(centerX - textWidth / 2.f, centerY, 3);
            Quaternionf quaternion = new Quaternionf();
            quaternion.rotationXYZ((float) Math.toRadians(0), (float) Math.toRadians(180), (float) Math.toRadians(180));
            poseStack.mulPose(quaternion);
            Minecraft.getInstance().font.drawInBatch(name, -10, 30, 0xFFFFFF, false, poseStack.last().pose(), buffer, Font.DisplayMode.NORMAL, 0, 15728880);
            poseStack.popPose();

        }
        poseStack.popPose();
        poseStack.pushPose();
    }

    private void renderItemTexture(PoseStack poseStack, MultiBufferSource buffer, int packedLight, ResourceLocation textureLocation, Minecraft minecraft)
    {
        TextureAtlasSprite sprite = minecraft.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(textureLocation);
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutout(InventoryMenu.BLOCK_ATLAS));
        Matrix4f matrix = poseStack.last().pose();

        // Fixed vertex order - counter-clockwise winding with correct UV mapping
        vertexConsumer.addVertex(matrix, 0, 0, 0)
                .setColor(0xFFFFFFFF)
                .setUv(sprite.getU0(), sprite.getV0())
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(packedLight)
                .setNormal(0, 0, 1);

        vertexConsumer.addVertex(matrix, 1, 0, 0)
                .setColor(0xFFFFFFFF)
                .setUv(sprite.getU1(), sprite.getV0())
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(packedLight)
                .setNormal(0, 0, 1);

        vertexConsumer.addVertex(matrix, 1, 1, 0)
                .setColor(0xFFFFFFFF)
                .setUv(sprite.getU1(), sprite.getV1())
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(packedLight)
                .setNormal(0, 0, 1);

        vertexConsumer.addVertex(matrix, 0, 1, 0)
                .setColor(0xFFFFFFFF)
                .setUv(sprite.getU0(), sprite.getV1())
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(packedLight)
                .setNormal(0, 0, 1);
    }

    private ResourceLocation getBlockTexture(Block block)
    {
        ResourceLocation blockId = BuiltInRegistries.BLOCK.getKey(block);
        return ResourceLocation.fromNamespaceAndPath(blockId.getNamespace(), "block/" + blockId.getPath());
    }
}