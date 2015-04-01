package lukeperkin.craftingtableii.client.renderers.block;

import corelibrary.api.common.mod.IMod;
import corelibrary.client.renderer.block.BlockRendererCoreBase;
import corelibrary.helpers.BlockRendererHelper;
import corelibrary.utilities.Coordinates;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lukeperkin.craftingtableii.Clevercraft;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

/**
 * Created by Master801 on 3/28/2015 at 12:51 PM.
 * @author Master801
 */
@SideOnly(Side.CLIENT)
public final class BlockRendererCraftingTableII extends BlockRendererCoreBase {

    @Override
    public String getSpecialRenderID() {
        return "Clevercraft";
    }

    @Override
    public IMod getOwningMod() {
        return Clevercraft.instance;
    }

    @Override
    protected boolean renderBlock(IBlockAccess world, Coordinates coords, int modelID, RenderBlocks renderer, Tessellator tessellator) {
        return renderer.renderStandardBlock(world.getBlock(coords.getX(), coords.getY(), coords.getZ()), coords.getX(), coords.getY(), coords.getZ());
    }

    @Override
    protected void renderInInventory(Block block, int metadata, RenderBlocks renderer, Tessellator tessellator) {
        BlockRendererHelper.renderBlockAsItem(renderer, tessellator, block, metadata);
    }

}
