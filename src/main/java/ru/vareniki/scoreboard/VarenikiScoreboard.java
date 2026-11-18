
package ru.vareniki.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class VarenikiScoreboard extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(
            new ScoreboardListener(this), this
        );
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                updateBoard(player);
            }
        }, 0L, 20L);
        getLogger().info("VarenikiScoreboard enabled!");
    }

    public void updateBoard(Player player) {
        Scoreboard board = player.getScoreboard();
        if (board == Bukkit.getScoreboardManager().getMainScoreboard()) {
            board = Bukkit.getScoreboardManager().getNewScoreboard();
        }

        Objective objective = board.getObjective("vareniki");
        if (objective == null) {
            objective = board.registerNewObjective(
                "vareniki", "dummy",
                ChatColor.RED + "V" + ChatColor.WHITE + "arenikiSMP"
            );
        }

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        String[] lines = {
            ChatColor.GRAY + "────────────",
            ChatColor.WHITE + "Ник: " + ChatColor.RED + player.getName(),
            ChatColor.WHITE + "Ранг: " + ChatColor.RED + "Игрок",
            ChatColor.WHITE + "Баланс: " + ChatColor.RED + "0",
            ChatColor.WHITE + "Киллы: " + ChatColor.RED + player.getStatistic(Statistic.PLAYER_KILLS),
            ChatColor.WHITE + "Смерти: " + ChatColor.RED + player.getStatistic(Statistic.DEATHS),
            ChatColor.GRAY + "────────────"
        };

        for (int i = 0; i < lines.length; i++) {
            String entry = ChatColor.values()[i].toString() + ChatColor.RESET;
            Team team = board.getTeam("line" + i);
            if (team == null) team = board.registerNewTeam("line" + i);
            team.addEntry(entry);
            team.setPrefix(lines[i]);
            objective.getScore(entry).setScore(lines.length - i);
        }

        player.setScoreboard(board);
    }
}
