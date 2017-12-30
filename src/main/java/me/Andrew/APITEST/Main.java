package me.Andrew.APITEST;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import me.Andrew.DrugAPI.Drug;
import me.Andrew.DrugAPI.DrugAPI;

public class Main extends JavaPlugin{
DrugAPI drugapi;
private static Main ma;

public static Main getInstance() {
	return ma;
}

public void onEnable() {
	ma = this;
	if(getServer().getPluginManager().isPluginEnabled("DrugAPI")){
		drugapi = DrugAPI.getInstance();
	}else{
		getServer().getLogger().severe("This plugin needs the DrugAPI to work. Disabling");
		getServer().getPluginManager().disablePlugin(this);
	}
	getServer().getPluginManager().registerEvents(new CustomItemHandle(), this);
}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("drugs")){
			Player p = (Player) sender;
			new DrugGUI().openDrugList(p);
		}else if(label.equalsIgnoreCase("druginfo")){
			if(args.length == 1){
				Drug drug = drugapi.getDrug(args[0]);
				if(drug == null){
					sender.sendMessage("That is not a drug");
					return false;
				}
				Iterator<Entry<String, Object>> it = drug.getInfo().entrySet().iterator();
			    while (it.hasNext()) {
			        Map.Entry<String, Object> pair = (Map.Entry<String, Object>)it.next();
			        sender.sendMessage(pair.getKey() + " = " + pair.getValue().toString());
			        it.remove(); // avoids a ConcurrentModificationException
			    }
				
			}
		}else if(label.equalsIgnoreCase("getdrug")){
			if(args.length == 1){
				Drug drug = drugapi.getDrug(args[0]);
				if(drug == null){
					sender.sendMessage("That is not a drug");
					return false;
				}
				@SuppressWarnings("deprecation")
				ItemStack is = new ItemStack(drug.getItemID());
				ItemMeta im = is.getItemMeta();
				im.setDisplayName(drug.getDisplayName());
				is.setItemMeta(im);
				Player p = (Player) sender;
				p.getInventory().addItem(is);
				p.sendMessage(cc(drug.getDisplayName()+" &aHas been added to your inventory"));
			}
		}
		return false;
	}
	
	 @Override
     public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
             if (cmd.getName().equalsIgnoreCase("getdrug")||cmd.getName().equalsIgnoreCase("druginfo")) {
                     if (args.length == 1) {
                             return drugapi.getDrugList();
                     }
             }
            
             return null;
     }
	
public String cc(String data){
	return ChatColor.translateAlternateColorCodes('&', data);
}
}
