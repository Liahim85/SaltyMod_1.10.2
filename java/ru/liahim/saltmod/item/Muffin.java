package ru.liahim.saltmod.item;

import java.util.List;
import java.util.Random;

import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import ru.liahim.saltmod.init.AchievSalt;

public class Muffin extends ItemFood {
	
	public Muffin(String name, CreativeTabs tab) {
		super(3, 20, false);
		this.setUnlocalizedName(name);
		this.setCreativeTab(tab);
	}
	
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List list, boolean flag)
	{
		list.add(I18n.format(getUnlocalizedName() + ".tooltip"));	
	}
	
	@Override
	public void onFoodEaten(ItemStack item, World world, EntityPlayer player)
    {
		boolean chek = false;
		
        if (player.getFoodStats().getFoodLevel() == 20) {chek = true;}
        
        if (!world.isRemote && chek)
        {
        	player.addPotionEffect(new PotionEffect(MobEffects.SATURATION, 2400));
        	player.addStat(AchievSalt.muffin, 1);
        }
        
        if (world.isRemote && player.getFoodStats().getFoodLevel() == 20)
        {
        	Random rand = new Random();
        	player.addChatMessage(new TextComponentString(I18n.format(getUnlocalizedName() + ".mess." + rand.nextInt(4))));
        }
    }
}