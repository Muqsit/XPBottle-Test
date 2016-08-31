package XPBottle;

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