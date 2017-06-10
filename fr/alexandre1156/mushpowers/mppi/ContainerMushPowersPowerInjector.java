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
		this.func_75146_a(new SlotMPPIInputUp(handler, 0, 56, 17, tileEnt));
		this.func_75146_a(new SlotMPPIInputDownTwo(handler, 1, 56, 53, tileEnt));
		this.func_75146_a(new SlotMPPIOutput(handler, 2, 112, 17, playerInv.field_70458_d));
		this.func_75146_a(new SlotMPPIOutput(handler, 3, 112, 53, playerInv.field_70458_d));
		this.func_75146_a(new SlotMPPIInputDownOne(handler, 4, 36, 53, tileEnt));

		for (int y = 0; y < 3; y++) { // Add player Inventory
			for (int x = 0; x < 9; x++)
				this.func_75146_a(new Slot(playerInv, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
		}

		for (int x = 0; x < 9; x++) // Add player hotbar
			this.func_75146_a(new Slot(playerInv, x, 8 + x * 18, 142));

	}

	@Override
	public boolean func_75145_c(EntityPlayer playerIn) {
		return !playerIn.func_175149_v();
	}

	public ItemStack func_82846_b(EntityPlayer playerIn, int index) {
		ItemStack itemstack = ItemStack.field_190927_a;
		Slot slot = (Slot) this.field_75151_b.get(index);

		if (slot != null && slot.func_75216_d()) {
			ItemStack itemstack1 = slot.func_75211_c();
			itemstack = itemstack1.func_77946_l();

			if (index == 0 || index == 1 || index == 2 || index == 3 || index == 4) {
				if (!this.func_75135_a(itemstack1, 5, 41, true)) 
					return ItemStack.field_190927_a;
				if(index == 2 && itemstack1.func_190916_E() >= 64)
					this.te.setConsumingUp(true);
				if(index == 3 && itemstack1.func_190916_E() >= 64)
					this.te.setConsumingDown(true);
				slot.func_75220_a(itemstack1, itemstack);
			} else {
				if(itemstack1.func_77969_a(new ItemStack(Blocks.field_150337_Q)) && this.func_75135_a(itemstack1, 0, 1, false))
					return ItemStack.field_190927_a;
				if(itemstack1.func_77969_a(new ItemStack(CommonProxy.itemMushElexir)) && this.func_75135_a(itemstack1, 4, 5, false))
					return ItemStack.field_190927_a;
				if(CommonProxy.isModItem(itemstack1.func_77973_b()) && this.func_75135_a(itemstack1, 1, 2, false))
					return ItemStack.field_190927_a;
				if(index >= 32 && index < 41 && this.func_75135_a(itemstack1, 5, 31, false))
					return ItemStack.field_190927_a;
				if(index >= 5 && index < 31 && this.func_75135_a(itemstack1, 32, 41, false))
					return ItemStack.field_190927_a;
			}
			if (itemstack1.func_190926_b()) {
				slot.func_75215_d(ItemStack.field_190927_a);
			} else {
				slot.func_75218_e();
			}

			if (itemstack1.func_190916_E() == itemstack.func_190916_E()) {
				return ItemStack.field_190927_a;
			}

			slot.func_190901_a(playerIn, itemstack1);
		}
		return itemstack;
	}

}
