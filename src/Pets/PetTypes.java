package Pets;

import Domain.*;

/**
 * Enumeration for the different types of pets in the game
 */
public enum PetTypes
{
	POWER,
	SPEED,
	INTELLIGENCE;

	/**
	 * @return a string representation of the enum
	 */
	@Override
	public String toString()
	{		
		return Utils.convertEnumString(this.name());
	}
}
