package fr.alexandre1156.mushpowers.structure;

import java.util.ArrayList;
import java.util.Random;

import com.google.common.collect.Lists;

import fr.alexandre1156.mushpowers.bushs.BushMush;
import fr.alexandre1156.mushpowers.proxy.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenBigMushroom;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenMushPower extends WorldGenerator {

	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {
		if(this.isBiomeCorrect(Biome.getIdForBiome(world.getBiome(pos)))){
			ArrayList<BlockPos> list = Lists.<BlockPos>newArrayList();
			for(int x = 0; x < 16; x++){
				for(int z = 0; z < 16; z++){
					BlockPos blockPos = new BlockPos(pos.getX()+x, pos.getY(), pos.getZ()+z);
					if(!this.isOnSurface(blockPos, world)){
						blockPos = this.setAtSurface(blockPos, world);
						if(blockPos == null)
							return false;
						else
							list.add(new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ()));
					} else
						list.add(new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ()));
				}
			}
			for(BlockPos pos2 : list) {
				BlockPos upperPos = new BlockPos(pos2.getX(), pos2.getY()+1, pos2.getZ());
				if(this.needRemoved(upperPos, world))
					world.setBlockToAir(upperPos);
				int xAdded = Math.abs(pos2.getX()-pos.getX());
				int zAdded = Math.abs(pos2.getZ()-pos.getZ());
				
				if(this.isFirstLayer(xAdded, zAdded)){
					if(rand.nextInt(4) == 0)
						world.setBlockState(pos2, Blocks.MYCELIUM.getDefaultState());
				} else if(this.isSecondLayer(xAdded, zAdded)){
					if(rand.nextInt(3) == 0)
						world.setBlockState(pos2, Blocks.MYCELIUM.getDefaultState());
				} else if(this.isThirdLayer(xAdded, zAdded)){
					if(rand.nextBoolean())
						world.setBlockState(pos2, Blocks.MYCELIUM.getDefaultState());
				} else {
					world.setBlockState(pos2, Blocks.MYCELIUM.getDefaultState());
				}
				if(rand.nextInt(15) == 0)
					(new WorldGenBigMushroom(Blocks.RED_MUSHROOM_BLOCK)).generate(world, rand, upperPos);
				if(rand.nextInt(5) == 0 /*&& Block.isEqualTo(world.getBlockState(pos).getBlock(), Blocks.MYCELIUM)*/)
					this.generateRandomMushPowerBush(upperPos, rand, world);
			}
			return true;
		}
		return false;
	}
	
	private boolean isFirstLayer(int xAdded, int zAdded){
		return xAdded == 0 ? true : xAdded > 0 && xAdded < 15 ? zAdded == 0 || zAdded == 15 ? true : false : xAdded == 15 ? true : false;
	}
	
	private boolean isSecondLayer(int xAdded, int zAdded) {
		return xAdded == 1 ? zAdded > 0 && zAdded < 15 ? true : false : xAdded > 1 && xAdded < 14 ? zAdded == 1 || zAdded == 14 ? true : false : xAdded == 14 ? zAdded > 0 && zAdded < 15 ? true : false : false;
	}
	
	private boolean isThirdLayer(int xAdded, int zAdded) {
		return xAdded == 2 ? zAdded > 1 && zAdded < 14 ? true : false : xAdded > 2 && xAdded < 13 ? zAdded == 2 || zAdded == 13 ? true : false : xAdded == 13 ? zAdded > 1 && zAdded < 14 ? true : false : false;
	}
	
	private void generateRandomMushPowerBush(BlockPos pos, Random rand, World world){
		this.generateMushPowerBush(pos, world, CommonProxy.getBushs().get(rand.nextInt(CommonProxy.getBushs().size()-1)));
	}
	
	private void generateMushPowerBush(BlockPos pos, World world, BushMush bush) {
		if (world.isAirBlock(pos) && (!world.provider.hasNoSky() || pos.getY() < world.getHeight() - 1)
				&& bush.canBlockStay(world, pos, bush.getDefaultState()))
			world.setBlockState(pos, bush.getDefaultState(), 2);
	}
	
	private boolean isOnSurface(BlockPos pos, World world) {
		if(world.isAirBlock(pos) || !this.isSurfaceBlockCompatible(world.getBlockState(pos).getBlock()))
			return false;
		BlockPos underground = new BlockPos(pos.getX(), pos.getY()-1, pos.getZ());
		if(world.isAirBlock(underground) || !this.isSurfaceBlockCompatible(world.getBlockState(underground).getBlock()))
			return false;
		for(int height = 1; height+pos.getY() <= 256; height++){
			if(!world.isAirBlock(pos) || !this.isSurfaceBlockCompatible(world.getBlockState(new BlockPos(pos.getX(), pos.getY()+height, pos.getZ())).getBlock()))
				return false;
		}
		return true;
	}
	
	private BlockPos setAtSurface(BlockPos actualPos, World world){
		for(int i = 256; i > 0; i--){ //Need at least 7 layers
			BlockPos pos2 = new BlockPos(actualPos.getX(), i, actualPos.getZ());
			if(this.isSurfaceBlockCompatible(world.getBlockState(pos2).getBlock()) && !world.isAirBlock(pos2)){
				if(i >= 249 || world.getBlockState(new BlockPos(pos2.getX(), pos2.getY()+1, pos2.getZ())).getBlock() instanceof BlockStaticLiquid) {
					pos2 = null;
					actualPos = null;
					break;
				}
				actualPos = pos2;
				break;
			}
			if(i == 1)
				actualPos = null;
		}
		return actualPos;
	}
	
	private boolean isSurfaceBlockCompatible(Block block){
		return Block.isEqualTo(block, Blocks.LEAVES) ? false : Block.isEqualTo(block, Blocks.LEAVES2) ? false : 
			Block.isEqualTo(block, Blocks.TALLGRASS) ? false : Block.isEqualTo(block, Blocks.WATER) ? false : Block.isEqualTo(block, Blocks.LAVA) ? false : 
			Block.isEqualTo(block, Blocks.SNOW_LAYER) ? false : Block.isEqualTo(block, Blocks.RED_FLOWER) ? false :
			Block.isEqualTo(block, Blocks.YELLOW_FLOWER) ? false : Block.isEqualTo(block, Blocks.DEADBUSH) ? false :
			Block.isEqualTo(block, Blocks.SAPLING) ? false : Block.isEqualTo(block, Blocks.BROWN_MUSHROOM) ? false :
			Block.isEqualTo(block, Blocks.RED_MUSHROOM) ? false : Block.isEqualTo(block, Blocks.RED_MUSHROOM_BLOCK) ? false : 
			Block.isEqualTo(block, Blocks.BROWN_MUSHROOM_BLOCK) ? false : block instanceof BushMush ? false : 
			Block.isEqualTo(block, Blocks.VINE) ? false : Block.isEqualTo(block, Blocks.REEDS) ? false : Block.isEqualTo(block, Blocks.WATERLILY) ? false : 
			Block.isEqualTo(block, Blocks.DOUBLE_PLANT) ? false : Block.isEqualTo(block, Blocks.LOG) ? false : Block.isEqualTo(block, Blocks.LOG2) ? false : true;
	}
	
	private boolean needRemoved(BlockPos posUpper, World world){
		Block block = world.getBlockState(posUpper).getBlock();
		return Block.isEqualTo(block, Blocks.TALLGRASS) ? true : Block.isEqualTo(block, Blocks.SNOW_LAYER) ? true : 
			Block.isEqualTo(block, Blocks.RED_FLOWER) ? true : Block.isEqualTo(block, Blocks.YELLOW_FLOWER) ? true : 
				Block.isEqualTo(block, Blocks.DEADBUSH) ? true : Block.isEqualTo(block, Blocks.SAPLING) ? true : 
					Block.isEqualTo(block, Blocks.BROWN_MUSHROOM) ? false : Block.isEqualTo(block, Blocks.RED_MUSHROOM) ? true : 
						Block.isEqualTo(block, Blocks.VINE) ? true : Block.isEqualTo(block, Blocks.REEDS) ? true : 
							Block.isEqualTo(block, Blocks.WATERLILY) ? true : Block.isEqualTo(block, Blocks.DOUBLE_PLANT) ? true : 
								block instanceof BushMush ? true : false;
	}
	
	private boolean isBiomeCorrect(int biomeID){
		return biomeID != Biome.getIdForBiome(Biomes.DEEP_OCEAN) && biomeID != Biome.getIdForBiome(Biomes.EXTREME_HILLS)
			&& biomeID != Biome.getIdForBiome(Biomes.EXTREME_HILLS_EDGE) && biomeID != Biome.getIdForBiome(Biomes.EXTREME_HILLS_WITH_TREES)
			&& biomeID != Biome.getIdForBiome(Biomes.MUTATED_EXTREME_HILLS) && biomeID != Biome.getIdForBiome(Biomes.MUTATED_EXTREME_HILLS_WITH_TREES)
			&& biomeID != Biome.getIdForBiome(Biomes.HELL) && biomeID != Biome.getIdForBiome(Biomes.JUNGLE)
			&& biomeID != Biome.getIdForBiome(Biomes.JUNGLE_EDGE) && biomeID != Biome.getIdForBiome(Biomes.JUNGLE_HILLS)
			&& biomeID != Biome.getIdForBiome(Biomes.MUTATED_JUNGLE) && biomeID != Biome.getIdForBiome(Biomes.MUTATED_JUNGLE_EDGE)
			&& biomeID != Biome.getIdForBiome(Biomes.OCEAN) && biomeID != Biome.getIdForBiome(Biomes.SKY)
			&& biomeID != Biome.getIdForBiome(Biomes.VOID);
	}

}
