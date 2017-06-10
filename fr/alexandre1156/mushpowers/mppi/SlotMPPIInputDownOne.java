package fr.alexandre1156.mushpowers.mppi;

import fr.alexandre1156.mushpowers.proxy.CommonProxy;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotMPPIInputDownOne extends SlotItemHandler{

	private TileEntityMushPowersPowerInjector te;
	private int index;
	
	public SlotMPPIInputDownOne(IItemHandler itemHandler, int index, int xPosition, int yPosition, TileEntityMushPowersPowerInjector tile) {
		super(itemHandler, index, xPosition, yPosition);
		this.te = tile;
		this.index = index;
	}
	
	@Override
	public boolean func_75214_a(ItemStack stack) {
		return stack.func_77969_a(new ItemStack(CommonProxy.itemMushElexir));
	}
	
//	@Override
//	public ItemStack onTake(EntityPlayer thePlayer, ItemStack stack) {
//		this.te.setConsumingDown(false);
//		this.te.setCooldownUp(0);
//		return super.onTake(thePlayer, stack);
//	}

}
