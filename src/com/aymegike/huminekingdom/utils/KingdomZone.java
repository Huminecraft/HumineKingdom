package com.aymegike.huminekingdom.utils;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.Particle.DustOptions;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.aymegike.huminekingdom.HumineKingdom;
import com.aymegike.huminekingdom.utils.models.Kingdom;
import com.aypi.utils.Square;
import com.aypi.utils.Zone;
import com.aypi.utils.inter.ZoneListener;

public class KingdomZone extends Zone
{
	Kingdom m_kingdom;

	public KingdomZone(Square square, World world, Kingdom kingdom)
	{
		super(square, world, getZoneListener(kingdom));
		m_kingdom = kingdom;
	}
	
	private static ZoneListener getZoneListener(Kingdom kingdom)
	{

		return new ZoneListener() {

			private void cancel(Player player, Event e)
			{				
				player.playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 5, 1);
			}
			
			private void accept(Player player, Event e) {}

			@Override
			public void onPlayerTryToPlaceBlock(Player player, Block block, BlockPlaceEvent e) {
				
				System.out.println("TtPB 1");
				if (player.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE))
				{					
					System.out.println("TtPB 2");
					Location ploc = new Location(e.getBlock().getLocation().getWorld(), e.getBlock().getLocation().getBlockX() + 0.5, e.getBlock().getLocation().getBlockY(), e.getBlock().getLocation().getBlockZ()+0.5);
					
					if (HumineKingdom.getKingdomManager().getPlayerKingdom(player) != null && HumineKingdom.getKingdomManager().getPlayerKingdom(player) == kingdom)
					{
						System.out.println("TtPB 3");
						if (HumineKingdom.getKingdomManager().getPlayerGrade(player) != null && HumineKingdom.getKingdomManager().getPlayerGrade(player).containPermission(Permissions.BUILD.getPermission()))
						{
							System.out.println("TtPB 4");
							accept(player, e);
						}
						else if (!BlockList.isInZoneList(e.getBlock().getType()) && e.getPlayer().getGameMode() != GameMode.CREATIVE)
						{
								System.out.println("TtPB 5");
								e.getBlock().breakNaturally();
								DustOptions dustOptions = new DustOptions(Color.BLACK, (float) 10.0);
								e.getBlock().getWorld().spawnParticle(Particle.REDSTONE, ploc, 10, 0, 0.5, 0, 2, dustOptions);
								cancel(player, e);							
						}
					}
					else if (!BlockList.isInZoneList(e.getBlock().getType()) && e.getPlayer().getGameMode() != GameMode.CREATIVE)
					{
							System.out.println("TtPB 6");
							e.getBlock().breakNaturally();
							DustOptions dustOptions = new DustOptions(Color.BLACK, (float) 10.0);
							e.getBlock().getWorld().spawnParticle(Particle.REDSTONE, ploc, 10, 0, 0.5, 0, 2, dustOptions);
							cancel(player, e);
					}
				}				
			}

