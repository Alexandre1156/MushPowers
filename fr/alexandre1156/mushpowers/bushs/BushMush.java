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
		this.func_149711_c(0.0f);
		this.func_149672_a(SoundType.field_185850_c);
		this.func_149715_a(0.125f);
		this.func_149647_a(CreativeTabs.field_78031_c);
		this.powerCorrespondence = mushPowerCorrespondence;
	}
	
	@Override
	public Item func_180660_a(IBlockState state, Random rand, int fortune) {
		return Item.func_150898_a(this);
	}
	
	@Override
	public AxisAlignedBB func_185496_a(IBlockState state, IBlockAccess source, BlockPos pos) {
		return this.MUSHROOM_AABB;
	}
	
	@Override
	public boolean func_176196_c(World worldIn, BlockPos pos) {
        return super.func_176196_c(worldIn, pos) && this.func_180671_f(worldIn, pos, this.func_176223_P());
    }
	
	@Override
	protected boolean func_185514_i(IBlockState state) {
        return state.func_185913_b();
    }
	
	@Override
	public boolean func_180671_f(World worldIn, BlockPos pos, IBlockState state) {
        if (pos.func_177956_o() >= 0 && pos.func_177956_o() < 256) {
            IBlockState iblockstate = worldIn.func_180495_p(pos.func_177977_b());
            return iblockstate.func_177230_c() == Blocks.field_150391_bh ? true : (iblockstate.func_177230_c() == Blocks.field_150346_d && iblockstate.func_177229_b(BlockDirt.field_176386_a) == BlockDirt.DirtType.PODZOL ? true : worldIn.func_175699_k(pos) < 13 && iblockstate.func_177230_c().canSustainPlant(iblockstate, worldIn, pos.func_177977_b(), net.minecraft.util.EnumFacing.UP, this));
        } else {
            return false;
        }
    }
	
	public final Item getMushPowerCorrespondence(){
		return this.powerCorrespondence;
	}

}
