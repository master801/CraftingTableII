package lukeperkin.craftingtableii.common.inventory;

import corelibrary.helpers.*;
import lukeperkin.craftingtableii.Clevercraft;
import lukeperkin.craftingtableii.common.inventory.slots.SlotClevercraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.*;

public class ContainerClevercraft extends Container {
    
    private static InventoryBasic inventory = new InventoryBasic("tmp", false, 8*5);
    private static IRecipe[] favouriteRecipes = new IRecipe[8];
    
    public InventoryCrafting craftMatrix;
    public InventoryCraftingTableII craftableRecipes;
    private List<IRecipe> recipeList;
    private World worldObj;
    private EntityPlayer thePlayer;
    private Timer timer;
    
    public ContainerClevercraft(InventoryPlayer inventoryplayer, World world)
    {
        worldObj = world;
        thePlayer = inventoryplayer.player;
        craftMatrix = new InventoryCrafting(this, 3, 3);
        craftableRecipes = new InventoryCraftingTableII(1000);
        recipeList = Collections.unmodifiableList(CraftingManager.getInstance().getRecipeList());
        
        for(int l2 = 0; l2 < 5; l2++)
        {
            for(int j3 = 0; j3 < 8; j3++)
            {
                addSlotToContainer(new SlotCrafting(thePlayer, inventory, craftMatrix, j3 + l2 * 8, 8 + j3 * 18, 18 + l2 * 18));
            }
        }

        for(int j = 0; j < 3; j++)
        {
            for(int i1 = 0; i1 < 9; i1++)
            {
                addSlotToContainer(new Slot(inventoryplayer, i1 + j * 9 + 9, 8 + i1 * 18, 125 + j * 18));
            }
        }
        
        for(int i3 = 0; i3 < 9; i3++)
        {
            addSlotToContainer(new Slot(inventoryplayer, i3, 8 + i3 * 18, 184));
        }
        
        populateSlotsWithRecipes();
        if(WorldHelper.isServer(world)) {
            timer = new Timer();
            timer.schedule(new RemindTask(), 1000);
        }
    }
    
    class RemindTask extends TimerTask {
        public void run() {
          populateSlotsWithRecipes();
          timer.cancel();
        }
    }
    
    
//    static InventoryBasic getInventory()
//    {
//        return inventory;
//    }
    
    // Populate all the slots with recipes the player can craft.
    public void populateSlotsWithRecipes()
    {
        // Clear the list of craftable recipes.
        craftableRecipes.clearRecipes();
        // add favourites and recipes to recipe list.
        recipeList = new ArrayList<IRecipe>();
        for(int i = 0; i < 8; i++) {
            if(favouriteRecipes[i] != null) {
                recipeList.add( favouriteRecipes[i] );
            }
        }
        for(int i = 0; i < CraftingManager.getInstance().getRecipeList().size(); i++) {
            recipeList.add((IRecipe)CraftingManager.getInstance().getRecipeList().get(i));
        }
        recipeList = Collections.unmodifiableList(recipeList);
        
        // Loop through each recipe, getting the ingredient and checking the player
        // has the necessary ingredient.
        for(int i = 0; i < recipeList.size(); i++) {
            IRecipe irecipe = recipeList.get(i);
            // Copy the recipe ingredients into an ItemStack array.
            ItemStack[] recipeIngredients = getRecipeIngredients(irecipe);
            if(recipeIngredients == null)
                continue;
            // Check if the player has the required ingredients.
            // 1. Copy the players inventory to a temporary inventory.
            InventoryPlayer tempPlayerInventory = new InventoryPlayer( thePlayer );
            tempPlayerInventory.copyInventory( thePlayer.inventory );
            // 2. Loop through the temp inventory checking for the ingredients.
            boolean playerHasAllIngredients = true;
            for(int i1 = 0; i1 < recipeIngredients.length; i1++) {
                if(recipeIngredients[i1] == null)
                    continue;
                
                ItemStack itemstack = recipeIngredients[i1];
                itemstack.stackSize = 1;
                int slotindex = getFirstInventoryPlayerSlotWithItemStack(tempPlayerInventory, itemstack);
                if(slotindex != -1) {
                    tempPlayerInventory.decrStackSize(slotindex, itemstack.stackSize);
                } else {
                    playerHasAllIngredients = false;
                    break;
                }
            }
            // 3. Add recipe to list of craftable recipes if player has all the ingredients.
            if(playerHasAllIngredients) {
                craftableRecipes.addRecipe(irecipe);
            }
        }
        
        // Update the visible slots.
        updateVisibleSlots( 0.0F );
    }
    
