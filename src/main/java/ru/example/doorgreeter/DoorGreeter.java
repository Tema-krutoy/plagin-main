package ru.example.doorgreeter;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Door;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.java.JavaPlugin;

public final class DoorGreeter extends JavaPlugin implements Listener {

    private static final String MESSAGE = "Здрасьте!";

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler(ignoreCancelled = true)
    public void onDoorInteract(PlayerInteractEvent event) {
        // Только ПКМ по блоку
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        // Событие вызывается дважды (основная и вторая рука) — берём только основную,
        // чтобы сообщение отправилось один раз
        if (event.getHand() != EquipmentSlot.HAND) return;

        Player player = event.getPlayer();
        if (!player.isSneaking()) return;

        Block block = event.getClickedBlock();
        if (block == null) return;

        // Только двери, которые открываются рукой (железная не открывается)
        if (!(block.getBlockData() instanceof Door)) return;
        if (block.getType() == Material.IRON_DOOR) return;

        // Если взаимодействие с блоком запрещено (например, защитой региона) — молчим
        if (event.useInteractedBlock() == Event.Result.DENY) return;

        player.chat(MESSAGE);
    }
}
