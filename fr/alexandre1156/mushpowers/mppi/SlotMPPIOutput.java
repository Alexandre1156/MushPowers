package fr.alexandre1156.mushpowers.mppi;

import fr.alexandre1156.mushpowers.MushPowers;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotMPPIOutput extends SlotItemHandler {

	private int removeCount;
	private EntityPlayer player;
	
	public SlotMPPIOutput(IItemHandler itemHandler, int index, int xPosition, int yPosition, EntityPlayer player) {
		super(itemHandler, index, xPosition, yPosition);
		this.player = player;
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return false;
	}

	public ItemStack decrStackSize(int amount) {
		if (this.getHasStack()) {
			this.removeCount += Math.min(amount, this.getStack().getCount());
		}

		return super.decrStackSize(amount);
	}

	public ItemStack onTake(EntityPlayer thePlayer, ItemStack stack) {
		if(thePlayer instanceof EntityPlayerMP)
			MushPowers.CRAFT_MUSHPOWERS.trigger((EntityPlayerMP) player, stack, ((EntityPlayerMP) thePlayer).openContainer);
		this.onCrafting(stack);
		super.onTake(thePlayer, stack);
		return stack;
	}

	/**
	 * the itemStack passed in is the output - ie, iron ingots, and pickaxes,
	 * not ore and wood. Typically increases an internal count then calls
	 * onCrafting(item).
	 */
	protected void onCrafting(ItemStack stack, int amount) {
		this.removeCount += amount;
		this.onCrafting(stack);
	}

	/**
	 * the itemStack passed in is the output - ie, iron ingots, and pickaxes,
	 * not ore and wood.
	 */
	protected void onCrafting(ItemStack stack) {
		stack.onCrafting(this.player.world, this.player, this.removeCount);

		if (!this.player.world.isRemote) {
			int i = this.removeCount;
			float f = FurnaceRecipes.instance().getSmeltingExperience(stack);

			if (f == 0.0F) {
				i = 0;
			} else if (f < 1.0F) {
				int j = MathHelper.floor((float) i * f);

				if (j < MathHelper.ceil((float) i * f) && Math.random() < (double) ((float) i * f - (float) j)) {
					++j;
				}

				i = j;
			}

			while (i > 0) {
				int k = EntityXPOrb.getXPSplit(i);
				i -= k;
				this.player.world.spawnEntity(new EntityXPOrb(this.player.world, this.player.posX,
						this.player.posY + 0.5D, this.player.posZ + 0.5D, k));
			}
		}
		this.removeCount = 0;
	}

}
