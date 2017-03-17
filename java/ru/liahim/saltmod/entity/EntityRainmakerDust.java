package ru.liahim.saltmod.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import ru.liahim.saltmod.api.RainMakerEvent;
import ru.liahim.saltmod.init.SaltConfig;

public class EntityRainmakerDust extends Entity {
	
	private int lifeTime;
	private boolean rain = false;
    private EntityPlayer player;
    private int cloud = SaltConfig.cloudLevel.containsKey(this.worldObj.provider.getDimension()) ?
    		SaltConfig.cloudLevel.get(this.worldObj.provider.getDimension()) : 128;
	
    public EntityRainmakerDust(World world)
    {
        super(world);
    }
    
    public EntityRainmakerDust(World world, double x, double y, double z, EntityPlayer player)
    {
        super(world);
        this.setPosition(x, y, z);
        this.player = player;
    }

	@Override
	protected void entityInit() {}

	@Override
    public void onUpdate()
    {
		++this.lifeTime;
        this.setInvisible(true);
		
		if (this.worldObj.isRemote && this.lifeTime > 30)
		{
			double x = this.posX + this.rand.nextGaussian() * this.lifeTime/25;
			double y = this.posY + this.rand.nextGaussian() * 4.0D - this.lifeTime/15;
			double z = this.posZ + this.rand.nextGaussian() * this.lifeTime/25;
			
			this.worldObj.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, true, x, y, z, 0.0D, 0.0D, 0.0D);
		}
		
		if (!this.worldObj.isRemote && this.lifeTime > 200 && this.posY >= cloud && !rain && !this.worldObj.getWorldInfo().isThundering())
		{
			if (this.rand.nextInt(5) == 0 || this.worldObj.isRaining())
			MinecraftForge.EVENT_BUS.post(new RainMakerEvent(this.worldObj, this.posX, this.posY, this.posZ, this.player, true));
			else MinecraftForge.EVENT_BUS.post(new RainMakerEvent(this.worldObj, this.posX, this.posY, this.posZ, this.player, false));
			
			rain = true;
		}
		
		if (this.lifeTime > 250) {rain = false; this.setDead();}
    }
	
	@Override
    public void writeEntityToNBT(NBTTagCompound tag)
	{
		tag.setInteger("Life", this.lifeTime);
		tag.setBoolean("Rain", this.rain);
	}

    @Override
    public void readEntityFromNBT(NBTTagCompound tag)
    {
    	this.lifeTime = tag.getInteger("Life");
    	this.rain = tag.getBoolean("Rain");
    }
    
    @Override
    public boolean canBeAttackedWithItem()
    {
        return false;
    }
}