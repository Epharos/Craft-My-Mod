package fr.epharos.craftmymod.utils;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.GameSettings;

public class GuiOptions extends net.minecraft.client.gui.GuiOptions 
{
	public GuiOptions(GuiScreen par1GuiScreen, GameSettings par2GameSettings) 
	{
		super(par1GuiScreen, par2GameSettings);
	}
	
	public void initGui()
	{
		this.buttonList.add(new GuiButton(2406, this.width / 2 - 152, this.height / 6 + 72 - 30, 304, 20, "Craft My Mod"));
		super.initGui();
	}
}
