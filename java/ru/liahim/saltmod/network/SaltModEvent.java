package ru.liahim.saltmod.network;

import java.util.Random;
import java.util.UUID;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraft.world.storage.loot.functions.SetCount;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.liahim.saltmod.api.RainMakerEvent;
import ru.liahim.saltmod.common.CommonProxy;
import ru.liahim.saltmod.init.AchievSalt;
import ru.liahim.saltmod.init.ModBlocks;
import ru.liahim.saltmod.init.ModItems;
import ru.liahim.saltmod.init.SaltConfig;

public class SaltModEvent {
	
	private static final UUID uuid1 = UUID.fromString("ca3f8f85-df1e-4fe8-8cf6-e7030f33ed8e");
	private static final UUID uuid2 = UUID.fromString("42e70891-8397-4cf0-aca3-1a1d237768eb");
	private static final UUID uuid3 = UUID.fromString("b94a045b-f0e9-413a-86fe-2a8473f9ce9d");
	private static final UUID uuid4 = UUID.fromString("8fc1c0b4-350a-45d8-83c7-c788ec55b501");
	
	private static final AttributeModifier headModifierUP = (new AttributeModifier(uuid1, "mudBoostUP", 4F, 0));
	private static final AttributeModifier bodyModifierUP = (new AttributeModifier(uuid2, "mudBoostUP", 6F, 0));
	private static final AttributeModifier legsModifierUP = (new AttributeModifier(uuid3, "mudBoostUP", 6F, 0));
	private static final AttributeModifier feetModifierUP = (new AttributeModifier(uuid4, "mudBoostUP", 4F, 0));
	
	@SubscribeEvent
	public void onPlayerAttack(AttackEntityEvent e) {
		
		World world = e.getEntityPlayer().worldObj;
		
	    if (!world.isRemote && e.getEntityPlayer() != null && e.getTarget() != null && e.getTarget() instanceof EntityLivingBase) {

	    	EntityPlayer player = e.getEntityPlayer();
			EntityLivingBase target = (EntityLivingBase)e.getTarget();
	        ItemStack is = player.getHeldItem(EnumHand.MAIN_HAND);
	        Block block = null;
	    	
	    	if (is != null && EntityList.getEntityString(target) != null &&
	    	  ((EntityList.getEntityString(target).toLowerCase().contains("slime") &&
	    		!EntityList.getEntityString(target).toLowerCase().contains("lava")) ||
	    		EntityList.getEntityString(target).toLowerCase().contains("witch"))) {

	    		if (is.getItem() instanceof ItemBlock && Block.getBlockFromItem(is.getItem()) != Blocks.AIR) {
	                block = Block.getBlockFromItem(is.getItem());	                
	            }

	    		if (block != null) {
	    			if (block == ModBlocks.saltCrystal) {
	    				target.attackEntityFrom(DamageSource.cactus, 30.0F);
	    				world.playSound(null, new BlockPos(target.posX, target.posY, target.posZ), SoundEvents.BLOCK_STONE_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
	    				world.playSound(null, new BlockPos(target.posX, target.posY, target.posZ), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.BLOCKS, 1.5F, 1.8F);
	    				
	    				if (!player.capabilities.isCreativeMode) {
	    					
	    					--is.stackSize;
	    					if (is.stackSize == 0) {player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, null);}
	    					
	    					EntityItem EI = new EntityItem(world, target.posX, target.posY, target.posZ, new ItemStack(ModItems.saltPinch));
	    					EI.setPickupDelay(10);
	    					world.spawnEntityInWorld(EI);
	    					
	    					if (EntityList.getEntityString(target).toLowerCase().contains("witch"))
	    					{player.addStat(AchievSalt.saltWitch, 1);}
	    					
	    					if (target instanceof EntitySlime)
	    					{
		    					EntityItem EIS = new EntityItem(world, target.posX, target.posY, target.posZ, new ItemStack(ModItems.escargot));
		    					EI.setPickupDelay(10);
		    					world.spawnEntityInWorld(EIS);
	    						player.addStat(AchievSalt.saltSlime, 1);
	    					}
	    				}
	    			}
	    		}
	    	}
	    }
	}

	@SubscribeEvent
	public void updateArmor(TickEvent.PlayerTickEvent event) {
		
		if (event.phase == TickEvent.Phase.START && event.side == Side.SERVER)
		{
			EntityPlayer player = event.player;
		
			if (player != null)
			{
				ItemStack head = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
				ItemStack body = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
				ItemStack legs = player.getItemStackFromSlot(EntityEquipmentSlot.LEGS);
				ItemStack feet = player.getItemStackFromSlot(EntityEquipmentSlot.FEET);
				
				boolean mud = player.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY), MathHelper.floor_double(player.posZ))).getBlock() == ModBlocks.mudBlock;

