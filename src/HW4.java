import java.util.Random;

public class HW4 {
    public static int bossHealth = 700;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static int[] heroesHealth = {280, 270, 240, 200}; // Добавлен Medic
    public static int[] heroesDamage = {20, 15, 10, 0}; // Medic не атакует
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medic"};
    public static int medicHealAmount = 30; // Количество здоровья, которое может восстановить медик
    public static int roundNumber;

    public static void main(String[] args) {
        printStatistics();

        while (!isGameOver()) {
            playRound();
        }
    }

    public static void playRound() {
        roundNumber++;
        chooseBossDefence();
        bossAttacks();
        heroesAttack();
        medicHeals(); // Вызов метода лечения Медика
        printStatistics();
    }

    public static boolean isGameOver() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomInd = random.nextInt(heroesAttackType.length - 1); // Медик не может быть защитой босса
        bossDefence = heroesAttackType[randomInd];
    }

    public static void heroesAttack() {
        for (int i = 0; i < heroesDamage.length - 1; i++) { // Медик не атакует
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (heroesAttackType[i].equals(bossDefence)) {
                    Random random = new Random();
                    int coefficient = random.nextInt(9) + 2;
                    damage *= coefficient;
                    System.out.println("Critical damage: " + damage);
                }
                if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth = bossHealth - damage;
                }
            }
        }
    }

    public static void bossAttacks() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }

    // Метод, который управляет лечением Медика
    public static void medicHeals() {
        if (heroesHealth[3] > 0) { // Медик может лечить только если он жив
            int lowestHealthIndex = -1;
            for (int i = 0; i < heroesHealth.length - 1; i++) { // Ищем героя с наименьшим здоровьем
                if (heroesHealth[i] > 0 && heroesHealth[i] < 100) {
                    if (lowestHealthIndex == -1 || heroesHealth[i] < heroesHealth[lowestHealthIndex]) {
                        lowestHealthIndex = i;
                    }
                }
            }
            if (lowestHealthIndex != -1) {
                heroesHealth[lowestHealthIndex] += medicHealAmount;
                System.out.println("Medic healed " + heroesAttackType[lowestHealthIndex] + " for " + medicHealAmount + " health.");
            }
        }
    }

    public static void printStatistics() {
        System.out.println("ROUND " + roundNumber + " ------------");
        System.out.println("BOSS health: " + bossHealth + " damage: " + bossDamage +
                " defence: " + (bossDefence == null ? "No Defence" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i]
                    + " damage: " + heroesDamage[i]);
        }
    }
}
