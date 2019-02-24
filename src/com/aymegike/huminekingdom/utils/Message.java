package com.aymegike.huminekingdom.utils;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.aymegike.huminekingdom.HumineKingdom;
import com.aymegike.huminekingdom.utils.models.Grade;
import com.aymegike.huminekingdom.utils.models.Kingdom;
import com.aymegike.huminekingdom.utils.models.Shematic;

public class Message {
	
	final public static String PERMISSION = ChatColor.RED+"Désolé l'ami mais tu n'en as pas la permission. \n(Demande le l'aide a un adulte)";
	final public static String PROBLEM = ChatColor.RED+"Il y a comme un problème";
	
	final public static String SHIELD_DESTROY = ChatColor.RED+"Un bouclier a été détruit...";
	final public static String SHIELD_PLACE = ChatColor.BLUE+"Bravo à toi ! Tu viens de placer un générateur de bouclier. ";
	final public static String SHIELD_GIVE_NAME = ChatColor.GREEN+"Bien maintenant il va falloir lui donner un petit nom !";
	final public static String SHIELD_NOT_REGISTER = ChatColor.RED+"C'est bien une balise mais elle n'est pas reconnue en temps que generateur de bouclier...";
	final public static String SHIELD_IS_NOT_A_BEACON = ChatColor.RED+"Tu dois selectioner un generateur de bouclier...";
	final public static String SHIELD_DESACTIVATE = ChatColor.RED+"Un générateur de bouclier a été désactivé.";
	final public static String SHIELD_REACTIVATE = ChatColor.GREEN+"Un générateur de bouclier a été réactivé.";
	final public static String SHIELD_VOID_EXPLAIN = ChatColor.DARK_PURPLE+"Ce générateur de bouclié est désactivé. Pour pouvoir le réactivé tu dois luis donner une "+ChatColor.WHITE+"étoile du nether"+ChatColor.DARK_PURPLE+"... Normalement il devrait récupérer de l'energie !";
	
	final public static String KINGDOM_NAME_CANCEL(String name) {return ChatColor.RED+name+" est un nom super cool ! Du coup il est déja utilisé...";}
	final public static String KINGDOM_VALIDE_CONSTRUCT = ChatColor.DARK_PURPLE+"Construction du futur empire en cours...";
	final public static String KINGDOM_INVALIDE_NAME = ChatColor.RED+"Tu ne peux pas avoir d'espace dans le nom de ton royaume. Un peu de respect pour lui quoi...";
	final public static String KINGDOM_INVATION_NAME(String name) {return ChatColor.DARK_PURPLE+name+" a reçu l'invitation !";}
	final public static String KINGDOM_INVATION_ARLY_INVITED(String name) {return ChatColor.DARK_PURPLE+"C'est bête mais "+name+" fait déja partie d'un royaume !";}
	final public static String KINGDOM_INVITATION_NAME_DONT_CONNECT(String name) {return ChatColor.RED+name+" n'est pas connecté... Tu es sûr d'avoir bien écrit le nom de ton ami ?";}
	final public static String KINGDOM_CREATE = ChatColor.GREEN+"Chouette ! Tout d'abord il faut donner un nom à ton royaume ! "+ChatColor.DARK_PURPLE+"(Tu ne pourras pas le modifier)";
	final public static String KINGDOM_CANCEL = ChatColor.DARK_PURPLE+"Dommage comme vous voulez...";
	final public static String KINGDOM_WELCOM(Player sender, Player receiver) { return ChatColor.GREEN+"Bienvenue dans le royaume "+ChatColor.WHITE+HumineKingdom.getPlayerKingdom(sender).getName()+ChatColor.GREEN+" "+receiver.getName()+" !";}
	final public static String KINGDOM_INVITATION_REFUSE_RECEIVER = ChatColor.RED+"Vraiment ? Bon... tant pis pour eux !";
	final public static String KINGDOM_INVITATION_REFUSE_SENDER(Player receiver) { return ChatColor.RED+"Finalement "+receiver.getName()+" n'as pas voulu nous rejoindre... zut alors ! "; }
	final public static String KINGDOM_MUNITY(Player player) { return ChatColor.RED+"Tentative de mutinerie de la part de "+ChatColor.WHITE+player.getName()+ChatColor.RED+" dejouer !"; }
	final public static String KINGDOM_LEFT(Player player) { return ChatColor.RED+player.getName()+" a quitté "+ChatColor.WHITE+HumineKingdom.getPlayerKingdom(player).getName()+ChatColor.RED+" pour de bon."; }
	final public static String KINGDOM_DELET(Kingdom kingdom, Grade grade, Player player) { return kingdom.getName()+ChatColor.RED+" n'est plus. Le "+grade.getName()+" "+ChatColor.WHITE+player.getName()+ChatColor.RED+" y a mis fin."; }
	final public static String KINGDOM_INVITATION = ChatColor.GREEN+"Besoin d'une nouvelle recrue ? Ecris le nom du nouveau joueur pour lui envoyer une invitation.";
	
