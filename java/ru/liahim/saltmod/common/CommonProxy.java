package ru.liahim.saltmod.common;

import net.minecraft.block.BlockDispenser;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.OreDictionary;
import ru.liahim.saltmod.SaltMod;
import ru.liahim.saltmod.api.ExtractRegistry;
import ru.liahim.saltmod.dispenser.DispenserBehaviorRainmaiker;
import ru.liahim.saltmod.dispenser.DispenserBehaviorSaltPinch;
import ru.liahim.saltmod.entity.EntityRainmaker;
import ru.liahim.saltmod.entity.EntityRainmakerDust;
import ru.liahim.saltmod.init.ClientRegister;
import ru.liahim.saltmod.init.MilkBucketRecipe;
import ru.liahim.saltmod.init.ModBlocks;
import ru.liahim.saltmod.init.ModItems;
import ru.liahim.saltmod.init.SaltConfig;
import ru.liahim.saltmod.inventory.gui.GuiExtractorHandler;
import ru.liahim.saltmod.network.ExtractorButtonMessage;
import ru.liahim.saltmod.network.SaltModEvent;
import ru.liahim.saltmod.network.SaltWortMessage;
import ru.liahim.saltmod.tileEntity.TileEntityExtractor;
import ru.liahim.saltmod.world.SaltCrystalGenerator;
import ru.liahim.saltmod.world.SaltLakeGenerator;
import ru.liahim.saltmod.world.SaltOreGenerator;

public class CommonProxy {
	
	public static CreativeTabs saltTab = new SaltTab("saltTab");
	
	public static ArmorMaterial mudMaterial = addArmorMaterial("mudMaterial", "saltmod:MudArmor", 4, new int[] {1, 1, 1, 1}, 15, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0);
	private static ItemArmor.ArmorMaterial addArmorMaterial(String enumName, String textureName, int durability, int[] reductionAmounts, int enchantability, SoundEvent soundOnEquip, float toughness) {
		return EnumHelper.addEnum(ItemArmor.ArmorMaterial.class, enumName, new Class<?>[]{String.class, int.class, int[].class, int.class, SoundEvent.class, float.class}, textureName, durability, reductionAmounts, enchantability, soundOnEquip, toughness);
	}
	
	
	public static Fluid milk;
	
	public static SimpleNetworkWrapper network;
	
	public void preInit(FMLPreInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(new SaltModEvent());
		
		NetworkRegistry.INSTANCE.registerGuiHandler(SaltMod.instance, new GuiExtractorHandler());
		network = NetworkRegistry.INSTANCE.newSimpleChannel(SaltMod.MODID);
	    network.registerMessage(ExtractorButtonMessage.Handler.class, ExtractorButtonMessage.class, 0, Side.SERVER);
	    network.registerMessage(SaltWortMessage.Handler.class, SaltWortMessage.class, 1, Side.CLIENT);
	}

