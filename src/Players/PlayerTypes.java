package Players;

import Domain.Utils;

/**
 * Enumeration for the different types of players
 */
public enum PlayerTypes
{
	HUMAN,
	COMPUTER;

	/**
	 * @return a string representation of the enum
	 */
	@Override
	public String toString()
	{		
		return Utils.convertEnumString(this.name());
	}
}
