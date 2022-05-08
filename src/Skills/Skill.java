package Skills;

/**
 * Skill Class
 * Stores the cool down times for each skill for the pet
 * Stores the pets most recently picked skill for conditional damage calculations
 * Manages recharge times for each skill
 */
public class Skill
{
    static final int PAPER_CUT_RECHARGE = 1; // constant value for the recharge time of the paper cut skill
    static final int ROCK_THROW_RECHARGE = 1; // constant value for the recharge time of the rock throw skill
    static final int SCISSOR_POKE_RECHARGE = 1; // constant value for the recharge time of the scissor poke skill
    static final int SHOOT_THE_MOON_RECHARGE = 6; // constant value for the recharge time of the shoot the moon skill
    static final int REVERSAL_OF_FORTUNE_RECHARGE = 6; // constant value for the recharge time of the reversal of fortune skill
    private int paperCutCoolDown; // value to store the current cool down for the paper cut skill
    private int rockThrowCoolDown; // value to store the current cool down for the rock throw skill
    private int scissorPokeCoolDown; // value to store the current cool down for the scissor poke skill
    private int shootTheMoonCoolDown; // value to store the current cool down for the shoot the moon skill
    private int reversalOfFortuneCoolDown; // value to store the current cool down for the reversal Of fortune skill
    private Skills skillToBeUsed = null; // value to store the next skill to be used
    private Skills predictedSkill = null; // value to store the predicted skill

    /**
     * Skill constructor
     */
    public Skill()
    {
        this.paperCutCoolDown = 0;
        this.rockThrowCoolDown = 0;
        this.scissorPokeCoolDown = 0;
        this.shootTheMoonCoolDown = 0;
        this.reversalOfFortuneCoolDown = 0;
    }

    /**
     * Checks to see if the skill to be used is on cool down
     * and return true if the skill is on cool down
     * @param skillToBeUsed the skill to be used
     * @return if the skill passed is on cool down
     */
    public boolean isOnCoolDown(Skills skillToBeUsed)
    {
        if(skillToBeUsed == Skills.PAPER_CUT && paperCutCoolDown > 0)
            return true;
        else if(skillToBeUsed == Skills.ROCK_THROW && rockThrowCoolDown > 0)
            return true;
        else if(skillToBeUsed == Skills.SCISSORS_POKE && scissorPokeCoolDown > 0)
            return true;
        else if(skillToBeUsed == Skills.SHOOT_THE_MOON && shootTheMoonCoolDown > 0)
            return true;
        else return skillToBeUsed == Skills.REVERSAL_OF_FORTUNE && reversalOfFortuneCoolDown > 0;
    }

    /**
     * @param skillRequested the skill the pet wants to use
     * @return the recharge time for the passed requested skill
     */
    public int getCurrentSkillRechargeTime(Skills skillRequested)
    {
        if (skillRequested == Skills.PAPER_CUT)
        {
            return this.paperCutCoolDown;
        }
        else if (skillRequested == Skills.ROCK_THROW)
        {
            return this.rockThrowCoolDown;
        }
        else if (skillRequested == Skills.SCISSORS_POKE)
        {
            return this.scissorPokeCoolDown;
        }
        else if (skillRequested == Skills.SHOOT_THE_MOON)
        {
            return this.shootTheMoonCoolDown;
        }
        else if (skillRequested == Skills.REVERSAL_OF_FORTUNE)
        {
            return this.reversalOfFortuneCoolDown;
        }
        return -1;
    }

    /**
     * Sets the recharge time for a skill when a pet uses a skill
     * @param skillUsed the skill the pet is trying to use
     */
    public void useSkill(Skills skillUsed)
    {
        skillToBeUsed = skillUsed;

        if (skillUsed == Skills.PAPER_CUT)
        {
            this.paperCutCoolDown = PAPER_CUT_RECHARGE;
        }
        else if (skillUsed == Skills.ROCK_THROW)
        {
            this.rockThrowCoolDown = ROCK_THROW_RECHARGE;
        }
        else if (skillUsed == Skills.SCISSORS_POKE)
        {
            this.scissorPokeCoolDown = SCISSOR_POKE_RECHARGE;
        }
        else if (skillUsed == Skills.SHOOT_THE_MOON)
        {
            this.shootTheMoonCoolDown = SHOOT_THE_MOON_RECHARGE;
        }
        else if (skillUsed == Skills.REVERSAL_OF_FORTUNE)
        {
            this.reversalOfFortuneCoolDown = REVERSAL_OF_FORTUNE_RECHARGE;
        }
    }

