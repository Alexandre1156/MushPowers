package fr.alexandre1156.mushpowers.config;

import fr.alexandre1156.mushpowers.MushPowers;
import fr.alexandre1156.mushpowers.Reference;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;

public class GuiConfigMushPowers extends GuiConfig {

	public GuiConfigMushPowers(GuiScreen parentScreen) {
		super(parentScreen,
	            new ConfigElement(MushPowers.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
	            Reference.MOD_ID,
	            false,
	            false,
	            "MushPowers Config");
		this.titleLine2 = GuiConfig.getAbridgedConfigPath(MushPowers.config.toString());
//		Pattern commaDelimitedPattern = Pattern.compile("([A-Za-z]+((,){1}( )*|$))+?");
//		this.configElements.add(new DummyConfigElement("canSeeCursedShroom", "Separate,Each,Players,Name,With,A,Comma", ConfigGuiType.STRING, "mush.config.canSeeCursedShroom", commaDelimitedPattern));
	}

	@Override
	public void func_73866_w_() {
		
		super.func_73866_w_();
	}
	
	@Override
	public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
		
		super.func_73863_a(mouseX, mouseY, partialTicks);
	}
	
	@Override
	protected void func_146284_a(GuiButton button) {
		
		super.func_146284_a(button);
	}
	
	
	
	@Override
	public void func_146281_b() {
		MushPowers.syncConfig();
		super.func_146281_b();
	}
	
}
