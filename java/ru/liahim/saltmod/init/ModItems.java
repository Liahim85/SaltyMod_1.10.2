package ru.liahim.saltmod.init;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.registry.GameRegistry;
import ru.liahim.saltmod.SaltMod;
import ru.liahim.saltmod.common.CommonProxy;
import ru.liahim.saltmod.item.AchievItem;
import ru.liahim.saltmod.item.FizzyDrink;
import ru.liahim.saltmod.item.MainItems;
import ru.liahim.saltmod.item.MudArmor;
import ru.liahim.saltmod.item.Muffin;
import ru.liahim.saltmod.item.Rainmaker;
import ru.liahim.saltmod.item.Salt;
import ru.liahim.saltmod.item.SaltFood;
import ru.liahim.saltmod.item.SaltWortSeed;

public class ModItems {
	
	static CreativeTabs tab = CommonProxy.saltTab;
	
	public static Item achievItem = new AchievItem("achievItem", null);
	public static Item escargot = ((ItemFood) new SaltFood("escargot", 3, 2.4F).setAlwaysEdible().setCreativeTab(tab)).setPotionEffect(new PotionEffect(MobEffects.NAUSEA, 300, 0), 0.3F);
	
	public static Item salt = new Salt("salt", tab);
	public static Item saltPinch = new MainItems("saltPinch", tab);
	public static Item saltWortSeed = new SaltWortSeed("saltWortSeed", tab);
	public static Item soda = new MainItems("soda", tab);
	public static Item mineralMud = new MainItems("mineralMud", tab);
	
