package fr.alexandre1156.mushpowers.structure;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;

public class StructureMushPowerGenerator implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		switch(world.provider.getDimension()){
		case 0:
			if(random.nextInt(50) == 0) {
				int x = chunkX * 16 + random.nextInt(16);
				int y = random.nextInt(128);
				int z = chunkZ * 16 + random.nextInt(16);
				(new WorldGenMushPower()).generate(world, random, new BlockPos(x, y, z));
			}
			break;
		}
	}

}
