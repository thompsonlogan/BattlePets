package Skills;

import Domain.Utils;

/**
 * Enumeration for the different skills a pet can use
 */
public enum Skills
{
	ROCK_THROW,
	SCISSORS_POKE,
	PAPER_CUT,
	REVERSAL_OF_FORTUNE,
	SHOOT_THE_MOON;

	/**
	 * @return a string representation of the enum
	 */
	@Override
	public String toString()
	{		
		return Utils.convertEnumString(this.name());
	}

}
