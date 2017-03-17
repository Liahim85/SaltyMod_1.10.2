package ru.liahim.saltmod.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class RainMakerEvent extends Event {
	
    public final World world;
    public final double x;
    public final double y;
    public final double z;
    public final EntityPlayer player;
    public final boolean isThunder;
    
    /**
     * Fired when the RainMaker makes the rain.
     * 
     * @param world     - World.
     * @param x, y, z   - Explosion coordinates.
	 * @param player    - The player, who launched a RainMaker. May be null.
	 * @param isThunder - There is a storm or not.
     * 
     */
    
    public RainMakerEvent(World world, double x, double y, double z, EntityPlayer player, boolean isThunder)
    {
        this.world = world;
        this.x = z;
        this.y = y;
        this.z = z;
        this.player = player;
        this.isThunder = isThunder;
    }
}