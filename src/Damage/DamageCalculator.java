package Damage;

import Domain.IOManager;
import GameControllers.SeedGenerator;
import Pets.*;
import Skills.*;

/**
 * DamageCalculator Class
 * calculates the damage a pets skill is going to do
 * based and the receiving pets type, skill they used,
 * and their hp. Manages finding the random damage for
 * each attack and the conditional damage for each attack
 */
public class DamageCalculator
{
    private final double PERCENT_HP_0 = 0.00f; // constant value for 0% hp of a pet
    private final double PERCENT_HP_25 = 25.0f; // constant value for 25% hp of a pet
    private final double PERCENT_HP_75 = 75.0f; // constant value for 75% hp of a pet
    private final double SPEED_CONDITIONAL_DAMAGE = 10.0f; // constant value for a speed pet conditional multiplier
    private final double POWER_RANDOM_DAMAGE_MULTIPLIER = 5.0f; // constant value for the random damage multiplier
    private final double INTELLIGENCE_CONDITIONAL_DAMAGE_3 = 3; // constant value for intelligence conditional damage
    private final double INTELLIGENCE_CONDITIONAL_DAMAGE_2 = 2; // constant value for intelligence conditional damage
    private final double SHOOT_THE_MOON_CONDITIONAL_DAMAGE = 20; // constant value for shoot the moon conditional damage

    private double randomDamage = 0; // default random damage for each damage
    private double conditionalDamage = 0; // default conditional damage for damage
    private final SeedGenerator randomSeedGenerator = SeedGenerator.INSTANCE; // seed of the game used in calculating random damage

