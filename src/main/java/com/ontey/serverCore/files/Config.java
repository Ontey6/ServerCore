package com.ontey.serverCore.files;

import com.ontey.api.filelog.FileLog;
import com.ontey.api.files.Files;
import com.ontey.serverCore.ServerCore;
import com.ontey.serverCore.log.CoreLogger;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.ontey.serverCore.ServerCore.plugin;

public class Config {
   
   public static File file;
   
   public static YamlConfiguration config;
   
   private Config() { }
   
   public static void load() {
      file = new File(plugin.getDataFolder(), "config.yml");
      
      if(!file.exists())
         plugin.saveResource("config.yml", false);
      
      config = new YamlConfiguration();
      config.options().parseComments(true);
      
      try {
         config.load(file);
      } catch(Exception e) {
         ServerCore.disablePlugin("Couldn't load the config file.");
         FileLog.saveStackTrace(e);
         return;
      }
      
      loadFields();
   }
   
   public static void save() {
      try {
         config.save(file);
      } catch(IOException e) {
         CoreLogger.error("Couldn't save the config file.");
         FileLog.saveStackTrace(e);
      }
   }
   
   public static void set(String path, Object value) {
      config.set(path, value);
      save();
   }
   
   public static ConfigurationSection createSection(String path) {
      var section = config.createSection(path);
      save();
      return section;
   }
   
   public static ConfigurationSection createSection(String path, Map<String, ?> values) {
      var section = config.createSection(path, values);
      save();
      return section;
   }
   
   // Helpers
   
   @Contract("_, !null -> !null")
   public static <T> T getOrDefault(String path, T fallback) {
      return Files.getOrDefault(config, path, fallback);
   }
   
   @Nullable
   public static String getMessage(String path) {
      return Files.getMessage(config, path);
   }
   
   public static <T> List<T> singletonList(T t) {
      List<T> out = new ArrayList<>(1);
      out.add(t);
      return out;
   }
   
   // Fields
   
   private static void loadFields() {
      //PLACEHOLDER_FORMAT = new VariableFormat(getOrDefault("format.placeholder-format", "<%ph>"), "%ph");
      DEBUG = getOrDefault("debug", false);
      ServerCore.logger.setDebug(DEBUG);
      
      TELEPORT_TO_SPAWN_ON_JOIN = getOrDefault("on-join.teleport-to-spawn", true);
      SHOW_FEEDBACK = getOrDefault("spawn.show-feedback", true);
   }
   
   //public static VariableFormat PLACEHOLDER_FORMAT;
   
   public static boolean TELEPORT_TO_SPAWN_ON_JOIN, SHOW_FEEDBACK;
   
   public static boolean DEBUG;
}
