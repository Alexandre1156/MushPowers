package fr.alexandre1156.mushpowers.mppi;

import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
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
	public boolean func_75214_a(ItemStack stack) {
		return false;
	}

	public ItemStack func_75209_a(int amount) {
		if (this.func_75216_d()) {
			this.removeCount += Math.min(amount, this.func_75211_c().func_190916_E());
		}

		return super.func_75209_a(amount);
	}

	public ItemStack func_190901_a(EntityPlayer thePlayer, ItemStack stack) {
		this.func_75208_c(stack);
		super.func_190901_a(thePlayer, stack);
		return stack;
	}

	/**
	 * the itemStack passed in is the output - ie, iron ingots, and pickaxes,
	 * not ore and wood. Typically increases an internal count then calls
	 * onCrafting(item).
	 */
	protected void func_75210_a(ItemStack stack, int amount) {
		this.removeCount += amount;
		this.func_75208_c(stack);
	}

	/**
	 * the itemStack passed in is the output - ie, iron ingots, and pickaxes,
	 * not ore and wood.
	 */
	protected void func_75208_c(ItemStack stack) {
		stack.func_77980_a(this.player.field_70170_p, this.player, this.removeCount);

		if (!this.player.field_70170_p.field_72995_K) {
			int i = this.removeCount;
			float f = FurnaceRecipes.func_77602_a().func_151398_b(stack);

			if (f == 0.0F) {
				i = 0;
			} else if (f < 1.0F) {
				int j = MathHelper.func_76141_d((float) i * f);

				if (j < MathHelper.func_76123_f((float) i * f) && Math.random() < (double) ((float) i * f - (float) j)) {
					++j;
				}

				i = j;
			}

			while (i > 0) {
				int k = EntityXPOrb.func_70527_a(i);
				i -= k;
				this.player.field_70170_p.func_72838_d(new EntityXPOrb(this.player.field_70170_p, this.player.field_70165_t,
						this.player.field_70163_u + 0.5D, this.player.field_70161_v + 0.5D, k));
			}
		}
		this.removeCount = 0;
	}

}
