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
	public boolean func_180709_b(World world, Random rand, BlockPos pos) {
		if(this.isBiomeCorrect(Biome.func_185362_a(world.func_180494_b(pos)))){
			ArrayList<BlockPos> list = Lists.<BlockPos>newArrayList();
			for(int x = 0; x < 16; x++){
				for(int z = 0; z < 16; z++){
					BlockPos blockPos = new BlockPos(pos.func_177958_n()+x, pos.func_177956_o(), pos.func_177952_p()+z);
					if(!this.isOnSurface(blockPos, world)){
						blockPos = this.setAtSurface(blockPos, world);
						if(blockPos == null)
							return false;
						else
							list.add(new BlockPos(blockPos.func_177958_n(), blockPos.func_177956_o(), blockPos.func_177952_p()));
					} else
						list.add(new BlockPos(blockPos.func_177958_n(), blockPos.func_177956_o(), blockPos.func_177952_p()));
				}
			}
			for(BlockPos pos2 : list) {
				BlockPos upperPos = new BlockPos(pos2.func_177958_n(), pos2.func_177956_o()+1, pos2.func_177952_p());
				if(this.needRemoved(upperPos, world))
					world.func_175698_g(upperPos);
				int xAdded = Math.abs(pos2.func_177958_n()-pos.func_177958_n());
				int zAdded = Math.abs(pos2.func_177952_p()-pos.func_177952_p());
				
				if(this.isFirstLayer(xAdded, zAdded)){
					if(rand.nextInt(4) == 0)
						world.func_175656_a(pos2, Blocks.field_150391_bh.func_176223_P());
				} else if(this.isSecondLayer(xAdded, zAdded)){
					if(rand.nextInt(3) == 0)
						world.func_175656_a(pos2, Blocks.field_150391_bh.func_176223_P());
				} else if(this.isThirdLayer(xAdded, zAdded)){
					if(rand.nextBoolean())
						world.func_175656_a(pos2, Blocks.field_150391_bh.func_176223_P());
				} else {
					world.func_175656_a(pos2, Blocks.field_150391_bh.func_176223_P());
				}
				if(rand.nextInt(15) == 0)
					(new WorldGenBigMushroom(Blocks.field_150419_aX)).func_180709_b(world, rand, upperPos);
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
		if (world.func_175623_d(pos) && (!world.field_73011_w.func_177495_o() || pos.func_177956_o() < world.func_72800_K() - 1)
				&& bush.func_180671_f(world, pos, bush.func_176223_P()))
			world.func_180501_a(pos, bush.func_176223_P(), 2);
	}
	
	private boolean isOnSurface(BlockPos pos, World world) {
		if(world.func_175623_d(pos) || !this.isSurfaceBlockCompatible(world.func_180495_p(pos).func_177230_c()))
			return false;
		BlockPos underground = new BlockPos(pos.func_177958_n(), pos.func_177956_o()-1, pos.func_177952_p());
		if(world.func_175623_d(underground) || !this.isSurfaceBlockCompatible(world.func_180495_p(underground).func_177230_c()))
			return false;
		for(int height = 1; height+pos.func_177956_o() <= 256; height++){
			if(!world.func_175623_d(pos) || !this.isSurfaceBlockCompatible(world.func_180495_p(new BlockPos(pos.func_177958_n(), pos.func_177956_o()+height, pos.func_177952_p())).func_177230_c()))
				return false;
		}
		return true;
	}
	
	private BlockPos setAtSurface(BlockPos actualPos, World world){
		for(int i = 256; i > 0; i--){ //Need at least 7 layers
			BlockPos pos2 = new BlockPos(actualPos.func_177958_n(), i, actualPos.func_177952_p());
			if(this.isSurfaceBlockCompatible(world.func_180495_p(pos2).func_177230_c()) && !world.func_175623_d(pos2)){
				if(i >= 249 || world.func_180495_p(new BlockPos(pos2.func_177958_n(), pos2.func_177956_o()+1, pos2.func_177952_p())).func_177230_c() instanceof BlockStaticLiquid) {
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
		return Block.func_149680_a(block, Blocks.field_150362_t) ? false : Block.func_149680_a(block, Blocks.field_150361_u) ? false : 
			Block.func_149680_a(block, Blocks.field_150329_H) ? false : Block.func_149680_a(block, Blocks.field_150355_j) ? false : Block.func_149680_a(block, Blocks.field_150353_l) ? false : 
			Block.func_149680_a(block, Blocks.field_150431_aC) ? false : Block.func_149680_a(block, Blocks.field_150328_O) ? false :
			Block.func_149680_a(block, Blocks.field_150327_N) ? false : Block.func_149680_a(block, Blocks.field_150330_I) ? false :
			Block.func_149680_a(block, Blocks.field_150345_g) ? false : Block.func_149680_a(block, Blocks.field_150338_P) ? false :
			Block.func_149680_a(block, Blocks.field_150337_Q) ? false : Block.func_149680_a(block, Blocks.field_150419_aX) ? false : 
			Block.func_149680_a(block, Blocks.field_150420_aW) ? false : block instanceof BushMush ? false : 
			Block.func_149680_a(block, Blocks.field_150395_bd) ? false : Block.func_149680_a(block, Blocks.field_150436_aH) ? false : Block.func_149680_a(block, Blocks.field_150392_bi) ? false : 
			Block.func_149680_a(block, Blocks.field_150398_cm) ? false : Block.func_149680_a(block, Blocks.field_150364_r) ? false : Block.func_149680_a(block, Blocks.field_150363_s) ? false : true;
	}
	
	private boolean needRemoved(BlockPos posUpper, World world){
		Block block = world.func_180495_p(posUpper).func_177230_c();
		return Block.func_149680_a(block, Blocks.field_150329_H) ? true : Block.func_149680_a(block, Blocks.field_150431_aC) ? true : 
			Block.func_149680_a(block, Blocks.field_150328_O) ? true : Block.func_149680_a(block, Blocks.field_150327_N) ? true : 
				Block.func_149680_a(block, Blocks.field_150330_I) ? true : Block.func_149680_a(block, Blocks.field_150345_g) ? true : 
					Block.func_149680_a(block, Blocks.field_150338_P) ? false : Block.func_149680_a(block, Blocks.field_150337_Q) ? true : 
						Block.func_149680_a(block, Blocks.field_150395_bd) ? true : Block.func_149680_a(block, Blocks.field_150436_aH) ? true : 
							Block.func_149680_a(block, Blocks.field_150392_bi) ? true : Block.func_149680_a(block, Blocks.field_150398_cm) ? true : 
								block instanceof BushMush ? true : false;
	}
	
	private boolean isBiomeCorrect(int biomeID){
		return biomeID != Biome.func_185362_a(Biomes.field_150575_M) && biomeID != Biome.func_185362_a(Biomes.field_76770_e)
			&& biomeID != Biome.func_185362_a(Biomes.field_76783_v) && biomeID != Biome.func_185362_a(Biomes.field_150580_W)
			&& biomeID != Biome.func_185362_a(Biomes.field_185443_S) && biomeID != Biome.func_185362_a(Biomes.field_185434_af)
			&& biomeID != Biome.func_185362_a(Biomes.field_76778_j) && biomeID != Biome.func_185362_a(Biomes.field_76782_w)
			&& biomeID != Biome.func_185362_a(Biomes.field_150574_L) && biomeID != Biome.func_185362_a(Biomes.field_76792_x)
			&& biomeID != Biome.func_185362_a(Biomes.field_185446_X) && biomeID != Biome.func_185362_a(Biomes.field_185447_Y)
			&& biomeID != Biome.func_185362_a(Biomes.field_76771_b) && biomeID != Biome.func_185362_a(Biomes.field_76779_k)
			&& biomeID != Biome.func_185362_a(Biomes.field_185440_P);
	}

}
