package fr.alexandre1156.mushpowers;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class Electrocution extends EntityDamageSourceIndirect {

	public Electrocution(Entity attacker, Entity victim) {
		super("electrocution.death.message", attacker, victim);
	}
	
	public static DamageSource causeSwordDamage(Entity ent, Entity ent2){
		return (new EntityDamageSourceIndirect("sword", ent, ent2));
	}
	
	public static DamageSource causeElectrocution(Entity attacker, Entity victim){
		return new EntityDamageSourceIndirect("electrocution", attacker, victim);
	}
//	
//	@Override
//	public static DamageSource causePlayerDamage(EntityPlayer player)
//    {
//        return new EntityDamageSource("player", player);
//    }
	
	@Override
	public ITextComponent getDeathMessage(EntityLivingBase entityLivingBaseIn) {
		return new TextComponentTranslation("electrocution.death.message", entityLivingBaseIn.getName());
	}

}
