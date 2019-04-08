package com.aymegike.huminekingdom.utils.models;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Container;
import org.bukkit.block.data.Bisected.Half;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Orientable;
import org.bukkit.block.data.Rail;
import org.bukkit.block.data.type.Door;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.block.data.type.Comparator;
import org.bukkit.block.data.type.Comparator.Mode;
import org.bukkit.block.data.type.Repeater;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Slab.Type;
import org.bukkit.block.data.type.Stairs.Shape;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import com.aymegike.huminekingdom.utils.BlockList;
import com.aymegike.huminekingdom.utils.Message;
import com.aypi.manager.FileManager;
import com.aypi.utils.Square;

public class Shematic {
	
	public static final boolean SUCK_INVENTORY = true;
	public static final int SUCK_RAYON = 10;
	
	private String name;
	
	private File file;
	
	private Kingdom kingdom;
	private ShieldGenerator sg;
	private int task;
	private int index = 0;	
	
	private boolean isRebuild = false;
	
	public Shematic(Kingdom kingdom, String name, ShieldGenerator sg) {
		
		this.name = name;
		this.kingdom = kingdom;
		this.sg = sg;
		file = new File(kingdom.getShematicFile().getAbsolutePath()+"/"+name+"_"+sg.getLocation().getWorld().getName()+"_"+sg.getLocation().getBlockX()+"_"+sg.getLocation().getBlockY()+"_"+sg.getLocation().getBlockZ()+".craft");
		
		if (!file.exists()) {
			
			ArrayList<String> print = new ArrayList<String>();
			
			for (Location loc : sg.getZone().getSquare().getScareLoc()) {
				if (!BlockList.isInBlackList(loc.getBlock().getType())) {
					print.add(getLineToPrint(loc));
				}
			}
			
			FileManager fm = new FileManager(file);
			fm.printList(print);
		}
		
	}
	
