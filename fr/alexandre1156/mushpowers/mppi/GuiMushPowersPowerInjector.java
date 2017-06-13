package fr.alexandre1156.mushpowers.mppi;

import java.awt.Color;

import com.google.common.collect.Lists;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiMushPowersPowerInjector extends GuiContainer {

	private TileEntityMushPowersPowerInjector te;
	private IInventory playerInv;
	private static final ResourceLocation MPPI_GUI_TEXTURE = new ResourceLocation("mush",
			"textures/container/mppi.png");

	public GuiMushPowersPowerInjector(IInventory playerInv, TileEntityMushPowersPowerInjector tileEntity) {
		super(new ContainerMushPowersPowerInjector((InventoryPlayer) playerInv, tileEntity));
		this.te = tileEntity;
		this.playerInv = playerInv;
	}

	@Override
	protected void func_146976_a(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
		this.field_146297_k.func_110434_K().func_110577_a(MPPI_GUI_TEXTURE);
		int i = (this.field_146294_l - this.field_146999_f) / 2;
		int j = (this.field_146295_m - this.field_147000_g) / 2;
		this.func_73729_b(i, j, 0, 0, this.field_146999_f, this.field_147000_g);
	}

	@Override
	protected void func_146979_b(int mouseX, int mouseY) {
		String name = I18n.func_135052_a("container.mppi", new Object[0]);
		this.field_146289_q.func_78276_b(name, this.field_146999_f / 2 - this.field_146289_q.func_78256_a(name) / 2, 6,
				4210752);
		this.field_146289_q.func_78276_b(this.playerInv.func_145748_c_().func_150260_c(), 8, this.field_147000_g - 96 + 2,
				4210752);
		int i = (this.field_146294_l - this.field_146999_f) / 2;
		int j = (this.field_146295_m - this.field_147000_g) / 2;
		if (this.te.isConsumingUp()) {
			GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
			this.field_146297_k.func_110434_K().func_110577_a(MPPI_GUI_TEXTURE);
			int width = this.te.getCooldownUp() / 2 / 4;
			this.func_73729_b(79, 17, 176, 14, width, 17);
		}
		if(this.te.isConsumingDown()){
			GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
			this.field_146297_k.func_110434_K().func_110577_a(MPPI_GUI_TEXTURE);
			int width = this.te.getCooldownDown() / 2 / 24;
			this.func_73729_b(79, 54, 176, 14, width, 17);
		}
		if(mouseX >= (this.field_147003_i+80) && mouseX < (this.field_147003_i+102) && mouseY > (this.field_147009_r+50) && mouseY < (this.field_147009_r+70))
			this.func_146283_a(Lists.newArrayList(this.te.getCooldownDown()*100/1200+"%"), 69, 69);
		if(mouseX >= (this.field_147003_i+80) && mouseX < (this.field_147003_i+102) && mouseY > (this.field_147009_r+15) && mouseY < (this.field_147009_r+35))
			this.func_146283_a(Lists.newArrayList(this.te.getCooldownUp()*100/200+"%"), 69, 33);
		if(this.te.isDesactivedItem()){
			RenderHelper.func_74518_a();
			this.func_73731_b(field_146289_q, "DESACTIVED SHROOM", 20, 40, Color.red.getRGB());
			RenderHelper.func_74519_b();
		}
	}

}
