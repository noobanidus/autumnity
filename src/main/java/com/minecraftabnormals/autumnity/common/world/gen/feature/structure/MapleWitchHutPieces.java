package com.minecraftabnormals.autumnity.common.world.gen.feature.structure;

import com.minecraftabnormals.autumnity.core.Autumnity;
import com.minecraftabnormals.autumnity.core.other.AutumnityLootTables;
import com.minecraftabnormals.autumnity.core.registry.AutumnityBlocks;
import com.minecraftabnormals.autumnity.core.registry.AutumnityStructures;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.WitchEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.*;

import java.util.List;
import java.util.Random;

public class MapleWitchHutPieces {
	private static final BlockPos STRUCTURE_OFFSET = new BlockPos(0, 0, 0);
	private static final ResourceLocation STRUCTURE = new ResourceLocation(Autumnity.MOD_ID, "witch_hut/maple_witch_hut");
	private static final ResourceLocation STRUCTURE_OVERGROWN = new ResourceLocation(Autumnity.MOD_ID, "witch_hut/maple_witch_hut_overgrown");

	public static void addPieces(TemplateManager p_204760_0_, BlockPos p_204760_1_, Rotation p_204760_2_, Mirror p_204760_3_, List<StructurePiece> p_204760_4_, Random p_204760_5_) {
		p_204760_4_.add(new MapleWitchHutPieces.Piece(p_204760_0_, STRUCTURE, p_204760_1_, p_204760_2_, p_204760_3_, 1.0F));
		p_204760_4_.add(new MapleWitchHutPieces.Piece(p_204760_0_, STRUCTURE_OVERGROWN, p_204760_1_, p_204760_2_, p_204760_3_, 0.2F));
	}

	public static class Piece extends TemplateStructurePiece {
		private final ResourceLocation structure;
		private final Rotation rotation;
		private final Mirror mirror;
		private final float integrity;

		public Piece(TemplateManager templateManager, ResourceLocation templateIn, BlockPos templatePositionIn, Rotation rotationIn, Mirror mirrorIn, float integrityIn) {
			super(AutumnityStructures.Pieces.MAPLE_WITCH_HUT_PIECE, 0);
			this.structure = templateIn;
			this.templatePosition = templatePositionIn;
			this.rotation = rotationIn;
			this.mirror = mirrorIn;
			this.integrity = integrityIn;
			this.loadTemplate(templateManager);
		}

		public Piece(TemplateManager templateManager, CompoundNBT tagCompound) {
			super(AutumnityStructures.Pieces.MAPLE_WITCH_HUT_PIECE, tagCompound);
			this.structure = new ResourceLocation(tagCompound.getString("Template"));
			this.rotation = Rotation.valueOf(tagCompound.getString("Rot"));
			this.mirror = Mirror.valueOf(tagCompound.getString("Mirror"));
			this.integrity = tagCompound.getFloat("Integrity");
			this.loadTemplate(templateManager);
		}

		@Override
		protected void addAdditionalSaveData(CompoundNBT tagCompound) {
			super.addAdditionalSaveData(tagCompound);
			tagCompound.putString("Template", this.structure.toString());
			tagCompound.putString("Rot", this.rotation.name());
			tagCompound.putString("Mirror", this.mirror.name());
			tagCompound.putFloat("Integrity", this.integrity);
		}

		private void loadTemplate(TemplateManager templateManager) {
			Template template = templateManager.getOrCreate(this.structure);
			PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.rotation).setMirror(this.mirror).setRotationPivot(MapleWitchHutPieces.STRUCTURE_OFFSET).addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_BLOCK).addProcessor(new IntegrityProcessor(this.integrity));

			this.setup(template, this.templatePosition, placementsettings);
		}

		@Override
		protected void handleDataMarker(String function, BlockPos pos, IServerWorld worldIn, Random rand, MutableBoundingBox sbb) {
			if ("chest".equals(function)) {
				worldIn.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
				LockableLootTileEntity.setLootTable(worldIn, rand, pos.below(), AutumnityLootTables.CHESTS_MAPLE_WITCH_HUT);
			} else if ("decor".equals(function)) {
				if (rand.nextInt(2) == 0) {
					worldIn.setBlock(pos, Blocks.POTTED_RED_MUSHROOM.defaultBlockState(), 2);
				} else {
					worldIn.setBlock(pos, Blocks.POTTED_BROWN_MUSHROOM.defaultBlockState(), 2);
				}
			} else if ("flower".equals(function)) {
				if (rand.nextInt(4) == 0) {
					worldIn.setBlock(pos.below(), AutumnityBlocks.AUTUMN_CROCUS.get().defaultBlockState(), 2);
				}
				worldIn.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
			} else if (function.startsWith("witch")) {
				worldIn.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
				WitchEntity witchentity = EntityType.WITCH.create(worldIn.getLevel());
				witchentity.setPersistenceRequired();
				witchentity.setPos((double) pos.getX() + 0.5D, (double) pos.getY() - 1.0D, (double) pos.getZ() + 0.5D);
				witchentity.finalizeSpawn(worldIn, worldIn.getCurrentDifficultyAt(new BlockPos(pos.getX(), pos.getY(), (double) pos.getZ())), SpawnReason.STRUCTURE, null, null);
				worldIn.addFreshEntityWithPassengers(witchentity);
			} else if (function.startsWith("cat")) {
				worldIn.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
				CatEntity catentity = EntityType.CAT.create(worldIn.getLevel());
				catentity.setPersistenceRequired();
				catentity.setPos((double) pos.getX() + 0.5D, (double) pos.getY() - 1.0D, (double) pos.getZ() + 0.5D);
				catentity.finalizeSpawn(worldIn, worldIn.getCurrentDifficultyAt(new BlockPos(pos.getX(), pos.getY(), (double) pos.getZ())), SpawnReason.STRUCTURE, null, null);
				catentity.setCatType(10);
				worldIn.addFreshEntityWithPassengers(catentity);
			}
		}

		@Override
		public boolean postProcess(ISeedReader p_230383_1_, StructureManager p_230383_2_, ChunkGenerator p_230383_3_, Random p_230383_4_, MutableBoundingBox p_230383_5_, ChunkPos p_230383_6_, BlockPos p_230383_7_) {
			return super.postProcess(p_230383_1_, p_230383_2_, p_230383_3_, p_230383_4_, p_230383_5_, p_230383_6_, p_230383_7_);
		}
	}
}
