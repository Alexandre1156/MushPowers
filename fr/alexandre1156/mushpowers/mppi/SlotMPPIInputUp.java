package fr.alexandre1156.mushpowers.mppi;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotMPPIInputUp extends SlotItemHandler{

	private int index;
	private TileEntityMushPowersPowerInjector te;
	
	public SlotMPPIInputUp(IItemHandler inventoryIn, int index, int xPosition, int yPosition, TileEntityMushPowersPowerInjector tileEnt) {
		super(inventoryIn, index, xPosition, yPosition);
		this.index = index;
		this.te = tileEnt;
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		boolean valid = stack.isItemEqual(new ItemStack(Blocks.RED_MUSHROOM));
		if(valid)
			this.te.setConsumingUp(true);
		return valid;
	}
	
	@Override
	public ItemStack onTake(EntityPlayer thePlayer, ItemStack stack) {
		this.te.setConsumingUp(false);
		this.te.setCooldownUp(0);
		return super.onTake(thePlayer, stack);
	}
	

}
