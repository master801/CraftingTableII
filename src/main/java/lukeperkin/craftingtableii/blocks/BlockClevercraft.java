package lukeperkin.craftingtableii.blocks;

import corelibrary.api.block.IRender;
import corelibrary.api.common.mod.IMod;
import corelibrary.block.BlockCoreBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lukeperkin.craftingtableii.Clevercraft;
import lukeperkin.craftingtableii.tileentities.TileEntityCraftingTableII;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

public final class BlockClevercraft extends BlockCoreBase implements IRender
{

	private static final IIcon[] CLEVERCRAFT_ICONS = new IIcon[3];
	
	public BlockClevercraft()
	{
		super(Material.wood, false);
		this.setBlockName("craftingtableii");
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1F, 1.0F);
		setLightOpacity(0);
	}

	@Override
	public String getUnlocalisedName(int metadata)
	{
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected void registerIcon(TextureMap map)
	{
		final String[] types = new String[] { "Sides", "Front", "Top" };
		for(int i = 0; i < types.length; i++) {
			BlockClevercraft.CLEVERCRAFT_ICONS[i] = map.registerIcon(new ResourceLocation("clevercraft", "CTII-" + types[i]).toString());
		}
	}

	@Override
	public TileEntity createTileEntity(int metadata)
	{
		return new TileEntityCraftingTableII();
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected IIcon getIcon(IBlockAccess world, int xCoord, int yCoord, int zCoord, ForgeDirection side)
	{
		TileEntity tile = world.getTileEntity(xCoord, yCoord, zCoord);
		if (tile instanceof TileEntityCraftingTableII)
		{
			TileEntityCraftingTableII craftingTableII = (TileEntityCraftingTableII)tile;
			switch(side)
			{
				case DOWN:
				case UP:
					return BlockClevercraft.CLEVERCRAFT_ICONS[2];//Top&Bottom
				default:
					if (side == craftingTableII.getFacingDirection())
					{
						return BlockClevercraft.CLEVERCRAFT_ICONS[1];//Front
					}
					return BlockClevercraft.CLEVERCRAFT_ICONS[0];//Sides
			}
		}
		switch(side)
		{
			case DOWN:
				return BlockClevercraft.CLEVERCRAFT_ICONS[2];//Top&Bottom
			case UP:
				return BlockClevercraft.CLEVERCRAFT_ICONS[2];//Top&Bottom
			case SOUTH:
				return BlockClevercraft.CLEVERCRAFT_ICONS[1];//Front
			default:
				return BlockClevercraft.CLEVERCRAFT_ICONS[0];//Sides
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getSpecialRenderID()
	{
		return "Clevercraft";
	}

	@Override
	public IMod getMod()
	{
		return Clevercraft.instance;
	}

}
