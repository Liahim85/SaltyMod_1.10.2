package ru.liahim.saltmod.init;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import ru.liahim.saltmod.SaltMod;
import ru.liahim.saltmod.block.DoubleSaltSlab;
import ru.liahim.saltmod.block.Extractor;
import ru.liahim.saltmod.block.HalfSaltSlab;
import ru.liahim.saltmod.block.MudBlock;
import ru.liahim.saltmod.block.SaltBlock;
import ru.liahim.saltmod.block.SaltBrickStair;
import ru.liahim.saltmod.block.SaltCrystal;
import ru.liahim.saltmod.block.SaltDirt;
import ru.liahim.saltmod.block.SaltDirtLite;
import ru.liahim.saltmod.block.SaltGrass;
import ru.liahim.saltmod.block.SaltLake;
import ru.liahim.saltmod.block.SaltLamp;
import ru.liahim.saltmod.block.SaltOre;
import ru.liahim.saltmod.block.SaltPot;
import ru.liahim.saltmod.block.SaltSlab;
import ru.liahim.saltmod.block.SaltWort;
import ru.liahim.saltmod.common.CommonProxy;
import ru.liahim.saltmod.item.ItemSaltBlock;
import ru.liahim.saltmod.item.ItemSaltDirt;
import ru.liahim.saltmod.item.ItemSaltGrass;
import ru.liahim.saltmod.item.ItemSaltSlab;

public class ModBlocks {
	
	static CreativeTabs tab = CommonProxy.saltTab;
	
	public static Block saltOre = new SaltOre("saltOre", tab);
	public static Block saltLake = new SaltLake("saltLake", tab);
	public static Block saltBlock = new SaltBlock(tab);
	public static BlockStairs saltBrickStair = new SaltBrickStair("saltBrickStair", tab);
	public static SaltSlab saltSlab = new HalfSaltSlab("saltSlab");
	public static SaltSlab saltSlabDouble = new DoubleSaltSlab("saltSlabDouble");
	public static Block saltLamp = new SaltLamp("saltLamp", tab);
	public static Block saltDirt = new SaltDirt(tab);
	public static Block saltDirtLite = new SaltDirtLite("saltDirtLite", tab);
	public static Block saltGrass = new SaltGrass("saltGrass", tab);
	public static Block mudBlock = new MudBlock("mudBlock", tab);
	public static Block extractor = new Extractor(false, false, "extractor", tab);
	public static Block extractorLit = new Extractor(true, false, "extractor", null);
	public static Block extractorSteam = new Extractor(true, true, "extractor", null);
	public static Block saltCrystal = new SaltCrystal("saltCrystal", tab);
	public static Block saltWort = new SaltWort("saltWort", null);	
	public static Block saltPot = new SaltPot("saltPot", null);

    public static void createBlocks()
    {
    	SaltMod.logger.info("Start to initialize Blocks");
    	registerBlock(saltOre, "saltOre");
    	registerBlock(saltLake, "saltLake");
    	registerBlock(saltBlock, new ItemSaltBlock(saltBlock), "saltBlock");
    	registerBlock(saltBrickStair, "saltBrickStair");
		registerBlock(saltSlab, new ItemSaltSlab(saltSlab), "saltSlab");
		registerBlock(saltSlabDouble, new ItemSaltSlab(saltSlabDouble), "saltSlabDouble");		
    	registerBlock(saltLamp, "saltLamp");
    	registerBlock(saltDirt, new ItemSaltDirt(saltDirt), "saltDirt");		
    	registerBlock(saltDirtLite, "saltDirtLite");	
    	registerBlock(saltGrass, new ItemSaltGrass(saltGrass), "saltGrass");
    	registerBlock(mudBlock, "mudBlock");
		registerBlock(extractor, "extractor");
		registerBlock(extractorLit, "extractorLit").setLightLevel(0.9F);
		registerBlock(extractorSteam, "extractorSteam").setLightLevel(0.9F);
    	registerBlock(saltCrystal, "saltCrystal");
    	registerBlock(saltWort, "saltWort");
    	registerBlock(saltPot, "saltPot");
    	SaltMod.logger.info("Finished initializing Blocks");
    }
    
    private static Block registerBlock(Block block, String registryName) {
    	final ItemBlock itemBlock = new ItemBlock(block);
        return registerBlock(block, itemBlock, registryName);
    }
    
    private static Block registerBlock(Block block, ItemBlock itemBlock, String registryName) {
    	block.setRegistryName(registryName);    	
        GameRegistry.register(block);
        itemBlock.setRegistryName(registryName);
        GameRegistry.register(itemBlock);    	
        return block;
    }
}