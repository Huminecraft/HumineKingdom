package com.aymegike.huminekingdom.utils.models;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.aymegike.huminekingdom.HumineKingdom;
import com.aymegike.huminekingdom.utils.Message;
import com.aypi.utils.Timer;
import com.aypi.utils.inter.TimerFinishListener;
import com.aypi.utils.particle.ExternalWaveParticle;

public class Egg {
	
	final public static int ZONE_SIZE_R = 10; 
	
	private Location location;
	private Kingdom kingdom;
	
	private int task;
	private int task2;
	private int task3;
	
	private int timer = 20;
	
	public Egg(Kingdom kingdom, Location location, boolean place) {
		this.location = location;
		this.kingdom = kingdom;
		if (place) 
			spawnEgg();
		else
			particleEgg();
	}
	
	public Location getLocation() {
		return this.location;
	}
	
	public Kingdom getKingdom() {
		return this.kingdom;
	}
	
	private void spawnEgg() {
		registerBlockBefore();
		spawnParticle(Particle.EXPLOSION_HUGE, this.location, 0, 0, 0, 0);
		playSound(Sound.ENTITY_GENERIC_EXPLODE, location);
		structure(location);
		spiral(location);
		for (Player pls : Bukkit.getOnlinePlayers()) {
			pls.sendMessage(Message.EGG_NEW_KINGDOM(kingdom.getMembers().get(0)));
			pls.playSound(pls.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 5, 1);
		}
	}


	public void destroyEgg(Player player) {
		replaceBlocks();
		playSound(Sound.ENTITY_ENDER_DRAGON_GROWL, location);
		for (int x = location.getBlockX() - 1 ; x <= location.getBlockX() + 1 ; x++) {
			for (int z = location.getBlockZ() - 1 ; z <= location.getBlockZ() + 1 ; z++) {
				for (int y = location.getBlockY() ; y <= location.getBlockY() + 1 ; y++) {
					new Location(location.getWorld(), x, y, z).getBlock().setType(Material.AIR);
				}
				
			}
		}

		new ExternalWaveParticle(Particle.CRIT_MAGIC, location, 20).play();
		Bukkit.getScheduler().cancelTask(task3);
		HumineKingdom.getEggManager().removeEgg(player);
	}
	
