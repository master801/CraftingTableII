package lukeperkin.craftingtableii.common.inventory;

import corelibrary.common.inventory.container.ContainerTileEntityCoreBase;
import lukeperkin.craftingtableii.tileentities.TileEntityCraftingTableII;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Created by Master801 on 5/22/2015 at 7:10 AM.
 * @author Master801
 */
public class ContainerClevercraft_NEW extends ContainerTileEntityCoreBase<TileEntityCraftingTableII> {//TODO Make this container class from scratch!

    protected final World world;
    InventoryCrafting craftingMaxtrix = new InventoryCrafting(this, 3, 3);

    public ContainerClevercraft_NEW(InventoryPlayer playerInventory, TileEntityCraftingTableII tileentity) {
        super(playerInventory, tileentity);
        this.world = tileentity.getWorldObj();
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotNumber) {
        //TODO Not needed currently.
        return null;
    }

}
