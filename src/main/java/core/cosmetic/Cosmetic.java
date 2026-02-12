package core.cosmetic;

import lombok.NonNull;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

import static core.playtime.PlaytimeManager.playtimeManager;

/**
 * A cosmetic
 *
 * @param name The display name
 * @param unlockMinutes The time needed to have played on the server to unlock
 * @param display Specifies the particles.
 */

public record Cosmetic(String name, short unlockMinutes, DisplayInfo display) implements ConfigurationSerializable {
   
   public static final Vector
     HIGHEST_RELATIVE = new Vector(0, 1.2, 0),
     MEDIUM_RELATIVE = new Vector(0, 1.1, 0),
     LOWEST_RELATIVE = new Vector(0, 1, 0);
   
   public static final double
     LOWEST_OFFSET = 0.3,
     LOW_OFFSET = 0.4,
     MEDIUM_OFFSET = 0.5,
     HIGHEST_OFFSET = 0.6;
   
   public static final int
     LOWEST_COUNT = 3,
     LOW_COUNT = 4,
     MEDIUM_COUNT = 5,
     HIGH_COUNT = 6,
     HIGHEST_COUNT = 8;
   
   private static final DisplayInfo
     HEARTS_DISPLAY        = new DisplayInfo(Material.REDSTONE,         Particle.HEART,     LOWEST_RELATIVE,  LOWEST_COUNT,  LOWEST_OFFSET),
     FIRE_DISPLAY          = new DisplayInfo(Material.LAVA_BUCKET,      Particle.FLAME,     LOWEST_RELATIVE,  MEDIUM_COUNT,  LOWEST_OFFSET),
     SNOW_DISPLAY          = new DisplayInfo(Material.SNOWBALL,         Particle.SNOWFLAKE, LOWEST_RELATIVE,  MEDIUM_COUNT,  LOWEST_OFFSET),
     STARS_DISPLAY         = new DisplayInfo(Material.END_ROD,          Particle.END_ROD,   LOWEST_RELATIVE,  LOWEST_COUNT,  LOWEST_OFFSET),
     RAINBOW_DISPLAY       = new DisplayInfo(Material.AMETHYST_SHARD,   Particle.FLAME,     LOWEST_RELATIVE,  MEDIUM_COUNT,  MEDIUM_OFFSET),
     MUSICAL_NOTES_DISPLAY = new DisplayInfo(Material.NOTE_BLOCK,       Particle.NOTE,      LOWEST_RELATIVE,  HIGH_COUNT,    LOWEST_OFFSET),
     SPARKS_DISPLAY        = new DisplayInfo(Material.FIRE_CHARGE,      Particle.CRIT,      LOWEST_RELATIVE,  HIGHEST_COUNT, LOW_OFFSET),
     HEART_RAIN_DISPLAY    = new DisplayInfo(Material.HEART_OF_THE_SEA, Particle.HEART,     HIGHEST_RELATIVE, LOW_COUNT,     HIGHEST_OFFSET),
     CLOUDS_DISPLAY        = new DisplayInfo(Material.GLOWSTONE_DUST,   Particle.CLOUD,     MEDIUM_RELATIVE,  HIGH_COUNT,    MEDIUM_OFFSET),
     LUCKY_DISPLAY         = new DisplayInfo(Material.BLAZE_POWDER,     Particle.END_ROD,   MEDIUM_RELATIVE,  HIGH_COUNT,    LOW_OFFSET),
     ENCHANTMENT_DISPLAY   = new DisplayInfo(Material.ENCHANTING_TABLE, Particle.CRIT,      LOWEST_RELATIVE,  HIGH_COUNT,    MEDIUM_OFFSET),
     WITCH_SPELL_DISPLAY   = new DisplayInfo(Material.DIAMOND,          Particle.NOTE,      LOWEST_RELATIVE,  HIGH_COUNT,    LOW_OFFSET);
   
