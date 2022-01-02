package me.cominixo.adaptivebrightness.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.TranslatableText;

import java.io.*;

public class Config {

    public static double max_gamma = 1.0f;
    public static double min_gamma = 0.0f;
    public static boolean disabled = false;
    public static int max_lightlevel = 15;
    public static int min_lightlevel = 0;

    public static Screen init(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(new TranslatableText("title.adaptivebrightness.config"));

        ConfigCategory general = builder.getOrCreateCategory(new TranslatableText("category.adaptivebrightness.general"));

        int max_gamma_percent = (int)(max_gamma*100);
        int min_gamma_percent = (int)(min_gamma*100);

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        general.addEntry(entryBuilder.startIntSlider(new TranslatableText("option.adaptivebrightness.max_gamma"), max_gamma_percent, 0, 1000)
                .setDefaultValue(100)
                .setTooltip(new TranslatableText("option.adaptivebrightness.max_gamma.tooltip"))
                .setSaveConsumer(newValue -> max_gamma = newValue/100.0)
                .build());

        general.addEntry(entryBuilder.startIntSlider(new TranslatableText("option.adaptivebrightness.min_gamma"), min_gamma_percent, 0, 1000)
                .setDefaultValue(0)
                .setTooltip(new TranslatableText("option.adaptivebrightness.min_gamma.tooltip"))
                .setSaveConsumer(newValue -> min_gamma = newValue/100.0)
                .build());

        general.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("option.adaptivebrightness.disabled"), disabled)
                .setDefaultValue(disabled)
                .setTooltip(new TranslatableText("option.adaptivebrightness.disabled.tooltip"))
                .setSaveConsumer(newValue -> disabled = newValue)
                .build());

        general.addEntry(entryBuilder.startIntSlider(new TranslatableText("option.adaptivebrightness.max_lightlevel"), max_lightlevel, 0, 15)
                .setDefaultValue(15)
                .setTooltip(new TranslatableText("option.adaptivebrightness.max_lightlevel.tooltip"))
                .setSaveConsumer(newValue -> max_lightlevel = newValue)
                .build());

        general.addEntry(entryBuilder.startIntSlider(new TranslatableText("option.adaptivebrightness.min_lightlevel"), min_lightlevel, 0, 15)
                .setDefaultValue(0)
                .setTooltip(new TranslatableText("option.adaptivebrightness.min_lightlevel.tooltip"))
                .setSaveConsumer(newValue -> min_lightlevel = newValue)
                .build());

        builder.setSavingRunnable(() -> {
            FileWriter fileWriter;
            try {
                fileWriter = new FileWriter("config/adaptivebrightness.conf");
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.printf("max %f\n", max_gamma);
            printWriter.printf("min %f\n", min_gamma);
            printWriter.printf("disabled %b\n", disabled);
            printWriter.printf("maxlight %d\n", max_lightlevel);
            printWriter.printf("minlight %d\n", min_lightlevel);
            printWriter.close();
        });

        return builder.build();

    }

    public static void load() throws IOException {
        FileReader configFile;

        try {
            configFile = new FileReader("config/adaptivebrightness.conf");
        } catch (FileNotFoundException ignored) {
            return;
        }

        BufferedReader reader = new BufferedReader(configFile);
        String line;
        while((line = reader.readLine())!=null) {
            if (line.startsWith("max ")) {
                max_gamma = Double.parseDouble(line.split("max ")[1]);
            } else if (line.startsWith("min ")) {
                min_gamma = Double.parseDouble(line.split("min ")[1]);
            } else if (line.startsWith("disable ")) {
                disabled = Boolean.parseBoolean(line.split("disable ")[1]);
            } else if (line.startsWith("maxlight ")) {
                max_lightlevel = Integer.parseInt(line.split("maxlight ")[1]);
            } else if (line.startsWith("minlight ")) {
                min_lightlevel = Integer.parseInt(line.split("minlight ")[1]);
            }
        }
        System.out.println(max_gamma);
        System.out.println(min_gamma);
        System.out.println(disabled);
        System.out.println(max_lightlevel);
        System.out.println(min_lightlevel);
    }

}
