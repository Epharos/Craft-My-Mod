package fr.epharos.craftmymod.customs;

import net.minecraft.item.Item.ToolMaterial;

public class ToolMaterialCustom
{
	public String name;
	public ToolMaterial tool;
	
	public ToolMaterialCustom(String s, ToolMaterial material)
	{
		this.name = s;
		this.tool = material;
	}
}