   // Fallback if data-driven system fails
   @Deprecated
   public static final Cosmetic
     HEARTS        = new Cosmetic("&cHerzen",       (short) 0,      HEARTS_DISPLAY),
     FIRE          = new Cosmetic("&6Feuer",        (short) 10,     FIRE_DISPLAY),
     SNOW          = new Cosmetic("&bSchnee",       (short) 30,     SNOW_DISPLAY),
     STARS         = new Cosmetic("&eSterne",       (short) 60,     STARS_DISPLAY),
     RAINBOW       = new Cosmetic("&5Regenbogen",   (short) 120,    RAINBOW_DISPLAY),
     MUSICAL_NOTES = new Cosmetic("&aMusiknoten",   (short) 300,    MUSICAL_NOTES_DISPLAY),
     SPARKS        = new Cosmetic("&dFunken",       (short) 300,    SPARKS_DISPLAY),
     HEART_RAIN    = new Cosmetic("&cHerzregen",    (short) 720,    HEART_RAIN_DISPLAY),
     CLOUDS        = new Cosmetic("&7Wolken",       (short) 1440,   CLOUDS_DISPLAY),
     LUCKY         = new Cosmetic("&aGlücklich",    (short) 2880,   LUCKY_DISPLAY),
     ENCHANTMENT   = new Cosmetic("&bVerzauberung", (short) 4320,   ENCHANTMENT_DISPLAY),
     WITCH_SPELL   = new Cosmetic("&5Hexen-Zauber", (short) 7200,   WITCH_SPELL_DISPLAY);
   
   public void apply(Player player) {
      display.apply(player);
   }
   
   public boolean isUnlocked(@NonNull Player player) {
      return playtimeManager.getPlaytimeSeconds(player) >= unlockMinutes;
   }
   
   public long getUnlockRemainingSeconds(@NonNull Player player) {
      long remaining = unlockMinutes - playtimeManager.getPlaytimeSeconds(player);
      return remaining > 0 ? remaining : 0L;
   }
   
   @Override
   public @NotNull Map<String, Object> serialize() {
      return Map.of(
        "name", name,
        "unlock-threshold", unlockMinutes,
        "particle", display.particle.toString(),
        "material", display.material.toString(),
        "relative-location", display.relativeLocation.serialize(),
        "count", display.count,
        "offset", display.offset
      );
   }
   
   @SuppressWarnings("unchecked")
   public static Cosmetic deserialize(Map<String, Object> map) {
      String name = (String) map.get("name");
      short unlockThreshold = ((Number) map.get("unlock-threshold")).shortValue();
      
      Material material = Material.valueOf(((String) map.get("material")).toUpperCase());
      
      Particle particle = Particle.valueOf(((String) map.get("particle")).toUpperCase());
      
      Vector relativeLocation = Vector.deserialize((Map<String, Object>) map.get("relative-location"));
      
      int count = ((Number) map.get("count")).intValue();
      double offset = ((Number) map.get("offset")).doubleValue();
      
      return new Cosmetic(name, unlockThreshold, new DisplayInfo(material, particle, relativeLocation, count, offset));
   }
   
   /**
    * @param material The material that represents this item in the {@link CosmeticsMenu}.
    * @param particle The particle
    * @param relativeLocation The relative location
    * @param count How many particles are spawned at a time
    * @param offset The offset
    * @see org.bukkit.World#spawnParticle(Particle, Location, int, double, double, double)
    */
   
   public record DisplayInfo(Material material, Particle particle, Vector relativeLocation, int count, double offset, @Nullable Particle.DustOptions options) {
      
      public DisplayInfo(Material material, Particle particle, Vector relativeLocation, int count, double offset) {
         this(material, particle, relativeLocation, count, offset, null);
      }
      
      public void apply(Player player) {
         player.getWorld().spawnParticle(
           particle,
           player.getLocation().add(relativeLocation),
           count,
           offset,
           offset,
           offset,
           options
         );
      }
   }
}
