package fr.epharos.craftmymod.customs;

import net.minecraft.block.Block;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeCustom extends BiomeGenBase 
{
	public BiomeCustom(int par1) 
	{
		super(par1);
	}
	
	public BiomeGenBase setTopAndFillerBlocks(Block top, Block filler)
	{
		this.topBlock = top;
		this.fillerBlock = filler;
		return this;
	}
}
