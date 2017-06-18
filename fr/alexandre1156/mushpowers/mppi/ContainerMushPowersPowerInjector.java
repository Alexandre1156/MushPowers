package fr.alexandre1156.mushpowers.mppi;

import fr.alexandre1156.mushpowers.proxy.CommonProxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class ContainerMushPowersPowerInjector extends Container {

	private TileEntityMushPowersPowerInjector te;
	private int cookTime;
	private final int totalCookTime;

	public ContainerMushPowersPowerInjector(InventoryPlayer playerInv, TileEntityMushPowersPowerInjector tileEnt) {
		this.te = tileEnt;
		this.totalCookTime = 200;
		IItemHandler handler = tileEnt.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		this.addSlotToContainer(new SlotMPPIInputUp(handler, 0, 56, 17, tileEnt));
		this.addSlotToContainer(new SlotMPPIInputDownTwo(handler, 1, 56, 53, tileEnt));
		this.addSlotToContainer(new SlotMPPIOutput(handler, 2, 112, 17, playerInv.player));
		this.addSlotToContainer(new SlotMPPIOutput(handler, 3, 112, 53, playerInv.player));
		this.addSlotToContainer(new SlotMPPIInputDownOne(handler, 4, 36, 53, tileEnt));

		for (int y = 0; y < 3; y++) { // Add player Inventory
			for (int x = 0; x < 9; x++)
				this.addSlotToContainer(new Slot(playerInv, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
		}

		for (int x = 0; x < 9; x++) // Add player hotbar
			this.addSlotToContainer(new Slot(playerInv, x, 8 + x * 18, 142));

	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return !playerIn.isSpectator();
	}

	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index == 0 || index == 1 || index == 2 || index == 3 || index == 4) {
				if (!this.mergeItemStack(itemstack1, 5, 41, true)) 
					return ItemStack.EMPTY;
				if(index == 2 && itemstack1.getCount() >= 64)
					this.te.setConsumingUp(true);
				if(index == 3 && itemstack1.getCount() >= 64)
					this.te.setConsumingDown(true);
				slot.onSlotChange(itemstack1, itemstack);
			} else {
				if(itemstack1.isItemEqual(new ItemStack(Blocks.RED_MUSHROOM)) && this.mergeItemStack(itemstack1, 0, 1, false))
					return ItemStack.EMPTY;
				if(itemstack1.isItemEqual(new ItemStack(CommonProxy.itemMushElexir)) && this.mergeItemStack(itemstack1, 4, 5, false))
					return ItemStack.EMPTY;
				if(CommonProxy.isModItem(itemstack1.getItem()) && this.mergeItemStack(itemstack1, 1, 2, false))
					return ItemStack.EMPTY;
				if(index >= 32 && index < 41 && this.mergeItemStack(itemstack1, 5, 31, false))
					return ItemStack.EMPTY;
				if(index >= 5 && index < 31 && this.mergeItemStack(itemstack1, 32, 41, false))
					return ItemStack.EMPTY;
			}
			if (itemstack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(playerIn, itemstack1);
		}
		return itemstack;
	}

}
