package fr.alexandre1156.mushpowers;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import fr.alexandre1156.mushpowers.items.shrooms.ItemMushPowers;
import fr.alexandre1156.mushpowers.proxy.CommonProxy;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class ShroomRodStickRecipe implements IRecipe {

    private final ItemStack recipeOutput;
    public final ArrayList<ItemStack> recipeItems;

    public ShroomRodStickRecipe() {
        this.recipeOutput = new ItemStack(CommonProxy.itemShroomRodStick, 1, 0);
        this.recipeItems = Lists.newArrayList(new ItemStack(Items.FISHING_ROD));
        for(Item mushPowersItem : CommonProxy.getMushPowersCompatibleEntity())
        	this.recipeItems.add(new ItemStack(mushPowersItem));
    }
	
	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
		List<ItemStack> list = Lists.newArrayList(this.recipeItems);
        for (int i = 0; i < inv.getHeight(); ++i) {
            for (int j = 0; j < inv.getWidth(); ++j) {
                ItemStack itemstack = inv.getStackInRowAndColumn(j, i);
                if (!itemstack.isEmpty()) {
                    boolean flag = false;
                    for (ItemStack itemstack1 : list) {
                        if (itemstack.getItem() == itemstack1.getItem() && (itemstack1.getMetadata() == 32767 || itemstack.getMetadata() == itemstack1.getMetadata())) {
                            flag = true;
                            list.remove(itemstack1);
                            break;
                        }
                    }

                    if (!flag)
                        return false;
                }
            }
        }
        return list.size() == CommonProxy.getMushPowersCompatibleEntity().size()-1;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		for(int x = 0; x < inv.getWidth(); x++){
			for(int y = 0; y < inv.getHeight(); y++){
				if(inv.getStackInRowAndColumn(x, y).getItem() instanceof ItemMushPowers) {
					ItemMushPowers mushPowers = (ItemMushPowers) inv.getStackInRowAndColumn(x, y).getItem();
					ObfuscationReflectionHelper.setPrivateValue(ItemStack.class, this.recipeOutput, CommonProxy.getRodStickMetadataWithMushPowersRodStickRegsistry(mushPowers.getRegistryName().getResourcePath()), 7);
					break;
				}
			}
		}

        return this.recipeOutput.copy();
	}

	@Override
	public int getRecipeSize() {
		return this.recipeItems.size();
	}

	@Override
	public ItemStack getRecipeOutput() {
		return recipeOutput;
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
		NonNullList<ItemStack> nonnulllist = NonNullList.<ItemStack>withSize(inv.getSizeInventory(), ItemStack.EMPTY);
        for (int i = 0; i < nonnulllist.size(); ++i) {
            ItemStack itemstack = inv.getStackInSlot(i);
            nonnulllist.set(i, net.minecraftforge.common.ForgeHooks.getContainerItem(itemstack));
        }
        return nonnulllist;
	}

}
