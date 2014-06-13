package fr.epharos.craftmymod.customs;

import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class ItemFoodCustom extends ItemFood 
{
	public boolean eat = true;
	
	public ItemFoodCustom(int hunger, float saturation, boolean wolf) 
	{
		super(hunger, saturation, wolf);
	}
	
	public Item setEat(boolean b)
	{
		this.eat = b;
		return this;
	}

	public EnumAction getItemUseAction(ItemStack itemStack)
	{
		return this.eat ? EnumAction.eat : EnumAction.drink;
	}
}