	public static Item saltBeefCooked = new SaltFood("saltBeefCooked", 9, 13.8F).setCreativeTab(tab);
	public static Item saltPorkchopCooked = new SaltFood("saltPorkchopCooked", 9, 13.8F).setCreativeTab(tab);
	public static Item saltMuttonCooked = new SaltFood("saltMuttonCooked", 7, 10.6F).setCreativeTab(tab);
	public static Item saltPotatoBaked = new SaltFood("saltPotatoBaked", 7, 8.2F).setCreativeTab(tab);
	public static Item saltChickenCooked = new SaltFood("saltChickenCooked", 7, 8.2F).setCreativeTab(tab);
	public static Item saltRabbitCooked = new SaltFood("saltRabbitCooked", 6, 7.0F).setCreativeTab(tab);
	public static Item saltFishCod = new SaltFood("saltFishCod", 5, 4.2F).setCreativeTab(tab);
	public static Item saltFishCodCooked = new SaltFood("saltFishCodCooked", 6, 7.0F).setCreativeTab(tab);
	public static Item saltFishSalmon = new SaltFood("saltFishSalmon", 6, 5.0F).setCreativeTab(tab);
	public static Item saltFishSalmonCooked = new SaltFood("saltFishSalmonCooked", 7, 8.2F).setCreativeTab(tab);
	public static Item saltFishClownfish = new SaltFood("saltFishClownfish", 3, 1.6F).setCreativeTab(tab);
	public static Item saltBeetroot = new SaltFood("saltBeetroot", 2, 2.2F).setCreativeTab(tab);
	public static Item cornedBeef = new SaltFood("cornedBeef", 5, 2.8F).setCreativeTab(tab);
	public static Item saltBread = new SaltFood("saltBread", 6, 7.0F).setCreativeTab(tab);
	public static Item saltEgg = new SaltFood("saltEgg", 4, 4.8F).setMaxStackSize(16).setCreativeTab(tab);
	public static Item saltRabbitStew = new SaltFood("saltRabbitStew", 11, 13.0F, Items.BOWL).setMaxStackSize(1).setCreativeTab(tab);
	public static Item saltMushroomStew = new SaltFood("saltMushroomStew", 7, 8.2F, Items.BOWL).setMaxStackSize(1).setCreativeTab(tab);
	public static Item saltBeetrootSoup = new SaltFood("saltBeetrootSoup", 7, 8.2F, Items.BOWL).setMaxStackSize(1).setCreativeTab(tab);
	public static Item pumpkinPorridge = new SaltFood("pumpkinPorridge", 6, 4.8F, Items.BOWL).setMaxStackSize(1).setCreativeTab(tab);
	public static Item vegetableStew = new SaltFood("vegetableStew", 5, 6.0F, Items.BOWL).setMaxStackSize(1).setCreativeTab(tab);
	public static Item saltVegetableStew = new SaltFood("saltVegetableStew", 6, 7.2F, Items.BOWL).setMaxStackSize(1).setCreativeTab(tab);
	public static Item potatoMushroom = new SaltFood("potatoMushroom", 5, 6.2F, Items.BOWL).setMaxStackSize(1).setCreativeTab(tab);
	public static Item saltPotatoMushroom = new SaltFood("saltPotatoMushroom", 6, 7.2F, Items.BOWL).setMaxStackSize(1).setCreativeTab(tab);
	public static Item fishSoup = new SaltFood("fishSoup", 6, 6.8F, Items.BOWL).setMaxStackSize(1).setCreativeTab(tab);
	public static Item saltFishSoup = new SaltFood("saltFishSoup", 7, 7.8F, Items.BOWL).setMaxStackSize(1).setCreativeTab(tab);
	public static Item fishSalmonSoup = new SaltFood("fishSalmonSoup", 7, 7.2F, Items.BOWL).setMaxStackSize(1).setCreativeTab(tab);
	public static Item saltFishSalmonSoup = new SaltFood("saltFishSalmonSoup", 8, 8.2F, Items.BOWL).setMaxStackSize(1).setCreativeTab(tab);
	public static Item saltWortBeef = new SaltFood("saltWortBeef", 10, 14.0F, Items.BOWL, new PotionEffect(MobEffects.REGENERATION, 100, 0)).setMaxStackSize(1).setCreativeTab(tab);
	public static Item saltWortPorkchop = new SaltFood("saltWortPorkchop", 10, 14.0F, Items.BOWL, new PotionEffect(MobEffects.REGENERATION, 100, 0)).setMaxStackSize(1).setCreativeTab(tab);
	public static Item saltWortMutton = new SaltFood("saltWortMutton", 8, 10.8F, Items.BOWL, new PotionEffect(MobEffects.REGENERATION, 100, 0)).setMaxStackSize(1).setCreativeTab(tab);
	public static Item beetrootSalad = new SaltFood("beetrootSalad", 5, 6.6F, Items.BOWL).setMaxStackSize(1).setCreativeTab(tab);
	public static Item saltBeetrootSalad = new SaltFood("saltBeetrootSalad", 6, 7.6F, Items.BOWL).setMaxStackSize(1).setCreativeTab(tab);	
	public static Item HuFC = new SaltFood("HuFC", 6, 7.2F, Items.BOWL, new PotionEffect(MobEffects.HASTE, 800, 1)).setAlwaysEdible().setMaxStackSize(1).setCreativeTab(tab);
	public static Item saltHuFC = new SaltFood("saltHuFC", 7, 8.2F, Items.BOWL, new PotionEffect(MobEffects.HASTE, 1200, 1)).setAlwaysEdible().setMaxStackSize(1).setCreativeTab(tab);
	public static Item dandelionSalad = new SaltFood("dandelionSalad", 4, 2.4F, Items.BOWL, new PotionEffect(MobEffects.HEALTH_BOOST, 800, 0)).setAlwaysEdible().setMaxStackSize(1).setCreativeTab(tab);
	public static Item saltDandelionSalad = new SaltFood("saltDandelionSalad", 5, 3.4F, Items.BOWL, new PotionEffect(MobEffects.HEALTH_BOOST, 1200, 0)).setAlwaysEdible().setMaxStackSize(1).setCreativeTab(tab);
	public static Item wheatSprouts = new SaltFood("wheatSprouts", 3, 3.6F, Items.BOWL, new PotionEffect(MobEffects.HEALTH_BOOST, 600, 0)).setAlwaysEdible().setMaxStackSize(1).setCreativeTab(tab);
	public static Item saltWheatSprouts = new SaltFood("saltWheatSprouts", 4, 4.6F, Items.BOWL, new PotionEffect(MobEffects.HEALTH_BOOST, 900, 0)).setAlwaysEdible().setMaxStackSize(1).setCreativeTab(tab);
	public static Item fruitSalad = new SaltFood("fruitSalad", 5, 4.8F, Items.BOWL, new PotionEffect(MobEffects.SPEED, 800, 0)).setAlwaysEdible().setMaxStackSize(1).setCreativeTab(tab);
	public static Item gratedCarrot = new SaltFood("gratedCarrot", 5, 4.8F, Items.BOWL, new PotionEffect(MobEffects.NIGHT_VISION, 800, 0)).setAlwaysEdible().setMaxStackSize(1).setCreativeTab(tab);
	public static Item saltWortSalad = new SaltFood("saltWortSalad", 6, 2.4F, Items.BOWL, new PotionEffect(MobEffects.REGENERATION, 200, 1)).setAlwaysEdible().setMaxStackSize(1).setCreativeTab(tab);
	public static Item carrotPie = new SaltFood("carrotPie", 8, 7.2F).setCreativeTab(tab);
	public static Item applePie = new SaltFood("applePie", 8, 6.0F).setCreativeTab(tab);
	public static Item potatoPie = new SaltFood("potatoPie", 8, 4.8F).setCreativeTab(tab);
	public static Item onionPie = new SaltFood("onionPie", 7, 4.8F).setCreativeTab(tab);
	public static Item fishPie = new SaltFood("fishPie", 8, 8.4F).setCreativeTab(tab);
	public static Item fishSalmonPie = new SaltFood("fishSalmonPie", 9, 9.6F).setCreativeTab(tab);
	public static Item mushroomPie = new SaltFood("mushroomPie", 8, 7.2F).setCreativeTab(tab);
	public static Item pickledMushroom = new SaltFood("pickledMushroom", 8, 6.2F, Items.GLASS_BOTTLE).setMaxStackSize(1).setCreativeTab(tab);
	public static Item pickledFern = new SaltFood("pickledFern", 4, 4.8F, Items.GLASS_BOTTLE, new PotionEffect(MobEffects.RESISTANCE, 800)).setAlwaysEdible().setMaxStackSize(1).setCreativeTab(tab);
	
