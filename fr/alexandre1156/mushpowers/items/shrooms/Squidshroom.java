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
		this.setUnlocalizedName("squidshroom");
		this.setRegistryName(new ResourceLocation(Reference.MOD_ID, "squidshroom"));
		this.setCreativeTab(CreativeTabs.FOOD);
		this.setAlwaysEdible();
	}
	
	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		IPlayerMush mush = player.getCapability(PlayerMushProvider.MUSH_CAP, null);
		if(!worldIn.isRemote && !mush.isSquid()){
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
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		if(MushConfig.isMushPowersDesactived(this))
			return new ActionResult(EnumActionResult.FAIL, itemstack);
		else
			return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		tooltip.add(ChatFormatting.WHITE+"The bubble bar will appear while on ground and dissapear while in water.\n You could also move faster in water, however players may fish you on online servers.\n To fish a player, you must be in a 3x3x3 radius of the end of the fishing rod.\n If you are fished, you will die, your items will be vanished.\n Your nametag will be invisible in water and you will disguise as a squid.");
		tooltip.add(ChatFormatting.GREEN+""+ChatFormatting.BOLD+"Lasts in "+MushUtils.correctCooldownMessage(MushConfig.cooldownSquid));
		if(MushConfig.isMushPowersDesactived(this))
			tooltip.add(ChatFormatting.RED+"THIS SHROOM IS DESACTIVED");
	}
	
}