	public void init(FMLInitializationEvent event)
	{
		GameRegistry.registerTileEntity(TileEntityExtractor.class, "tileEntityExtractor");
	    EntityRegistry.registerModEntity(EntityRainmaker.class, "entityRainmaker", 0, SaltMod.instance, 64, 20, true);
		EntityRegistry.registerModEntity(EntityRainmakerDust.class, "entityRainmakerDust", 1, SaltMod.instance, 64, 20, false);		
	    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.rainmaker, new DispenserBehaviorRainmaiker());
	    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.saltPinch, new DispenserBehaviorSaltPinch());
		
		GameRegistry.registerWorldGenerator(new SaltOreGenerator(), 0);
		GameRegistry.registerWorldGenerator(new SaltCrystalGenerator(), 10);
		GameRegistry.registerWorldGenerator(new SaltLakeGenerator(), 15);
		
	//Recipe
		ExtractRegistry.instance().addExtracting(FluidRegistry.WATER, ModItems.saltPinch, 1000, 0.0F);
		
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltPinch, 9), new ItemStack(ModItems.salt));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.salt, 9), new ItemStack(ModBlocks.saltBlock));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.salt, 9), new ItemStack(ModBlocks.saltBlock, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.salt, 9), new ItemStack(ModBlocks.saltBlock, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.salt, 9), new ItemStack(ModBlocks.saltBlock, 1, 5));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.salt, 9), new ItemStack(ModBlocks.saltBlock, 1, 6));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.salt, 9), new ItemStack(ModBlocks.saltBlock, 1, 7));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.salt, 9), new ItemStack(ModBlocks.saltBlock, 1, 8));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.salt, 9), new ItemStack(ModBlocks.saltBlock, 1, 9));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.salt, 9), new ItemStack(ModBlocks.saltLamp));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.salt, 9), new ItemStack(ModBlocks.saltBrickStair));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltPinch, 40), new ItemStack(ModBlocks.saltSlab, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltPinch, 40), new ItemStack(ModBlocks.saltSlab, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltPinch, 40), new ItemStack(ModBlocks.saltSlab, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltPinch), new ItemStack(ModBlocks.saltCrystal));
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.saltDirt), new ItemStack(ModItems.salt), new ItemStack(Blocks.DIRT));
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.saltDirt), new ItemStack(ModBlocks.saltDirtLite), new ItemStack(ModItems.saltPinch), new ItemStack(ModItems.saltPinch), new ItemStack(ModItems.saltPinch));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.DYE, 1, 2), new ItemStack(ModItems.saltWortSeed));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.fizzyDrink), new ItemStack(ModItems.soda), new ItemStack(Items.POTIONITEM));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.POTIONITEM), new ItemStack(Items.GLASS_BOTTLE), new ItemStack(Items.SNOWBALL));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.mineralMud), new ItemStack(ModItems.soda), new ItemStack(ModItems.salt), new ItemStack(Items.COAL), new ItemStack(Items.CLAY_BALL));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.mineralMud), new ItemStack(ModItems.soda), new ItemStack(ModItems.salt), new ItemStack(Items.COAL, 1, 1), new ItemStack(Items.CLAY_BALL));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.mineralMud, 4), new ItemStack(ModBlocks.mudBlock));

		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltBeefCooked), new ItemStack(ModItems.saltPinch), new ItemStack(Items.COOKED_BEEF));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltPorkchopCooked), new ItemStack(ModItems.saltPinch), new ItemStack(Items.COOKED_PORKCHOP));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltPotatoBaked), new ItemStack(ModItems.saltPinch), new ItemStack(Items.BAKED_POTATO));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltChickenCooked), new ItemStack(ModItems.saltPinch), new ItemStack(Items.COOKED_CHICKEN));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltFishCod), new ItemStack(ModItems.saltPinch), new ItemStack(ModItems.saltPinch), new ItemStack(ModItems.saltPinch), new ItemStack(Items.FISH));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltFishCodCooked), new ItemStack(ModItems.saltPinch), new ItemStack(Items.COOKED_FISH));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltFishSalmon), new ItemStack(ModItems.saltPinch), new ItemStack(ModItems.saltPinch), new ItemStack(ModItems.saltPinch), new ItemStack(Items.FISH, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltFishSalmonCooked), new ItemStack(ModItems.saltPinch), new ItemStack(Items.COOKED_FISH, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltFishClownfish), new ItemStack(ModItems.saltPinch), new ItemStack(ModItems.saltPinch), new ItemStack(ModItems.saltPinch), new ItemStack(Items.FISH, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltMushroomStew), new ItemStack(ModItems.saltPinch), new ItemStack(Items.MUSHROOM_STEW));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltMushroomStew), new ItemStack(ModItems.saltPinch), new ItemStack(Items.BOWL), new ItemStack(Blocks.BROWN_MUSHROOM), new ItemStack(Blocks.RED_MUSHROOM));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltBeetroot), new ItemStack(ModItems.saltPinch), new ItemStack(Items.BEETROOT));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltBread), new ItemStack(ModItems.saltPinch), new ItemStack(Items.BREAD));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltEgg), new ItemStack(ModItems.saltPinch), new ItemStack(Items.EGG));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltBeetrootSoup), new ItemStack(ModItems.saltPinch), new ItemStack(Items.BEETROOT_SOUP));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltBeetrootSoup), new ItemStack(ModItems.saltPinch), new ItemStack(Items.BOWL), new ItemStack(Items.BEETROOT), new ItemStack(Items.BEETROOT), new ItemStack(Items.BEETROOT), new ItemStack(Items.BEETROOT), new ItemStack(Items.BEETROOT), new ItemStack(Items.BEETROOT));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltBeetrootSoup), new ItemStack(Items.BOWL), new ItemStack(ModItems.saltBeetroot), new ItemStack(Items.BEETROOT), new ItemStack(Items.BEETROOT), new ItemStack(Items.BEETROOT), new ItemStack(Items.BEETROOT), new ItemStack(Items.BEETROOT));		
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.pumpkinPorridge), new ItemStack(Items.BOWL), new ItemStack(Blocks.PUMPKIN));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.vegetableStew), new ItemStack(Items.BOWL), new ItemStack(Items.CARROT), new ItemStack(Items.POTATO), new ItemStack(Blocks.BROWN_MUSHROOM));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.vegetableStew), new ItemStack(Items.BOWL), new ItemStack(Items.CARROT), new ItemStack(Items.POTATO), new ItemStack(Blocks.RED_MUSHROOM));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltVegetableStew), new ItemStack(ModItems.saltPinch), new ItemStack(Items.BOWL), new ItemStack(Items.CARROT), new ItemStack(Items.POTATO), new ItemStack(Blocks.BROWN_MUSHROOM));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltVegetableStew), new ItemStack(ModItems.saltPinch), new ItemStack(Items.BOWL), new ItemStack(Items.CARROT), new ItemStack(Items.POTATO), new ItemStack(Blocks.RED_MUSHROOM));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltVegetableStew), new ItemStack(ModItems.saltPinch), new ItemStack(ModItems.vegetableStew));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.potatoMushroom), new ItemStack(Items.BOWL), new ItemStack(Items.POTATO), new ItemStack(Items.POTATO), new ItemStack(Blocks.BROWN_MUSHROOM));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.potatoMushroom), new ItemStack(Items.BOWL), new ItemStack(Items.POTATO), new ItemStack(Items.POTATO), new ItemStack(Blocks.RED_MUSHROOM));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltPotatoMushroom), new ItemStack(Items.BOWL), new ItemStack(ModItems.saltPinch), new ItemStack(Items.POTATO), new ItemStack(Items.POTATO), new ItemStack(Blocks.BROWN_MUSHROOM));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltPotatoMushroom), new ItemStack(Items.BOWL), new ItemStack(ModItems.saltPinch), new ItemStack(Items.POTATO), new ItemStack(Items.POTATO), new ItemStack(Blocks.RED_MUSHROOM));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltPotatoMushroom), new ItemStack(ModItems.potatoMushroom), new ItemStack(ModItems.saltPinch));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltPotatoMushroom), new ItemStack(ModItems.potatoMushroom), new ItemStack(ModItems.saltPinch));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.fishSoup), new ItemStack(Items.BOWL), new ItemStack(Items.CARROT), new ItemStack(Items.POTATO), new ItemStack(Items.FISH));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltFishSoup), new ItemStack(Items.BOWL), new ItemStack(ModItems.saltPinch), new ItemStack(Items.CARROT), new ItemStack(Items.POTATO), new ItemStack(Items.FISH));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltFishSoup), new ItemStack(ModItems.fishSoup), new ItemStack(ModItems.saltPinch));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.fishSalmonSoup), new ItemStack(Items.BOWL), new ItemStack(Items.CARROT), new ItemStack(Items.POTATO), new ItemStack(Items.FISH, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltFishSalmonSoup), new ItemStack(Items.BOWL), new ItemStack(ModItems.saltPinch), new ItemStack(Items.CARROT), new ItemStack(Items.POTATO), new ItemStack(Items.FISH, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltFishSalmonSoup), new ItemStack(ModItems.fishSalmonSoup), new ItemStack(ModItems.saltPinch));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltWortBeef), new ItemStack(Items.BOWL), new ItemStack(Items.COOKED_BEEF), new ItemStack(ModItems.saltWortSeed), new ItemStack(ModItems.saltWortSeed));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltWortPorkchop), new ItemStack(Items.BOWL), new ItemStack(Items.COOKED_PORKCHOP), new ItemStack(ModItems.saltWortSeed), new ItemStack(ModItems.saltWortSeed));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltWortMutton), new ItemStack(Items.BOWL), new ItemStack(Items.COOKED_MUTTON), new ItemStack(ModItems.saltWortSeed), new ItemStack(ModItems.saltWortSeed));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.beetrootSalad), new ItemStack(Items.BOWL), new ItemStack(Items.BEETROOT), new ItemStack(Items.POTATO), new ItemStack(Items.CARROT));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltBeetrootSalad), new ItemStack(ModItems.saltPinch), new ItemStack(Items.BOWL), new ItemStack(Items.BEETROOT), new ItemStack(Items.POTATO), new ItemStack(Items.CARROT));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltBeetrootSalad), new ItemStack(Items.BOWL), new ItemStack(ModItems.saltBeetroot), new ItemStack(Items.POTATO), new ItemStack(Items.CARROT));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltBeetrootSalad), new ItemStack(ModItems.saltPinch), new ItemStack(ModItems.beetrootSalad));		
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.HuFC), new ItemStack(Items.BOWL), new ItemStack(Items.BEETROOT), new ItemStack(Items.POTATO), new ItemStack(Items.CARROT), new ItemStack(Items.FISH), new ItemStack(Items.EGG), new ItemStack(Blocks.RED_FLOWER, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltHuFC), new ItemStack(ModItems.saltPinch), new ItemStack(Items.BOWL), new ItemStack(Items.BEETROOT), new ItemStack(Items.POTATO), new ItemStack(Items.CARROT), new ItemStack(Items.FISH), new ItemStack(Items.EGG), new ItemStack(Blocks.RED_FLOWER, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltHuFC), new ItemStack(Items.BOWL), new ItemStack(ModItems.saltBeetroot), new ItemStack(Items.POTATO), new ItemStack(Items.CARROT), new ItemStack(Items.FISH), new ItemStack(Items.EGG), new ItemStack(Blocks.RED_FLOWER, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltHuFC), new ItemStack(ModItems.saltPinch), new ItemStack(ModItems.HuFC));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.dandelionSalad), new ItemStack(Items.BOWL), new ItemStack(Items.WHEAT_SEEDS), new ItemStack(Blocks.YELLOW_FLOWER), new ItemStack(Blocks.RED_FLOWER, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltDandelionSalad), new ItemStack(ModItems.saltPinch), new ItemStack(Items.BOWL), new ItemStack(Items.WHEAT_SEEDS), new ItemStack(Blocks.YELLOW_FLOWER), new ItemStack(Blocks.RED_FLOWER, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltDandelionSalad), new ItemStack(ModItems.saltPinch), new ItemStack(ModItems.dandelionSalad));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.wheatSprouts), new ItemStack(Items.BOWL), new ItemStack(Items.WHEAT_SEEDS), new ItemStack(Items.WHEAT_SEEDS), new ItemStack(Items.WHEAT_SEEDS), new ItemStack(Items.WHEAT_SEEDS), new ItemStack(Items.WHEAT_SEEDS), new ItemStack(Items.WHEAT_SEEDS));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltWheatSprouts), new ItemStack(ModItems.saltPinch), new ItemStack(Items.BOWL), new ItemStack(Items.WHEAT_SEEDS), new ItemStack(Items.WHEAT_SEEDS), new ItemStack(Items.WHEAT_SEEDS), new ItemStack(Items.WHEAT_SEEDS), new ItemStack(Items.WHEAT_SEEDS), new ItemStack(Items.WHEAT_SEEDS));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltWheatSprouts), new ItemStack(ModItems.saltPinch), new ItemStack(ModItems.wheatSprouts));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.fruitSalad), new ItemStack(Items.BOWL), new ItemStack(Items.APPLE), new ItemStack(Items.CARROT), new ItemStack(Items.MELON));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.gratedCarrot), new ItemStack(Items.BOWL), new ItemStack(Items.CARROT), new ItemStack(Items.CARROT), new ItemStack(Items.SUGAR));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.carrotPie), new ItemStack(Items.CARROT), new ItemStack(Items.CARROT), new ItemStack(Items.SUGAR), new ItemStack(Items.EGG));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.applePie), new ItemStack(Items.APPLE), new ItemStack(Items.APPLE), new ItemStack(Items.SUGAR), new ItemStack(Items.EGG));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.potatoPie), new ItemStack(ModItems.saltPinch), new ItemStack(Items.POTATO), new ItemStack(Items.POTATO), new ItemStack(Items.EGG));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.onionPie), new ItemStack(ModItems.saltPinch), new ItemStack(Blocks.RED_FLOWER, 1, 2), new ItemStack(Blocks.RED_FLOWER, 1, 2), new ItemStack(Items.EGG));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.fishPie), new ItemStack(ModItems.saltPinch), new ItemStack(Items.WHEAT), new ItemStack(Items.FISH), new ItemStack(Items.EGG));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.fishSalmonPie), new ItemStack(ModItems.saltPinch), new ItemStack(Items.WHEAT), new ItemStack(Items.FISH, 1, 1), new ItemStack(Items.EGG));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.mushroomPie), new ItemStack(ModItems.saltPinch), new ItemStack(Blocks.BROWN_MUSHROOM), new ItemStack(Blocks.RED_MUSHROOM), new ItemStack(Items.EGG));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.mushroomPie), new ItemStack(ModItems.saltPinch), new ItemStack(Blocks.BROWN_MUSHROOM), new ItemStack(Blocks.BROWN_MUSHROOM), new ItemStack(Items.EGG));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.mushroomPie), new ItemStack(ModItems.saltPinch), new ItemStack(Blocks.RED_MUSHROOM), new ItemStack(Blocks.RED_MUSHROOM), new ItemStack(Items.EGG));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.pickledMushroom), new ItemStack(ModItems.saltPinch), new ItemStack(Items.POTIONITEM), new ItemStack(Blocks.BROWN_MUSHROOM), new ItemStack(Blocks.BROWN_MUSHROOM));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.pickledMushroom), new ItemStack(ModItems.saltPinch), new ItemStack(Items.POTIONITEM), new ItemStack(Blocks.RED_MUSHROOM), new ItemStack(Blocks.RED_MUSHROOM));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.pickledMushroom), new ItemStack(ModItems.saltPinch), new ItemStack(Items.POTIONITEM), new ItemStack(Blocks.BROWN_MUSHROOM), new ItemStack(Blocks.RED_MUSHROOM));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.pickledFern), new ItemStack(ModItems.saltPinch), new ItemStack(Items.POTIONITEM), new ItemStack(Blocks.TALLGRASS, 1, 2), new ItemStack(Blocks.TALLGRASS, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltWortPie), new ItemStack(ModItems.saltWortSeed), new ItemStack(ModItems.saltWortSeed), new ItemStack(Items.WHEAT), new ItemStack(Items.EGG));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltWortSalad), new ItemStack(Items.BOWL), new ItemStack(ModItems.saltWortSeed), new ItemStack(ModItems.saltWortSeed), new ItemStack(ModItems.saltWortSeed), new ItemStack(ModItems.saltWortSeed), new ItemStack(ModItems.saltWortSeed), new ItemStack(ModItems.saltWortSeed));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.fermentedSaltWort), new ItemStack(Items.GLASS_BOTTLE), new ItemStack(Items.GHAST_TEAR), new ItemStack(ModItems.saltWortSeed), new ItemStack(ModItems.saltWortSeed), new ItemStack(ModItems.saltWortSeed), new ItemStack(ModItems.saltWortSeed), new ItemStack(ModItems.saltWortSeed));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.muffin), new ItemStack(ModItems.soda), new ItemStack(Items.EGG), new ItemStack(Items.WHEAT), new ItemStack(Items.DYE, 1, 3));
		GameRegistry.addRecipe(new MilkBucketRecipe());
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltStar), new ItemStack(Items.GUNPOWDER), new ItemStack(ModItems.salt), new ItemStack(ModItems.salt), new ItemStack(ModItems.salt), new ItemStack(ModItems.salt), new ItemStack(ModItems.soda), new ItemStack(ModItems.soda), new ItemStack(ModItems.soda), new ItemStack(ModItems.soda));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rainmaker), new ItemStack(ModItems.saltStar), new ItemStack(ModItems.saltStar), new ItemStack(ModItems.saltStar), new ItemStack(ModItems.saltStar), new ItemStack(ModItems.saltStar), new ItemStack(Items.PAPER), new ItemStack(Items.GUNPOWDER), new ItemStack(Items.GUNPOWDER), new ItemStack(Items.GUNPOWDER));

		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltMuttonCooked), new ItemStack(ModItems.saltPinch), new ItemStack(Items.COOKED_MUTTON));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltRabbitCooked), new ItemStack(ModItems.saltPinch), new ItemStack(Items.COOKED_RABBIT));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltRabbitStew), new ItemStack(ModItems.saltPinch), new ItemStack(Items.RABBIT_STEW));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltRabbitStew), new ItemStack(Items.BOWL), new ItemStack(ModItems.saltPinch), new ItemStack(Blocks.RED_MUSHROOM), new ItemStack(Items.COOKED_RABBIT), new ItemStack(Items.CARROT), new ItemStack(Items.BAKED_POTATO));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltRabbitStew), new ItemStack(Items.BOWL), new ItemStack(ModItems.saltPinch), new ItemStack(Blocks.BROWN_MUSHROOM), new ItemStack(Items.COOKED_RABBIT), new ItemStack(Items.CARROT), new ItemStack(Items.BAKED_POTATO));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltRabbitStew), new ItemStack(Items.BOWL), new ItemStack(Blocks.RED_MUSHROOM), new ItemStack(ModItems.saltRabbitCooked), new ItemStack(Items.CARROT), new ItemStack(Items.BAKED_POTATO));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltRabbitStew), new ItemStack(Items.BOWL), new ItemStack(Blocks.BROWN_MUSHROOM), new ItemStack(ModItems.saltRabbitCooked), new ItemStack(Items.CARROT), new ItemStack(Items.BAKED_POTATO));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltRabbitStew), new ItemStack(Items.BOWL), new ItemStack(Blocks.RED_MUSHROOM), new ItemStack(Items.COOKED_RABBIT), new ItemStack(Items.CARROT), new ItemStack(ModItems.saltPotatoBaked));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltRabbitStew), new ItemStack(Items.BOWL), new ItemStack(Blocks.BROWN_MUSHROOM), new ItemStack(Items.COOKED_RABBIT), new ItemStack(Items.CARROT), new ItemStack(ModItems.saltPotatoBaked));
		
		GameRegistry.addRecipe(new ItemStack(ModItems.salt), "xxx", "xxx", "xxx", 'x', ModItems.saltPinch);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.saltBlock), "xxx", "xxx", "xxx", 'x', ModItems.salt);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.saltLamp), "x", "y", 'x', new ItemStack(ModBlocks.saltBlock, 1, 0), 'y', Blocks.TORCH);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.saltBlock, 4, 5), "xx", "xx", 'x', new ItemStack(ModBlocks.saltBlock, 1, 0));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.saltBlock, 2, 2), "x", "x", 'x', new ItemStack(ModBlocks.saltBlock, 1, 0));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.saltBlock, 1, 1), "x", "x", 'x', new ItemStack(ModBlocks.saltSlab, 1, 0));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.saltBlock, 1, 8), "x", "x", 'x', new ItemStack(ModBlocks.saltSlab, 1, 1));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.saltBlock, 1, 9), "x", "x", 'x', new ItemStack(ModBlocks.saltSlab, 1, 2));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.saltBrickStair, 6), "  x", " xx", "xxx", 'x', new ItemStack(ModBlocks.saltBlock, 1, 5));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.saltSlab, 6, 0), "xxx", 'x', new ItemStack(ModBlocks.saltBlock, 1, 0));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.saltSlab, 6, 1), "xxx", 'x', new ItemStack(ModBlocks.saltBlock, 1, 5));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.saltSlab, 6, 2), "xxx", 'x', new ItemStack(ModBlocks.saltBlock, 1, 2));		
		GameRegistry.addRecipe(new ItemStack(ModItems.cornedBeef), "xxx", "xyx", "xxx", 'x', ModItems.saltPinch, 'y', Items.ROTTEN_FLESH);
		
		GameRegistry.addRecipe(new ItemStack(ModBlocks.mudBlock), "xx", "xx", 'x', ModItems.mineralMud);
		GameRegistry.addRecipe(new ItemStack(ModItems.mudHelmet), "xxx", "x x", 'x', ModItems.mineralMud);
		GameRegistry.addRecipe(new ItemStack(ModItems.mudChestplate), "x x", "xxx", "xxx", 'x', ModItems.mineralMud);
		GameRegistry.addRecipe(new ItemStack(ModItems.mudLeggings), "xxx", "x x", "x x", 'x', ModItems.mineralMud);
		GameRegistry.addRecipe(new ItemStack(ModItems.mudBoots), "x x", "x x", 'x', ModItems.mineralMud);

		GameRegistry.addRecipe(new ItemStack(ModBlocks.extractor), "xyx", "x x", "xxx", 'x', Blocks.COBBLESTONE, 'y', Items.CAULDRON);
		
		GameRegistry.addSmelting(ModBlocks.saltOre, new ItemStack(ModItems.salt, 1), 0.7F);
		GameRegistry.addSmelting(ModBlocks.saltLake, new ItemStack(ModItems.salt, 1), 0.7F);
		GameRegistry.addSmelting(new ItemStack(ModBlocks.saltBlock, 1, 0), new ItemStack(ModBlocks.saltBlock, 1, 6), 0.0F);
		GameRegistry.addSmelting(new ItemStack(ModBlocks.saltBlock, 1, 5), new ItemStack(ModBlocks.saltBlock, 1, 7), 0.0F);
		GameRegistry.addSmelting(ModItems.saltWortSeed, new ItemStack(ModItems.soda, 1), 0.0F);
		
	//OreDictionary
		OreDictionary.registerOre("oreSalt", ModBlocks.saltOre);
		OreDictionary.registerOre("blockSalt", ModBlocks.saltBlock);
		OreDictionary.registerOre("blockSaltCrystal", ModBlocks.saltCrystal);
		OreDictionary.registerOre("lumpSalt", ModItems.salt);
		OreDictionary.registerOre("dustSalt", ModItems.saltPinch);
		OreDictionary.registerOre("dustSoda", ModItems.soda);
		OreDictionary.registerOre("dustMilk", ModItems.powderedMilk);
		OreDictionary.registerOre("cropSaltwort", ModItems.saltWortSeed);
		OreDictionary.registerOre("materialMineralMud", ModItems.mineralMud);
	}
	
    private static Item registerItem(Item item, String registryName) {
        item.setRegistryName(registryName);
        return GameRegistry.register(item);
    }
	
    public void postInit(FMLPostInitializationEvent event)
    {
    //Milk
    	if (FluidRegistry.isFluidRegistered("milk"))
    	{
    		Fluid milk = FluidRegistry.getFluid("milk");
    		ExtractRegistry.instance().addExtracting(milk, ModItems.powderedMilk, 1000, 0.0F);
    	}    	
    	else
    	{
    		milk = new Fluid("milk", new ResourceLocation("saltmod:blocks/Milk"), new ResourceLocation("saltmod:blocks/Milk"));
    		FluidRegistry.registerFluid(milk);
    		FluidContainerRegistry.registerFluidContainer(new FluidStack(milk, FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(Items.MILK_BUCKET), FluidContainerRegistry.EMPTY_BUCKET);
    		ExtractRegistry.instance().addExtracting(milk, ModItems.powderedMilk, 1000, 0.0F);
    	}
    //Blood
    	if (FluidRegistry.isFluidRegistered("blood"))
    	{
    		Fluid blood = FluidRegistry.getFluid("blood");
    		registerItem(ModItems.hemoglobin, "hemoglobin");
    		if (event.getSide().isClient()) ClientRegister.registerItems(ModItems.hemoglobin);
    		ExtractRegistry.instance().addExtracting(blood, ModItems.hemoglobin, 1000, 1.0F);
    	}
	//BOP
    	Item saladveggie = GameRegistry.findItem("BiomesOPlenty", "saladveggie");
    	if (saladveggie != null) {
    		registerItem(SaltConfig.bop_saltSaladVeggie, "bop_saltSaladVeggie");
    		if (event.getSide().isClient()) ClientRegister.registerItems(SaltConfig.bop_saltSaladVeggie);
			GameRegistry.addShapelessRecipe(new ItemStack(SaltConfig.bop_saltSaladVeggie), new ItemStack(saladveggie), new ItemStack(ModItems.saltPinch));
		}
    	Item saladshroom = GameRegistry.findItem("BiomesOPlenty", "saladshroom");
    	if (saladshroom != null) {
    		registerItem(SaltConfig.bop_saltSaladShroom, "bop_saltSaladShroom");
    		if (event.getSide().isClient()) ClientRegister.registerItems(SaltConfig.bop_saltSaladShroom);
			GameRegistry.addShapelessRecipe(new ItemStack(SaltConfig.bop_saltSaladShroom), new ItemStack(saladshroom), new ItemStack(ModItems.saltPinch));
		}
    	Item ricebowl = GameRegistry.findItem("BiomesOPlenty", "ricebowl");
    	if (ricebowl != null) {
    		registerItem(SaltConfig.bop_saltRiceBowl, "bop_saltRiceBowl");
    		if (event.getSide().isClient()) ClientRegister.registerItems(SaltConfig.bop_saltRiceBowl);
			GameRegistry.addShapelessRecipe(new ItemStack(SaltConfig.bop_saltRiceBowl), new ItemStack(ricebowl), new ItemStack(ModItems.saltPinch));
		}
    	Item turnip = GameRegistry.findItem("BiomesOPlenty", "turnip");
    	if (turnip != null) {
    		GameRegistry.registerItem(SaltConfig.bop_pickledTurnip, "bop_pickledTurnip");
    		if (event.getSide().isClient()) ClientRegister.registerItems(SaltConfig.bop_pickledTurnip);
			GameRegistry.addShapelessRecipe(new ItemStack(SaltConfig.bop_pickledTurnip), new ItemStack(turnip), new ItemStack(turnip), new ItemStack(Items.POTIONITEM), new ItemStack(ModItems.saltPinch));
		}
    	Item shroompowder = GameRegistry.findItem("BiomesOPlenty", "shroompowder");
    	if (shroompowder != null) {
    		registerItem(SaltConfig.bop_saltShroomPowder, "bop_saltShroomPowder");
    		if (event.getSide().isClient()) ClientRegister.registerItems(SaltConfig.bop_saltShroomPowder);
			GameRegistry.addShapelessRecipe(new ItemStack(SaltConfig.bop_saltShroomPowder), new ItemStack(shroompowder), new ItemStack(ModItems.saltPinch));
		}
    	Item bop_dart = GameRegistry.findItem("BiomesOPlenty", "dart");
        ItemStack bop_poisondart = new ItemStack(bop_dart, 1, 1);
		if (bop_dart != null && FluidRegistry.isFluidRegistered("poison")) {
			Fluid poisonFl = FluidRegistry.getFluid("poison");
    		registerItem(SaltConfig.bop_poison, "bop_poison");
    		if (event.getSide().isClient()) ClientRegister.registerItems(SaltConfig.bop_poison);
			ExtractRegistry.instance().addExtracting(poisonFl, SaltConfig.bop_poison, 1000, 1.0F);
			GameRegistry.addShapelessRecipe(bop_poisondart, new ItemStack(bop_dart), SaltConfig.bop_poison);
		}
    }
}