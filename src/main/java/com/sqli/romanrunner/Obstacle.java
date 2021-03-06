package com.sqli.romanrunner;

import com.sqli.romanrunner.players.Player;

final class Obstacle extends ArrivableByPlayerTrackSlot
{

  @Override
  public char draw()
  {
    return '_';
  }

  @Override
  void arrivedAtByPlayer(Player player)
  {
    player.arrivedAtAnObstacle();
  }

}
