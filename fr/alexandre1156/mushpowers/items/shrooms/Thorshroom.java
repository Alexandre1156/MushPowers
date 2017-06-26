package fr.alexandre1156.mushpowers.items.shrooms;

import java.util.List;

import fr.alexandre1156.mushpowers.particle.ShroomParticle;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class Thorshroom extends ItemMushPowers {

	public Thorshroom() {
		super(1, 0.0f, "thorshroom");
	}
	
	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		
	}

	@Override
	public TextFormatting getColorName() {
		return TextFormatting.OBFUSCATED;
		
	}

	@Override
	public boolean onUsedOnLivingEntity(World world, EntityLivingBase entLiv, EntityPlayer player) {return false;}

	@Override
	public ShroomParticle getParticleOnLivingEntity() {
		return null;
	}

	@Override
	public boolean isEntityLivingCompatible() {
		return false;
	}

}
