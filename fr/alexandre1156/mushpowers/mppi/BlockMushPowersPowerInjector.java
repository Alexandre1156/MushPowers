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
		super(Material.ROCK);
		this.setUnlocalizedName("mppi");
		this.setRegistryName(new ResourceLocation(Reference.MOD_ID, "mppi"));
		this.setHardness(2f);
		this.setResistance(2f);
		this.setCreativeTab(CreativeTabs.MISC);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(this);
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityMushPowersPowerInjector();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote)
			playerIn.openGui(MushPowers.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityMushPowersPowerInjector();
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntity tileentity = worldIn.getTileEntity(pos);
		if (tileentity instanceof TileEntityMushPowersPowerInjector){
			TileEntityMushPowersPowerInjector te = (TileEntityMushPowersPowerInjector) tileentity;
			for(int i = 0; i < te.getItems().size(); i++)
				InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), te.getItems().get(i));
		}
		super.breakBlock(worldIn, pos, state);
	}

}
