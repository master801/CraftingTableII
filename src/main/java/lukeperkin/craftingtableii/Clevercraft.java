package lukeperkin.craftingtableii;

import corelibrary.api.common.mod.IMod;
import corelibrary.api.network.proxy.IProxy;
import corelibrary.helpers.*;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import lukeperkin.craftingtableii.common.resources.CleverResources;
import lukeperkin.craftingtableii.itemblocks.ItemBlockClevercraft;
import lukeperkin.craftingtableii.tileentities.TileEntityCraftingTableII;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Master801 on 3/28/2015 at 12:14 PM.
 * @author Master801
 */
@Mod(modid = CleverResources.CRAFTING_TABLE_II_MOD_ID, name = CleverResources.CRAFTING_TABLE_II_NAME, version = CleverResources.CRAFTING_TABLE_II_VERSION, dependencies = CleverResources.CRAFTING_TABLE_II_DEPENDENCIES)
public final class Clevercraft implements IMod {

    public static final Logger CLEVER_CRAFT_LOGGER = LogManager.getLogger("Crafting Table II");

    @Instance(CleverResources.CRAFTING_TABLE_II_MOD_ID)
    public static Clevercraft instance;

    @SidedProxy(serverSide = "lukeperkin.craftingtableii.proxies.ServerProxy", clientSide = "lukeperkin.craftingtableii.proxies.ClientProxy")
    public static IProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        try {
            ModHelper.addChildMod(Clevercraft.class);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        GameRegistry.registerTileEntity(TileEntityCraftingTableII.class, "tileClevercraft");
        GameRegistry.registerBlock(CleverResources.BLOCK_CLEVERCRAFT, ItemBlockClevercraft.class, "clevercraft");
        ProxyHelper.addProxyToMapping(Clevercraft.proxy);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        RegistryHelper.registerGuiHandler(Clevercraft.instance);
        GameRegistry.addShapelessRecipe(new ItemStack(CleverResources.BLOCK_CLEVERCRAFT, 1, 0), Blocks.crafting_table, Items.book);
    }

    @Override
    public Class<? extends IMod> getMainClass() {
        return Clevercraft.class;
    }

}