	public static Item saltWortPie = new SaltFood("saltWortPie", 6, 3.6F, new PotionEffect(MobEffects.REGENERATION, 100, 0)).setAlwaysEdible().setCreativeTab(tab);
	public static Item fermentedSaltWort = new SaltFood("fermentedSaltWort", 5, 3.6F, Items.GLASS_BOTTLE, new PotionEffect(MobEffects.REGENERATION, 600, 2)).setAlwaysEdible().setMaxStackSize(1).setCreativeTab(tab);
	public static Item fizzyDrink = new FizzyDrink("fizzyDrink", tab);
	public static Item muffin = new Muffin("muffin", tab);
	
	public static Item mudHelmet = new MudArmor("mudHelmet", CommonProxy.mudMaterial, EntityEquipmentSlot.HEAD);
	public static Item mudChestplate = new MudArmor("mudChestplate", CommonProxy.mudMaterial, EntityEquipmentSlot.CHEST);
	public static Item mudLeggings = new MudArmor("mudLeggings", CommonProxy.mudMaterial, EntityEquipmentSlot.LEGS);
	public static Item mudBoots = new MudArmor("mudBoots", CommonProxy.mudMaterial, EntityEquipmentSlot.FEET);	

	public static Item powderedMilk = new MainItems("powderedMilk", tab);
	public static Item hemoglobin = new SaltFood("hemoglobin", 2, 4.0F, new PotionEffect(MobEffects.INSTANT_HEALTH, 1, 1)).setAlwaysEdible().setCreativeTab(tab);
	
	public static Item saltStar = new MainItems("saltStar", tab);
	public static Item rainmaker = new Rainmaker("rainmaker", tab);
	
