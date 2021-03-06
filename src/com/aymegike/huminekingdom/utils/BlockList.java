package com.aymegike.huminekingdom.utils;

import org.bukkit.Material;

public class BlockList {
	
	private static Material[] blackList = 
		{Material.AIR, 
		Material.VOID_AIR, 
		Material.BEDROCK, 
		Material.WATER,	
		Material.LAVA, 
		Material.CAVE_AIR, 
		
		Material.PISTON_HEAD,
		Material.MOVING_PISTON,
		};
	
	private static Material[] whiteList = {Material.AIR, Material.VOID_AIR, Material.WATER, Material.LAVA, Material.FIRE, Material.CAVE_AIR};
	
	private static Material[] zoneList = {
		Material.TNT,
		Material.TORCH,
		Material.REDSTONE_TORCH,
		Material.WATER,
		Material.LAVA,
		Material.FIRE,
		Material.LEVER,
		Material.STONE_BUTTON,
		Material.ACACIA_BUTTON,
		Material.BIRCH_BUTTON,
		Material.DARK_OAK_BUTTON,
		Material.JUNGLE_BUTTON,
		Material.OAK_BUTTON,
		Material.SPRUCE_BUTTON,
		Material.RAIL
	};
	
	private static Material[] topBeaconList = {
		Material.LAVA,
		Material.WATER,
		Material.ENCHANTING_TABLE
	};
	
	private static Material[] structEgg= {
			Material.GLOWSTONE,
			Material.QUARTZ_STAIRS,
			Material.COBBLESTONE_WALL,
			Material.END_ROD
	};
	
	public static boolean isInStructEgg(Material m) {
		for (Material material : structEgg) {
			if (material == m) {
				return true;
			}
		}
		return false;
	}
			
	public static boolean isInTopBeaconList(Material m) {
		for (Material material : topBeaconList) {
			if (material == m) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isInZoneList(Material m) {
		for (Material material : zoneList) {
			if (material == m) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isInBlackList(Material m) {
		for (Material material : blackList) {
			if (material == m) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isInWhiteList(Material m) {
		for (Material material : whiteList) {
			if (material == m) {
				return true;
			}
		}
		return false;
	}

}
