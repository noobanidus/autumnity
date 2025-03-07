package com.minecraftabnormals.autumnity.client.renderer.entity;

import com.minecraftabnormals.autumnity.client.renderer.entity.model.TurkeyModel;
import com.minecraftabnormals.autumnity.common.entity.passive.TurkeyEntity;
import com.minecraftabnormals.autumnity.core.Autumnity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TurkeyRenderer extends MobRenderer<TurkeyEntity, TurkeyModel<TurkeyEntity>> {
	private static final ResourceLocation TURKEY_TEXTURES = new ResourceLocation(Autumnity.MOD_ID, "textures/entity/turkey.png");

	public TurkeyRenderer(EntityRendererManager manager) {
		super(manager, new TurkeyModel<>(), 0.5F);
	}

	@Override
	public ResourceLocation getTextureLocation(TurkeyEntity entity) {
		return TURKEY_TEXTURES;
	}
}