import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void saveGame(String path, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String path, List<String> pathesToFiles) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(path));
        ) {
            for (String s : pathesToFiles) {
                try (FileInputStream fis = new FileInputStream(s)
                ) {
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);

                    Path path2 = Paths.get(s);
                    Path fileName = path2.getFileName();

                    ZipEntry entry = new ZipEntry(fileName.toString());
                    zout.putNextEntry(entry);
                    zout.write(buffer);
                    zout.closeEntry();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] args) {

        GameProgress game1 = new GameProgress(100, 1, 1, 100);
        GameProgress game2 = new GameProgress(85, 3, 3, 350);
        GameProgress game3 = new GameProgress(35, 4, 5, 550);

        saveGame("C:\\learning\\IdeaProjects\\HomeWorks\\Games\\savegames\\save.dat", game1);
        saveGame("C:\\learning\\IdeaProjects\\HomeWorks\\Games\\savegames\\save2.dat", game2);
        saveGame("C:\\learning\\IdeaProjects\\HomeWorks\\Games\\savegames\\save3.dat", game3);

        List<String> list = new ArrayList<>();
        list.add("C:\\learning\\IdeaProjects\\HomeWorks\\Games\\savegames\\save.dat");
        list.add("C:\\learning\\IdeaProjects\\HomeWorks\\Games\\savegames\\save2.dat");
        list.add("C:\\learning\\IdeaProjects\\HomeWorks\\Games\\savegames\\save3.dat");

        zipFiles("C:\\learning\\IdeaProjects\\HomeWorks\\Games\\savegames\\zip.zip", list);

        for (String s : list) {
            File file = new File(s);
            file.delete();
        }
    }
}
