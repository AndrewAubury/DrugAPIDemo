package me.Andrew.APITEST;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import me.Andrew.DrugAPI.Drug;
import me.Andrew.DrugAPI.DrugAPI;

public class DrugGUI {
Main MA;
DrugAPI drugapi;	
public DrugGUI(){
	MA = Main.getInstance();
    drugapi = MA.drugapi;
    }
	public void openDrugList(Player p){
		Inventory inv = MA.getServer().createInventory(null, 9, "Drug List");
		for(String drug : drugapi.getDrugList()){
			inv.addItem(makeDrugItem(drugapi.getDrug(drug),p.hasPermission("drugs.spawn")));
		}
		p.openInventory(inv);
	}
	
	private ItemStack makeDrugItem(Drug drug, boolean getFromMenu){
		@SuppressWarnings("deprecation")
		ItemStack is = new ItemStack(drug.getItemID());
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(drug.getDisplayName());
		List<String> lore = new ArrayList<String>();
		lore.add(MA.cc("&cBuy Price: $"+drug.getBuyCost()));
		lore.add(MA.cc("&cSell Price: $"+drug.getSellCost()));
		if(getFromMenu){
			lore.add(MA.cc("Click to spawn in item"));
		}
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}
}
