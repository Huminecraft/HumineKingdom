package com.aymegike.huminekingdom.utils.models;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import com.aymegike.huminekingdom.HumineKingdom;
import com.aymegike.huminekingdom.utils.KingdomZone;
import com.aymegike.huminekingdom.utils.MenuList;
import com.aymegike.huminekingdom.utils.Message;
import com.aymegike.huminekingdom.utils.Permissions;
import com.aymegike.huminekingdom.utils.managers.FileManager;
import com.aypi.Aypi;
import com.aypi.utils.Square;

public class Kingdom {
	
	private File gradeListFile;
	private ArrayList<Grade> grades = new ArrayList<Grade>();
	
	private File membersFile;
	private ArrayList<OfflinePlayer> members = new ArrayList<OfflinePlayer>();
	
	private File oldMembersFile;
	private ArrayList<OfflinePlayer> oldMembers = new ArrayList<OfflinePlayer>();
	
	private File traitorsFile;
	private ArrayList<OfflinePlayer> m_traitors = new ArrayList<OfflinePlayer>();
	
	private File shieldFile;
	private ArrayList<ShieldGenerator> shieldGenerators = new ArrayList<ShieldGenerator>();
	
	private File shemaFile;
	private ArrayList<Shematic> shematics = new ArrayList<Shematic>();
	
	private File KingdomId;
	private String name;
	private int glory;
	private String kingGradeName;
		
	private Date lastUpdate;
	
	public Kingdom(String name) {
		
		System.out.println("Kingdom: "+name);
		
		this.name = name;
		gradeListFile = new File(FileManager.KINGDOM_FILE+"/"+name+"/grades");
		
		if (gradeListFile.exists())
		{
			File[] list = gradeListFile.listFiles();
			for (int i = 0 ; i<list.length ; i++)
			{
				grades.add(new Grade(this, list[i].getName()));
			}
		}		
		
		membersFile = new File(FileManager.KINGDOM_FILE+"/"+name+"/members.craft");
		if (membersFile.exists())
		{
			for (int i = 0 ; i<new com.aypi.manager.FileManager(membersFile).getTextFile().size() ; i++)
			{
				System.out.println(new com.aypi.manager.FileManager(membersFile).getTextFile().get(i) + " -> "+Bukkit.getOfflinePlayer(UUID.fromString(new com.aypi.manager.FileManager(membersFile).getTextFile().get(i))).getName());
				members.add(Bukkit.getOfflinePlayer(UUID.fromString(new com.aypi.manager.FileManager(membersFile).getTextFile().get(i))));
			}
		}	
		
		oldMembersFile = new File(FileManager.KINGDOM_FILE+"/"+name+"/oldMembers.craft");
		if (oldMembersFile.exists())
		{
			for (int i = 0 ; i<new com.aypi.manager.FileManager(oldMembersFile).getTextFile().size() ; i++)
			{
				System.out.println(new com.aypi.manager.FileManager(oldMembersFile).getTextFile().get(i) + " -> "+Bukkit.getOfflinePlayer(UUID.fromString(new com.aypi.manager.FileManager(oldMembersFile).getTextFile().get(i))).getName());
				oldMembers.add(Bukkit.getOfflinePlayer(UUID.fromString(new com.aypi.manager.FileManager(oldMembersFile).getTextFile().get(i))));
			}
		}
		
		traitorsFile = new File(FileManager.KINGDOM_FILE+"/"+name+"/traitors.craft");
		if (traitorsFile.exists())
		{
			for (int i = 0 ; i<new com.aypi.manager.FileManager(traitorsFile).getTextFile().size() ; i++)
			{
				System.out.println(new com.aypi.manager.FileManager(traitorsFile).getTextFile().get(i) + " -> "+Bukkit.getOfflinePlayer(UUID.fromString(new com.aypi.manager.FileManager(traitorsFile).getTextFile().get(i))).getName());
				m_traitors.add(Bukkit.getOfflinePlayer(UUID.fromString(new com.aypi.manager.FileManager(traitorsFile).getTextFile().get(i))));
			}
		}
		
		shieldFile = new File(FileManager.KINGDOM_FILE+"/"+name+"/shield.craft");
		if (shieldFile.exists()) {
			for (int i = 0 ; i<new com.aypi.manager.FileManager(shieldFile).getTextFile().size() ; i++) {
				String[] line = new com.aypi.manager.FileManager(shieldFile).getTextFile().get(i).split(" ");
				shieldGenerators.add(new ShieldGenerator(this, new Location(Bukkit.getWorld(line[0]), Integer.parseInt(line[1]), Integer.parseInt(line[2]), Integer.parseInt(line[3])), new KingdomZone(new Square(new Location(Bukkit.getWorld(line[4]), Integer.parseInt(line[5]), Integer.parseInt(line[6]), Integer.parseInt(line[7])), new Location(Bukkit.getWorld(line[8]), Integer.parseInt(line[9]), Integer.parseInt(line[10]), Integer.parseInt(line[11]))), Bukkit.getWorld(line[4]), this), Boolean.getBoolean(line[12])));
			}
		}
		
		shemaFile = new File(FileManager.KINGDOM_FILE+"/"+name+"/shematics");
		if (shemaFile.exists()) {
			for (int i = 0 ; i< shemaFile.listFiles().length ; i++) {
				String[] line = shemaFile.listFiles()[i].getName().replace(".craft", "").split("_");
				System.out.println(line[0]+" "+line[1]+" "+line[2]+" "+line[3]+" "+line[4]);
				shematics.add(new Shematic(this, line[0], getShieldGenerator(new Location(Bukkit.getWorld(line[1]), Integer.parseInt(line[2]), Integer.parseInt(line[3]), Integer.parseInt(line[4])))));
			}
		}
		
		System.out.println("Members: " + members.size());
		System.out.println("Old Members: " + oldMembers.size());
		System.out.println("Traitors: " + m_traitors.size());
		System.out.println("Grades: " + grades.size());
		System.out.println("ShieldGenerator: " + shieldGenerators.size());
		
		//m_traitors = new ArrayList<OfflinePlayer>();
		
		setUpKingdomId();
		
	}
	
