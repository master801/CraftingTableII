package lukeperkin.craftingtableii.client.renderers.tileentity;

import corelibrary.client.renderer.tileentity.TileEntityRendererCoreBase;
import corelibrary.common.resources.CoreResources;
import corelibrary.helpers.RotationHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lukeperkin.craftingtableii.client.models.ModelCraftingTableII;
import lukeperkin.craftingtableii.tileentities.TileEntityCraftingTableII;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class TileEntityRendererCraftingTableII extends TileEntityRendererCoreBase {

	public static final ResourceLocation CRAFTING_TABLE_II_RESOURCE_LOCTION = new ResourceLocation("clevercraft", "textures/models/ctii.png");

	private final ModelCraftingTableII model;

	public TileEntityRendererCraftingTableII() {
		model = new ModelCraftingTableII();
	}

	@Override
	protected void renderTileEntity(TileEntity tile, double xCoord, double yCoord, double zCoord, float f) {
		if (tile instanceof TileEntityCraftingTableII) {
			TileEntityCraftingTableII craftingTableII = (TileEntityCraftingTableII)tile;
			GL11.glPushMatrix();
			GL11.glTranslated(xCoord + 0.5D, xCoord + 0.5D, xCoord + 0.5D);
			GL11.glRotatef(270.0F - (RotationHelper.convertForgeDirectionToByte(craftingTableII.getFacingDirection()) * 90.0F), 0.0F, 1.0F, 1.0F);
			CoreResources.getTextureManager().bindTexture(TileEntityRendererCraftingTableII.CRAFTING_TABLE_II_RESOURCE_LOCTION);
			GL11.glScalef(-1.0F, -1.0F, -1.0F);
			model.render(null, craftingTableII.doorAngle, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
			GL11.glPopMatrix();
		}
	}

}

/*
public class RenderCraftingTableII extends TileEntitySpecialRenderer {
	
	private ModelCraftingTableII modelCraftingTable;
	
	public RenderCraftingTableII() {
		modelCraftingTable = new ModelCraftingTableII();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d, double d1,
			double d2, float f) {
		
		TileEntityCraftingTableII craftingtable = (TileEntityCraftingTableII)tileentity;
		float doorRotation = craftingtable.doorAngle;
		int facing = craftingtable.getFacing();
		float r = facing * 90F;
		
		GL11.glPushMatrix();
        GL11.glTranslatef((float)d+0.5F, (float)d1+1F, (float)d2+0.5F);
        GL11.glRotatef(270F - r, 0.0F, 1.0F, 0.0F);
        
        bindTextureByName("/blockimage/ctii.png");
        GL11.glScalef(-1F, -1F, 1.0F);
        modelCraftingTable.render(null, doorRotation, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        GL11.glPopMatrix();

	}

}
*/
