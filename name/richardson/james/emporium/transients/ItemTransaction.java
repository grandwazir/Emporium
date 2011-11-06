package name.richardson.james.emporium.transients;

import name.richardson.james.emporium.InventoryType;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


public class ItemTransaction {

  private Inventory containerInventory;
  private Inventory playerInventory;
  private int firstSlot = 9;
  private int lastSlot;
  private ItemStack itemBeingMoved;
  private InventoryType previousInventoryType;
  

  public ItemTransaction(Inventory containerInventory, Inventory playerInventory) {
    this.containerInventory = containerInventory;
    this.playerInventory = playerInventory;
    this.firstSlot = 9;
    this.lastSlot = containerInventory.getContents().length + 8;
  }
  
  public void commit() {
    
  }
  
  
  public InventoryType getPreviousInventoryType() {
    return this.previousInventoryType;
  }
  
  public boolean isItemFromContainer(int ItemSlot) {
    if (ItemSlot >= this.firstSlot && ItemSlot <= this.lastSlot) {
      return true;
    } else {
      return false;
    }
  }

  public void rollback() {

  }
  
  public void setItemBeingMoved(ItemStack item) {
    this.itemBeingMoved = item;
  }

  public void setPreviousInventoryType(InventoryType inventoryType) {
    this.previousInventoryType = inventoryType;
  }
  
  public String toString() {
    return "Item being traded: " + this.itemBeingMoved.toString() + ". PreviousInventoryType: " + this.previousInventoryType.toString();
  }
  
}