	private void particleEgg() {
		Bukkit.getScheduler().cancelTask(task3);
		final Location location = new Location(this.location.getWorld(), this.location.getBlockX(), this.location.getY(), this.location.getBlockZ());
		task3 = Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("HumineKingdom"), new Runnable() {
			
			double radius = 5;
			float y = 0;
			
			@Override
			public void run() {
				spawnParticle(Particle.PORTAL, location, 5, 1, 1, 1);
				
				y += 0.1;
				double x = Math.sin((y * radius)+1);
				double z = Math.cos((y * radius)+1);
				spawnParticle(Particle.END_ROD, new Location(location.getWorld(), location.getX()+x+0.5, location.getY() + 1.5, location.getZ() + z+0.5), 0, 0, 0, 0);
				playSound(Sound.BLOCK_BEACON_AMBIENT, location);
			}
			
		}, 5, 5);
		
	}
	
	
	private void registerBlockBefore() {
		
		for (int x = location.getBlockX()-1 ; x<=location.getBlockX()+1 ; x++) {
			for (int z = location.getBlockZ()-1 ; z<=location.getBlockZ()+1 ; z++) {
				Block block = new Location(location.getWorld(), x, location.getY()-1, z).getBlock();
				HumineKingdom.getEggManager().getFileManager().printLine("block: "+block.getType()+" "+block.getWorld().getName()+" "+block.getLocation().getBlockX()+" "+block.getLocation().getBlockY()+" "+block.getLocation().getBlockZ());
				block.setType(Material.BEDROCK);
			}
		}
		
		for (int x = location.getBlockX()-2 ; x <=location.getBlockX()+2 ; x++) {
			for (int z = location.getBlockZ()-2 ; z <=location.getBlockZ()+2 ; z++) {
				for (int y = location.getBlockY() ; y <=location.getBlockY()+7 ; y++) {
					Block block = new Location(location.getWorld(), x, y, z).getBlock();
					if (block.getType() != Material.DRAGON_EGG)
						block.breakNaturally();
				}
			}
		}
		
	}
	
	private void replaceBlocks() {
		for (String line : HumineKingdom.getEggManager().getFileManager().getTextFile()) {
			String[] args = line.split(" ");
			if (args[0].equalsIgnoreCase("block:")) {
				Location loc = new Location(Bukkit.getWorld(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]));
				Material m = Material.valueOf(args[1]);
				loc.getBlock().setType(m);
			}
		}
	}
	
	private void spiral(Location loc){
		
		World world = loc.getWorld();
		
		double radius = 5;
		int i = 1;
		while(new Location(world, loc.getX(), loc.getY()+i, loc.getZ()).getBlock().getType() == Material.AIR && i < 6 && loc.getBlockY()+i < 255){
			i++;
		}
		double maxHeight = i-2;
		playSound(Sound.BLOCK_END_PORTAL_SPAWN, location);
		new Timer(Bukkit.getPluginManager().getPlugin("HumineKingdom"), 2, new TimerFinishListener() {
			
			@Override
			public void execute() {
				playSound(Sound.BLOCK_PORTAL_TRIGGER,location);
			}
			
		}).start();
		
		task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("HumineKingdom"), new Runnable() {
			float y = 0;
			@Override
			public void run() {
				y += 0.05;
					
				double x = Math.sin((y * radius)+1);
				double z = Math.cos((y * radius)+1);
				spawnParticle(Particle.END_ROD, new Location(world, ((float) (loc.getX() + x+0.5)), ((float) (loc.getY() + y)), ((float) (loc.getZ() + z+0.5))), 0, 0, 0, 0);
				spawnParticle(Particle.PORTAL, new Location(world, (float) (loc.getBlockX()+0.5), ((float) (loc.getBlockY()+maxHeight)), ((float) (loc.getBlockZ()+0.5))), 50, 0, 0, 0);
				
				if(y > maxHeight){
					Bukkit.getScheduler().cancelTask(task);
					new Timer(Bukkit.getPluginManager().getPlugin("HumineKingdom"), 2, new TimerFinishListener() {
						
						@Override
						public void execute() {							
							spawnParticle(Particle.EXPLOSION_HUGE, new Location(world, loc.getBlockX(), loc.getY()+maxHeight, loc.getBlockZ()), 0, 0, 0, 0);
							playSound(Sound.ENTITY_ENDER_DRAGON_GROWL, loc);
							world.strikeLightningEffect(new Location(world, loc.getBlockX(), loc.getY()+maxHeight, loc.getBlockZ()));
							playSound(Sound.ENTITY_LIGHTNING_BOLT_IMPACT, loc);
							playSound(Sound.ENTITY_LIGHTNING_BOLT_THUNDER, loc);
							playSound(Sound.ENTITY_WITHER_SPAWN, loc);
							
						}
					}).start();
					
					new Timer(Bukkit.getPluginManager().getPlugin("HumineKingdom"), 3, new TimerFinishListener() {

						@Override
						public void execute() {
							new ExternalWaveParticle(Particle.FIREWORKS_SPARK, location, 50, 0).play();
							new ExternalWaveParticle(Particle.FLAME, location, 20, 0.2).play();
							playSound(Sound.ENTITY_ENDER_DRAGON_HURT, loc);
							for (Entity e : location.getWorld().getNearbyEntities(loc, 5, 5, 5)) {
								e.setVelocity(new Vector(e.getLocation().getX() - location.getX(), e.getLocation().getY() - (location.getY()-1), e.getLocation().getZ() - location.getZ()).multiply(0.2));
							}
						}
						
					}).start();
					
				}
				
			}
			
		}, 1, 0);
	}
	
	private void structure(Location loc) {
		
		int x = loc.getBlockX();
		int y = loc.getBlockY();
		int z = loc.getBlockZ();
		
		int i=1;
		while((new Location(loc.getWorld(), x, y+i, z).getBlock().getType() == Material.AIR &&
				new Location(loc.getWorld(), x+1, y+i, z).getBlock().getType() == Material.AIR &&
				new Location(loc.getWorld(), x+1, y+i, z+1).getBlock().getType() == Material.AIR &&
				new Location(loc.getWorld(), x+1, y+i, z-1).getBlock().getType() == Material.AIR &&
				new Location(loc.getWorld(), x, y+i, z+1).getBlock().getType() == Material.AIR &&
				new Location(loc.getWorld(), x, y+i, z-1).getBlock().getType() == Material.AIR &&
				new Location(loc.getWorld(), x-1, y+i, z+1).getBlock().getType() == Material.AIR &&
				new Location(loc.getWorld(), x-1, y+i, z).getBlock().getType() == Material.AIR &&
				new Location(loc.getWorld(), x-1, y+i, z-1).getBlock().getType() == Material.AIR) && i < 75 && y+i < 100){
			i++;
		}
		final int I = i-3;
		
		task2 = Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("HumineKingdom"), new Runnable(){

			@Override
			public void run() {
				timer--;
				if(timer == 15){
					
					Location l = new Location(loc.getWorld(), x+0.5, y+I, z+0.5);
					Block b = l.getBlock();
					b.setType(Material.GLOWSTONE);
					BlockData bd = b.getBlockData();
					
					
					Bukkit.getWorld(loc.getWorld().getName()).spawnFallingBlock(l, bd);
					l.getBlock().setType(Material.AIR);
				}
				if(timer == 14){
					Location l = new Location(loc.getWorld(), x+1.5, y+I, z+0.5);
					Block b = l.getBlock();
					b.setType(Material.QUARTZ_STAIRS);
					BlockData bd = b.getBlockData();
					
					Directional d = (Directional) bd;
					d.setFacing(BlockFace.WEST);
					
					Bukkit.getWorld(loc.getWorld().getName()).spawnFallingBlock(l, d);
					l.getBlock().setType(Material.AIR);
				}
				if(timer == 13){
					Location l = new Location(loc.getWorld(), x+0.5, y+I, z-0.5);
					Block b = l.getBlock();
					b.setType(Material.QUARTZ_STAIRS);
					BlockData bd = b.getBlockData();
					
					Directional d = (Directional) bd;
					d.setFacing(BlockFace.SOUTH);
					
					Bukkit.getWorld(loc.getWorld().getName()).spawnFallingBlock(l, d);
					l.getBlock().setType(Material.AIR);
				}
				if(timer == 12){
					Location l = new Location(loc.getWorld(), x-0.5, y+I, z+0.5);
					Block b = l.getBlock();
					b.setType(Material.QUARTZ_STAIRS);
					BlockData bd = b.getBlockData();
					
					Directional d = (Directional) bd;
					d.setFacing(BlockFace.EAST);
					
					Bukkit.getWorld(loc.getWorld().getName()).spawnFallingBlock(l, d);
					l.getBlock().setType(Material.AIR);
				}
				if(timer == 11){
					Location l = new Location(loc.getWorld(), x+0.5, y+I, z+1.5);
					Block b = l.getBlock();
					b.setType(Material.QUARTZ_STAIRS);
					BlockData bd = b.getBlockData();
					
					Directional d = (Directional) bd;
					d.setFacing(BlockFace.NORTH);
					
					Bukkit.getWorld(loc.getWorld().getName()).spawnFallingBlock(l, d);
					l.getBlock().setType(Material.AIR);

				}
				
				if (timer == 10) {
					Location l = new Location(loc.getWorld(), x+1.5, y+I, z+1.5);
					Block b = l.getBlock();
					b.setType(Material.COBBLESTONE_WALL);
					BlockData bd = b.getBlockData();
					
					
					Bukkit.getWorld(loc.getWorld().getName()).spawnFallingBlock(l, bd);
					l.getBlock().setType(Material.AIR);
					
					l = new Location(loc.getWorld(), x+1.5, y+I+1.5, z+1.5);
					b = l.getBlock();
					b.setType(Material.END_ROD);
					bd = b.getBlockData();
					
					
					Bukkit.getWorld(loc.getWorld().getName()).spawnFallingBlock(l, bd);
					l.getBlock().setType(Material.AIR);
					playSound(Sound.ENTITY_ENDER_DRAGON_HURT, l);
					l.getWorld().strikeLightningEffect(l);
				}
				
				if (timer == 8) {
					Location l = new Location(loc.getWorld(), x+1.5, y+I, z-0.5);
					Block b = l.getBlock();
					b.setType(Material.COBBLESTONE_WALL);
					BlockData bd = b.getBlockData();
					
					
					Bukkit.getWorld(loc.getWorld().getName()).spawnFallingBlock(l, bd);
					l.getBlock().setType(Material.AIR);
					
					l = new Location(loc.getWorld(), x+1.5, y+I+1.5, z-0.5);
					b = l.getBlock();
					b.setType(Material.END_ROD);
					bd = b.getBlockData();
					
					
					Bukkit.getWorld(loc.getWorld().getName()).spawnFallingBlock(l, bd);
					l.getBlock().setType(Material.AIR);
					playSound(Sound.ENTITY_ENDER_DRAGON_HURT, l);
					l.getWorld().strikeLightningEffect(l);
				}
		
				if (timer == 5 ) {
					Location l = new Location(loc.getWorld(), x-0.5, y+I, z-0.5);
					Block b = l.getBlock();
					b.setType(Material.COBBLESTONE_WALL);
					BlockData bd = b.getBlockData();
					
					
					Bukkit.getWorld(loc.getWorld().getName()).spawnFallingBlock(l, bd);
					l.getBlock().setType(Material.AIR);
					
					l = new Location(loc.getWorld(), x-0.5, y+I+1.5, z-0.5);
					b = l.getBlock();
					b.setType(Material.END_ROD);
					bd = b.getBlockData();
					
					
					Bukkit.getWorld(loc.getWorld().getName()).spawnFallingBlock(l, bd);
					l.getBlock().setType(Material.AIR);
					playSound(Sound.ENTITY_ENDER_DRAGON_HURT, l);
					l.getWorld().strikeLightningEffect(l);
				}
				
				if (timer == 3) {
					Location l = new Location(loc.getWorld(), x-0.5, y+I, z+1.5);
					Block b = l.getBlock();
					b.setType(Material.COBBLESTONE_WALL);
					BlockData bd = b.getBlockData();
					
					
					Bukkit.getWorld(loc.getWorld().getName()).spawnFallingBlock(l, bd);
					l.getBlock().setType(Material.AIR);
					
					l = new Location(loc.getWorld(), x-0.5, y+I+1.5, z+1.5);
					b = l.getBlock();
					b.setType(Material.END_ROD);
					bd = b.getBlockData();
					
					
					Bukkit.getWorld(loc.getWorld().getName()).spawnFallingBlock(l, bd);
					l.getBlock().setType(Material.AIR);
					playSound(Sound.ENTITY_ENDER_DRAGON_HURT, l);
					l.getWorld().strikeLightningEffect(l);
				}
				
				if(timer == 0){
					Bukkit.getScheduler().cancelTask(task2);
					new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY()-1, loc.getBlockZ()).getBlock().setType(Material.BEDROCK);
					timer = 20;
					particleEgg();
					

					Location l = new Location(loc.getWorld(), x+0.5, y+I+1, z+0.5);
					Block b = l.getBlock();
					b.setType(Material.DRAGON_EGG);
					BlockData bd = b.getBlockData();
					
					
					Bukkit.getWorld(loc.getWorld().getName()).spawnFallingBlock(l, bd);
					l.getBlock().setType(Material.AIR);
				}
				if(new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY()-1, loc.getBlockZ()).getBlock().getType() == Material.QUARTZ_BLOCK)
				{
					new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY()-1, loc.getBlockZ()).getBlock().setType(Material.PURPUR_BLOCK);
				}
				else if(new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY()-1, loc.getBlockZ()).getBlock().getType() == Material.PURPUR_BLOCK)
				{
					new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY()-1, loc.getBlockZ()).getBlock().setType(Material.QUARTZ_BLOCK);
				}				
			}
			
		},5, 5);
		
	}
	
	private void spawnParticle(Particle particle, Location location, int count, double offsetX, double offsetY, double offsetZ) {
        location.getWorld().spawnParticle(particle, location, count, offsetX, offsetY, offsetZ, null);
	}
	
	private void playSound(Sound sound, Location location) {
		for (Player pls : Bukkit.getOnlinePlayers()) {
			pls.playSound(location, sound, 5, 1);
		}
	}
	
}




/*
 * 
 * MIZA DES BISOUS <3
 * 
 * 	private void makeBloodParticle(Location loc) {
 *  	DustOptions dustOptions = new DustOptions(Color.RED, (float) 10.0);
 *   	loc.getWorld().spawnParticle(Particle.REDSTONE, loc, 10, 0, 0.5, 0, 2, dustOptions);
 * 	}
 * 
 */



