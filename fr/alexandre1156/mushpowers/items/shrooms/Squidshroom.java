package fr.alexandre1156.mushpowers.items.shrooms;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import fr.alexandre1156.mushpowers.MushUtils;
import fr.alexandre1156.mushpowers.Reference;
import fr.alexandre1156.mushpowers.capabilities.IPlayerMush;
import fr.alexandre1156.mushpowers.capabilities.PlayerMush.MainMushPowers;
import fr.alexandre1156.mushpowers.capabilities.PlayerMushProvider;
import fr.alexandre1156.mushpowers.config.MushConfig;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class Squidshroom extends ItemFood {

	public Squidshroom() {
		super(1, 0.0f, false);
		this.func_77655_b("squidshroom");
		this.setRegistryName(new ResourceLocation(Reference.MOD_ID, "squidshroom"));
		this.func_77637_a(CreativeTabs.field_78039_h);
		this.func_77848_i();
	}
	
	@Override
	protected void func_77849_c(ItemStack stack, World worldIn, EntityPlayer player) {
		IPlayerMush mush = player.getCapability(PlayerMushProvider.MUSH_CAP, null);
		if(!worldIn.field_72995_K && !mush.isSquid()){
			mush.setSquid(true);
			mush.setSquidAir(300);
			mush.setCooldown(MainMushPowers.SQUID, (short) MushConfig.cooldownSquid);
			PlayerMushProvider.resetOtherMainMushPower(player, MainMushPowers.SQUID);
			PlayerMushProvider.syncCapabilities(player);
			PlayerMushProvider.sendSquidPacket(player, true);
		}
//		for(int i = 0; i < worldIn.countEntities(EntitySquid.class); i++){
//			AxisAlignedBB box = worldIn.getEntities(EntitySquid.class, EntitySelectors.IS_ALIVE).get(i).getEntityBoundingBox();
//			EntitySquid ent = worldIn.getEntities(EntitySquid.class, EntitySelectors.IS_ALIVE).get(i);
//			System.out.println(box.maxX+" "+box.maxY+" "+box.maxZ+" "+box.minX+" "+box.minY+" "+box.minZ+" "+ent.width+" "+ent.height);
//		}
//		if(player instanceof EntityPlayerMP)
//			((EntityPlayerMP) player).connection.sendPacket(new SPacketPlayerAbilities(player.capabilities));
	}
	
//	@Override
//	public boolean onDroppedByPlayer(ItemStack item, EntityPlayer player) {
//		IPlayerMush mush = player.getCapability(PlayerMushProvider.MUSH_CAP, null);
//		mush.setSquid(false);
//		PlayerMushProvider.syncCapabilities(player);
//		return super.onDroppedByPlayer(item, player);
//	}
	
	@Override
	public ActionResult<ItemStack> func_77659_a(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack itemstack = playerIn.func_184586_b(handIn);
		if(MushConfig.isMushPowersDesactived(this))
			return new ActionResult(EnumActionResult.FAIL, itemstack);
		else
			return super.func_77659_a(worldIn, playerIn, handIn);
	}
	
	@Override
	public void func_77624_a(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		tooltip.add(ChatFormatting.WHITE+"The bubble bar will appear while on ground and dissapear while in water.\n You could also move faster in water, however players may fish you on online servers.\n To fish a player, you must be in a 3x3x3 radius of the end of the fishing rod.\n If you are fished, you will die, your items will be vanished.\n Your nametag will be invisible in water and you will disguise as a squid.");
		tooltip.add(ChatFormatting.GREEN+""+ChatFormatting.BOLD+"Lasts in "+MushUtils.correctCooldownMessage(MushConfig.cooldownSquid));
		if(MushConfig.isMushPowersDesactived(this))
			tooltip.add(ChatFormatting.RED+"THIS SHROOM IS DESACTIVED");
	}
	
}
