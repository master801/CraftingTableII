package lukeperkin.craftingtableii.itemblocks;

import corelibrary.itemblock.ItemBlockCoreBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Master801 on 3/28/2015 at 1:10 PM.
 * @author Master801
 */
public final class ItemBlockClevercraft extends ItemBlockCoreBase {

    public ItemBlockClevercraft(Block block) {
        super(block, false);
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected List<String> addInformation(ItemStack stack, EntityPlayer player, boolean var3) {
        final List<String> list = new ArrayList<String>();
        list.add("The crafting table of awesomeness that you are using right now! No need to remember fiddly recipe patterns, the");
        list.add("Crafting Table II will figure it out all for you.");
        list.add("");
        list.add("");
        list.add("Shift click to craft as much of that item as possible.");
        return list;
    }

}