	public Kingdom(String name, OfflinePlayer king, String kingGradeName) {
		
		this.name = name;
		this.kingGradeName = kingGradeName;
		
		
		membersFile = new File(FileManager.KINGDOM_FILE+"/"+name+"/members.craft");
		oldMembersFile = new File(FileManager.KINGDOM_FILE+"/"+name+"/oldMembers.craft");
		traitorsFile = new File(FileManager.KINGDOM_FILE+"/"+name+"/traitors.craft");
		gradeListFile = new File(FileManager.KINGDOM_FILE+"/"+name+"/grades");
		shieldFile = new File(FileManager.KINGDOM_FILE+"/"+name+"/shield.craft");
		shemaFile = new File(FileManager.KINGDOM_FILE+"/"+name+"/shematics");
		
		addMember(king);
		
		Grade kingGrade = new Grade(this, kingGradeName);
		kingGrade.addMember(king);
		
		for (PermData perm : Permissions.getAllPermissions())
			kingGrade.addPermission(perm.getPermission());
		
		addGrade(kingGrade);
		//m_traitors = new ArrayList<OfflinePlayer>();

		lastUpdate = new Date();
		
		setUpKingdomId();
		
		HumineKingdom.getKingdomManager().addKingdom(this);
	}
	
	public void updateDate() {
		lastUpdate = new Date();
		new com.aypi.manager.FileManager(KingdomId).clearFile();
		List<String> list = new ArrayList<String>();
		list.add("name: "+name);
		list.add("glory: "+glory);
		list.add("kingGradeName: "+kingGradeName);
		list.add("update: "+lastUpdate.getMonth()+" "+lastUpdate.getDate());
		new com.aypi.manager.FileManager(KingdomId).printList(list);
	}
	
	public Date getLastUpdate() {
		return lastUpdate;
	}
	