    /**
     * updates all skills recharge time for each turn if the
     * skill is on cool down
     */
    public void updateSkillCoolDown()
    {
        if (paperCutCoolDown > 0)
        {
            paperCutCoolDown --;
        }
        if (rockThrowCoolDown > 0)
        {
            rockThrowCoolDown --;
        }
        if (scissorPokeCoolDown > 0)
        {
            scissorPokeCoolDown--;
        }
        if (shootTheMoonCoolDown > 0)
        {
            shootTheMoonCoolDown--;
        }
        if (reversalOfFortuneCoolDown > 0)
        {
            reversalOfFortuneCoolDown--;
        }
    }

    /**
     * @return the skill a pet is going to use in its next turn
     */
    public Skills getSkillToBeUsed()
    {
        return skillToBeUsed;
    }

    /**
     * @return the skill a pet predicted
     */
    public Skills getSkillToBePredicted()
    {
        return predictedSkill;
    }

    /**
     * sets the skill the play predicted their opponent was going to use
     * @param skillToBePredicted skill to be predicted
     */
    public void setSkillToBePredicted(Skills skillToBePredicted)
    {
        this.predictedSkill = skillToBePredicted;
    }

    /**
     *
     * Sets the skill a pet is going to use in its next turn
     * @param skillToBeUsed the skill to be used by the pet
     */
    public void setSkillToBeUsed(Skills skillToBeUsed)
    {
        this.skillToBeUsed = skillToBeUsed;
    }

    /**
     * @return the skill currently on cool down, null
     * if no skills are on cool down
     * // TODO: 10/15/2020: this will need to be adjusted to support multiple skills on cool down
     */
    public Skills getSkillRecharging()
    {
        if (paperCutCoolDown > 0)
        {
            return Skills.PAPER_CUT;
        }
        else if (rockThrowCoolDown > 0)
        {
            return Skills.ROCK_THROW;
        }
        else if (scissorPokeCoolDown > 0)
        {
            return Skills.SCISSORS_POKE;
        }
        else if (shootTheMoonCoolDown > 0)
        {
            return Skills.SHOOT_THE_MOON;
        }
        else if (reversalOfFortuneCoolDown > 0)
        {
            return Skills.REVERSAL_OF_FORTUNE;
        }
        else
        {
            return null;
        }
    }

    /**
     * resets the skill cool downs after the fight
     * resets them to 0
     */
    public void restCoolDownsAfterFight()
    {
        this.paperCutCoolDown = 0;
        this.rockThrowCoolDown = 0;
        this.scissorPokeCoolDown = 0;
        this.shootTheMoonCoolDown = 0;
        this.reversalOfFortuneCoolDown = 0;
    }

    /**
     * Increments the passed skills cool down by 1
     * currently used in testing
     * @param skill, the skill to be checked
     */
    public void setSkillCoolDown(Skills skill)
    {
        if (skill == Skills.PAPER_CUT)
        {
            this.paperCutCoolDown += 1;
        }
        else if (skill == Skills.ROCK_THROW)
        {
            this.rockThrowCoolDown += 1;
        }
        else if (skill == Skills.SCISSORS_POKE)
        {
            this.scissorPokeCoolDown += 1;
        }
        else if (skill == Skills.SHOOT_THE_MOON)
        {
            this.shootTheMoonCoolDown += 6;
        }
        else if (skill == Skills.REVERSAL_OF_FORTUNE)
        {
            this.reversalOfFortuneCoolDown += 6;
        }
    }
}