    // Check InventorPlayer contains the ItemStack.
    private int getFirstInventoryPlayerSlotWithItemStack(InventoryPlayer inventory, ItemStack itemstack)
    {
        for(int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack itemstack1 = inventory.getStackInSlot(i);
            if(itemstack1 != null
                    && itemstack1.getItem() == itemstack.getItem()
                    && (itemstack1.getItemDamage() == itemstack.getItemDamage() || itemstack.getItemDamage() == -1)) {
                return i;
            }
        }
        
        return -1;
    }
    
    // Get a list of ingredient required to craft the recipe item.
    private ItemStack[] getRecipeIngredients(IRecipe irecipe)
    {
        if(irecipe instanceof ShapedRecipes) {
            return ReflectionHelper.getObfuscatedFieldValue(Clevercraft.CLEVER_CRAFT_LOGGER, ShapedRecipes.class, (ShapedRecipes)irecipe, "c", "field_77574_d", "recipeItems");
        } else if(irecipe instanceof ShapelessRecipes) {
            List recipesItems = ReflectionHelper.getObfuscatedFieldValue(Clevercraft.CLEVER_CRAFT_LOGGER, ShapelessRecipes.class, (ShapelessRecipes)irecipe, "b", "field_77579_b", "recipeItems");
            return ListHelper.convertListToArray((List<ItemStack>)recipesItems, new ItemStack[0]);
        } else {
            return null;
        }
    }
    
    public void updateVisibleSlots(float f)
    {
        int numberOfRecipes = craftableRecipes.nextEmptySlot();
        int i = (numberOfRecipes / 8 - 4) + 1;
        int j = (int)((double)(f * (float)i) + 0.5D);
        if(j < 0)
            j = 0;
        
        for(int k = 0; k < 5; k++) {
            for(int l = 0; l < 8; l++) {
                int i1 = l + (k + j) * 8;
                Slot slot = (Slot)inventorySlots.get(l + k * 8);
                if(i1 >= 0 && i1 < numberOfRecipes) {
                    ItemStack recipeOutput = craftableRecipes.getRecipeOutput(i1);
                    if(recipeOutput != null) {
                        inventory.setInventorySlotContents(l + k * 8, recipeOutput);
                        if(slot instanceof SlotClevercraft) {
                            ((SlotClevercraft)slot).setIRecipe( craftableRecipes.getIRecipe(i1) );
                        }
                    } else {
                        inventory.setInventorySlotContents(l + k * 8, null);
                        if(slot instanceof SlotClevercraft) {
                            ((SlotClevercraft)slot).setIRecipe(null);
                        }
                    }
                } else {
                    inventory.setInventorySlotContents(l + k * 8, null);
                    if(slot instanceof SlotClevercraft) {
                        ((SlotClevercraft)slot).setIRecipe(null);
                    }
                }
            }
        }
    }
    
    public ItemStack slotClick(int slotIndex, int mouseButton, boolean shiftIsDown, EntityPlayer entityplayer)
    {
        if(slotIndex != -999 
                && inventorySlots.get(slotIndex) != null 
                && inventorySlots.get(slotIndex) instanceof SlotClevercraft) {
            
            // Check if the currently held itemstack is different to the clicked itemstack.
            ItemStack itemstack = inventory.getStackInSlot(slotIndex);
            ItemStack playerItemStack = entityplayer.inventory.getItemStack();
            boolean currentItemStackIsDifferent = false;
            if(playerItemStack != null && itemstack != null) {
                if(playerItemStack.getItem() == itemstack.getItem()
                        && (itemstack.getItemDamage() == -1 || itemstack.getItemDamage() == playerItemStack.getItemDamage())) {
                    currentItemStackIsDifferent = false;
                } else {
                    currentItemStackIsDifferent = true;
                }
            }
            
            if(currentItemStackIsDifferent)
                return null;
            
            // Ignore right click.
            if(mouseButton == 1) {
                return null;
            } else if(shiftIsDown) {
                onRequestMaximumRecipeOutput( (SlotClevercraft)inventorySlots.get(slotIndex) );
                populateSlotsWithRecipes();
                return null;
            } else {
                if( !onRequestSingleRecipeOutput( (SlotClevercraft)inventorySlots.get(slotIndex) ) )
                    return null;
            }
        }
        
        if(WorldHelper.isServer(worldObj) || shiftIsDown) {
            populateSlotsWithRecipes();
            return null;
        } else {
//            ItemStack itemstack = super.slotClick(slotIndex, mouseButton, shiftIsDown, entityplayer);
            ItemStack itemstack = super.slotClick(slotIndex, mouseButton, 1, entityplayer);
            populateSlotsWithRecipes();
            return itemstack;
        }
    }
    
