package com.aymegike.huminekingdom.utils.models.glory;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftTNTPrimed;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import com.aymegike.huminekingdom.HumineKingdom;
import com.aymegike.huminekingdom.utils.Message;
import com.aymegike.huminekingdom.utils.Permissions;
import com.aymegike.huminekingdom.utils.models.GloryEvent;
import com.aymegike.huminekingdom.utils.models.Kingdom;
import com.aymegike.huminekingdom.utils.models.ShieldGenerator;

public class DestroyBeacon extends GloryEvent {

	public DestroyBeacon(String event) {
		super(event);
	}

	@Override
	public void onPlayerBeGlorious(Player player, Event event) {
		
		if (event instanceof BlockBreakEvent)
		{
			Kingdom kingdom = HumineKingdom.getKingdomManager().getPlayerKingdom(player);
			
			if (kingdom == null)
				return;
			
			BlockBreakEvent e = (BlockBreakEvent) event;
			
			if (e.getBlock().getType() == Material.BEACON) {
				
				Location blockLoc = e.getBlock().getLocation();
				for (Kingdom owner : HumineKingdom.getKingdomManager().getKingdomList())
				{				
					ShieldGenerator shield = owner.getShieldGenerator(blockLoc);
					if (shield != null)
					{					
						if (!owner.equals(kingdom))
						{
							/*if (owner.getGlory() > 0)
							{
								owner.removeGlory(350);
								owner.sendMessageToMembers(Message.LOST_POINTS(350));
							}
							owner.sendMessageToMembers(Message.SHIELD_DESTROY);
							kingdom.addGlory(300);
							kingdom.sendMessageToMembers(Message.WIN_POINTS(300));*/
						}
						else
						{
							if (owner.getPlayerGrade(player) != null && owner.getPlayerGrade(player).containPermission(Permissions.BREAK.getName()))
							{
								if (owner.getGlory() > 0)
								{
									owner.removeGlory(300);
									owner.sendMessageToMembers(Message.LOST_POINTS(300));
								}
								owner.sendMessageToMembers(Message.SHIELD_DESTROY);
								kingdom.removeShield(shield);
							}
						}
						return;
					}
				}
			}
		}
		

		if (event instanceof EntityExplodeEvent)
		{
			System.out.println("ENTITY EXPLODE 1");
			EntityExplodeEvent e = (EntityExplodeEvent) event;
			for (Block b : e.blockList())
			{
				if (b.getType() == Material.BEACON)
				{
					Kingdom kingdomOfDestroyer = null;
					System.out.println("ENTITY EXPLODE 2");
					Location blockLoc = b.getLocation();
					for (Kingdom owner : HumineKingdom.getKingdomManager().getKingdomList())
					{				
						ShieldGenerator shield = owner.getShieldGenerator(blockLoc);
						if (shield != null)
						{	
							System.out.println("ENTITY EXPLODE 3");
							boolean hasBeenDestroyedByEnnemies = false;
							if (e.getEntity() instanceof TNTPrimed)
							{
								System.out.println("ENTITY EXPLODE 4");
								TNTPrimed test = (TNTPrimed)e.getEntity();
								if (test.getSource() instanceof Player)
								{
									System.out.println("ENTITY EXPLODE 5");
									Player p = (Player) test.getSource();
									kingdomOfDestroyer = HumineKingdom.getKingdomManager().getPlayerKingdom(p);
									if (!owner.equals(kingdomOfDestroyer))
									{
										System.out.println("ENTITY EXPLODE 6");
										hasBeenDestroyedByEnnemies = true;
									}
								}
							}
							
							if (hasBeenDestroyedByEnnemies)
							{
								System.out.println("ENTITY EXPLODE 7");
								owner.sendMessageToMembers(Message.SHIELD_DESTROY);
								if (owner.getGlory() > 0)
								{
									owner.removeGlory(350);
									owner.sendMessageToMembers(Message.LOST_POINTS(350));
								}
								if (kingdomOfDestroyer != null)
								{
									kingdomOfDestroyer.addGlory(300);
									kingdomOfDestroyer.sendMessageToMembers(Message.WIN_POINTS(300));
								}
							}
							else
							{
								System.out.println("ENTITY EXPLODE 8");
								owner.sendMessageToMembers(Message.SHIELD_DESTROY);
								if (owner.getGlory() > 0)
								{
									owner.removeGlory(300);
									owner.sendMessageToMembers(Message.LOST_POINTS(300));
								}
							}							
						}
					}				
				}
				
				
				/*if (b.getType() == Material.BEACON)
				{
					if (e.getEntity() instanceof TNTPrimed)
					{
						TNTPrimed test = (TNTPrimed)e.getEntity();
						if (test.getSource() instanceof Player)
						{
							Player p = (Player) test.getSource();

							Location blockLoc = b.getLocation();
							for (Kingdom owner : HumineKingdom.getKingdomManager().getKingdomList())
							{				
								ShieldGenerator shield = owner.getShieldGenerator(blockLoc);
								if (shield != null)
								{	
									if (!owner.equals(kingdom))
									{
										if (owner.getGlory() > 0)
										{
											owner.removeGlory(350);
											owner.sendMessageToMembers(Message.LOST_POINTS(350));
										}
										owner.sendMessageToMembers(Message.SHIELD_DESTROY);
										kingdom.addGlory(300);
										kingdom.sendMessageToMembers(Message.WIN_POINTS(300));
									}
									else
									{
										if (owner.getGlory() > 0)
										{
											owner.removeGlory(300);
											owner.sendMessageToMembers(Message.LOST_POINTS(300));
										}
										owner.sendMessageToMembers(Message.SHIELD_DESTROY);
									}
									kingdom.removeShield(shield);
									return;
								}
							}
						}
					}
				}*/
			}
		}
		
	}
}
