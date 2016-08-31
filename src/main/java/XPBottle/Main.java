package XPBottle;

/*
*
*  _    _ ______ ______                 _       
* \ \  / (_____ (____  \       _   _   | |      
*  \ \/ / _____) )___)  ) ___ | |_| |_ | | ____ 
*   )  ( |  ____/  __  ( / _ \|  _)  _)| |/ _  )
*  / /\ \| |    | |__)  ) |_| | |_| |__| ( (/ / 
* /_/  \_\_|    |______/ \___/ \___)___)_|\____)
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU Lesser General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
*/

import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.event.Listener;
import cn.nukkit.item.Item;
import cn.nukkit.Player;
import cn.nukkit.level.sound.ExperienceOrbSound;

public class Main extends PluginBase implements Listener{

  @Override
  public void onLoad(){
    this.getServer().registerEvents(this, this);
  }
  
  public void calculateExpReduction(Player p, int exp){
    p.setExperience(p.getExperience() - exp);
  }
  
  public void redeemExp(Player player, int exp){
    currentExp = player.getExperience();
    if(currentExp >= exp){
      this.calculateExpReduction(player, exp);
      xpBottle = Item.get(384, exp, 1);
      xpBottle.setCustomName(TextFormat.GREEN + TextFormat.BOLD + "Experience Bottle " + TextFormat.RESET + TextFormat.GRAY + "(Throw)");
      player.getInventory().addItem(xpBottle);
      player.sendMessage(TextFormat.GREEN + TextFormat.BOLD + "XPBottle " + TextFormat.RESET + TextFormat.GREEN + "You have successfully redeemed " + TextFormat.YELLOW + exp + TextFormat.GREEN + ".");
      player.getLevel().addSound(new ExperienceOrbSound(player), [player]);
    }else{
    ` player.sendMessage(TextFormat.RED + TextFormat.BOLD + "XPBottle " + TextFormat.RESET + TextFormat.RED + "You don't have enough experience. Your current experience is " + TextFormat.YELLOW + currentExp);
    }
  }
  
  public void onInteract(PlayerInteractEvent e){
    p = e.getPlayer();
    i = e.getItem();
    if(i.getId() === 384 && i.getDamage() > 0){
      i.setCount(i.getCount() -1);
      p.getInventory().setItem(p.getInventory().getHeldItemSlot(), i);
      p.addExperience($e.getItem().getDamage());
      p.getLevel().addSound(new ExperienceOrbSound(p), [p]);
      e.setCancelled(true);
    }
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
    if(!sender instanceof Player) return;
    switch((cmd.getName().toLowerCase())){
      case "exp":
        sender.sendMessage(TextFormat.GREEN + TextFormat.BOLD + "XPBottle " + TextFormat.RESET + TextFormat.GREEN + "You have " + TextFormat.YELLOW + sender.getExperience() + " XP" + TextFormat.GREEN + " with you right now.");
      break;
      case "xpbottle":
        if(!sender.hasPermission("redeem.exp")) return;
        if(!isset(args[0])) sender.sendMessage(TextFormat.YELLOW + "/xpbottle <amount>\n" + TextFormat.GRAY + "Check your current experience using the command" + TextFormat.YELLOW + "/exp");
        if(isset(args[0])){
          if(is_numeric(args[0])) this.redeemExp(sender, args[0]);
          else sender.sendMessage(TextFormat.RED + TextFormat.BOLD + "XPBottle " + TextFormat.RESET + TextFormat.RED + "You have provided an invalid amount.");
        }
      break;
    }
  }
}