    /**
     * Calculates the random damage and conditional damage based ony many factors,
     * see game description for exact details(Game.ppt)
     * @param inAttacker the pet attacking
     * @param inReceiver the pet being attacked
     * @return a damage object for the attacker to apply to the receiver
     */
    public Damage calculateDamage(Playable inAttacker, Playable inReceiver, DamageCounter damageCounter){
        conditionalDamage = 0;
        randomDamage = randomSeedGenerator.getSeed();

        BattlePet attacker = (BattlePet)inAttacker;
        BattlePet receiver = (BattlePet)inReceiver;
        PetTypes attackerType = attacker.getPetType();
        Skills receiverSkill = receiver.getSkill().getSkillToBeUsed();

        //POWER PET TYPE
        if (attackerType == PetTypes.POWER)
        {
            if(attacker.getSkill().getSkillToBeUsed() == Skills.ROCK_THROW && receiverSkill == Skills.SCISSORS_POKE)
            {
                conditionalDamage = randomDamage * POWER_RANDOM_DAMAGE_MULTIPLIER;
            }
            else if (attacker.getSkill().getSkillToBeUsed() == Skills.SCISSORS_POKE && receiverSkill == Skills.PAPER_CUT){
                conditionalDamage = randomDamage * POWER_RANDOM_DAMAGE_MULTIPLIER;
            }
            else if(attacker.getSkill().getSkillToBeUsed() == Skills.PAPER_CUT && receiverSkill == Skills.ROCK_THROW)
            {
                conditionalDamage = randomDamage * POWER_RANDOM_DAMAGE_MULTIPLIER;
            }
            else if(attacker.getSkill().getSkillToBeUsed() == Skills.SHOOT_THE_MOON)
            {
                if(receiverSkill == attacker.getSkill().getSkillToBePredicted())
                {
                    conditionalDamage = SHOOT_THE_MOON_CONDITIONAL_DAMAGE;
                }

            }
            else if(attacker.getSkill().getSkillToBeUsed() == Skills.REVERSAL_OF_FORTUNE)
            {
                conditionalDamage = damageCounter.getPetRandomDamageDealt(attacker.getPetName()) - damageCounter.getPetRandomDamageTaken(attacker.getPetName());
                randomDamage = randomDamage - conditionalDamage;
                conditionalDamage = conditionalDamage * -1;
            }
        }


        //SPEED PET TYPE
        else if (attackerType == PetTypes.SPEED)
        {
            if (attacker.getSkill().getSkillToBeUsed() == Skills.ROCK_THROW)
            {
                if (receiver.calculateHpPercent() >= PERCENT_HP_75 && receiverSkill == Skills.SCISSORS_POKE)
                {
                    conditionalDamage = SPEED_CONDITIONAL_DAMAGE;
                }
                if (receiver.calculateHpPercent() >= PERCENT_HP_75 && receiverSkill == Skills.PAPER_CUT)
                {
                    conditionalDamage = SPEED_CONDITIONAL_DAMAGE;
                }
            }

            else if (attacker.getSkill().getSkillToBeUsed() == Skills.SCISSORS_POKE)
            {
                if (receiver.calculateHpPercent() >= PERCENT_HP_25 && receiver.calculateHpPercent() < PERCENT_HP_75 && receiverSkill == Skills.ROCK_THROW )
                {
                    conditionalDamage = SPEED_CONDITIONAL_DAMAGE;
                }
                if (receiver.calculateHpPercent() >= PERCENT_HP_25 && receiver.calculateHpPercent() < PERCENT_HP_75 && receiverSkill == Skills.PAPER_CUT)
                {
                    conditionalDamage = SPEED_CONDITIONAL_DAMAGE;
                }
            }

            else if (attacker.getSkill().getSkillToBeUsed() == Skills.PAPER_CUT)
            {
                if (receiver.calculateHpPercent() >= PERCENT_HP_0 && receiver.calculateHpPercent() < PERCENT_HP_25 && receiverSkill == Skills.ROCK_THROW)
                {
                    conditionalDamage = SPEED_CONDITIONAL_DAMAGE;
                }
                if (receiver.calculateHpPercent() >= PERCENT_HP_0 && receiver.calculateHpPercent() < PERCENT_HP_25 && receiverSkill == Skills.SCISSORS_POKE)
                {
                    conditionalDamage = SPEED_CONDITIONAL_DAMAGE;
                }
            }
            else if(attacker.getSkill().getSkillToBeUsed() == Skills.SHOOT_THE_MOON)
            {

                if(receiverSkill == attacker.getSkill().getSkillToBePredicted())
                {
                    conditionalDamage =  SHOOT_THE_MOON_CONDITIONAL_DAMAGE;
                }
            }
            else if(attacker.getSkill().getSkillToBeUsed() == Skills.REVERSAL_OF_FORTUNE)
            {
                conditionalDamage = damageCounter.getPetRandomDamageDealt(attacker.getPetName()) - damageCounter.getPetRandomDamageTaken(attacker.getPetName());
                randomDamage = randomDamage - conditionalDamage;
                conditionalDamage = conditionalDamage * -1;
            }
        }

        //INTELLIGENCE PET TYPE
        else
        {
            if (attacker.getSkill().getSkillToBeUsed() == Skills.ROCK_THROW)
            {
                if (receiver.getSkill().getCurrentSkillRechargeTime(Skills.SCISSORS_POKE) > 0)
                {
                    conditionalDamage = conditionalDamage + INTELLIGENCE_CONDITIONAL_DAMAGE_3;
                }
                if (receiver.getSkill().getCurrentSkillRechargeTime(Skills.ROCK_THROW) >0)
                {
                    conditionalDamage = conditionalDamage + INTELLIGENCE_CONDITIONAL_DAMAGE_2;
                }
                if (receiver.getSkill().getCurrentSkillRechargeTime(Skills.SHOOT_THE_MOON) >0)
                {
                    conditionalDamage = conditionalDamage + INTELLIGENCE_CONDITIONAL_DAMAGE_2;
                }
            }

            else if (attacker.getSkill().getSkillToBeUsed() == Skills.SCISSORS_POKE)
            {
                if (receiver.getSkill().getCurrentSkillRechargeTime(Skills.PAPER_CUT) >0)
                {
                    conditionalDamage = conditionalDamage +INTELLIGENCE_CONDITIONAL_DAMAGE_3;
                }
                if (receiver.getSkill().getCurrentSkillRechargeTime(Skills.SCISSORS_POKE) >0)
                {
                    conditionalDamage = conditionalDamage +INTELLIGENCE_CONDITIONAL_DAMAGE_2;
                }
                if (receiver.getSkill().getCurrentSkillRechargeTime(Skills.SHOOT_THE_MOON) >0)
                {
                    conditionalDamage = conditionalDamage + INTELLIGENCE_CONDITIONAL_DAMAGE_2;
                }
            }

            else if (attacker.getSkill().getSkillToBeUsed() == Skills.PAPER_CUT)
            {
                if (receiver.getSkill().getCurrentSkillRechargeTime(Skills.ROCK_THROW) >0)
                {
                    conditionalDamage = conditionalDamage + INTELLIGENCE_CONDITIONAL_DAMAGE_3;
                }
                if (receiver.getSkill().getCurrentSkillRechargeTime(Skills.PAPER_CUT) >0)
                {
                    conditionalDamage = conditionalDamage +INTELLIGENCE_CONDITIONAL_DAMAGE_2;
                }
                if (receiver.getSkill().getCurrentSkillRechargeTime(Skills.SHOOT_THE_MOON) >0)
                {
                    conditionalDamage = conditionalDamage +INTELLIGENCE_CONDITIONAL_DAMAGE_2;
                }
            }

            else if(attacker.getSkill().getSkillToBeUsed() == Skills.SHOOT_THE_MOON)
            {
                if(receiverSkill == attacker.getSkill().getSkillToBePredicted())
                {
                    conditionalDamage = SHOOT_THE_MOON_CONDITIONAL_DAMAGE;
                }

            }
            else if(attacker.getSkill().getSkillToBeUsed() == Skills.REVERSAL_OF_FORTUNE)
            {
                conditionalDamage = damageCounter.getPetRandomDamageDealt(attacker.getPetName()) - damageCounter.getPetRandomDamageTaken(attacker.getPetName());
                randomDamage = randomDamage - conditionalDamage;
                conditionalDamage = conditionalDamage * -1;
            }
        }
        return new Damage(randomDamage, conditionalDamage);
    }

    /**
     * @return the calculated random damage
     */
    public double getRandomDamage()
    {
        return this.randomDamage;
    }

    /**
     * @return the calculated conditional damage
     */
    public double getConditionalDamage()
    {
        return this.conditionalDamage;
    }
}