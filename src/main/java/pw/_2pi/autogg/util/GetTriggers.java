package pw._2pi.autogg.util;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;

import pw._2pi.autogg.gg.AutoGG;

public class GetTriggers implements Runnable {

    @Override
    public void run() {
        try {
            // ✅ Correct file location (inside .minecraft/config)
            File file = new File(
                AutoGG.getInstance().getMinecraft().mcDataDir,
                "config/autogg_triggers.txt"
            );

            System.out.println("AutoGG: Looking for triggers at: " + file.getAbsolutePath());
            System.out.println("AutoGG: File exists? " + file.exists());

            // ✅ Create file if missing
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();

                // Default triggers
                Files.write(file.toPath(), Arrays.asList(
                "1st Killer - ",
				"1st Place - ",
				"Winner: ",
				" - Damage Dealt - ",
				"Winning Team - ",
				"1st - ",
				"Winners: ",
				"Winner:",
				"Winning Team: ",
				" won the game!",
				"Top Seeker: ",
				"1st Place: ",
				"Last team standing!",
				"Winner #1 (",
				"Top Survivors",
				"Winners - ",
				"Survivors ("
                ));

                System.out.println("AutoGG: Created default triggers file.");
            }

            System.out.println("AutoGG: Attempting to read triggers...");

            // ✅ READ FROM CORRECT FILE (FIXED)
            String rawTriggers = IOUtils.toString(
                new FileInputStream(file),
                "UTF-8"
            );

            System.out.println("AutoGG: Raw triggers:");
            System.out.println(rawTriggers);

            // ✅ Clean + parse triggers
            ArrayList<String> triggers = Arrays.stream(rawTriggers.split("\n"))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toCollection(ArrayList::new));

            System.out.println("AutoGG: Parsed triggers:");
            for (String t : triggers) {
                System.out.println("Trigger -> [" + t + "]");
            }

            // ✅ Set triggers
            AutoGG.getInstance().setTriggers(triggers);

        } catch (Exception e) {
            System.out.println("AutoGG: FAILED to load triggers!");
            e.printStackTrace();
        }
    }
}