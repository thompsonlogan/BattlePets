package Pets;

import Domain.IOManager;
import GameControllers.Battle;
import Players.PlayerTypes;
import Skills.Skill;
import Skills.Skills;

import java.util.List;

/**
 * Battle pet class
 * Contains variables for its type, starting hp, current hp, name, and skill
 * Contains nested BattlePetBuilder class
 */
public final class BattlePet implements Playable
{
    private final int PERCENTAGE_FACTOR = 100;

    private final int id; // Playable Id to identify battle pet
    private double currentHp; // The current health point value of the pet object
    private final String name; // The title given to the pet object
    private final String playerName; // The name of the player who controls the pet object
    private final PetTypes type; // The type of pet specified for pet object (Power, Speed, or Intelligence)
    private final PlayerTypes playerType; // The type of the player that controls the pet object
    private final double startingHp; // The starting health point value of the pet object
    private final Skill skill = new Skill(); // Skill object used by the pet
    private final IOManager io = IOManager.INSTANCE; // for input and output

    /**
     * Battle pet constructor
     * @params BattlePetBuilder battlePetBuilder
     * @params BattlePetBuilder battlePetBuilder
     * Sets values from the nested builder class to BattlePet object
     * Private constructor can not be instantiated outside of the class
     * State exceptions passed from the builder are included here
     */
    private BattlePet(BattlePetBuilder battlePetBuilder)
    {
        if (battlePetBuilder.type == null)
        {
            throw new IllegalStateException("The pet type can not be null");
        }
        else
        {
            type = battlePetBuilder.type;
        }

        if (battlePetBuilder.startingHp == 0.0f)
        {
            throw new IllegalStateException("The starting hp can not be 0");
        }
        else
        {
            startingHp = battlePetBuilder.startingHp;
        }

        if (battlePetBuilder.currentHp == 0.0f)
        {
            currentHp = battlePetBuilder.startingHp;
        }
        else
        {
            currentHp = battlePetBuilder.currentHp;
        }

        if (battlePetBuilder.name == null)
        {
            throw new IllegalStateException("The name can not be null");
        }
        else
        {
            name = battlePetBuilder.name;
        }

        if (battlePetBuilder.id == -1)
        {
            throw new IllegalStateException("The pet must be set with an ID");
        }
        else
        {
            id = battlePetBuilder.id;
        }

        if(battlePetBuilder.playerName == null)
        {
            throw new IllegalStateException("The player name can not be null");
        }
        else
        {
            playerName = battlePetBuilder.playerName;
        }

        if(battlePetBuilder.playerType == null)
        {
            throw new IllegalStateException("The player type can not be null");
        }
        else
        {
            playerType = battlePetBuilder.playerType;
        }
    }

    /**
     * equals override for battle pet
     * @params Object otherObject
     * returns the boolean value if the parameter passed is the same as the object calling the equals method
     */
    @Override
    public boolean equals(Object otherObject)
    {
        if (this == otherObject)
        {
            return true;
        }
        if(!(otherObject instanceof Battle))
        {
            return false;
        }

        BattlePet otherBattlePet = (BattlePet) otherObject;

        return this.name.equalsIgnoreCase(otherBattlePet.name)
                && this.startingHp == otherBattlePet.startingHp
                && this.type == otherBattlePet.type;
    }


    /**
     * Playable overrides for battle pet
     * See 'Playable.java' for javadoc descriptions
     */
    @Override
    public String getPlayerName()
    {
        return this.playerName;
    }

    @Override
    public String getPetName()
    {
        return name;
    }

    @Override
    public PlayerTypes getPlayerType()
    {
        return this.playerType;
    }

    @Override
    public PetTypes getPetType()
    {
        return type;
    }

    @Override
    public double getCurrentHp()
    {
        return currentHp;
    }

    @Override
    public Skills chooseSkill()
    {
        Skills skillToBeUsed = io.getSkillInput(this);
        if(skillToBeUsed == Skills.SHOOT_THE_MOON)
            skill.setSkillToBePredicted(io.predictSkill());
        this.skill.setSkillToBeUsed(skillToBeUsed);
        return skillToBeUsed;
    }

