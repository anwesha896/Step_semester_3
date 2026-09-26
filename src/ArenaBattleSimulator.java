interface BattleAttackable {
    String attack();
    String attack(String weaponName);
}
interface BattleDefendable {
    String defend();
}
abstract class BattleGameCharacter {
    private static int characterCounter = 1000;
    private final String characterId;
    BattleGameCharacter() {
        characterId = "CHAR-" + (++characterCounter);
    }
    public String getCharacterId() {
        return characterId;
    }
    public abstract String getSpecialMove();
}
class BattleWarrior extends BattleGameCharacter implements BattleAttackable, BattleDefendable {
    private String name;
    public BattleWarrior(String name) {
        this.name = name;
    }
    @Override
    public String attack() {
        return name + " strikes with a blade";
    }
    @Override
    public String attack(String weaponName) {
        return name + " strikes with an " + weaponName;
    }
    @Override
    public String defend() {
        return name + " raises a shield";
    }
    @Override
    public String getSpecialMove() {
        return name + " unleashes Whirlwind Slash";
    }
}
class BattleTrap implements BattleDefendable {
    private String trapType;
    public BattleTrap(String trapType) {
        this.trapType = trapType;
    }
    @Override
    public String defend() {
        return trapType + " triggers automatically";
    }
}
public class ArenaBattleSimulator {
    static void resolveDefense(BattleDefendable[] combatants) {
        for (BattleDefendable combatant : combatants) {
            System.out.println(combatant.defend());
        }
    }
    public static void main(String[] args) {
        BattleWarrior w = new BattleWarrior("Kael");
        System.out.println(w.attack());
        System.out.println(w.attack("Iron Sword"));
        System.out.println(w.defend());
        System.out.println(w.getSpecialMove());
        BattleTrap t = new BattleTrap("Spike Pit");
        System.out.println(t.defend());
        resolveDefense(new BattleDefendable[]{w, t});
        System.out.println(w.getCharacterId());
    }
}


