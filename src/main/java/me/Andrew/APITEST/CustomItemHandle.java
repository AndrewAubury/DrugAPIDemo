package me.Andrew.APITEST;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.server.TabCompleteEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.Andrew.DrugAPI.Drug;
import me.Andrew.DrugAPI.DrugAPI;

public class CustomItemHandle implements Listener {
	Main MA;
	DrugAPI drugapi;

	public CustomItemHandle() {
		MA = Main.getInstance();
		drugapi = MA.drugapi;
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onItemCraft(PrepareItemCraftEvent e) {
		ItemStack item = e.getInventory().getResult();
		int id = item.getTypeId();
		Drug drug = drugapi.getDrugFromID(id);
		if (drug != null) {
			if (!drug.isCanCraft()) {
				e.getInventory().setResult(new ItemStack(Material.AIR));
				e.getView().getPlayer().sendMessage(MA.cc("&cYou can not craft:" + drug.getDisplayName()));
				e.getView().getPlayer().sendMessage(MA.cc("&cYou may have to use a drug tool"));
			}

			ItemMeta im = item.getItemMeta();
			im.setDisplayName(drug.getDisplayName());
			item.setItemMeta(im);
		}
	}

	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		if (e.getView().getTopInventory().getName() == "Drug List") {
			e.setCancelled(true);
			if (e.getCurrentItem().getItemMeta().getLore().contains("Click to spawn in item")) {
				@SuppressWarnings("deprecation")
				Drug drug = drugapi.getDrugFromID(e.getCurrentItem().getTypeId());
				if (drug != null) {
					Player p = (Player) e.getView().getPlayer();
					p.getInventory().addItem(drug.getDrugItem());
					p.sendMessage(MA.cc(drug.getDisplayName() + " &aHas been added to your inventory"));
				}
			}

		}

	}
	@EventHandler
	public void onSpiderClick(PlayerInteractEntityEvent e) {
		if (e.getRightClicked().getType() == EntityType.SPIDER) {
			e.getRightClicked().setPassenger(e.getPlayer());
		}
	}
}
