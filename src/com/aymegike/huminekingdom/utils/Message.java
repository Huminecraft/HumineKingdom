package com.aymegike.huminekingdom.utils;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.aymegike.huminekingdom.HumineKingdom;
import com.aymegike.huminekingdom.utils.models.Grade;
import com.aymegike.huminekingdom.utils.models.Kingdom;
import com.aymegike.huminekingdom.utils.models.Shematic;

public class Message {
	
	final public static String PERMISSION = ChatColor.RED+"Désolé l'ami mais tu n'en as pas la permission. \n(Demande de l'aide à un adulte)";
	final public static String PROBLEM = ChatColor.RED+"Aïe. Il y a comme un problème.";
	
	final public static String SHIELD_DESTROY = ChatColor.RED+"Un bouclier a été détruit...";
	final public static String SHIELD_PLACE = ChatColor.BLUE+"Bravo à toi ! Tu viens de placer un générateur de bouclier. ";
	final public static String SHIELD_GIVE_NAME = ChatColor.GREEN+"Bien maintenant il va falloir lui donner un petit nom !";
	final public static String SHIELD_NOT_REGISTER = ChatColor.RED+"Attention, cette balise n'est pas reconnue en temps que générateur de bouclier...";
	final public static String SHIELD_IS_NOT_A_BEACON = ChatColor.RED+"Tu dois sélectionner un générateur de bouclier...";
	final public static String SHIELD_DESACTIVATE = ChatColor.RED+"Un générateur de bouclier a été désactivé.";
	final public static String SHIELD_REACTIVATE = ChatColor.GREEN+"Un générateur de bouclier a été réactivé.";
	final public static String SHIELD_VOID_EXPLAIN = ChatColor.DARK_PURPLE+"Ce générateur de bouclier est désactivé. Pour pouvoir le réactiver tu dois luis donner une "+ChatColor.WHITE+"étoile du nether"+ChatColor.DARK_PURPLE+"... Normalement il devrait récupérer de l'énergie !";
	
	final public static String KINGDOM_NAME_CANCEL(String name) {return ChatColor.RED+name+" est un nom super cool ! Du coup il est déja utilisé...";}
	final public static String KINGDOM_VALIDE_CONSTRUCT = ChatColor.DARK_PURPLE+"Construction du futur empire en cours...";
	final public static String KINGDOM_INVALIDE_NAME = ChatColor.RED+"Tu ne peux pas avoir d'espace dans le nom de ton royaume. Trouve autre chose !";
	final public static String KINGDOM_INVATION_NAME(String name) {return ChatColor.DARK_PURPLE+name+" a reçu l'invitation !";}
	final public static String KINGDOM_INVATION_ARLY_INVITED(String name) {return ChatColor.DARK_PURPLE+"Dommage, "+name+" fait déja partie d'un royaume !";}
	final public static String KINGDOM_INVITATION_NAME_DONT_CONNECT(String name) {return ChatColor.RED+name+" n'est pas connecté... Tu es sûr d'avoir bien écrit le nom de ton ami ?";}
	final public static String KINGDOM_CREATE = ChatColor.GREEN+"Chouette ! Tout d'abord il faut donner un nom à ton royaume ! "+ChatColor.DARK_PURPLE+"(Tu ne pourras pas le modifier)";
	final public static String KINGDOM_CANCEL = ChatColor.DARK_PURPLE+"Dommage comme vous voulez...";
	final public static String KINGDOM_WELCOM(Player sender, Player receiver) { return ChatColor.GREEN+"Bienvenue dans le royaume "+ChatColor.WHITE+HumineKingdom.getKingdomManager().getPlayerKingdom(sender).getName()+ChatColor.GREEN+" "+receiver.getName()+" !";}
	final public static String KINGDOM_INVITATION_REFUSE_RECEIVER = ChatColor.RED+"Vraiment ? Bon... tant pis pour eux !";
	final public static String KINGDOM_INVITATION_REFUSE_SENDER(Player receiver) { return ChatColor.RED+"Finalement "+receiver.getName()+" n'as pas voulu nous rejoindre... zut alors ! "; }
	final public static String KINGDOM_MUNITY(Player player) { return ChatColor.RED+"Tentative de mutinerie de la part de "+ChatColor.WHITE+player.getName()+ChatColor.RED+" déjouée !"; }
	final public static String KINGDOM_LEFT(OfflinePlayer player, Kingdom kingdom) { return ChatColor.RED+player.getName()+" a quitté "+ChatColor.WHITE+kingdom.getName()+ChatColor.RED+" pour de bon."; }
	final public static String KINGDOM_DELET(Kingdom kingdom, Grade grade, Player player) { return kingdom.getName()+ChatColor.RED+" n'est plus. Le "+grade.getName()+" "+ChatColor.WHITE+player.getName()+ChatColor.RED+" y a mis fin."; }
	final public static String KINGDOM_INVITATION = ChatColor.GREEN+"Besoin d'une nouvelle recrue ? Ecris le nom du nouveau joueur pour lui envoyer une invitation.";
	