			@Override
			public void onPlayerTryToRemoveBlock(Player player, Block block, BlockBreakEvent e) {
				if (player.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE))
				{
					Location ploc = new Location(e.getBlock().getLocation().getWorld(), e.getBlock().getLocation().getBlockX() + 0.5, e.getBlock().getLocation().getBlockY(), e.getBlock().getLocation().getBlockZ()+0.5);
					if (HumineKingdom.getKingdomManager().getPlayerKingdom(player) != null && HumineKingdom.getKingdomManager().getPlayerKingdom(player) == kingdom)
					{
						if (HumineKingdom.getKingdomManager().getPlayerGrade(player) != null && HumineKingdom.getKingdomManager().getPlayerGrade(player).containPermission(Permissions.BREAK.getPermission()))
						{
							accept(player, e);
						}
						else if (!BlockList.isInZoneList(e.getBlock().getType()) && e.getPlayer().getGameMode() != GameMode.CREATIVE)
						{
							DustOptions dustOptions = new DustOptions(Color.BLACK, (float) 10.0);
							e.getBlock().getWorld().spawnParticle(Particle.REDSTONE, ploc, 10, 0, 0.5, 0, 2, dustOptions);
							cancel(player, e);
							e.setCancelled(true);
							return;
						}
					}
					else if (!BlockList.isInZoneList(e.getBlock().getType()) && e.getPlayer().getGameMode() != GameMode.CREATIVE)
					{
						DustOptions dustOptions = new DustOptions(Color.BLACK, (float) 10.0);
						e.getBlock().getWorld().spawnParticle(Particle.REDSTONE, ploc, 10, 0, 0.5, 0, 2, dustOptions);
						cancel(player, e);
						e.setCancelled(true);
						return;
					}
					
				}
			}

			@Override
			public void onPlayerTryToInteractEvent(Player player, PlayerInteractEvent e) {
				if (player.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)) {
					
					if (HumineKingdom.getKingdomManager().getPlayerKingdom(player) != null && HumineKingdom.getKingdomManager().getPlayerKingdom(player) == kingdom) {
						if (HumineKingdom.getKingdomManager().getPlayerGrade(player) != null && HumineKingdom.getKingdomManager().getPlayerGrade(player).containPermission(Permissions.BREAK.getPermission())) {
							accept(player, e);
						} else {
							if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
								Location ploc = new Location(e.getClickedBlock().getLocation().getWorld(), e.getClickedBlock().getLocation().getBlockX() + 0.5, e.getClickedBlock().getLocation().getBlockY(), e.getClickedBlock().getLocation().getBlockZ()+0.5);
								if (e.getClickedBlock().getType() == Material.BEACON) {
									e.setCancelled(true);
									DustOptions dustOptions = new DustOptions(Color.BLACK, (float) 10.0);
									e.getClickedBlock().getWorld().spawnParticle(Particle.REDSTONE, ploc, 10, 0, 0.5, 0, 2, dustOptions);
								}
							}
						}
					} else {
						
						if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
							Location ploc = new Location(e.getClickedBlock().getLocation().getWorld(), e.getClickedBlock().getLocation().getBlockX() + 0.5, e.getClickedBlock().getLocation().getBlockY(), e.getClickedBlock().getLocation().getBlockZ()+0.5);
							if (e.getClickedBlock().getType() == Material.BEACON) {
								e.setCancelled(true);
								DustOptions dustOptions = new DustOptions(Color.BLACK, (float) 10.0);
								e.getClickedBlock().getWorld().spawnParticle(Particle.REDSTONE, ploc, 10, 0, 0.5, 0, 2, dustOptions);
							}
						}
			
					}
					
				}

			}

			@Override
			public void onDamage(Entity arg0, EntityDamageEvent arg1) {}

			@Override
			public void onPlayerEnterZone(Player player)
			{
				if (kingdom != null)
				{
					if (kingdom.getTraitors().contains(Bukkit.getOfflinePlayer(player.getUniqueId())))
					{
						player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, Integer.MAX_VALUE, 0), true);
					}
				}
			}

			@Override
			public void onPlayerExitZone(Player player)
			{
				if (kingdom != null)
				{
					if (kingdom.getTraitors().contains(Bukkit.getOfflinePlayer(player.getUniqueId())))
					{
						if (player.hasPotionEffect(PotionEffectType.WEAKNESS))
						{
							player.removePotionEffect(PotionEffectType.WEAKNESS);
						}
					}
				}				
			}

			@Override
			public void onPlayerMoveInZone(Player arg0, PlayerMoveEvent arg1) {}

			@Override
			public void onEntityDeath(Entity arg0, EntityDeathEvent arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onEntityMakesDamages(Entity arg0, EntityDamageByEntityEvent arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onEntitySuffersDamages(Entity arg0, EntityDamageByEntityEvent arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onExplosionPrime(ExplosionPrimeEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPortalCreate(PortalCreateEvent arg0) {
				// TODO Auto-generated method stub
				
			}

		};
		
	}

}
