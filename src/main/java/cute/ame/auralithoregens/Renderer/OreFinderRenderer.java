package cute.ame.auralithoregens.Renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import cute.ame.auralithoregens.Registries.DataComponentRegistries;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.joml.Matrix4f;

public class OreFinderRenderer extends BlockEntityWithoutLevelRenderer
{
    public OreFinderRenderer()
    {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay)
    {
        poseStack.pushPose();

        BakedModel model = Minecraft.getInstance().getItemRenderer().getModel(stack, null, null, 0);
        Item item = stack.getItem();

        model = model.applyTransform(displayContext, poseStack, false);
        poseStack.translate(-.5f, -.5f, -.5f);

        long seed = 42L;
        RandomSource randomSource = RandomSource.create();

        for(BakedModel passModel : model.getRenderPasses(stack, false))
        {
            for(RenderType renderType : passModel.getRenderTypes(stack, false))
            {

                VertexConsumer consumer = buffer.getBuffer(renderType);

                for(Direction dir : Direction.values())
                {
                    randomSource.setSeed(seed);
                    Minecraft.getInstance().getItemRenderer().renderQuadList(poseStack, consumer, passModel.getQuads(null, dir, randomSource), stack, packedLight, packedOverlay);
                }

                randomSource.setSeed(seed);
                Minecraft.getInstance().getItemRenderer().renderQuadList(poseStack, consumer, passModel.getQuads(null, null, randomSource), stack, packedLight, packedOverlay);
            }
        }

        poseStack.popPose();

        if(stack.has(DataComponentRegistries.ORE_DATA))
        {
            Minecraft minecraft = Minecraft.getInstance();

            String[] id = stack.get(DataComponentRegistries.ORE_DATA).split(":");
            Block oreBlock = BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath(id[0], id[1]));
            if (oreBlock != Blocks.AIR)
            {
                ResourceLocation blockTexture = getBlockTexture(oreBlock);
                poseStack.pushPose();

                poseStack.translate(.25f, .75f, 0.51f);
                poseStack.scale(.5f, .5f, 1.0f);
                renderItemTexture(poseStack, buffer, packedLight, blockTexture, minecraft);

                poseStack.popPose();
            }
        }
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