	public void delete() {
		/*for (int i = 0 ; i < grades.size() ; i++) {
			removeGrade(grades.get(i));
			i--;
		}
		for (int i = 0 ; i < members.size() ; i++) {
			removeMember(members.get(i));
			i--;
		}
		for (int i = 0 ; i < oldMembers.size() ; i++) {
			removeOldMember(oldMembers.get(i));
			i--;
		}
		for (int i = 0 ; i < m_traitors.size() ; i++) {
			removeTraitor(m_traitors.get(i));
			i--;
		}
		for (int i = 0 ; i < shieldGenerators.size() ; i++) {
			removeShield(shieldGenerators.get(i));
			i--;
		}*/
		
		removeFile(new File(FileManager.KINGDOM_FILE+"/"+name));
		HumineKingdom.getKingdomManager().removeKingdom(this);
	}
	
	private void setUpKingdomId()
	{
		KingdomId = new File(FileManager.KINGDOM_FILE+"/"+name+"/kingdomId.craft");
		if (!KingdomId.exists())
		{			
			glory = 50;
			
			com.aypi.manager.FileManager kingdomIdManager = new com.aypi.manager.FileManager(KingdomId);
			kingdomIdManager.printLine("name: "+name);
			kingdomIdManager.printLine("glory: "+glory);
			kingdomIdManager.printLine("kingGradeName: "+kingGradeName);
			kingdomIdManager.printLine("update: "+lastUpdate.getMonth()+" "+lastUpdate.getDate());			
		}
		else
		{			
			com.aypi.manager.FileManager kingdomIdManager = new com.aypi.manager.FileManager(KingdomId);
			for (String str : kingdomIdManager.getTextFile()) {
				if (str.contains("glory: ")) {
					glory = Integer.parseInt(str.replace("glory: ", ""));
				}
				if (str.contains("kingGradeName: ")) {
					kingGradeName = str.replace("kingGradeName: ", "");
				}
				if (str.contains("update: ")) {
					lastUpdate = new Date(new Date().getYear(), Integer.parseInt(str.split(" ")[1]), Integer.parseInt(str.split(" ")[2]));
				}
			}			
		}		
	}
	
	public void addGrade(Grade grade) {
		grades.add(grade);
	}
	
	public void removeGrade(Grade grade) {
		grade.delet();
		grades.remove(grade);
	}
	