	public static void createItems()
	{
		SaltMod.logger.info("Start to initialize Items");
		registerItem(achievItem, "achievItem");
	//Main Items	
		registerItem(salt, "salt");
		registerItem(saltPinch, "saltPinch");
		registerItem(saltWortSeed, "saltWortSeed");
		registerItem(soda, "soda");
		registerItem(mineralMud, "mineralMud");
	//Food Items
		registerItem(saltBeefCooked, "saltBeefCooked");
		registerItem(saltPorkchopCooked, "saltPorkchopCooked");
		registerItem(saltMuttonCooked, "saltMuttonCooked");
		registerItem(saltPotatoBaked, "saltPotatoBaked");
		registerItem(saltChickenCooked, "saltChickenCooked");
		registerItem(saltRabbitCooked, "saltRabbitCooked");
		registerItem(saltFishCod, "saltFishCod");
		registerItem(saltFishCodCooked, "saltFishCodCooked");
		registerItem(saltFishSalmon, "saltFishSalmon");
		registerItem(saltFishSalmonCooked, "saltFishSalmonCooked");
		registerItem(saltFishClownfish, "saltFishClownfish");
		registerItem(saltBeetroot, "saltBeetroot");
		registerItem(cornedBeef, "cornedBeef");
		registerItem(saltBread, "saltBread");
		registerItem(saltEgg, "saltEgg");
		registerItem(saltRabbitStew, "saltRabbitStew");
		registerItem(saltMushroomStew, "saltMushroomStew");
		registerItem(saltBeetrootSoup, "saltBeetrootSoup");
		registerItem(pumpkinPorridge, "pumpkinPorridge");
		registerItem(vegetableStew, "vegetableStew");
		registerItem(saltVegetableStew, "saltVegetableStew");
		registerItem(potatoMushroom, "potatoMushroom");
		registerItem(saltPotatoMushroom, "saltPotatoMushroom");
		registerItem(fishSoup, "fishSoup");
		registerItem(saltFishSoup, "saltFishSoup");
		registerItem(fishSalmonSoup, "fishSalmonSoup");
		registerItem(saltFishSalmonSoup, "saltFishSalmonSoup");
		registerItem(saltWortBeef, "saltWortBeef");
		registerItem(saltWortPorkchop, "saltWortPorkchop");
		registerItem(saltWortMutton, "saltWortMutton");		
		registerItem(beetrootSalad, "beetrootSalad");
		registerItem(saltBeetrootSalad, "saltBeetrootSalad");
		registerItem(HuFC, "HuFC");
		registerItem(saltHuFC, "saltHuFC");		
		registerItem(dandelionSalad, "dandelionSalad");
		registerItem(saltDandelionSalad, "saltDandelionSalad");
		registerItem(wheatSprouts, "wheatSprouts");
		registerItem(saltWheatSprouts, "saltWheatSprouts");
		registerItem(fruitSalad, "fruitSalad");
		registerItem(gratedCarrot, "gratedCarrot");
		registerItem(saltWortSalad, "saltWortSalad");
		registerItem(carrotPie, "carrotPie");
		registerItem(applePie, "applePie");
		registerItem(potatoPie, "potatoPie");
		registerItem(onionPie, "onionPie");
		registerItem(fishPie, "fishPie");
		registerItem(fishSalmonPie, "fishSalmonPie");
		registerItem(mushroomPie, "mushroomPie");
		registerItem(saltWortPie, "saltWortPie");
		registerItem(fermentedSaltWort, "fermentedSaltWort");
		registerItem(pickledMushroom, "pickledMushroom");
		registerItem(pickledFern, "pickledFern");
		registerItem(fizzyDrink, "fizzyDrink");
		registerItem(muffin, "muffin");
	//Armor
		registerItem(mudHelmet, "mudHelmet");
		registerItem(mudChestplate, "mudChestplate");
		registerItem(mudLeggings, "mudLeggings");
		registerItem(mudBoots, "mudBoots");
	//Milk
		registerItem(powderedMilk, "powderedMilk");
		
		registerItem(escargot, "escargot");
	//Rainmaker
		registerItem(saltStar, "saltStar");
		registerItem(rainmaker, "rainmaker");
		SaltMod.logger.info("Finished initializing Items");
    }
	
    private static Item registerItem(Item item, String registryName) {
        item.setRegistryName(registryName);
        return GameRegistry.register(item);
    }
}