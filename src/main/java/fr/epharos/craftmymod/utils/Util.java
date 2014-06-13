package fr.epharos.craftmymod.utils;

import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import fr.epharos.craftmymod.CraftMyMod;

public class Util 
{
	public static CreativeTabs getCreativeTabByString(String s)
	{
		for(CreativeTabs c : CreativeTabs.creativeTabArray)
		{
			if(String.valueOf(ObfuscationReflectionHelper.getPrivateValue(CreativeTabs.class, c, "tabLabel", "field_78034_o")).equals(s))
			{
				return c;
			}
		}
		
		return null;
	}
}
