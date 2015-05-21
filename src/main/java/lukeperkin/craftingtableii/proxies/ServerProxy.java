package lukeperkin.craftingtableii.proxies;

import corelibrary.api.common.mod.IMod;
import corelibrary.proxies.AbstractProxy;
import cpw.mods.fml.relauncher.Side;
import lukeperkin.craftingtableii.Clevercraft;

/**
 * Created by Master801 on 3/28/2015 at 12:17 PM.
 * @author Master801
 */
public final class ServerProxy extends AbstractProxy {

    @Override
    public void registerBlockRenderers() {
    }

    @Override
    public void registerTileEntityRenderers() {
    }

    @Override
    public void registerItemRenderers() {
    }

    @Override
    public Side getSide() {
        return Side.SERVER;
    }

    @Override
    public IMod getOwningMod() {
        return Clevercraft.instance;
    }

}
