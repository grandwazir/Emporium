package name.richardson.james.emporium;

import java.util.logging.Level;
import java.util.logging.Logger;

import name.richardson.james.emporium.listeners.EmporiumInventoryListener;
import name.richardson.james.emporium.listeners.EmporiumPlayerListener;

import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


public class EmporiumPlugin extends JavaPlugin {

  private Logger logger = Logger.getLogger("Minecraft");
  private PluginDescriptionFile description;
  private PluginManager pluginManager;
  
  private final EmporiumPlayerListener playerListener;
  private final EmporiumInventoryListener inventoryListener;
  
  public EmporiumPlugin() {
    playerListener = new EmporiumPlayerListener(this);
    inventoryListener = new EmporiumInventoryListener(this);
  }

  public void log(final Level level, final String message) {
    logger.log(level, "[" + description.getName() + "] " + message);
  }
  
  @Override
  public void onDisable() {
    log(Level.INFO, String.format("%s is now disabled.", description.getName())); 
  }

  @Override
  public void onEnable() {
    pluginManager = getServer().getPluginManager();
    description = getDescription();
    
    registerEvents();
    log(Level.INFO, String.format("%s is now enabled.", description.getFullName()));
  }

  /**
   * Registers listeners for all the events required 
   * for Emporium to function.
   */
  private void registerEvents() {
    pluginManager.registerEvent(Event.Type.CUSTOM_EVENT, inventoryListener, Event.Priority.Monitor, this);
  }

}
