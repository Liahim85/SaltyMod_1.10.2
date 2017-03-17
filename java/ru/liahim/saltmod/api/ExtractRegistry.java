package ru.liahim.saltmod.api;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;

public class ExtractRegistry {
	
	private static final ExtractRegistry instance = new ExtractRegistry();
	public static final ExtractRegistry instance()
	{
		return instance;
	}
	
	//////////////////////////////////////////////////////////////////////
	// EXTRACTOR RECIPES /////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	/**
	 * addExtracting()
	 * 
	 * Example Usage:
	 * ExtractRegistry.instance().addExtracting(FluidRegistry.WATER, SaltMod.saltPinch, 1000, 0.0F);
	 * 
	 * @param fluid  - The input Fluid.
	 * @param stack  - The resulting Item/Block/ItemStack.
	 * @param vol    - The volume of Fluid needed for the Item/Block/ItemStack to be extracted.
	 * @param exp    - The experience number.
	 */

	public void addExtracting(Fluid fluid, ItemStack stack, int vol, float exp)
	{
		this.extractingList.put(fluid, new ExtractResults(stack, vol));
		this.experienceList.put(Arrays.asList(stack.getItem(), stack.getItemDamage()), new Experience(exp));
	}
	
	public void addExtracting(Fluid fluid, Item item, int vol, float exp)
	{
		this.addExtracting(fluid, new ItemStack(item, 1), vol, exp);
	}
	
	public void addExtracting(Fluid fluid, Block block, int vol, float exp)
	{
		this.addExtracting(fluid, Item.getItemFromBlock(block), vol, exp);
	}
	
	//////////////////////////////////////////////////////////////////////
	// EXTRACTOR UTIL ////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	
	private Map extractingList = new HashMap();
	public Map getExtractingList() { return extractingList; }
	
	private HashMap<List, Experience> experienceList = new HashMap<List, Experience>();
	public Map<List, Experience> getExperienceList() { return experienceList; }
	
	class ExtractResults
	{
		public final ItemStack s;   //stack
		public final int v;   		//volume
		public ExtractResults(ItemStack s, int v)
		{
			this.s = s;
			this.v = v;
		}
	}
	
	//EXTRACTING
	
	public ExtractResults getExtractResults(Fluid fluid)
	{
		if (fluid == null)
		{
			return null;
		}		
		ExtractResults ret = (ExtractResults)extractingList.get(Arrays.asList(fluid));		
		if (ret != null)
		{
			return ret;
		}
		return (ExtractResults)extractingList.get(fluid);
	}

	public ItemStack getExtractItemStack(Fluid fluid)
	{
		ExtractResults extractresults = this.getExtractResults(fluid);
		if (fluid == null || extractresults == null)
		{
			return null;
		}
		return extractresults.s;
	}

	public int getExtractFluidVolum(Fluid fluid)
	{
		ExtractResults extractresults = this.getExtractResults(fluid);
		if (fluid == null || extractresults == null)
		{
			return 0;
		}
		return extractresults.v;
	}
	
	//EXPERIENCE
	
	class Experience
	{
		public final float e; 		//experience
		public Experience(float e)
		{
			this.e = e;
		}
	}
	
	public Experience getExperience(ItemStack stack)
	{
		if (stack == null)
		{
			return null;
		}
		Experience ret = experienceList.get(Arrays.asList(stack.getItem(), stack.getItemDamage()));		
		if (ret != null)
		{
			return ret;
		}
		return experienceList.get(stack);
	}

	public float getExtractExperience(ItemStack stack)
	{
		Experience experiencecount = this.getExperience(stack);
		if (stack == null || experiencecount == null)
		{
			return 0.0F;
		}
		return experiencecount.e;
	}
}