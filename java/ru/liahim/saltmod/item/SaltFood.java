package ru.liahim.saltmod.item;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import ru.liahim.saltmod.init.ModItems;

public class SaltFood extends ItemFood {
	
	private Item container;
	private PotionEffect[] effects;
	
	public SaltFood(String name, int amount, float saturation, Item container, PotionEffect... potionEffect) {
		super(amount, saturation, false);
		this.setUnlocalizedName(name);
		this.container = container;
		this.effects = potionEffect;
	}

	public SaltFood(String name, int amount, float saturation, PotionEffect... potionEffect) {
		this(name, amount, saturation, null, potionEffect);
	}

	public SaltFood(String name, int amount, float saturation) {
		this(name, amount, saturation, null, (PotionEffect[])null);
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List list, boolean flag) {

		super.addInformation(is, player, list, flag);

		if (this.effects != null)
    	{
			for (int i = 0; i < effects.length; i ++)
			{	 
				String mess = "";
        	
				if (effects[i] != null)
				{
					mess += (effects[i].getPotion().isBadEffect() ? TextFormatting.RED : TextFormatting.GRAY);
					mess += I18n.translateToLocal(effects[i].getEffectName()).trim();

					if (effects[i].getAmplifier() == 1){mess += " II";}
					else if (effects[i].getAmplifier() == 2){mess += " III";}
					else if (effects[i].getAmplifier() == 3){mess += " IV";}
					else if (effects[i].getAmplifier() == 4){mess += " V";}

        			if (effects[i].getDuration() > 20)
        				mess += " (" + Potion.getPotionDurationString(effects[i], 1) + ")";
	    
        			mess += TextFormatting.RESET;

        			list.add(mess);
        		}
        	}
    	}
	}
	
    @Override
	public EnumAction getItemUseAction(ItemStack item)
    {
    	if (getUnlocalizedName().equals(ModItems.fermentedSaltWort.getUnlocalizedName()))
    	{
    		return EnumAction.DRINK;
    	}
    		
        return EnumAction.EAT;
    }

	@Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entityLiving)
    {
        super.onItemUseFinish(stack, world, entityLiving);
        
        EntityPlayer player = entityLiving instanceof EntityPlayer ? (EntityPlayer)entityLiving : null;
        
        if (player != null && this.effects != null)
    	{
        	for (int i = 0; i < effects.length; i ++)
        	{
        		if (!world.isRemote && effects[i] != null)
        			player.addPotionEffect(new PotionEffect(this.effects[i]));
        	}
    	}
        
        if (!world.isRemote && getUnlocalizedName().equals(ModItems.saltEgg.getUnlocalizedName()))
        {
        	world.spawnEntityInWorld(new EntityItem(world, entityLiving.posX, entityLiving.posY, entityLiving.posZ, new ItemStack(Items.DYE, 1, 15)));
        }
        
        return this.container != null ? new ItemStack(this.container) : stack;
    }
}