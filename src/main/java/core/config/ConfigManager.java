package core.config;

import ontey.config.Config;

import java.io.File;

import static core.ServerCore.*;

public class ConfigManager extends Config {
   
   public static ConfigManager config;
   
   public ConfigManager() {
      super(plugin, new File(plugin.getDataFolder(), "config.yml"));
      config = this;
      
      loadFields();
   }
   
   public void loadFields() {
      TELEPORT_TO_SPAWN_ON_JOIN = getOrDefault("on-join.teleport-to-spawn", true);
      SHOW_FEEDBACK = getOrDefault("spawn.show-feedback", true);
   }
   
   public void reload() {
      load();
      loadFields();
   }
   
   public static boolean TELEPORT_TO_SPAWN_ON_JOIN, SHOW_FEEDBACK;
}