    private boolean onRequestSingleRecipeOutput( SlotClevercraft slot )
    {
        // Get IRecipe from slot.
        IRecipe irecipe = slot.getIRecipe();
        if(irecipe == null)
            return false;
        ItemStack recipeOutputStack = irecipe.getRecipeOutput().copy();
        this.addFavouriteRecipe(irecipe);
        
        // Check if we are already holding a full stack.
        ItemStack heldStack = thePlayer.inventory.getItemStack();
        if(heldStack != null
        && heldStack.stackSize + recipeOutputStack.stackSize > heldStack.getMaxStackSize())
        {
            return false;
        }
        
        
        // Send request packet if multiplayer.
        if(WorldHelper.isServer(worldObj)) {
//            mod_Clevercraft.getInstance().sendCraftingRequestPacket(recipeOutputStack, false);
            //TODO?
        }
        
        // Take the necesarry ingredients from the player.
        InventoryPlayer inventoryPlayer = thePlayer.inventory;
        ItemStack[] recipeIngredients = getRecipeIngredients(irecipe);
        if (recipeIngredients == null) {
            throw new NullPointerException(String.format("Couldn't get Recipe Ingredients from recipe \"%s\"!", irecipe.toString()));
        }
        for(int i = 0; i < recipeIngredients.length; i++) {
            ItemStack recipeIngredient = recipeIngredients[i];
            if(recipeIngredient == null)
                continue;
            for(int i1 = 0; i1 < inventoryPlayer.getSizeInventory(); i1++) {
                ItemStack itemstack = inventoryPlayer.getStackInSlot(i1);
                if(itemstack != null && itemstack.getItem() == recipeIngredient.getItem()
                        && (itemstack.getItemDamage() == recipeIngredient.getItemDamage() || recipeIngredient.getItemDamage() == -1)) {
                    // Transfer the items in the player's inventory to the craft matrix.
                    craftMatrix.setInventorySlotContents(i, recipeIngredient.copy());
                    inventoryPlayer.decrStackSize(i1, 1);
                    break;
                }
            }
        }
        
        onCraftMatrixChanged(recipeOutputStack);
        return true;
    }
    
