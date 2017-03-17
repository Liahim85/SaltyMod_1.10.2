package ru.liahim.saltmod.item;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import ru.liahim.saltmod.init.AchievSalt;
import ru.liahim.saltmod.init.SaltConfig;

public class FizzyDrink extends Item {
	
	public FizzyDrink(String name, CreativeTabs tab) {
		this.setMaxStackSize(1);
		this.setUnlocalizedName(name);
		this.setCreativeTab(tab);		
	}
	
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List list, boolean flag)
	{
		list.add(I18n.format(getUnlocalizedName() + ".tooltip"));	
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
    {
		EntityPlayer player = entityLiving instanceof EntityPlayer ? (EntityPlayer)entityLiving : null;

		if (!worldIn.isRemote)
		{
			if (SaltConfig.fizzyEffect) {entityLiving.clearActivePotions();}
			else {entityLiving.curePotionEffects(new ItemStack(Items.MILK_BUCKET));}
			if (entityLiving.isBurning())
			{
				if (player != null) player.addStat(AchievSalt.fizzyDrink, 1);
				entityLiving.extinguish();
			}
		}
		
		if ((player != null && !player.capabilities.isCreativeMode) || player == null)
		{
			return new ItemStack(Items.GLASS_BOTTLE);
		}
		
        return stack;
    }
	
	@Override
	public int getMaxItemUseDuration(ItemStack item)
    {
        return 32;
    }
	
    @Override
	public EnumAction getItemUseAction(ItemStack item)
    {
        return EnumAction.DRINK;
    }
	
    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        playerIn.setActiveHand(hand);
        return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
    }
}