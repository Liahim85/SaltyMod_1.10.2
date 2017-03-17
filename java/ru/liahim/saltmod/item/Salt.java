package ru.liahim.saltmod.item;

import java.util.Random;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public class Salt extends Item {
	
	public Salt(String name, CreativeTabs tab) {
		this.setUnlocalizedName(name);
		this.setCreativeTab(tab);
	}
	
	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase target, EnumHand hand)
	{
	    if (target instanceof EntityCow)
	    {
	    	if (((EntityCow)target).processInteract(player, hand, new ItemStack(Items.WHEAT)))
	    	--stack.stackSize;
	    }
	    
	    if (target instanceof EntityHorse)
	    {
	    	EntityHorse horse = (EntityHorse)target;
	    	boolean flag = false;
	    	
	    	if(horse.getHealth() < horse.getMaxHealth())
	    	{horse.heal(2.0F); flag = true;}
	    	
	    	if(!horse.isAdultHorse())
	    	{horse.addGrowth(10); flag = true;}
	    	
	        if (flag)
	        {
	            if (!horse.isSilent())
	            {horse.worldObj.playSound((EntityPlayer)null, horse.posX, horse.posY, horse.posZ, SoundEvents.ENTITY_HORSE_EAT, horse.getSoundCategory(), 1.0F, 1.0F + (new Random().nextFloat() - new Random().nextFloat()) * 0.2F);}
	            
	            --stack.stackSize;
            	return true;
	        }
	    	
	        return false;
	    }
	    
	    return false;
	}
}