	public void addMember(OfflinePlayer player) {
		members.add(player);
		updateMemberMenu();
		new com.aypi.manager.FileManager(membersFile).printLine(player.getUniqueId().toString());
		if (player.isOnline())
			MenuList.closePlayerMenu(player.getPlayer());
		
		if (oldMembers.contains(player))
		{
			removeOldMember(player);
		}
		
		//GLORY
		if (members.size() >= 7) {
			if (!new File(FileManager.KINGDOM_FILE+"/"+name+"/members.badge").exists()) {
				try {
					new File(FileManager.KINGDOM_FILE+"/"+name+"/members.badge").createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				addGlory(70);
			}
		}		
	}
	
	public void addOldMember(OfflinePlayer player) {
		if (!oldMembers.contains(player))
		{
			oldMembers.add(player);
		}
		new com.aypi.manager.FileManager(oldMembersFile).printLine(player.getUniqueId().toString());
	}
	
	public void removeMember(OfflinePlayer player) {
		if (HumineKingdom.getKingdomManager().getPlayerGrade(player) != null)
		{
			HumineKingdom.getKingdomManager().getPlayerGrade(player).removeMember(player);
		}
		members.remove(player);
		updateMemberMenu();
		new com.aypi.manager.FileManager(membersFile).removeAllLine(player.getUniqueId().toString());
		addOldMember(player);
	}

	public void removeOldMember(OfflinePlayer player) {
		oldMembers.remove(player);
		
		new com.aypi.manager.FileManager(oldMembersFile).removeAllLine(player.getUniqueId().toString());
	}
	
	public void addTraitor(OfflinePlayer player) {
		m_traitors.add(player);
		updateTraitorMenu();
		new com.aypi.manager.FileManager(traitorsFile).printLine(player.getUniqueId().toString());
	}
	
	public void removeTraitor(OfflinePlayer player)
	{
		m_traitors.remove(player);
		if (player.isOnline())
		{
			Player onlinePlayer = (Player) player;
			if (onlinePlayer.hasPotionEffect(PotionEffectType.WEAKNESS))
			{
				onlinePlayer.removePotionEffect(PotionEffectType.WEAKNESS);
			}
		}
		updateTraitorMenu();
		new com.aypi.manager.FileManager(traitorsFile).removeAllLine(player.getUniqueId().toString());
	}
	
	public void addShield(ShieldGenerator shieldGenerator) {
		shieldGenerators.add(shieldGenerator);
		updateShieldGeneratorMenu();
		new com.aypi.manager.FileManager(shieldFile).printLine(shieldGenerator.getLocation().getWorld().getName()+" "+shieldGenerator.getLocation().getBlockX()+" "+shieldGenerator.getLocation().getBlockY()+" "+shieldGenerator.getLocation().getBlockZ()
				+" "+shieldGenerator.getZone().getSquare().getPos1().getWorld().getName()+" "+shieldGenerator.getZone().getSquare().getPos1().getBlockX()+" "+shieldGenerator.getZone().getSquare().getPos1().getBlockY()+" "+shieldGenerator.getZone().getSquare().getPos1().getBlockZ()
				+" "+shieldGenerator.getZone().getSquare().getPos2().getWorld().getName()+" "+shieldGenerator.getZone().getSquare().getPos2().getBlockX()+" "+shieldGenerator.getZone().getSquare().getPos2().getBlockY()+" "+shieldGenerator.getZone().getSquare().getPos2().getBlockZ()+" "+shieldGenerator.isActive());
		
	}
	
	public void removeShield(ShieldGenerator shieldGenerator) {
		String line = "";
		
		for (String str : new com.aypi.manager.FileManager(shieldFile).getTextFile()) {
			String[] arg = str.split(" ");
			if (arg[0].equalsIgnoreCase(shieldGenerator.getLocation().getWorld().getName())
			&& arg[1].equalsIgnoreCase(shieldGenerator.getLocation().getBlockX()+"")
			&& arg[2].equalsIgnoreCase(shieldGenerator.getLocation().getBlockY()+"")
			&& arg[3].equalsIgnoreCase(shieldGenerator.getLocation().getBlockZ()+"")) {
				line = str;
				Aypi.getZoneManager().removeZone(shieldGenerator.getZone());
			}
		}
		
		new com.aypi.manager.FileManager(shieldFile).removeAllLine(line);
		shieldGenerators.remove(shieldGenerator);
	}
	
	public void breakShield(ShieldGenerator shieldGenerator) {
		shieldGenerator.getLocation().getBlock().setType(Material.BEDROCK);
		
		shieldGenerator.setActive(false);
		updateShield(shieldGenerator);
		shieldGenerator.getLocation().getWorld().createExplosion(shieldGenerator.getLocation(), 4.0f);
		removeGlory(300);
		sendMessageToMembers(Message.LOST_POINTS(300));
		for (OfflinePlayer pls : members) {
			if (pls.isOnline()) {
				pls.getPlayer().sendMessage(Message.SHIELD_DESACTIVATE);
			}
		}
	}
	
	public void regeneShield(ShieldGenerator shieldGenerator) {
		shieldGenerator.getLocation().getBlock().setType(Material.BEACON);
		shieldGenerator.setActive(true);
		updateShield(shieldGenerator);
		for (OfflinePlayer pls : members) {
			if (pls.isOnline()) {
				pls.getPlayer().sendMessage(Message.SHIELD_REACTIVATE);
			}
		}
	}
	
	private void updateShield(ShieldGenerator shieldGenerator) {
		
		String line = "";
		
		for (String str : new com.aypi.manager.FileManager(shieldFile).getTextFile()) {
			String[] arg = str.split(" ");
			if (arg[0].equalsIgnoreCase(shieldGenerator.getLocation().getWorld().getName())
			&& arg[1].equalsIgnoreCase(shieldGenerator.getLocation().getBlockX()+"")
			&& arg[2].equalsIgnoreCase(shieldGenerator.getLocation().getBlockY()+"")
			&& arg[3].equalsIgnoreCase(shieldGenerator.getLocation().getBlockZ()+"")) {
				line = str;
				Aypi.getZoneManager().removeZone(shieldGenerator.getZone());
			}
		}
		
		new com.aypi.manager.FileManager(shieldFile).removeAllLine(line);
		
		new com.aypi.manager.FileManager(shieldFile).printLine(shieldGenerator.getLocation().getWorld().getName()+" "+shieldGenerator.getLocation().getBlockX()+" "+shieldGenerator.getLocation().getBlockY()+" "+shieldGenerator.getLocation().getBlockZ()
				+" "+shieldGenerator.getZone().getSquare().getPos1().getWorld().getName()+" "+shieldGenerator.getZone().getSquare().getPos1().getBlockX()+" "+shieldGenerator.getZone().getSquare().getPos1().getBlockY()+" "+shieldGenerator.getZone().getSquare().getPos1().getBlockZ()
				+" "+shieldGenerator.getZone().getSquare().getPos2().getWorld().getName()+" "+shieldGenerator.getZone().getSquare().getPos2().getBlockX()+" "+shieldGenerator.getZone().getSquare().getPos2().getBlockY()+" "+shieldGenerator.getZone().getSquare().getPos2().getBlockZ()+" "+shieldGenerator.isActive());
		
		Aypi.getZoneManager().addZone(shieldGenerator.getZone());
	}
	
	public void addShematic(Shematic s) {
		shematics.add(s);
	}
	
	public void removeShematic(Shematic s) {
		removeFile(s.getFile());
		shematics.remove(s);
	}
	
	public Shematic getShematic(String name) {
		for (Shematic s : shematics) {
			if (s.getName().equalsIgnoreCase(name)) {
				return s;
			}
		}
		
		return null;
	}
	
	public Grade getGrade(String name) {
		
		for (Grade grade : grades) {
			if (grade.getName().equalsIgnoreCase(name)) {
				return grade;
			}
		}
		
		return null;		
	}
	
	public Grade getPlayerGrade(OfflinePlayer player) {
		
		for (Grade grade : grades) {
			for (OfflinePlayer op : grade.getMembers()) {
				if (op == player) {
					return grade;
				}
			}
		}
		
		return null;
	}

	public boolean isMember(OfflinePlayer op) {
		
		for (OfflinePlayer l : members) {
			if (l.getName().equalsIgnoreCase(op.getName())) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isOldMember(OfflinePlayer op) {
		
		for (OfflinePlayer l : oldMembers) {
			if (l.getUniqueId().equals(op.getUniqueId())) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isTraitor(OfflinePlayer op) {
		
		for (OfflinePlayer l : m_traitors) {
			if (l.getUniqueId().equals(op.getUniqueId())) {
				return true;
			}
		}
		
		return false;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getGlory()
	{
		return glory;
	}
	
	/*public void setGlory(int value) {
		glory = value;
		new com.aypi.manager.FileManager(KingdomId).clearFile();
		List<String> list = new ArrayList<String>();
		list.add("name: "+name);
		list.add("glory: "+glory);
		list.add("kingGradeName: "+kingGradeName);
		list.add("update: "+lastUpdate.getMonth()+" "+lastUpdate.getDate());
		new com.aypi.manager.FileManager(KingdomId).printList(list);
	}*/
	
	public void addGlory(int value)
	{
		glory += value;
		new com.aypi.manager.FileManager(KingdomId).clearFile();
		List<String> list = new ArrayList<String>();
		list.add("name: "+name);
		list.add("glory: "+glory);
		list.add("kingGradeName: "+kingGradeName);
		list.add("update: "+lastUpdate.getMonth()+" "+lastUpdate.getDate());
		new com.aypi.manager.FileManager(KingdomId).printList(list);
	}
	
	public void removeGlory(int value)
	{
		System.out.println("On enlève de la gloire dans la fonction");
		if (glory == 0)
		{
			return;
		}

		System.out.println("ANCIENNE GLOIRE : " + glory);
		glory -= value;
		if (glory < 0)
		{
			glory = 0;
		}
		System.out.println("NOUVELLE GLOIRE : " + glory);
		new com.aypi.manager.FileManager(KingdomId).clearFile();
		List<String> list = new ArrayList<String>();
		list.add("name: "+name);
		list.add("glory: "+glory);
		list.add("kingGradeName: "+kingGradeName);
		list.add("update: "+lastUpdate.getMonth()+" "+lastUpdate.getDate());
		new com.aypi.manager.FileManager(KingdomId).printList(list);
	}
	
	public ArrayList<OfflinePlayer> getTraitors() {
		return m_traitors;
	}
	
	public ArrayList<OfflinePlayer> getMembers() {
		return members;
	}
	
	public ArrayList<Grade> getGrades() {
		return grades;
	}
	
	public ArrayList<ShieldGenerator> getShieldGenerators() {
		return shieldGenerators;
	}
	
	public ShieldGenerator getShieldGenerator(Location loc) {
		
		for (ShieldGenerator sg : shieldGenerators) {
			if (sg.getLocation().equals(loc)) {
				return sg;
			}
		}
		
		return null;
	}
	
	public String getKingGradeName() {
		return kingGradeName;
	}
	
	public File getShematicFile() {
		return shemaFile;
	}
	
	public ArrayList<Shematic> getShematics() {
		return shematics;
	}
	
	private void updateMemberMenu() {
		for (OfflinePlayer op : members) {
			if(op.isOnline()) {
				if (op.getPlayer().getOpenInventory().getTitle().contains("Membres")) {
					for (int i = 0 ; i < MenuList.user.size() ; i++) {
						if (MenuList.user.get(i).getPlayer() == op.getPlayer()) {
							MenuList.user.get(i).closePlayerMenu();
							MenuList.user.remove(MenuList.user.get(i));
							i--;
						}
					}
					MenuList.membersMenu(op.getPlayer()).openMenu();
				}
			}
		}
	}
	
	private void updateTraitorMenu()
	{
		for (OfflinePlayer op : members)
		{
			if(op.isOnline())
			{
				if (op.getPlayer().getOpenInventory().getTitle().contains("Traîtres"))
				{
					for (int i = 0 ; i < MenuList.user.size() ; i++)
					{
						if (MenuList.user.get(i).getPlayer() == op.getPlayer())
						{
							MenuList.user.get(i).closePlayerMenu();
							MenuList.user.remove(MenuList.user.get(i));
							i--;
						}
					}
					MenuList.traitorsMenu(op.getPlayer()).openMenu();
				}
			}
		}
	}
	
	private void updateShieldGeneratorMenu() {
		for (OfflinePlayer op : members) {
			if(op.isOnline()) {
				if (op.getPlayer().getOpenInventory().getTitle().contains("mes generateurs")) {
					for (int i = 0 ; i < MenuList.user.size() ; i++) {
						if (MenuList.user.get(i).getPlayer() == op.getPlayer()) {
							MenuList.user.get(i).closePlayerMenu();
							MenuList.user.remove(MenuList.user.get(i));
							i--;
						}
					}
					MenuList.GestionMenu(op.getPlayer()).openMenu();
				}
			}
		}
	}
	
	private void removeFile(File file) {
		
		if (file.isDirectory()) {
			File[] list = file.listFiles();
			for (File f : list) {
				removeFile(f);
			}
		}
		
		file.delete();
		
	}
	
	public boolean isMaster() {
		return HumineKingdom.getEggManager().getKingdom() != null && HumineKingdom.getEggManager().getKingdom().getName().equalsIgnoreCase(name);
	}
	
	public OfflinePlayer getKing() {
		for (OfflinePlayer op : members)
		{
			if (HumineKingdom.getKingdomManager() != null && HumineKingdom.getKingdomManager().getPlayerGrade(op) != null && HumineKingdom.getKingdomManager().getPlayerGrade(op).getName().equalsIgnoreCase(kingGradeName)) {
				return op;
			}
		}
		return null;
	}
	
	public void sendMessageToMembers(String message) {
		for (OfflinePlayer m : members) 
			if (m.isOnline()) 
				m.getPlayer().sendMessage(message);
	}
	
	public void playSoundToMembers(Sound sound, int v, int p) {
		for (OfflinePlayer m : members)
			if (m.isOnline())
				m.getPlayer().playSound(m.getPlayer().getLocation(), sound, v, p);
	}
	
	public boolean equals(Kingdom kingdom) {
		return kingdom != null && kingdom.getName().equalsIgnoreCase(name);
	}

}


