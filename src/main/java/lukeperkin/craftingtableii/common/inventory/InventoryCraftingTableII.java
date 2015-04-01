package lukeperkin.craftingtableii.common.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

public final class InventoryCraftingTableII {
	
	private IRecipe[] recipes;

	public InventoryCraftingTableII(int i)
    {
		recipes = new IRecipe[i];
    }
	
	public int nextEmptySlot()
	{
		for(int i = 0; i < recipes.length; i++) {
			if(recipes[i] == null)
				return i;
		}
		
		return 0;
	}
	
	public boolean addRecipe(IRecipe irecipe)
	{
		int empty = nextEmptySlot();
		if(empty >= recipes.length || irecipe == null) {
			return false;
		}
		recipes[empty] = irecipe;
		return true;
	}
	
	public IRecipe getIRecipe(int i)
	{
		return recipes[i];
	}
	
	public ItemStack getRecipeOutput(int i)
	{
		if(recipes[i] != null)
		{
			return recipes[i].getRecipeOutput().copy();
		}
		else
		{
			return null;
		}
	}
	
	public void clearRecipes()
	{
		int length = recipes.length;
		recipes = null;
		recipes = new IRecipe[length];
	}

}
