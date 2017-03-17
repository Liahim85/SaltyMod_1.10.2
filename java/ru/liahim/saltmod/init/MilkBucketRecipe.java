package ru.liahim.saltmod.init;

import java.util.ArrayList;
import java.util.Iterator;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class MilkBucketRecipe implements IRecipe {
	
	private final ItemStack recipeOutput;
	public final ArrayList recipeItems = new ArrayList();;
	
	public MilkBucketRecipe()
    {
        this.recipeOutput = new ItemStack(Items.MILK_BUCKET);
        this.recipeItems.add(new ItemStack(Items.WATER_BUCKET));
        this.recipeItems.add(new ItemStack(ModItems.powderedMilk));
    }

	@Override
	public boolean matches(InventoryCrafting inv, World world) {
		
        ArrayList arraylist = new ArrayList(this.recipeItems);

        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 3; ++j)
            {
                ItemStack itemstack = inv.getStackInRowAndColumn(j, i);

                if (itemstack != null)
                {
                    boolean flag = false;
                    Iterator iterator = arraylist.iterator();

                    while (iterator.hasNext())
                    {
                        ItemStack itemstack1 = (ItemStack)iterator.next();

                        if (itemstack.getItem() == itemstack1.getItem())
                        {
                            flag = true;
                            arraylist.remove(itemstack1);
                            break;
                        }
                    }

                    if (!flag)
                    {
                        return false;
                    }
                }
            }
        }

        return arraylist.isEmpty();
	}
	
	@Override
	public ItemStack[] getRemainingItems(InventoryCrafting ic)
	{
	    return new ItemStack[ic.getSizeInventory()];
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv)
	{
		return this.recipeOutput.copy();
	}

	@Override
	public int getRecipeSize()
    {
        return this.recipeItems.size();
	}

	@Override
	public ItemStack getRecipeOutput()
	{		
		return null;
	}
}