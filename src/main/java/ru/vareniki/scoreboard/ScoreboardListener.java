
package ru.vareniki.scoreboard;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ScoreboardListener implements Listener {

    private final VarenikiScoreboard plugin;

    public ScoreboardListener(VarenikiScoreboard plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        plugin.getServer().getScheduler().runTask(
            plugin,
            () -> plugin.updateBoard(event.getPlayer())
        );
    }
}
