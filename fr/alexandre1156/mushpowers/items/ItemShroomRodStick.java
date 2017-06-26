package fr.alexandre1156.mushpowers.items;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import fr.alexandre1156.mushpowers.MushUtils;
import fr.alexandre1156.mushpowers.Reference;
import fr.alexandre1156.mushpowers.items.shrooms.ItemMushPowers;
import fr.alexandre1156.mushpowers.items.shrooms.Regenshroom;
import fr.alexandre1156.mushpowers.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;

public class ItemShroomRodStick extends Item {
	
	private final String[] type = new String[]{"normal", "regen", "chicken", "shield", "fly", "random"};

	public ItemShroomRodStick() {
		this.setUnlocalizedName("shroomrodstick");
		this.setRegistryName(new ResourceLocation(Reference.MOD_ID, "shroomrodstick"));
		this.setCreativeTab(CreativeTabs.MISC);
		this.setHasSubtypes(true);
	}
	
	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
		Item mushPowers = CommonProxy.getMushPowersRodStickByMetadata(stack.getMetadata());
		if(mushPowers != null && target instanceof EntityLivingBase && !(target instanceof EntityPlayer) && mushPowers instanceof ItemMushPowers) {
			if(((ItemMushPowers) mushPowers).compatibleEntityBase(target)) {
		        boolean happend = ((ItemMushPowers) mushPowers).onUsedOnLivingEntity(playerIn.getEntityWorld(), (EntityLivingBase) target, playerIn);
		        if(happend)
		        	stack.shrink(1);
			} else 
				playerIn.sendStatusMessage(new TextComponentTranslation("rodstick.uncompatible.message", new Object[0]), true);
	    }
		return false;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int metadata = stack.getItemDamage();
		if(metadata > CommonProxy.getMushPowers().size() || metadata < 0)
			metadata = 0;
		return super.getUnlocalizedName() + "." + type[metadata];
	}
	
	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems) {
		int metadata = 0;
		subItems.add(new ItemStack(itemIn, 1, 0));
		for(ItemMushPowers mush : CommonProxy.getMushPowers()) {
			if(mush.isEntityLivingCompatible()) {
				metadata++;
				subItems.add(new ItemStack(itemIn, 1, metadata));
			}
		}
	}
	
	@Override
	public int getMetadata(int damage) {
		return damage;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		if(stack.getMetadata() == 0)
			tooltip.add("Effective MushPowers : None");
		else {
			Item power = CommonProxy.getMushPowersRodStickByMetadata(stack.getMetadata());
			if(power != null && power instanceof ItemMushPowers) {
				tooltip.add("Effective MushPowers : "+MushUtils.startStringWithCapital(((ItemMushPowers)power).getColorName()+power.getRegistryName().getResourcePath()));
				if(power instanceof Regenshroom)
					tooltip.add(ChatFormatting.GREEN+"Compatible entity : Animals, Bat and Creature except Zombie and Skeleton");
				else
					tooltip.add(ChatFormatting.GREEN+"Compatible entity : Animals, Bat and Creature");
			} else
				tooltip.add("Effective MushPowers : ERROR");
		}
	}
	
}