	public void rebuild() {
		if (!isRebuild) {
			isRebuild = true;
			ArrayList<String> str = new FileManager(file).getTextFile();
			
			task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("HumineKingdom"), new Runnable() {
				
				
				@Override
				public void run() {
					
					String[] line;
					
					do {
						if (index >= str.size()) {
							Bukkit.getScheduler().cancelTask(task);
							index = 0;
							isRebuild = false;
							sendEndMessage();
							return;
						}
						line = str.get(index).split(" ");
						index++;
					} while (!BlockList.isInWhitList(new Location(Bukkit.getWorld(line[0]), Integer.parseInt(line[1]), Integer.parseInt(line[2]), Integer.parseInt(line[3])).getBlock().getType())
							|| new Location(Bukkit.getWorld(line[0]), Integer.parseInt(line[1]), Integer.parseInt(line[2]), Integer.parseInt(line[3])).getBlock().getType() == Material.matchMaterial(line[4]));
					
					
					if (BlockList.isInWhitList(new Location(Bukkit.getWorld(line[0]), Integer.parseInt(line[1]), Integer.parseInt(line[2]), Integer.parseInt(line[3])).getBlock().getType())) {
						
						if (SUCK_INVENTORY) {
							Material toPlace = Material.matchMaterial(line[4]);
							if (!suckInInventory(toPlace)) {
								System.out.println("pas trouvé");
								return;
							}
						} 
						
						placeBlock(line);
						
					}
					
				}
				
			}, 1, 1);
		}
	}
	
	/**
	 * Recupaire un item dans un invetory au alentour du beacon
	 * @param material
	 * @return
	 */
	private boolean suckInInventory(Material material) {
		
		ArrayList<Inventory> invL = getInventorys();
		
		for (Inventory inv : invL) {
			if (inv.contains(material)) {
				inv.getLocation().getWorld().spawnParticle(Particle.VILLAGER_HAPPY, inv.getLocation().add(0.5, 1, 0.5), 0, 0, 0, 0, null);
				ItemStack[] storage = inv.getStorageContents();
				
				for (int i = 0 ; i < storage.length ; i++) {
					ItemStack is = storage[i];
					if (is != null && is.getType() == material) {
						if (is.getAmount() > 1)
							is.setAmount(is.getAmount() - 1);
						else
							storage[i] = null;
					}
				}
				
				inv.setStorageContents(storage);
				
				return true;
			}
		}
		
		
		return false;
	}
	
	private ArrayList<Inventory> getInventorys() {
		ArrayList<Inventory> invL = new ArrayList<Inventory>();
		
		Square s = new Square(new Location(sg.getLocation().getWorld(), sg.getLocation().getBlockX() - SUCK_RAYON, sg.getLocation().getBlockY() - SUCK_RAYON, sg.getLocation().getBlockZ() - SUCK_RAYON), 
				new Location(sg.getLocation().getWorld(), sg.getLocation().getBlockX() + SUCK_RAYON, sg.getLocation().getBlockY() + SUCK_RAYON, sg.getLocation().getBlockZ() + SUCK_RAYON));
		
		for (Location loc : s.getScareLoc()) {
			if (loc.getBlock().getState() instanceof InventoryHolder) {
				invL.add(((Container) loc.getBlock().getState()).getInventory());
			}
		}
		
		return invL;
	}
	
	private void placeBlock(String[] line) {
		new Location(Bukkit.getWorld(line[0]), Integer.parseInt(line[1]), Integer.parseInt(line[2]), Integer.parseInt(line[3])).getBlock().setType(Material.matchMaterial(line[4]));
		
		
		//SET ++
		Block block = new Location(Bukkit.getWorld(line[0]), Integer.parseInt(line[1]), Integer.parseInt(line[2]), Integer.parseInt(line[3])).getBlock();
		Random r = new Random();
		boolean anvil = r.nextInt(100) == 0;
		boolean piston = r.nextInt(100) == 1;
		for (Player pls : Bukkit.getOnlinePlayers()) {
			pls.playSound(block.getLocation(), Sound.BLOCK_STONE_PLACE, 5, 1);
			
			if (anvil) {
				pls.playSound(block.getLocation(), Sound.BLOCK_ANVIL_USE, 5, 1);
			}
			
			if (piston) {
				pls.playSound(block.getLocation(), Sound.BLOCK_PISTON_EXTEND, 5, 1);
			}
		}
		
		if (block.getBlockData() instanceof Directional ) { //CLASSIQUE
			
			Directional dir = (Directional) block.getBlockData();
			
			dir.setFacing(BlockFace.valueOf(line[5]));
			
			block.setBlockData(dir);
			
		}
		
		if (block.getBlockData() instanceof Slab) {
			Slab s = (Slab) block.getBlockData();
			
			s.setType(Type.valueOf(line[5]));
			
			block.setBlockData(s);
		}
		
		if (block.getBlockData() instanceof Stairs) { //STAIRS
			
			Stairs s = (Stairs) block.getBlockData();
			
			s.setHalf(Half.valueOf(line[6]));
			s.setShape(Shape.valueOf(line[7]));
			
			block.setBlockData(s);
			
		} 
		
		if (block.getBlockData() instanceof Repeater) {
			
			Repeater rp = (Repeater) block.getBlockData();
			
			rp.setDelay(Integer.parseInt(line[6]));
			
			block.setBlockData(rp);
			
		}
		
		if (block.getBlockData() instanceof Comparator) {
			
			Comparator c = (Comparator) block.getBlockData();
			
			c.setMode(Mode.valueOf(line[6]));
			
			block.setBlockData(c);
		}
		
		if (block.getBlockData() instanceof Leaves) {
			Leaves l = (Leaves) block.getBlockData();
			l.setPersistent(true);
			block.setBlockData(l);
		}
		
		if (block.getBlockData() instanceof Rail) {
			Rail r1 = (Rail) block.getBlockData();
			r1.setShape(Rail.Shape.valueOf(line[6]));
			
			block.setBlockData(r1);
		}
	}
	
	
	public String getName() {
		return name;
	}
	
	public File getFile() {
		return file;
	}
	
	private void sendEndMessage() {
		for (OfflinePlayer op : this.kingdom.getMembers()) {
			if (op.isOnline()) {
				op.getPlayer().sendMessage(Message.SHEMATIC_RECONSTRUCT_DONE);
				op.getPlayer().playSound(op.getPlayer().getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 5, 1);
			}
		}
	}
	
	private String getLineToPrint(Location loc) {
		
		String type = loc.getBlock().getType().name();
		if (loc.getBlock().getType() == Material.COAL_ORE
		|| loc.getBlock().getType() == Material.DIAMOND_ORE
		|| loc.getBlock().getType() == Material.EMERALD_ORE
		|| loc.getBlock().getType() == Material.REDSTONE_ORE
		|| loc.getBlock().getType() == Material.GOLD_ORE
		|| loc.getBlock().getType() == Material.IRON_ORE
		|| loc.getBlock().getType() == Material.LAPIS_ORE
		|| loc.getBlock().getType() == Material.NETHER_QUARTZ_ORE) {
			type = Material.STONE.name();
		}
		
		String lineToPrint = loc.getWorld().getName()+" "+loc.getBlockX()+" "+loc.getBlockY()+" "+loc.getBlockZ()+" "+type;
		
		if (loc.getBlock().getBlockData() instanceof Directional ) { //DIRECTIONALS
			
			Directional dir = (Directional) loc.getBlock().getBlockData();
			
			lineToPrint +=" "+dir.getFacing().toString();
			
		}
		
		if (loc.getBlock().getBlockData() instanceof Orientable) {
			
		}
		
		if (loc.getBlock().getBlockData() instanceof Stairs) { //STAIRS
			
			Stairs s = (Stairs) loc.getBlock().getBlockData();
			
			lineToPrint +=" "+s.getHalf().toString()+" "+s.getShape().toString();
		}
		
		if (loc.getBlock().getBlockData() instanceof Slab) { //SLAB
			
			Slab s = (Slab) loc.getBlock().getBlockData();
			
			lineToPrint += " "+s.getType().toString();
			
		}
		
		if (loc.getBlock().getBlockData() instanceof Door) { //DOORS
			
			Door d = (Door) loc.getBlock().getBlockData();
			lineToPrint +=" "+d.getHinge().toString()+" "+d.getHalf().toString();
			
		}
		
		if (loc.getBlock().getBlockData() instanceof Repeater) {
			Repeater rp = (Repeater) loc.getBlock().getBlockData();
			
			lineToPrint += " "+rp.getDelay();
		}
		
		if (loc.getBlock().getBlockData() instanceof Comparator) {
			Comparator c = (Comparator) loc.getBlock().getBlockData();
			lineToPrint += " "+c.getMode().toString();
		}
		
		if (loc.getBlock().getBlockData() instanceof Rail) {
			Rail r = (Rail) loc.getBlock().getBlockData();
			lineToPrint += " "+r.getShape();
		}
		
		
		return lineToPrint;
		
	}
	public Kingdom getkingdom() {
		return this.kingdom;
	}

	public void refresh() {
		
		ArrayList<String> print = new ArrayList<String>();
		
		for (Location loc : sg.getZone().getSquare().getScareLoc()) {
			if (!BlockList.isInBlackList(loc.getBlock().getType())) {
				print.add(getLineToPrint(loc));
			}
		}
		
		FileManager fm = new FileManager(file);
		fm.clearFile();
		fm.printList(print);
	}

}