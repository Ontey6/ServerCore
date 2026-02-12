package core.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.util.Vector;

import static core.manager.LobbyManager.lobbyManager;

public class LobbyGeneralListener implements Listener {
   
   @EventHandler
   public void onStartFallFly(EntityToggleGlideEvent event) {
      
      // not a player || is STOPPING to glide || not in lobby -> return
      if(!(event.getEntity() instanceof final Player player) || !event.isGliding() || !lobbyManager.isLobby(player.getWorld()))
         return;
      
      Vector dir = player.getLocation().getDirection().setY(0).normalize().multiply(0.8);
      player.setVelocity(player.getVelocity().add(dir));
   }
}
