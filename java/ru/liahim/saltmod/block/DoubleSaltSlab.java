package ru.liahim.saltmod.block;


public class DoubleSaltSlab extends SaltSlab
{
	public DoubleSaltSlab(String name)
	{
		super(name);
	}

	@Override
	public boolean isDouble()
    {
        return true;
    }
}