    private void onRequestMaximumRecipeOutput( SlotClevercraft slot )
    {    
        IRecipe irecipe = slot.getIRecipe();
        if(irecipe == null)
            return;
        
        if(WorldHelper.isServer(worldObj)) {
//            mod_Clevercraft.getInstance().sendCraftingRequestPacket(slot.getIRecipe().getRecipeOutput(), true);
            //TODO?
        }
        
        List collatedRecipe = new ArrayList();
        ItemStack[] recipeIngredients = getRecipeIngredients(irecipe);
        if (recipeIngredients == null) {
            throw new NullPointerException(String.format("Couldn't get Recipe Ingredients from recipe \"%s\"!", irecipe.toString()));
        }
        InventoryPlayer inventoryPlayer = thePlayer.inventory;
        int minimumOutputStackSize = 64;
        
        // Collate recipe ingredients into ordered list.
        for(int i = 0; i < recipeIngredients.length; i++) {
            ItemStack recipeIngredient = recipeIngredients[i];
            if(recipeIngredient != null) {
                recipeIngredient.stackSize = 1;
                if(recipeIngredient.getMaxStackSize() == 1)
                    minimumOutputStackSize = 1;
                if(collatedRecipe.size() == 0) {
                    collatedRecipe.add(recipeIngredient);
                } else {
                    boolean didUpdate = false;
                    for(int i1 = 0; i1 < collatedRecipe.size(); i1++) {
                        ItemStack itemstack1 = (ItemStack)collatedRecipe.get(i1);
                        if(itemstack1 != null && itemstack1.isItemEqual(recipeIngredient)) {
                            itemstack1.stackSize += recipeIngredient.stackSize;
                            didUpdate = true;
                            break;
                        }
                    }
                    if(!didUpdate)
                        collatedRecipe.add(recipeIngredient);
                }
            }
        }
        
        ItemStack recipeOutputStack = irecipe.getRecipeOutput().copy();
        if(minimumOutputStackSize == 1 || recipeOutputStack.getMaxStackSize() == 1) {
            return;
        }
        
        // Add favourite.
        this.addFavouriteRecipe(irecipe);
        
        // Calculate the maximum stackSize we can create.
        for(int i = 0; i < collatedRecipe.size(); i++) {
            ItemStack recipeIngredient = (ItemStack)collatedRecipe.get(i);
            Item itemid = recipeIngredient.getItem();
            int damageval = recipeIngredient.getItemDamage();
            int count = 0;
            for(int i1 = 0; i1 < inventoryPlayer.getSizeInventory(); i1++) {
                ItemStack itemstack = inventoryPlayer.getStackInSlot(i1);
                if(itemstack != null && itemstack.getItem() == itemid
                        && (itemstack.getItemDamage() == damageval || damageval == -1)) {
                    count += itemstack.stackSize;
                }
            }
            int maxStackDivision = MathHelper.floor_double(64 / recipeOutputStack.stackSize);
            int stackDivision = MathHelper.floor_double(count / recipeIngredient.stackSize);
            minimumOutputStackSize = Math.min(maxStackDivision, stackDivision);
        }
        
        // Multiply output stack size.
        recipeOutputStack.stackSize *= minimumOutputStackSize;
        
        // Check to see if a free spot is available.
        boolean canAddStack = false;
        for(int i = 0; i < inventoryPlayer.mainInventory.length; i++) {
            ItemStack stack = inventoryPlayer.mainInventory[i];
            if(stack == null) {
                canAddStack = true;
                break;
            }
            if(stack != null
                    && stack.getItem() == recipeOutputStack.getItem()
                    && (stack.getItemDamage() == recipeOutputStack.getItemDamage() || recipeOutputStack.getItemDamage() == -1)
                    && stack.stackSize + recipeOutputStack.stackSize < stack.getMaxStackSize()) {
                canAddStack = true;
                break;
            }
        }
        
        if(canAddStack) {
            // Add output to the players inventory.
            if(WorldHelper.isClient(worldObj)) {
                inventoryPlayer.addItemStackToInventory(recipeOutputStack);
            }
            
            // Transfer necessary items from player to craft matrix.
            for(int i = 0; i < recipeIngredients.length; i++) {
                ItemStack recipeIngredient = recipeIngredients[i];
                
                if(recipeIngredient != null) {
                    recipeIngredient.stackSize = 1;
                    craftMatrix.setInventorySlotContents(i, recipeIngredient);
                    int count = minimumOutputStackSize;
                    
                    ItemStack[] playerMainInventory = inventoryPlayer.mainInventory;
                    for(int i1 = 0; i1 < playerMainInventory.length; i1++) {
                        ItemStack itemstack = playerMainInventory[i1];
                        int dmg = recipeIngredient.getItemDamage();
                        if(itemstack != null && itemstack.getItem() == recipeIngredient.getItem()
                                && (itemstack.getItemDamage() == dmg || dmg == -1)) {
                            if(itemstack.stackSize >= count) {
                                inventoryPlayer.decrStackSize(i1, count);
                                count = 0;
                                break;
                            } else {
                                count -= itemstack.stackSize;
                                inventoryPlayer.decrStackSize(i1, itemstack.stackSize);
                            }
                        }
                    }
                }
            }
            
            onCraftMatrixChanged(recipeOutputStack);
        }
    }
    
    private void onCraftMatrixChanged(ItemStack recipeOutputStack)
    {
        InventoryPlayer inventoryPlayer = thePlayer.inventory;
        // Call custom hooks.
//        ModLoader.TakenFromCrafting(thePlayer, recipeOutputStack, craftMatrix);
//        ForgeHooks.onTakenFromCrafting(thePlayer, recipeOutputStack, craftMatrix);
        // Remove items from the craftMatrix and replace container items.
        for(int i = 0; i < craftMatrix.getSizeInventory(); i++)
        {
            ItemStack itemstack1 = craftMatrix.getStackInSlot(i);
            if(itemstack1 != null)
            {
                craftMatrix.decrStackSize(i, 1);
                if(itemstack1.getItem().hasContainerItem())
                {
                    craftMatrix.setInventorySlotContents(i, new ItemStack(itemstack1.getItem().getContainerItem()));
                }
            }
        }
        // Transfer any remaining items in the craft matrix to the player.
        for(int i = 0; i < craftMatrix.getSizeInventory(); i++) {
            ItemStack itemstack = craftMatrix.getStackInSlot(i);
            if(itemstack != null) {
                inventoryPlayer.addItemStackToInventory(itemstack);
                craftMatrix.setInventorySlotContents(i, null);
            }
        }
    }
    
    private static void addFavouriteRecipe(IRecipe recipe)
    {
        for(int i = 7; i > 0; i--) {
            favouriteRecipes[i] = favouriteRecipes[i-1];
            if(favouriteRecipes[i] == recipe)
                favouriteRecipes[i] = null;
        }
        favouriteRecipes[0] = recipe;
    }
    
    @Override
    public boolean canInteractWith(EntityPlayer entityplayer) {
        return true;
    }

}
