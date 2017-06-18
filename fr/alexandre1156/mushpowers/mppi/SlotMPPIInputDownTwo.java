package fr.alexandre1156.mushpowers.mppi;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotMPPIInputDownTwo extends SlotItemHandler{

	private TileEntityMushPowersPowerInjector te;
	private int index;
	
	public SlotMPPIInputDownTwo(IItemHandler itemHandler, int index, int xPosition, int yPosition, TileEntityMushPowersPowerInjector tile) {
		super(itemHandler, index, xPosition, yPosition);
		this.te = tile;
		this.index = index;
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		return this.te.canBeSmelted(stack);
	}

}
