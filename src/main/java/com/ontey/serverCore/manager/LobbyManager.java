package com.ontey.serverCore.manager;

import com.ontey.serverCore.files.Config;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.List;
import java.util.Objects;

public class LobbyManager {
   
   public static void load() {
      world = Objects.requireNonNull(Bukkit.getWorld("world"));
      spawnpoint = world.getSpawnLocation();
      preventions = Config.getOrDefault("spawn.preventions", List.of());
   }
   
   @Getter
   private static World world;
   
   @Getter
   private static Location spawnpoint;
   
   private static List<String> preventions;
   
   public static boolean isLobby(World world) {
      return LobbyManager.world.equals(world);
   }
   
   public static boolean isActive(String prevention) {
      return preventions.contains(prevention);
   }
}