    @Override
    public Skills getSkillPrediction()
    {
        return io.predictSkill();
    }

    @Override
    public int getSkillRechargeTime(Skills skill)
    {
        return this.skill.getCurrentSkillRechargeTime(skill);
    }

    @Override
    public void decrementRechargeTimes()
    {
        skill.updateSkillCoolDown();
    }

    @Override
    public void setRechargeTime(Skills skill, int rechargeTime)
    {
        this.skill.useSkill(this.skill.getSkillToBeUsed());
    }

    @Override
    public void updateHp(double hp)
    {
        currentHp = currentHp - hp;
    }

    @Override
    public void resetHp()
    {
        currentHp = startingHp;
    }

    @Override
    public boolean isAwake()
    {
        return currentHp > 0.0f;
    }

    @Override
    public double calculateHpPercent()
    {
        return PERCENTAGE_FACTOR * (currentHp / startingHp);
    }

    @Override
    public double getStartingHp()
    {
        return startingHp;
    }

    @Override
    public void reset() //skills
    {
        this.skill.restCoolDownsAfterFight();
    }

    @Override
    public int getPlayableId()
    {
        return id;
    }

    /**
     * Get method for returning skill
     * @return Skill object
     */
    public Skill getSkill()
    {
        return skill;
    }

    /**
     * BattlePetBuilder class for BattlePet
     * builder class
     * public class so class can be instantiated outside of the class
     */
    public static class BattlePetBuilder
    {
        // Variable names passed from builder if builder has same variables set
        private int id; // Playable Id to identify battle pet
        private String name; // The title given to the pet object
        private PetTypes type; // The type of pet specified for pet object (Power, Speed, or Intelligence)
        private double currentHp; // The current health point value of the pet object
        private String playerName; // Name of the player controlling the pet
        private double startingHp; // The starting health point value of the pet object
        private PlayerTypes playerType; // Type of the player controlling the pet

        /**
         * BattlePetBuilder constructor
         * Left empty, but public for out of class use
         */
        public BattlePetBuilder()
        {

        }

        /**
         * withType sets the type of the BattlePet to be built
         * @params PetTypes type
         * @return Returns object of BattlePetBuilder (with new value for type)
         */
        public BattlePetBuilder withType(PetTypes type)
        {
            this.type = type;
            return this;
        }


        /**
         * withStartingHp sets the starting hp of the BattlePet to be built
         * @params double startingHp
         * @return Returns object of BattlePetBuilder (with new value for starting hp)
         */
        public BattlePetBuilder withStartingHp(double startingHp)
        {
            this.startingHp = startingHp;
            return this;
        }


        /**
         * withCurrentHp sets the current hp of the BattlePet to be built
         * @params double currentHp
         * @return returns object of BattlePetBuilder (with new value for current hp)
         */
        public BattlePetBuilder withCurrentHp(double currentHp)
        {
            this.currentHp = currentHp;
            return this;
        }

        /**
         * withName sets the name of the BattlePet to be built
         * @params String name
         * @return returns object of BattlePetBuilder (with new value for name)
         */
        public BattlePetBuilder withName(String name)
        {
            this.name = name;
            return this;
        }

        /**
         * withId sets the ID of the BattlePet to be built
         * @params int id
         * @return returns object of BattlePetBuilder (with new value for id)
         */
        public BattlePetBuilder withId(int id)
        {
            this.id = id;
            return this;
        }

        /**
         * withPlayerName sets the playerName of the BattlePet to be built
         * @param name playerName
         * @return object of BattlePetBuilder (with new value for playerName)
         */
        public BattlePetBuilder withPlayerName(String name)
        {
            this.playerName = name;
            return this;
        }

        /**
         * withPlayerType sets the playerType of the BattlePet to be built
         * @param type playerType
         * @return object of BattlePetBuilder (with new value for playerType)
         */
        public BattlePetBuilder withPlayerType(PlayerTypes type)
        {
            this.playerType = type;
            return this;
        }

        /**
         * builds a new object of BattlePet
         * Based on values pass through BattlePetBuilder's 'with' methods
         * @return returns new object of BattlePet
         */
        public BattlePet build()
        {
            return new BattlePet(this);
        }
    }
}
