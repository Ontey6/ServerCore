package core;

import core.config.ConfigManager;
import core.cosmetic.Cosmetic;
import core.manager.LobbyManager;
import core.manager.SpawnInventoryManager;
import ontey.log.NamedLogger;
import ontey.plugin.OnteyPlugin;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

import static core.cosmetic.CosmeticsManager.cosmeticsManager;
import static core.playtime.PlaytimeManager.playtimeManager;

public final class ServerCore extends OnteyPlugin {
   
   public static ServerCore plugin;
   public static NamedLogger logger;
   
   @Override
   public void onEnable() {
      plugin = this;
      logger = getLog();
      
      ConfigurationSerialization.registerClass(Cosmetic.class);
      registerConfig(new ConfigManager());
      registerSingleClassLoader(LobbyManager.class, LobbyManager::load);
      registerSingleClassLoader(SpawnInventoryManager.class, SpawnInventoryManager::load);
      load();
   }
   
   @Override
   public void onDisable() {
      cosmeticsManager.disable();
      playtimeManager.disable();
   }
}
