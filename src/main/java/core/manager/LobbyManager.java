package core.manager;

import lombok.Getter;
import ontey.plugin.OnteyPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.UnknownNullability;

import java.util.List;
import java.util.Objects;

import static core.config.ConfigManager.config;

public class LobbyManager {
   
   public static LobbyManager lobbyManager;
   
   public LobbyManager() {
      lobbyManager = this;
   }
   
   public void load() {
      world = Objects.requireNonNull(Bukkit.getWorld("world"));
      spawnpoint = world.getSpawnLocation();
      preventions = config.getOrDefault("spawn.preventions", List.of());
   }
   
   @Getter
   private World world;
   
   @Getter
   private Location spawnpoint;
   
   private List<String> preventions;
   
   public boolean isLobby(@UnknownNullability World world) {
      return this.world.equals(world);
   }
   
   public boolean isActive(String prevention) {
      return preventions.contains(prevention);
   }
}
