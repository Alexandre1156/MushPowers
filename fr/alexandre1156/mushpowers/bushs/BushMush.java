package fr.alexandre1156.mushpowers.bushs;

import java.util.Random;

import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BushMush extends BlockBush {
	
	protected final Item powerCorrespondence;
	protected static final AxisAlignedBB MUSHROOM_AABB = new AxisAlignedBB(0.30000001192092896D, 0.0D, 0.30000001192092896D, 0.699999988079071D, 0.4000000059604645D, 0.699999988079071D);
	
	public BushMush(Item mushPowerCorrespondence) {
		this.setHardness(0.0f);
		this.setSoundType(SoundType.PLANT);
		this.setLightLevel(0.125f);
		this.setCreativeTab(CreativeTabs.DECORATIONS);
		this.powerCorrespondence = mushPowerCorrespondence;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(this);
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return this.MUSHROOM_AABB;
	}
	
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return super.canPlaceBlockAt(worldIn, pos) && this.canBlockStay(worldIn, pos, this.getDefaultState());
    }
	
	@Override
	protected boolean canSustainBush(IBlockState state) {
        return state.isFullBlock();
    }
	
	@Override
	public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
        if (pos.getY() >= 0 && pos.getY() < 256) {
            IBlockState iblockstate = worldIn.getBlockState(pos.down());
            return iblockstate.getBlock() == Blocks.MYCELIUM ? true : (iblockstate.getBlock() == Blocks.DIRT && iblockstate.getValue(BlockDirt.VARIANT) == BlockDirt.DirtType.PODZOL ? true : worldIn.getLight(pos) < 13 && iblockstate.getBlock().canSustainPlant(iblockstate, worldIn, pos.down(), net.minecraft.util.EnumFacing.UP, this));
        } else {
            return false;
        }
    }
	
	public final Item getMushPowerCorrespondence(){
		return this.powerCorrespondence;
	}

}
