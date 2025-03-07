package com.minecraftabnormals.autumnity.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import com.minecraftabnormals.autumnity.common.entity.passive.TurkeyEntity;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * ModelTurkey - Undefined
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class TurkeyModel<T extends TurkeyEntity> extends AgeableModel<T> {
	private final ModelRenderer head;
	private final ModelRenderer beak;
	private final ModelRenderer waddle;
	private final ModelRenderer body;
	private final ModelRenderer rightWing;
	private final ModelRenderer leftWing;
	private final ModelRenderer tail;
	private final ModelRenderer rightLeg;
	private final ModelRenderer leftLeg;

	public TurkeyModel() {
		this.texWidth = 64;
		this.texHeight = 32;

		this.head = new ModelRenderer(this, 0, 0);
		this.head.setPos(0.0F, 14.0F, -3.0F);
		this.head.addBox(-2.0F, -5.0F, -2.0F, 4.0F, 6.0F, 3.0F, 0.0F, 0.0F, 0.0F);

		this.beak = new ModelRenderer(this, 0, 9);
		this.beak.setPos(0.0F, 0.0F, 0.0F);
		this.beak.addBox(-2.0F, -3.0F, -4.0F, 4.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);

		this.waddle = new ModelRenderer(this, 0, 13);
		this.waddle.setPos(0.0F, 0.0F, 0.0F);
		this.waddle.addBox(-1.0F, -1.0F, -4.0F, 2.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);

		this.head.addChild(this.waddle);
		this.head.addChild(this.beak);

		this.body = new ModelRenderer(this, 0, 14);
		this.body.setPos(0.0F, 21.0F, 1.0F);
		this.body.addBox(-5.0F, -8.0F, -5.0F, 10.0F, 8.0F, 10.0F, 0.0F, 0.0F, 0.0F);

		this.rightWing = new ModelRenderer(this);
		this.rightWing.mirror = true;
		this.rightWing.setPos(5.0F, -8.0F, 0.0F);
		this.rightWing.texOffs(44, 0).addBox(0.0F, 0.0F, -4.0F, 2.0F, 6.0F, 8.0F, 0.0F);

		this.leftWing = new ModelRenderer(this);
		this.leftWing.setPos(-5.0F, -8.0F, 0.0F);
		this.leftWing.texOffs(44, 0).addBox(-2.0F, 0.0F, -4.0F, 2.0F, 6.0F, 8.0F, 0.0F);

		this.tail = new ModelRenderer(this, 16, 4);
		this.tail.setPos(0.0F, -7.0F, 4.0F);
		this.tail.addBox(-7.0F, -10.0F, 0.0F, 14.0F, 10.0F, 0.0F, 0.0F, 0.0F, 0.0F);

		this.setRotateAngle(tail, -0.7853981633974483F, 0.0F, 0.0F);

		this.body.addChild(this.leftWing);
		this.body.addChild(this.rightWing);
		this.body.addChild(this.tail);

		this.rightLeg = new ModelRenderer(this, 30, 17);
		this.rightLeg.setPos(-3.0F, 20.0F, 1.0F);
		this.rightLeg.addBox(-1.5F, 0.0F, -3.0F, 3.0F, 4.0F, 3.0F, 0.0F, 0.0F, 0.0F);

		this.leftLeg = new ModelRenderer(this, 30, 17);
		this.leftLeg.mirror = true;
		this.leftLeg.setPos(3.0F, 20.0F, 1.0F);
		this.leftLeg.addBox(-1.5F, 0.0F, -3.0F, 3.0F, 4.0F, 3.0F, 0.0F, 0.0F, 0.0F);
	}

	protected Iterable<ModelRenderer> headParts() {
		return ImmutableList.of(this.head);
	}

	protected Iterable<ModelRenderer> bodyParts() {
		return ImmutableList.of(this.body, this.rightLeg, this.leftLeg);
	}

	public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		float partialtick = ageInTicks - (float) entityIn.tickCount;
		float winganim = entityIn.getWingRotation(partialtick);
		float peckanim = entityIn.getPeckProgress(partialtick);

		this.head.xRot = headPitch * ((float) Math.PI / 180F);
		this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
		this.head.xRot += peckanim * 0.8F;
		this.head.z = -3.0F - peckanim * 1.5F;

		this.rightWing.zRot = -winganim;
		this.leftWing.zRot = winganim;

		this.rightLeg.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.leftLeg.xRot = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
	}

	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}
