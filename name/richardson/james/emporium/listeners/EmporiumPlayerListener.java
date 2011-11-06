package name.richardson.james.emporium.listeners;

import java.util.logging.Level;

import name.richardson.james.emporium.EmporiumPlugin;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerInventoryEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.inventory.ItemStack;


public class EmporiumPlayerListener extends PlayerListener {

  private EmporiumPlugin plugin;
  
  public EmporiumPlayerListener(EmporiumPlugin plugin) {
    this.plugin = plugin;
  }
  
  public void onInventoryOpen(PlayerInventoryEvent event) {
    for (ItemStack item : event.getInventory().getContents()) {
      plugin.log(Level.INFO, item.getType().toString());
    }
  }
  
  public void onItemHeldChange(PlayerItemHeldEvent event) {
    plugin.log(Level.INFO, "New inventory slot: " + Integer.toString(event.getNewSlot()));
    plugin.log(Level.INFO, "Old inventory slot: " + Integer.toString(event.getPreviousSlot()));
    plugin.log(Level.INFO, "Material in hand: " + event.getPlayer().getItemInHand().getType().toString());
  }
  
  public void onItemInteract(PlayerInteractEvent event) {
    plugin.log(Level.INFO, "Item  in hand: " + event.getMaterial().toString());
  }
  
  public void onPlayerInteractEvent(PlayerInteractEvent event) {
    
  }
  
}
