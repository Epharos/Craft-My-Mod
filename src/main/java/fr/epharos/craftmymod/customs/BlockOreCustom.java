package fr.epharos.craftmymod.customs;

import net.minecraft.block.Block;

public class BlockOreCustom extends BlockCustom 
{
	public int probability, maxHeight, maxOre, dimensionID, replaceID;
	
	public BlockOreCustom(String material) 
	{
		super(material);
	}
	
	public Block setInformations(int probability, int maxHeight, int maxOre, int dimensionID, int replaceID)
	{
		this.probability = probability;
		this.maxHeight = maxHeight;
		this.maxOre = maxOre;
		this.dimensionID = dimensionID;
		this.replaceID = replaceID;
		return this;
	}
}
