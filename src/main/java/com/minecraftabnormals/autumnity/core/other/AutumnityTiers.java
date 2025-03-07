package com.minecraftabnormals.autumnity.core.other;

import com.minecraftabnormals.abnormals_core.core.api.AbnormalsArmorMaterial;
import com.minecraftabnormals.autumnity.core.Autumnity;
import com.minecraftabnormals.autumnity.core.registry.AutumnityItems;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;

public class AutumnityTiers {
	public static final AbnormalsArmorMaterial SNAIL_SHELL = new AbnormalsArmorMaterial(new ResourceLocation(Autumnity.MOD_ID, "snail_shell"), 23, new int[]{0, 0, 5, 0}, 9, SoundEvents.ARMOR_EQUIP_TURTLE, 0.0F, 0.0F, () -> Ingredient.of(AutumnityItems.SNAIL_SHELL_PIECE.get()));
}
