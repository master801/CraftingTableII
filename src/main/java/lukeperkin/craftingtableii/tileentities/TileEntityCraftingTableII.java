package lukeperkin.craftingtableii.tileentities;

import corelibrary.api.tileentity.IRotatable;
import corelibrary.helpers.GuiHelper;
import corelibrary.helpers.PlayerHelper;
import corelibrary.tileentity.TileEntityCoreBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lukeperkin.craftingtableii.Clevercraft;
import lukeperkin.craftingtableii.client.gui.GuiClevercraft;
import lukeperkin.craftingtableii.common.inventory.ContainerClevercraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityCraftingTableII extends TileEntityCoreBase implements IRotatable {

	public static final float OPEN_SPEED = 0.2F;

	public float doorAngle = 0.0F;
	private byte tablestate = 0;

	@Override
	public void updateEntity()
	{
		EntityPlayer entityplayer = worldObj.getClosestPlayer((float)xCoord + 0.5F, (float)yCoord + 0.5F, (float)zCoord + 0.5F, 10D);
		if(entityplayer != null)
		{
			double playerDistance = entityplayer.getDistanceSq((double)xCoord, (double)yCoord, (double)zCoord);
			if(playerDistance < 7.0D)
			{
				doorAngle += TileEntityCraftingTableII.OPEN_SPEED;
				if(tablestate != 1)
				{
					tablestate = 1;
					worldObj.playSoundEffect((double)xCoord, (double)yCoord + 0.5D, (double)zCoord, "random.chestopen", 0.2F, worldObj.rand.nextFloat() * 0.1F + 0.2F);
				}
				if(doorAngle > 1.8F)
				{
					doorAngle = 1.8F;
				}
			}
			else if(playerDistance > 7.0D)
			{
				doorAngle -= TileEntityCraftingTableII.OPEN_SPEED;
				if(tablestate != 0)
				{
					tablestate = 0;
					worldObj.playSoundEffect((double)xCoord, (double)yCoord + 0.5D, (double)zCoord, "random.chestclosed", 0.2F, worldObj.rand.nextFloat() * 0.1F + 0.2F);
				}
				if(doorAngle < 0F)
				{
					doorAngle = 0F;
				}
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		doorAngle = nbt.getFloat("angle");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setFloat("angle", doorAngle);
	}

	@Override
	public DirectionType getDirectionType()
	{
		return DirectionType.FURNACE;
	}

	@Override
	public boolean onActivated(EntityPlayer player, ForgeDirection side)
	{
		if (PlayerHelper.isPlayerNotSneaking(player))
		{
			return GuiHelper.openGui(Clevercraft.instance, player, this);
		}
		return false;
	}

	@Override
	public boolean doesHaveGui() {
		return true;
	}

	@Override
	public Container getServerGui(InventoryPlayer inventory) {
		return new ContainerClevercraft(inventory, worldObj);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiContainer getClientGui(InventoryPlayer inventory) {
		return new GuiClevercraft(inventory.player, worldObj);
	}

}
