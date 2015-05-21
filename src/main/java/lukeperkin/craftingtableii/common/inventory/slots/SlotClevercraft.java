package lukeperkin.craftingtableii.common.inventory.slots;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.crafting.IRecipe;

public class SlotClevercraft extends SlotCrafting {
    
    public IInventory craftMatrix;
    private IRecipe irecipe;
    
    public SlotClevercraft(EntityPlayer entityplayer, IInventory craftableRecipes, IInventory matrix, int i, int j, int k) {
        super(entityplayer, matrix, craftableRecipes, i, j, k);
        craftMatrix = matrix;
    }

    public void setIRecipe(IRecipe theIRecipe) {
        irecipe = theIRecipe;
    }

    public IRecipe getIRecipe() {
        return irecipe;
    }

}