	final public static String GRADE_NAME_CANCEL(String name) {	return ChatColor.RED+name+" est un nom super cool ! Du coup il est déja utilisé...";}
	final public static String GRADE_VALIDE_CONSTRUCT = ChatColor.DARK_PURPLE+"Construction du grade en cours...";
	final public static String GRADE_INVALIDE_NAME = ChatColor.RED+"Tu ne peut pas avoir d'espace dans le nom de ton grade. Un peut de respect pour lui quoi...";
	final public static String GRADE_CAN_CHANGE_BY_YOUR_SELF = ChatColor.RED+"Tu ne peux pas modifier ton propre grade.";
	final public static String GRADE_UP(OfflinePlayer target, Grade grade) { return target.getName()+ChatColor.GREEN+" a était promu "+grade.getName(); }
	final public static String GRADE_UP_TARGET(Grade grade) { return ChatColor.GREEN+"Felicitation tu a était promus "+grade.getName()+" !"; }
	final public static String GRADE_GIVE_NAME = ChatColor.DARK_PURPLE+"Tout d'abords il faut donner un nom à ce nouveau grade.";
	final public static String GRADE_AUTO_DELET = ChatColor.RED+"Désolé l'ami mais tu ne peux pas modifier ton propre grade.";
	final public static String GRADE_DELET(Grade grade) { return ChatColor.RED+"Le grade "+ChatColor.WHITE+grade.getName()+ChatColor.RED+" a était supprimé"; }
	
	final public static String SHEMATIC_NAME_CANCEL(String name) { return ChatColor.RED+name+" est un nom super cool ! Du coup il est déjà utilisé...";}
	final public static String SHEMATIC_VALIDE_CONSTRUCT = ChatColor.DARK_PURPLE+"Construction du plans en cours...";
	final public static String SHEMATIC_INVALIDE_NAME = ChatColor.RED+"Tu ne peut pas avoir d'espace dans le nom de ton plans. Un peut de respect pour lui quoi...";
	final public static String SHEMATIC_RECONSTRUCT_DONE = ChatColor.GREEN+"Reconstruction terminée !";
	final public static String SHEMATIC_CREATE = ChatColor.GREEN+"Besoin d'un nouveau plans de construction ? Clique sur un de tes generateurs de boucliers ! "+ChatColor.ITALIC+"(Balise)";
	final public static String SHEMATIC_DELET(Shematic shematic) { return ChatColor.RED+"Tu a supprimé "+shematic.getName(); }
	final public static String SHEMATIC_SCAN = ChatColor.GREEN+"Scanne de la zone en cours...";
	final public static String SHEMATIC_SCAN_FINISH = ChatColor.GREEN+"Scanne terminé.";
	
	final public static String EGG_HAND_IS_FULL = ChatColor.BLACK+"["+ChatColor.WHITE+"oeuf"+ChatColor.BLACK+"] "+ChatColor.DARK_PURPLE+"Vous ne pouvez pas me récupérer les mains pleines.";
	public static final String EGG_NO_KINGDOM = ChatColor.DARK_PURPLE+"Attention l'ami ! Evite de griller les étapes... Avant de voler l'oeuf il va falloir créer un royaume qui se vera emplie de gloire. Fait: "+ChatColor.WHITE+""+ChatColor.ITALIC+"/kingdom"+ChatColor.RESET+ChatColor.DARK_PURPLE+" pour en créer un !";
	final public static String EGG_NEW_KINGDOM(OfflinePlayer offlinePlayer) { return ChatColor.DARK_PURPLE+"L'oeuf de dragon a trouvé de nouveaux maitres ! Gloire a "+ChatColor.WHITE+HumineKingdom.getPlayerKingdom(offlinePlayer).getName()+ChatColor.DARK_PURPLE+" !"; }
	
	
	final public static String EGG_UPDATE_DATE = ChatColor.WHITE+"Vous gagner 30 points de glorie";
	public static String PLACE_BEACON(Player player) { return ChatColor.DARK_PURPLE+player.getName()+" "+ChatColor.WHITE+" apporte "+ChatColor.DARK_PURPLE+"100 points de gloire"+ChatColor.WHITE+" a votre royaume. Il viens de placer un g�n�rateur de boucli�.";}

	public static String WIN_POINTS(int points) { return "Vous gagnez "+ChatColor.DARK_PURPLE+points+ChatColor.WHITE+" points de gloire.";}
	public static String LOST_POINTS(int points) { return "Vous perdez "+ChatColor.DARK_PURPLE+points+ChatColor.WHITE+" points de gloire.";}
}
