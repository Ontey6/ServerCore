package com.ontey.serverCore;

import com.ontey.api.plugin.OnteyPlugin;
import com.ontey.serverCore.command.ServerCoreCommand;
import com.ontey.serverCore.command.SpawnCommand;
import com.ontey.serverCore.files.Config;
import com.ontey.serverCore.listener.HotbarClickListener;
import com.ontey.serverCore.listener.JoinListener;
import com.ontey.serverCore.listener.LobbyPreventionsListener;
import com.ontey.serverCore.manager.LobbyManager;
import com.ontey.serverCore.manager.SpawnInventoryManager;

public final class ServerCore extends OnteyPlugin {
   
   @Override
   public void onEnable() {
      load();
      
      Config.load();
      LobbyManager.load();
      SpawnInventoryManager.load();
      
      registerCommands();
      
      pluginManager.registerEvents(new JoinListener(), plugin);
      pluginManager.registerEvents(new LobbyPreventionsListener(), plugin);
      pluginManager.registerEvents(new HotbarClickListener(), plugin);
   }
   
   private void registerCommands() {
      ServerCoreCommand.register();
      SpawnCommand.register();
   }
}
