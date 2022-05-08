package Damage;

/**
 * Damage class
 * Stores the randomDamage and conditionalDamage to be applied
 * to a battle pet during a fight
 */
public class Damage
{
	private double randomDamage; // the random damage component of damage
	private double conditionalDamage; // the conditional damage component of damage

	/**
	 * Damage constructor
	 * @param randomDamage the random damage portion of the damage
	 * @param conditionalDamage the conditional portion of the damage
	 */
	public Damage(double randomDamage, double conditionalDamage)
	{
		this.randomDamage = randomDamage;
		this.conditionalDamage = conditionalDamage;
	}

	/**
	 * @return the random damage portion of the damage
	 */
	public double getRandomDamage()
	{
		return randomDamage;
	}

	/**
	 * Sets the random damage for the damage object
	 * @param randomDamage the random damage portion of the damage
	 */
	public void setRandomDamage(double randomDamage)
	{
		this.randomDamage = randomDamage;
	}

	/**
	 * @return the conditional damage portion of the damage
	 */
	public double getConditionalDamage()
	{
		return conditionalDamage;
	}

	/**
	 * Sets the conditional damage for the damage object
	 * @param conditionalDamage the conditional damage portion of the damage
	 */
	public void setConditionalDamage(double conditionalDamage)
	{
		this.conditionalDamage = conditionalDamage;
	}

	/**
	 * Calculates the total damage to be applied, random + conditional
	 * @return the total damage to be applied
	 */
	public double calculateTotalDamage()
	{
		return randomDamage + conditionalDamage;
	}
}