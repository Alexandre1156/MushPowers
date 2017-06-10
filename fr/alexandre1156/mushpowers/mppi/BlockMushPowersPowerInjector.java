package fr.alexandre1156.mushpowers.mppi;

import java.util.Random;

import fr.alexandre1156.mushpowers.MushPowers;
import fr.alexandre1156.mushpowers.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockMushPowersPowerInjector extends Block implements ITileEntityProvider {

	public BlockMushPowersPowerInjector() {
		super(Material.field_151576_e);
		this.func_149663_c("mppi");
		this.setRegistryName(new ResourceLocation(Reference.MOD_ID, "mppi"));
		this.func_149711_c(2f);
		this.func_149752_b(2f);
		this.func_149647_a(CreativeTabs.field_78026_f);
	}

	@Override
	public Item func_180660_a(IBlockState state, Random rand, int fortune) {
		return Item.func_150898_a(this);
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityMushPowersPowerInjector();
	}

	@Override
	public boolean func_180639_a(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.field_72995_K)
			playerIn.openGui(MushPowers.instance, 0, worldIn, pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p());
		return true;
	}

	@Override
	public TileEntity func_149915_a(World worldIn, int meta) {
		return new TileEntityMushPowersPowerInjector();
	}

	@Override
	public void func_180663_b(World worldIn, BlockPos pos, IBlockState state) {
		TileEntity tileentity = worldIn.func_175625_s(pos);
		if (tileentity instanceof TileEntityMushPowersPowerInjector){
			TileEntityMushPowersPowerInjector te = (TileEntityMushPowersPowerInjector) tileentity;
			for(int i = 0; i < te.getItems().size(); i++)
				InventoryHelper.func_180173_a(worldIn, pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p(), te.getItems().get(i));
		}
		super.func_180663_b(worldIn, pos, state);
	}

}
