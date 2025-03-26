import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CharacterFileManager {
    private static final String FILE_NAME = "characters.txt";

    // Menyimpan karakter ke dalam file
    public void saveCharacter(Character character) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(character.getName() + "," + character.getHealth() + "," + character.getLevel() + "," + character.getXP());
            writer.newLine();
            System.out.println("Karakter disimpan ke file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Membaca karakter dari file
    public void loadCharacters() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String name = data[0];
                int health = Integer.parseInt(data[1]);
                int level = Integer.parseInt(data[2]);
                int xp = Integer.parseInt(data[3]);
                // Buat objek karakter dari data yang dibaca
                Character character = new Character(name, health);
                character.setLevel(level);
                character.setXP(xp);
                System.out.println("Karakter dimuat: " + character.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}