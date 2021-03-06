package FrogCraft.Machines2;

import java.util.Iterator;

import FrogCraft.Common.ProductSlot;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ContainerLiquidOutput extends Container {

	protected TileEntityLiquidOutput tileEntity;
	
	public int idOut,damageOut,amountOutP;
	
	public ContainerLiquidOutput (InventoryPlayer inventoryPlayer, TileEntityLiquidOutput te){
        tileEntity = te;

        addSlotToContainer(new Slot(tileEntity, 0, 113, 21));
        addSlotToContainer(new ProductSlot(tileEntity, 1, 113, 56));
        
        
        bindPlayerInventory(inventoryPlayer);
	}
	
	
	//Common Stuff
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return true;
	}

    protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
        for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 9; j++) {
                        addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9,
                                        8 + j * 18, 84 + i * 18));
                }
        }

        for (int i = 0; i < 9; i++) {
                addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
        }
}
	
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
            ItemStack stack = null;
            Slot slotObject = (Slot) inventorySlots.get(slot);

            //null checks and checks if the item can be stacked (maxStackSize > 1)
            if (slotObject != null && slotObject.getHasStack()) {
                    ItemStack stackInSlot = slotObject.getStack();
                    stack = stackInSlot.copy();

                    //merges the item into player inventory since its in the tileEntity
                    if (slot < 2) {
                            if (!this.mergeItemStack(stackInSlot, 2, 38, true)) {
                                    return null;
                            }
                    }
                    //places it into the tileEntity is possible since its in the player inventory
                    else if (!this.mergeItemStack(stackInSlot, 0,1, false)) {
                            return null;
                    }

                    if (stackInSlot.stackSize == 0) {
                            slotObject.putStack(null);
                    } else {
                            slotObject.onSlotChanged();
                    }

                    if (stackInSlot.stackSize == stack.stackSize) {
                            return null;
                    }
                    slotObject.onPickupFromSlot(player, stackInSlot);
            }
            return stack;
    }
    
    
    
    
    
    @Override
    public void addCraftingToCrafters(ICrafting par1iCrafting) {
    	super.addCraftingToCrafters(par1iCrafting);
    	par1iCrafting.sendProgressBarUpdate(this, 0, tileEntity.idOut);
    	par1iCrafting.sendProgressBarUpdate(this, 1, tileEntity.damageOut);    	
    	par1iCrafting.sendProgressBarUpdate(this, 2, tileEntity.amountOutP);    	
    }
    
    
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2)
    {
    	switch(par1){
    		case 0:{tileEntity.idOut = par2;return;}    		
    		case 1:{tileEntity.damageOut = par2;return;}     
    		case 2:{tileEntity.amountOutP = par2;return;}       		
    	}
    	
   	}
    
    @Override
    public void detectAndSendChanges()
    {
    	super.detectAndSendChanges();
    	Iterator var1 = this.crafters.iterator();
    	while (var1.hasNext())
    	{
    		ICrafting var2 = (ICrafting)var1.next();
    		var2.sendProgressBarUpdate(this, 0, idOut);     
    		var2.sendProgressBarUpdate(this, 1, damageOut);        
    		var2.sendProgressBarUpdate(this, 2, amountOutP);     		
    	}
    	
    	idOut=tileEntity.idOut;
    	damageOut=tileEntity.damageOut;
    	amountOutP=tileEntity.amountOutP;
    }





}