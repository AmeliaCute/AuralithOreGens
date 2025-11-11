package cute.ame.auralithoregens.Client;

import cute.ame.auralithoregens.Auralithoregens;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class OreFinderModel implements BakedModel {

    private final List<BakedQuad> quads;
    private final TextureAtlasSprite atlasSprite;

    public OreFinderModel() {
        this.quads = new ArrayList<>();
        atlasSprite = Minecraft.getInstance().getTextureAtlas(
                TextureAtlas.LOCATION_BLOCKS).apply(ResourceLocation.fromNamespaceAndPath(Auralithoregens.MODID, "item/ore_finder")
        );
        FaceBakery bakery = new FaceBakery();
        ModelState transform = new ModelState() {};

        Vector3f from0 = new Vector3f(4f, 0f, 7f);
        Vector3f to0   = new Vector3f(12f, 13f, 8f);
        quads.add(bakery.bakeQuad(from0, to0,
                new BlockElementFace(Direction.NORTH, -1, "0", new BlockFaceUV(new float[]{0f,0f,4f,6.5f}, 0)),
                atlasSprite, Direction.NORTH, transform, null, true));
        quads.add(bakery.bakeQuad(from0, to0,
                new BlockElementFace(Direction.EAST, -1, "0", new BlockFaceUV(new float[]{8f,0f,8.5f,6.5f}, 0)),
                atlasSprite, Direction.EAST, transform, null, true));
        quads.add(bakery.bakeQuad(from0, to0,
                new BlockElementFace(Direction.SOUTH, -1, "0", new BlockFaceUV(new float[]{4f,0f,8f,6.5f}, 0)),
                atlasSprite, Direction.SOUTH, transform, null, true));
        quads.add(bakery.bakeQuad(from0, to0,
                new BlockElementFace(Direction.WEST, -1, "0", new BlockFaceUV(new float[]{8f,6.5f,8.5f,13f}, 0)),
                atlasSprite, Direction.WEST, transform, null, true));
        quads.add(bakery.bakeQuad(from0, to0,
                new BlockElementFace(Direction.UP, -1, "0", new BlockFaceUV(new float[]{12.5f,0.5f,8.5f,0f}, 0)),
                atlasSprite, Direction.UP, transform, null, true));
        quads.add(bakery.bakeQuad(from0, to0,
                new BlockElementFace(Direction.DOWN, -1, "0", new BlockFaceUV(new float[]{12.5f,0.5f,8.5f,1f}, 0)),
                atlasSprite, Direction.DOWN, transform, null, true));

        Vector3f from1 = new Vector3f(4f,0f,8f);
        Vector3f to1   = new Vector3f(12f,7f,9f);
        quads.add(bakery.bakeQuad(from1, to1,
                new BlockElementFace(Direction.NORTH, -1, "0", new BlockFaceUV(new float[]{0f,6.5f,4f,10f}, 0)),
                atlasSprite, Direction.NORTH, transform, null, true));
        quads.add(bakery.bakeQuad(from1, to1,
                new BlockElementFace(Direction.EAST, -1, "0", new BlockFaceUV(new float[]{8.5f,4f,9f,7.5f}, 0)),
                atlasSprite, Direction.EAST, transform, null, true));
        quads.add(bakery.bakeQuad(from1, to1,
                new BlockElementFace(Direction.SOUTH, -1, "0", new BlockFaceUV(new float[]{4f,6.5f,8f,10f}, 0)),
                atlasSprite, Direction.SOUTH, transform, null, true));
        quads.add(bakery.bakeQuad(from1, to1,
                new BlockElementFace(Direction.WEST, -1, "0", new BlockFaceUV(new float[]{8.5f,7.5f,9f,11f}, 0)),
                atlasSprite, Direction.WEST, transform, null, true));
        quads.add(bakery.bakeQuad(from1, to1,
                new BlockElementFace(Direction.UP, -1, "0", new BlockFaceUV(new float[]{12.5f,1.5f,8.5f,1f}, 0)),
                atlasSprite, Direction.UP, transform, null, true));
        quads.add(bakery.bakeQuad(from1, to1,
                new BlockElementFace(Direction.DOWN, -1, "0", new BlockFaceUV(new float[]{12.5f,1.5f,8.5f,2f}, 0)),
                atlasSprite, Direction.DOWN, transform, null, true));

        Vector3f from2 = new Vector3f(5.25f,4f,9f);
        Vector3f to2   = new Vector3f(7.25f,6f,9.75f);
        quads.add(bakery.bakeQuad(from2, to2,
                new BlockElementFace(Direction.NORTH, -1, "0", new BlockFaceUV(new float[]{1f,10f,2f,11f},0)),
                atlasSprite, Direction.NORTH, transform, null, true));
        quads.add(bakery.bakeQuad(from2, to2,
                new BlockElementFace(Direction.EAST, -1, "0", new BlockFaceUV(new float[]{3f,10f,3.5f,11f},0)),
                atlasSprite, Direction.EAST, transform, null, true));
        quads.add(bakery.bakeQuad(from2, to2,
                new BlockElementFace(Direction.SOUTH, -1, "0", new BlockFaceUV(new float[]{2f,10f,3f,11f},0)),
                atlasSprite, Direction.SOUTH, transform, null, true));
        quads.add(bakery.bakeQuad(from2, to2,
                new BlockElementFace(Direction.WEST, -1, "0", new BlockFaceUV(new float[]{3.5f,10f,4f,11f},0)),
                atlasSprite, Direction.WEST, transform, null, true));
        quads.add(bakery.bakeQuad(from2, to2,
                new BlockElementFace(Direction.UP, -1, "0", new BlockFaceUV(new float[]{5f,10.5f,4f,10f},0)),
                atlasSprite, Direction.UP, transform, null, true));
        quads.add(bakery.bakeQuad(from2, to2,
                new BlockElementFace(Direction.DOWN, -1, "0", new BlockFaceUV(new float[]{11f,4f,10f,4.5f},0)),
                atlasSprite, Direction.DOWN, transform, null, true));

        Vector3f from3 = new Vector3f(9.5f,4.5f,9f);
        Vector3f to3   = new Vector3f(10.5f,5.5f,9.25f);
        quads.add(bakery.bakeQuad(from3, to3,
                new BlockElementFace(Direction.NORTH, -1, "0", new BlockFaceUV(new float[]{10f,4.5f,10.5f,5f},0)),
                atlasSprite, Direction.NORTH, transform, null, true));
        quads.add(bakery.bakeQuad(from3, to3,
                new BlockElementFace(Direction.EAST, -1, "0", new BlockFaceUV(new float[]{5f,10f,5.5f,10.5f},0)),
                atlasSprite, Direction.EAST, transform, null, true));
        quads.add(bakery.bakeQuad(from3, to3,
                new BlockElementFace(Direction.SOUTH, -1, "0", new BlockFaceUV(new float[]{10f,5f,10.5f,5.5f},0)),
                atlasSprite, Direction.SOUTH, transform, null, true));
        quads.add(bakery.bakeQuad(from3, to3,
                new BlockElementFace(Direction.WEST, -1, "0", new BlockFaceUV(new float[]{5.5f,10f,6f,10.5f},0)),
                atlasSprite, Direction.WEST, transform, null, true));
        quads.add(bakery.bakeQuad(from3, to3,
                new BlockElementFace(Direction.UP, -1, "0", new BlockFaceUV(new float[]{10.5f,6f,10f,5.5f},0)),
                atlasSprite, Direction.UP, transform, null, true));
        quads.add(bakery.bakeQuad(from3, to3,
                new BlockElementFace(Direction.DOWN, -1, "0", new BlockFaceUV(new float[]{6.5f,10f,6f,10.5f},0)),
                atlasSprite, Direction.DOWN, transform, null, true));

        Vector3f from4 = new Vector3f(8f,4.5f,9f);
        Vector3f to4   = new Vector3f(9f,5.5f,9.25f);
        quads.add(bakery.bakeQuad(from4, to4,
                new BlockElementFace(Direction.NORTH, -1, "0", new BlockFaceUV(new float[]{10f,6f,10.5f,6.5f},0)),
                atlasSprite, Direction.NORTH, transform, null, true));
        quads.add(bakery.bakeQuad(from4, to4,
                new BlockElementFace(Direction.EAST, -1, "0", new BlockFaceUV(new float[]{6.5f,10f,7f,10.5f},0)),
                atlasSprite, Direction.EAST, transform, null, true));
        quads.add(bakery.bakeQuad(from4, to4,
                new BlockElementFace(Direction.SOUTH, -1, "0", new BlockFaceUV(new float[]{10f,6.5f,10.5f,7f},0)),
                atlasSprite, Direction.SOUTH, transform, null, true));
        quads.add(bakery.bakeQuad(from4, to4,
                new BlockElementFace(Direction.WEST, -1, "0", new BlockFaceUV(new float[]{7f,10f,7.5f,10.5f},0)),
                atlasSprite, Direction.WEST, transform, null, true));
        quads.add(bakery.bakeQuad(from4, to4,
                new BlockElementFace(Direction.UP, -1, "0", new BlockFaceUV(new float[]{10.5f,7.5f,10f,7f},0)),
                atlasSprite, Direction.UP, transform, null, true));
        quads.add(bakery.bakeQuad(from4, to4,
                new BlockElementFace(Direction.DOWN, -1, "0", new BlockFaceUV(new float[]{8f,10f,7.5f,10.5f},0)),
                atlasSprite, Direction.DOWN, transform, null, true));
    }

    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState blockState, @Nullable Direction direction, RandomSource randomSource) {
        return this.quads;
    }

    @Override
    public boolean useAmbientOcclusion() {
        return true;
    }

    @Override
    public boolean isGui3d() {
        return true;
    }

    @Override
    public boolean usesBlockLight() {
        return true;
    }

    @Override
    public boolean isCustomRenderer() {
        return false;
    }

    @Override
    public @NotNull TextureAtlasSprite getParticleIcon() {
        return atlasSprite;
    }

    @Override
    public @NotNull ItemOverrides getOverrides() {
        return ItemOverrides.EMPTY;
    }
}
