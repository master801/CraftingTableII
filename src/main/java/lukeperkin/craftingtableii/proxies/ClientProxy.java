package lukeperkin.craftingtableii.proxies;

import corelibrary.api.common.mod.IMod;
import corelibrary.helpers.RegistryHelper;
import corelibrary.proxies.AbstractProxy;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lukeperkin.craftingtableii.Clevercraft;
import lukeperkin.craftingtableii.client.renderers.block.BlockRendererCraftingTableII;
import lukeperkin.craftingtableii.client.renderers.tileentity.TileEntityRendererCraftingTableII;
import lukeperkin.craftingtableii.tileentities.TileEntityCraftingTableII;

/**
 * Created by Master801 on 3/28/2015 at 12:17 PM.
 * @author Master801
 */
@SideOnly(Side.CLIENT)
public final class ClientProxy extends AbstractProxy {

    @Override
    public void registerBlockRenderers() {
        RegistryHelper.registerBlockHandler(new BlockRendererCraftingTableII());
    }

    @Override
    public void registerTileEntityRenderers() {
        RegistryHelper.bindSpecialRendererToTileEntity(TileEntityCraftingTableII.class, new TileEntityRendererCraftingTableII());
    }

    @Override
    public void registerItemRenderers() {
    }

    @Override
    public Side getSide() {
        return Side.CLIENT;
    }

    @Override
    public IMod getOwningMod() {
        return Clevercraft.instance;
    }

}
