package com.minecraftabnormals.autumnity.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TurkeyEggRenderer<T extends Entity & IRendersAsItem> extends EntityRenderer<T> {
	private final net.minecraft.client.renderer.ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
	private final float scale;
	private final boolean fullBright;

	public TurkeyEggRenderer(EntityRendererManager renderManagerIn, float scaleIn, boolean p_i226035_4_) {
		super(renderManagerIn);
		this.scale = scaleIn;
		this.fullBright = p_i226035_4_;
	}

	public TurkeyEggRenderer(EntityRendererManager renderManagerIn) {
		this(renderManagerIn, 1.0F, false);
	}

	@Override
	protected int getBlockLightLevel(T entityIn, BlockPos partialTicks) {
		return this.fullBright ? 15 : super.getBlockLightLevel(entityIn, partialTicks);
	}

	public void render(T entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
		matrixStackIn.pushPose();
		matrixStackIn.scale(this.scale, this.scale, this.scale);
		matrixStackIn.mulPose(this.entityRenderDispatcher.cameraOrientation());
		matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180.0F));
		this.itemRenderer.renderStatic(entityIn.getItem(), ItemCameraTransforms.TransformType.GROUND, packedLightIn, OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn);
		matrixStackIn.popPose();
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	public ResourceLocation getTextureLocation(Entity entity) {
		return AtlasTexture.LOCATION_BLOCKS;
	}
}