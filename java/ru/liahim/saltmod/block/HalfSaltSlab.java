package ru.liahim.saltmod.block;

public class HalfSaltSlab extends SaltSlab
{
	public HalfSaltSlab(String name)
	{
		super(name);
	}

	@Override
	public boolean isDouble()
    {
        return false;
    }
}