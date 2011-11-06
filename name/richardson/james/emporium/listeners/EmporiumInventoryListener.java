package name.richardson.james.emporium.listeners;

import java.util.logging.Level;

import name.richardson.james.emporium.EmporiumPlugin;
import name.richardson.james.emporium.InventoryType;
import name.richardson.james.emporium.transients.ItemTransaction;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.getspout.spoutapi.event.inventory.InventoryClickEvent;
import org.getspout.spoutapi.event.inventory.InventoryCloseEvent;
import org.getspout.spoutapi.event.inventory.InventoryListener;
import org.getspout.spoutapi.event.inventory.InventoryOpenEvent;
import org.getspout.spoutapi.event.inventory.InventoryPlayerClickEvent;


public class EmporiumInventoryListener extends InventoryListener {

 private EmporiumPlugin plugin;
 private ItemTransaction transaction;
  
  public EmporiumInventoryListener(EmporiumPlugin plugin) {
    this.plugin = plugin;
  }
  
  /**
   * Called when a player attempts to pick up an item in an
   * inventory. This identifies both the start and the end of all
   * item transactions and is responsible for providing that 
   * information to the current ItemTransaction.
   *
   * @param event InventoryClickEvent from Spout.
   */
  public void onInventoryClick(InventoryClickEvent event) {
    final InventoryType inventoryType = getInventoryType(event.getInventory().getSize());
    if (event.getItem() != null) {
      // Player has picked up an item
      plugin.log(Level.INFO, "Picked an item to trade");
      transaction.setItemBeingMoved(event.getItem());
      transaction.setPreviousInventoryType(inventoryType);
    } else {
      final InventoryType destinationInventory = getInventoryType(event.getInventory().getSize());
      // Player has placed an item in a inventory slot
      plugin.log(Level.INFO, "Completing trade");
      if (event.getSlot() == -999) {
        plugin.log(Level.INFO, "You can not throw stuff out of the chest");
        event.setCancelled(true);
        transaction.rollback();
      } else if (!transaction.getPreviousInventoryType().equals(destinationInventory)) {
        plugin.log(Level.INFO, "No stealing!");
        event.setCancelled(true);
        transaction.rollback();
      }
    }
  }
  
  public void onInventoryClose(InventoryCloseEvent event) {
    transaction.rollback();
    ItemStack itemInHand = event.getPlayer().getInventory().getItemInHand();
    if (!itemInHand.getType().equals(Material.AIR)) {
      itemInHand.setAmount(-1);
      event.getPlayer().getInventory().setItemInHand(itemInHand);
    }
  }
  
  public void onInventoryOpen(InventoryOpenEvent event) {
    for (ItemStack item : event.getInventory().getContents()) {
      if (item != null) {
        plugin.log(Level.INFO, item.getType().toString());
      }
    }
    
    transaction = new ItemTransaction(event.getInventory(), event.getBottomInventory());
    plugin.log(Level.INFO, Integer.toString(event.getInventory().getContents().length));
  }
  
  public void onInventoryPlayerClick(InventoryPlayerClickEvent event) {
    
    if (event.getItem() != null) {
      // Player has picked up an item
      transaction.setItemBeingMoved(event.getItem());
    } else {
      // Player has placed an item in a inventory slot
      if (event.getSlot() == -999) {
        plugin.log(Level.INFO, "You can not throw stuff out of the chest");
        event.setCancelled(true);
      } else {
        if (!transaction.isItemFromContainer(event.getSlot())) {
          plugin.log(Level.INFO, "No stealing!");
          event.setCancelled(true);
        }
      }
    }
    
    plugin.log(Level.INFO, Integer.toString(event.getSlot()));
    plugin.log(Level.INFO, Boolean.toString(transaction.isItemFromContainer(event.getSlot())));
    plugin.log(Level.INFO, event.getSlotType().toString());
  }
  
  private InventoryType getInventoryType(int totalSlots) {
    if (totalSlots == 10) {
      return InventoryType.WORKBENCH;
    } else if (totalSlots == 36) {
      return InventoryType.PLAYER_INVENTORY;
    } else if (totalSlots == 54) {
      return InventoryType.DOUBLE_CHEST;
    } else if (totalSlots == 27) {
      return InventoryType.FURNACE;
    } else {
      return null;
    }
  }
  
}
