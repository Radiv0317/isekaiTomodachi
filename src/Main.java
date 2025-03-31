import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;



class Item {
    private final String name;
    private int healingAmount;
    private int upgradeLevel;
    private final String type; 

    public Item(String name, int healingAmount, String type) {
        this.name = name;
        this.healingAmount = healingAmount;
        this.upgradeLevel = 1;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getHealingAmount() {
        return healingAmount * upgradeLevel;
    }

    public void upgrade() {
        upgradeLevel++;
        healingAmount += 5;
        System.out.println(name + " telah di-upgrade ke level " + upgradeLevel + "!");
    }

    public String getType() {
        return type;
    }
}

//quest
class Quest {
    private final String description;
    private boolean isCompleted;

    public Quest(String description) {
        this.description = description;
        this.isCompleted = false;
    }

    public String getDescription() {
        return description;
    }

    public void completeQuest() {
        isCompleted = true;
        System.out.println("Quest selesai: " + description);
    }

    public boolean isCompleted() {
        return isCompleted;
    }
}
class Character {
    private String name;
        private final ArrayList<Quest> quests;
        private int health;
        private int XP;
        private int level;
        private Item item; 
        private final String skillName;
        private final int skillDamage;
        private final ArrayList<Item> inventory; 
        private final Random random;
    
        public Character(String name, int health) {
            this.name = name;
            this.health = health;
            this.XP = 0;
            this.level = 1;
            this.skillName = "Fireball"; 
            this.skillDamage = 20;
            this.inventory = new ArrayList<>(); 
            this.quests = new ArrayList<>();
            this.random = new Random();
        }
    
        public void addQuest(Quest quest) {
            quests.add(quest);
            System.out.println("Quest ditambahkan: " + quest.getDescription());
        }
    
        public void completeQuest(int index) {
            if (index >= 0 && index < quests.size()) {
                Quest quest = quests.get(index);
                if (!quest.isCompleted()) {
                    quest.completeQuest();
                    addXP(10); 
                } else {
                    System.out.println("Quest sudah selesai.");
                }
            } else {
                System.out.println("Quest tidak valid.");
            }
        }
    
        public void useSkill(Enemy enemy) {
            System.out.println(name + " menggunakan skill " + skillName + "!");
            enemy.takeDamage(skillDamage);
            System.out.println("Damage yang diberikan: " + skillDamage);
        }
    
        public void addItem(Item item) {
            inventory.add(item);
            System.out.println("Kamu mendapatkan item: " + item.getName());
        }
    
        public void useItem(int index) {
            if (index >= 0 && index < inventory.size()) {
                Item item = inventory.get(index);
                health += item.getHealingAmount();
                System.out.println("Kamu menggunakan " + item.getName() + " dan mendapatkan " + item.getHealingAmount() + " health!");
                inventory.remove(index); // Item digunakan, jadi hapus dari inventaris
            } else {
                System.out.println("Item tidak valid.");
            }
        }
    
    
        public void showInventory() {
            System.out.println("Inventaris:");
            for (int i = 0; i < inventory.size(); i++) {
                System.out.println("[" + i + "] " + inventory.get(i).getName());
            }
        }
    
        private void checkLevelUp() {
            if (XP >= level * 10) { 
                level++;
                health += 10; 
                System.out.println(name + " naik level ke " + level + "!");
            }
        }
    
        public void upgradeItem() {
            if (item != null) {
                item.upgrade();
            } else {
                System.out.println("Kamu tidak memiliki item untuk di-upgrade.");
            }
        }
    
        public void setItem(Item item) {
            this.item = item;
            System.out.println("Kamu mendapatkan item: " + item.getName());
        }
        public void takeDamage(int damage) {
            this.health -= damage;
        }
    
        public void addXP(int additionalXP) {
            this.XP += additionalXP;
            checkLevelUp();
        }
    
        public int attack() {
            return random.nextInt(10) + 5; 
        }
    
        public String getName() {
            return name;
        }
    
        public int getHealth() {
            return health;
        }
    
        public int getXP() {
            return XP;
        }
    
        public int getLevel() {
            return level;
        }
    
        public void setLevel(int level) {
            this.level = level;
        }
    
        public void setXP(int XP) {
            this.XP = XP;
        }
    
        public void setName(String name) {
            this.name = name;
        }

        public void setHealth(int health) {
            this.health = health;
        }
    

}

// Enemy.java
class Enemy {
    private final String name;
    private int health;
    private final Random random;

    public Enemy(String name, int health) {
        this.name = name;
        this.health = health;
        this.random = new Random();
    }

    public int attack() {
        return random.nextInt(8) + 3; 
    }

    public void takeDamage(int damage) {
        this.health -= damage;
    }

    public boolean isDefeated() {
        return health <= 0;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }
}

// Scene.java
class Scene {
    private final String description;
    private final String choiceA;
    private final String choiceB;
    private final Scene nextSceneA;
    private final Scene nextSceneB;
    private final int damageA;
    private final int damageB;
    private final int XPA;
    private final int XPB;
    private final boolean hasEnemy;