	final public static String ADD_TRAITOR = ChatColor.GREEN+"Un nouveau traître à déclarer ? Écris le nom de ce félon pour l'ajouter à la liste noire.";

	final public static String GRADE_NAME_CANCEL(String name) {	return ChatColor.RED+name+" est un nom super cool ! Du coup il est déja utilisé...";}
	final public static String GRADE_VALIDE_CONSTRUCT = ChatColor.DARK_PURPLE+"Construction du grade en cours...";
	final public static String GRADE_INVALIDE_NAME = ChatColor.RED+"Tu ne peux pas avoir d'espace dans le nom de ton grade. Trouve autre chose !";
	final public static String GRADE_CAN_CHANGE_BY_YOUR_SELF = ChatColor.RED+"Tu ne peux pas modifier ton propre grade.";
	final public static String GRADE_UP(OfflinePlayer target, Grade grade) { return target.getName()+ChatColor.GREEN+" a été promu "+grade.getName(); }
	final public static String GRADE_UP_TARGET(Grade grade) { return ChatColor.GREEN+"Félicitations, tu a été promu "+grade.getName()+" !"; }
	final public static String GRADE_GIVE_NAME = ChatColor.DARK_PURPLE+"Tout d'abord, il faut donner un nom à ce nouveau grade.";
	final public static String GRADE_AUTO_DELET = ChatColor.RED+"Désolé l'ami, mais tu ne peux pas modifier ton propre grade.";
	final public static String GRADE_DELET(Grade grade) { return ChatColor.RED+"Le grade "+ChatColor.WHITE+grade.getName()+ChatColor.RED+" a été supprimé"; }
	
	final public static String SHEMATIC_NAME_CANCEL(String name) { return ChatColor.RED+name+" est un nom super cool ! Du coup il est déjà utilisé...";}
	final public static String SHEMATIC_VALIDE_CONSTRUCT = ChatColor.DARK_PURPLE+"Construction du plan en cours...";
	final public static String SHEMATIC_INVALIDE_NAME = ChatColor.RED+"Tu ne peux pas avoir d'espace dans le nom de ton plan. Essaie autre chose ! ";
	final public static String SHEMATIC_RECONSTRUCT_DONE = ChatColor.GREEN+"Reconstruction terminée !";
	final public static String SHEMATIC_CREATE = ChatColor.GREEN+"Besoin d'un nouveau plan de construction ? Clique sur un de tes générateurs de bouclier ! "+ChatColor.ITALIC+"(Balise)";
	final public static String SHEMATIC_DELET(Shematic shematic) { return ChatColor.RED+"Tu as supprimé "+shematic.getName(); }
	final public static String SHEMATIC_SCAN = ChatColor.GREEN+"Scan de la zone en cours...";
	final public static String SHEMATIC_SCAN_FINISH = ChatColor.GREEN+"Scan terminé.";
	
	final public static String EGG_HAND_IS_FULL = ChatColor.BLACK+"["+ChatColor.WHITE+"oeuf"+ChatColor.BLACK+"] "+ChatColor.DARK_PURPLE+"Vous ne pouvez pas me récupérer les mains pleines.";
	public static final String EGG_NO_KINGDOM = ChatColor.DARK_PURPLE+"Attention l'ami ! Evite de griller les étapes... Avant de voler l'oeuf il va falloir créer un royaume qui se verra emplie de gloire. Fait: "+ChatColor.WHITE+""+ChatColor.ITALIC+"/kingdom"+ChatColor.RESET+ChatColor.DARK_PURPLE+" pour en créer un !";
	final public static String EGG_NEW_KINGDOM(OfflinePlayer offlinePlayer) { return ChatColor.DARK_PURPLE+"L'oeuf de dragon a trouvé de nouveaux maitres ! Gloire à "+ChatColor.WHITE+HumineKingdom.getKingdomManager().getPlayerKingdom(offlinePlayer).getName()+ChatColor.DARK_PURPLE+" !"; }
	
	final public static String EGG_UPDATE_DATE = ChatColor.WHITE+"Vous gagnez 30 points de gloire";
	public static String PLACE_BEACON(Player player) { return ChatColor.DARK_PURPLE+player.getName() + ChatColor.WHITE+" apporte "+ChatColor.DARK_PURPLE+"300 points de gloire"+ChatColor.WHITE+" à votre royaume. Il vient de placer un générateur de bouclier.";}

	public static String WIN_POINTS(int points) { return "Vous gagnez "+ChatColor.DARK_PURPLE+points+ChatColor.WHITE+" points de gloire.";}
	public static String LOST_POINTS(int points) { return "Vous perdez "+ChatColor.DARK_PURPLE+points+ChatColor.WHITE+" points de gloire.";}
}