				IAttributeInstance boost = event.player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);

				if (head != null && boost.getModifier(uuid1) == null && head.getItem() == ModItems.mudHelmet) {

					boost.applyModifier(headModifierUP);}

				if ((head == null || head.getItem() != ModItems.mudHelmet) && boost.getModifier(uuid1) != null) {

					boost.removeModifier(headModifierUP);
					if (player.getHealth() > player.getMaxHealth()) {player.setHealth(player.getMaxHealth());}}

				if (body != null && boost.getModifier(uuid2) == null && body.getItem() == ModItems.mudChestplate) {

					boost.applyModifier(bodyModifierUP);}
				
				if ((body == null || body.getItem() != ModItems.mudChestplate) && boost.getModifier(uuid2) != null) {

					boost.removeModifier(bodyModifierUP);
					if (player.getHealth() > player.getMaxHealth()) {player.setHealth(player.getMaxHealth());}}

				if (legs != null && boost.getModifier(uuid3) == null && legs.getItem() == ModItems.mudLeggings) {

					boost.applyModifier(legsModifierUP);}
				
				if ((legs == null || legs.getItem() != ModItems.mudLeggings) && boost.getModifier(uuid3) != null) {

					boost.removeModifier(legsModifierUP);
					if (player.getHealth() > player.getMaxHealth()) {player.setHealth(player.getMaxHealth());}}


				if (((feet != null && feet.getItem() == ModItems.mudBoots) || mud) && boost.getModifier(uuid4) == null) {

					boost.applyModifier(feetModifierUP);}

				if ((feet == null || feet.getItem() != ModItems.mudBoots) && !mud && boost.getModifier(uuid4) != null) {

					boost.removeModifier(feetModifierUP);
					if (player.getHealth() > player.getMaxHealth()) {player.setHealth(player.getMaxHealth());}}	
				
				if (player.getHealth() < player.getMaxHealth() && player.getFoodStats().getFoodLevel() > 0)
				{
					int chek = 0;
					
					if (head != null && head.getItem() == ModItems.mudHelmet)
					{chek = chek + 1;}					
					if (body != null && body.getItem() == ModItems.mudChestplate)
					{chek = chek + 2;}
					if (legs != null && legs.getItem() == ModItems.mudLeggings)
					{chek = chek + 2;}
					if ((feet != null && feet.getItem() == ModItems.mudBoots) || mud)
					{chek = chek + 1;}
				
					if (chek > 0)
					{
						if (player.ticksExisted % ((10 - chek)*SaltConfig.mudRegenSpeed) == 0)
						{
							player.heal(1);
						}
						
						if (chek == 6)
						{
							if (player.isBurning())
							{
								player.extinguish();
							}
							
							event.player.addStat(AchievSalt.fullMud, 1);
						}
					}
				}
			}
		}
	}
	
	//Farming
	@SubscribeEvent
	public void addTempt(LivingUpdateEvent event)
	{
		if (event.getEntity() instanceof EntityAnimal)
		{
			EntityAnimal animal = (EntityAnimal)event.getEntity();

			if ((animal instanceof EntityCow || animal instanceof EntityHorse) && animal.ticksExisted == 20)
			{
				animal.tasks.addTask(3, new EntityAITempt(animal, 1.25D, ModItems.salt, false));
			}
		}	
	}
	
	//RainMaker
	@SubscribeEvent
	public void addRain(RainMakerEvent event)
	{
		if (!event.world.isRemote)
		{
			int i = (300 + (new Random()).nextInt(600)) * 20;
			event.world.getWorldInfo().setCleanWeatherTime(0);
			event.world.getWorldInfo().setRainTime(i);
			event.world.getWorldInfo().setThunderTime(i);
			event.world.getWorldInfo().setRaining(true);

			if (event.isThunder)
			{event.world.getWorldInfo().setThundering(true);}			
			else
			{event.world.getWorldInfo().setThundering(false);}

			if (event.player != null) {event.player.addStat(AchievSalt.rain, 1);}
		}
	}
	
	//MilkIconRegister
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void registerIcons(TextureStitchEvent.Pre event) {
		if (FluidRegistry.isFluidRegistered(CommonProxy.milk)) {
			event.getMap().registerSprite(new ResourceLocation("saltmod:blocks/Milk"));
		}	
	}
	
	//Achievements
    @SubscribeEvent
	public void itemPickup(EntityItemPickupEvent event)
    {
    	World world = event.getEntityPlayer().worldObj;
    	
    	if (!world.isRemote)
    	{
    		if (event.getItem().getEntityItem().getItem() == ModItems.salt)
    		{
    			event.getEntityPlayer().addStat(AchievSalt.salt, 1);
    		}
    		
    		if (event.getItem().getEntityItem().getItem() == Item.getItemFromBlock(ModBlocks.saltCrystal))
    		{
    			event.getEntityPlayer().addStat(AchievSalt.saltCrystalGet, 1);
    		}
    		
    		if (event.getItem().getEntityItem().getItem() == ModItems.mineralMud)
    		{
    			event.getEntityPlayer().addStat(AchievSalt.mineralMud, 1);
    		}
    		
    		if (event.getItem().getEntityItem().getItem() == ModItems.saltWortSeed)
    		{
    			event.getEntityPlayer().addStat(AchievSalt.saltWort, 1);
    		}
    	}
    }
    
    @SubscribeEvent
    public void crafting(ItemCraftedEvent event)
    {
    	if(event.crafting.getItem() == ModItems.mineralMud)
    	{
    		event.player.addStat(AchievSalt.mineralMud, 1);
    	}
    }

	@SubscribeEvent
	public void onLootTablesLoaded(LootTableLoadEvent event) {
		if (event.getName().equals(LootTableList.CHESTS_SPAWN_BONUS_CHEST)) {
			final LootPool pool2 = event.getTable().getPool("pool2");
			if (pool2 != null) {
				pool2.addEntry(new LootEntryItem(ModItems.salt, 5, 0, new LootFunction[] { new SetCount(new LootCondition[0], new RandomValueRange(2, 5)) }, new LootCondition[0], "saltmod:salt"));
			}
		} else if (event.getName().equals(LootTableList.CHESTS_SIMPLE_DUNGEON)) {
			final LootPool pool2 = event.getTable().getPool("pool2");
			if (pool2 != null) {
				pool2.addEntry(new LootEntryItem(ModItems.salt, 5, 0, new LootFunction[] { new SetCount(new LootCondition[0], new RandomValueRange(2, 5)) }, new LootCondition[0], "saltmod:salt"));
				pool2.addEntry(new LootEntryItem(ModItems.saltWortSeed, 5, 0, new LootFunction[] { new SetCount(new LootCondition[0], new RandomValueRange(2, 3)) }, new LootCondition[0], "saltmod:saltwort"));
			}
		} else if (event.getName().equals(LootTableList.CHESTS_STRONGHOLD_CORRIDOR)) {
			final LootPool pool2 = event.getTable().getPool("pool2");
			if (pool2 != null) {
				pool2.addEntry(new LootEntryItem(ModItems.salt, 5, 0, new LootFunction[] { new SetCount(new LootCondition[0], new RandomValueRange(2, 5)) }, new LootCondition[0], "saltmod:salt"));
				pool2.addEntry(new LootEntryItem(ModItems.saltWortSeed, 5, 0, new LootFunction[] { new SetCount(new LootCondition[0], new RandomValueRange(2, 5)) }, new LootCondition[0], "saltmod:saltwort"));
			}
		} else if (event.getName().equals(LootTableList.CHESTS_ABANDONED_MINESHAFT)) {
			final LootPool pool2 = event.getTable().getPool("pool2");
			if (pool2 != null) {
				pool2.addEntry(new LootEntryItem(ModItems.salt, 10, 0, new LootFunction[] { new SetCount(new LootCondition[0], new RandomValueRange(2, 5)) }, new LootCondition[0], "saltmod:salt"));
			}
		} else if (event.getName().equals(LootTableList.CHESTS_VILLAGE_BLACKSMITH)) {
			final LootPool pool2 = event.getTable().getPool("pool2");
			if (pool2 != null) {
				pool2.addEntry(new LootEntryItem(ModItems.salt, 10, 0, new LootFunction[] { new SetCount(new LootCondition[0], new RandomValueRange(2, 5)) }, new LootCondition[0], "saltmod:salt"));
			}
		} else if (event.getName().equals(LootTableList.CHESTS_IGLOO_CHEST)) {
			final LootPool pool2 = event.getTable().getPool("pool2");
			if (pool2 != null) {
				pool2.addEntry(new LootEntryItem(ModItems.salt, 5, 0, new LootFunction[] { new SetCount(new LootCondition[0], new RandomValueRange(2, 5)) }, new LootCondition[0], "saltmod:salt"));
			}
		} else if (event.getName().equals(LootTableList.CHESTS_DESERT_PYRAMID)) {
			final LootPool pool2 = event.getTable().getPool("pool2");
			if (pool2 != null) {
				pool2.addEntry(new LootEntryItem(ModItems.saltWortSeed, 5, 0, new LootFunction[] { new SetCount(new LootCondition[0], new RandomValueRange(2, 3)) }, new LootCondition[0], "saltmod:saltwort"));
			}
		} else if (event.getName().equals(LootTableList.CHESTS_JUNGLE_TEMPLE)) {
			final LootPool pool2 = event.getTable().getPool("pool2");
			if (pool2 != null) {
				pool2.addEntry(new LootEntryItem(ModItems.saltWortSeed, 10, 0, new LootFunction[] { new SetCount(new LootCondition[0], new RandomValueRange(2, 5)) }, new LootCondition[0], "saltmod:saltwort"));
			}
		}
	}
}