    public Scene(String description, String choiceA, Scene nextSceneA, int damageA, int XPA,
                 String choiceB, Scene nextSceneB, int damageB, int XPB, boolean hasEnemy) {
        this.description = description;
        this.choiceA = choiceA;
        this.nextSceneA = nextSceneA;
        this.damageA = damageA;
        this.XPA = XPA;
        this.choiceB = choiceB;
        this.nextSceneB = nextSceneB;
        this.damageB = damageB;
        this.XPB = XPB;
        this.hasEnemy = hasEnemy;
    }

    public void displayScene() {
        System.out.println(description);
        System.out.println("A: " + choiceA);
        System.out.println("B: " + choiceB);
    }

    public Scene makeChoice(String choice, Character player, Scanner scanner) {
        if (hasEnemy) {
            battle(player, scanner);
        }
        if (choice.equalsIgnoreCase("A")) {
            player.takeDamage(damageA);
            player.addXP(XPA);
            return nextSceneA;
        } else if (choice.equalsIgnoreCase("B")) {
            player.takeDamage(damageB);
            player.addXP(XPB);
            return nextSceneB;
        } else {
            System.out.println("Pilihan tidak valid. Coba lagi.");
            return this;
        }
    }

    private void battle(Character player, Scanner scanner) {
        Enemy enemy = new Enemy("Goblin", 20);
        System.out.println("\nKamu diserang oleh " + enemy.getName() + "!");
        while (player.getHealth() > 0 && !enemy.isDefeated()) {
            System.out.println("[1] Serang [2] Bertahan [3] Kabur [4] Gunakan Item [5] Upgrade Item [6] Gunakan Skill");
            System.out.print("Aksi: ");
            String action = scanner.nextLine();
    
            if (action.equals("1")) {
                int playerDamage = player.attack();
                enemy.takeDamage(playerDamage);
                System.out.println("Kamu menyerang " + enemy.getName() + " dengan " + playerDamage + " damage!");
            } else if (action.equals("2")) {
                System.out.println("Kamu bertahan dan mengurangi damage musuh!");
            } else if (action.equals("3")) {
                System.out.println("Kamu berhasil kabur!");
                return;
            } else if (action.equals("4")) {
                player.showInventory();
                System.out.print("Pilih item untuk digunakan: ");
                int itemIndex = Integer.parseInt(scanner.nextLine()); // Ambil input indeks dari pengguna
                player.useItem(itemIndex);
            } else if (action.equals("5")) {
                player.upgradeItem();
            } else if (action.equals("6")) {
                player.useSkill(enemy); // Menggunakan skill
            }
    
            if (!enemy.isDefeated()) {
                int enemyDamage = enemy.attack();
                player.takeDamage(enemyDamage);
                System.out.println(enemy.getName() + " menyerangmu dengan " + enemyDamage + " damage!");
            }
        }
    
        if (player.getHealth() > 0) {
            System.out.println("Kamu mengalahkan " + enemy.getName() + "! XP +10");
            player.addXP(10);
            player.addItem(new Item("Health Potion", 20, "Potion")); // Contoh item
        } else {
            System.out.println("Kamu kalah...");
        }
    }
}

// Story.java
class Story {
    private final Scene startScene;
    private final Character player;
    private final Scanner scanner;

    public Story(Scene startScene, Character player) {
        this.startScene = startScene;
        this.player = player;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        play();
    }

    public void play() {
        Scene currentScene = startScene;
        while (currentScene != null && player.getHealth() > 0) {
            currentScene.displayScene();
            System.out.println("Ketik 'INFO' untuk melihat status karakter.");
            System.out.print("Pilihanmu: ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("INFO")) {
                System.out.println("Health: " + player.getHealth() + ", XP: " + player.getXP());
                continue;
            }

            currentScene = currentScene.makeChoice(input, player, scanner);
        }
        System.out.println("Game Over");
    }
}

// Main.java
public class Main {
    public static void main(String[] args) {
        Character player = new Character("Hero", 100);
        loadCharacter(player);
        

        Scene endScene = new Scene("Kamu telah menyelesaikan perjalanan.", "", null, 0, 0, "", null, 0, 0, false);
        Scene secondScene = new Scene("Kamu menemukan jalan bercabang.", "Ambil kiri", endScene, 5, 10, "Ambil kanan", endScene, 10, 5, true);
        Scene startScene = new Scene("Petualangan dimulai! Kamu berjalan di hutan.", "Lanjut ke depan", secondScene, 0, 5, "Belok ke kanan", secondScene, 0, 5, false);

        Story story = new Story(startScene, player);
        story.start();


        saveCharacter(player);
    }

    public static void saveCharacter(Character character) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("character.txt"))) {
            writer.write(character.getName() + "," + character.getHealth() + "," + character.getLevel() + "," + character.getXP());
            writer.newLine();
            System.out.println("Karakter disimpan ke file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadCharacter(Character character) {
        try (BufferedReader reader = new BufferedReader(new FileReader("character.txt"))) {
            String line = reader.readLine();
            if (line != null) {
                String[] data = line.split(",");
                character.setName(data[0]);
                character.setHealth(Integer.parseInt(data[1]));
                character.setLevel(Integer.parseInt(data[2]));
                character.setXP(Integer.parseInt(data[3]));
                System.out.println("Karakter dimuat: " + character.getName());
            }
        } catch (IOException e) {
            System.out.println("Tidak ada data karakter yang ditemukan, menggunakan karakter default.");
        }
    }
}
