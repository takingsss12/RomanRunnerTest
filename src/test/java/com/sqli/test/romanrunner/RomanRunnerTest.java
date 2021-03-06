package com.sqli.test.romanrunner;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.sqli.romanrunner.Circenses;
import com.sqli.romanrunner.CircensesBuilder;
import com.sqli.romanrunner.exceptions.ObstacleHitedException;
import com.sqli.romanrunner.players.Charioteer;
import com.sqli.romanrunner.players.Knight;
import com.sqli.romanrunner.players.Player;

public class RomanRunnerTest
{
  @Test
  public void circensesBuilderTest()
  {
    Circenses circenses = new CircensesBuilder().addCoin()
        .addEmptySlot()
        .addCoin()
        .addObstacle()
        .build();

    String expectedDisplay = new StringBuilder().append("|##|\n") // the final line
        .append("|_ |\n") // the obstacle
        .append("|o |\n") // the second coin
        .append("|  |\n") // empty slot
        .append("|o |\n") // the first coin
        .append("|  |") // the start line is empty as no player is registered
        .toString();

    assertEquals(expectedDisplay, circenses.draw());
  }

  @Test
  public void aPlayerCanStartTheGame()
  {
    Circenses circenses = new CircensesBuilder().addCoin()
        .addEmptySlot()
        .addCoin()
        .addObstacle()
        .build();

    Player player = new Charioteer("lucius");
    player.startGame(circenses);

    String expectedDisplay = new StringBuilder().append("|##|\n") // the final line
        .append("|_ |\n") // the obstacle
        .append("|o |\n") // the second coin
        .append("|  |\n") // empty slot
        .append("|o |\n") // the first coin
        .append("|L |") // the Charioteer "Lucius" at the start line
        .toString();

    assertEquals(expectedDisplay, circenses.draw());
  }

  @Test
  public void aPlayerCanMoveForward() throws ObstacleHitedException
  {
    Circenses circenses = new CircensesBuilder().addEmptySlot()
        .build();

    Player player = new Charioteer("tiberius");
    player.startGame(circenses);

    player.forward();

    String expectedDisplay = new StringBuilder().append("|##|\n")
        .append("|T |\n")
        .append("|@ |")
        .toString();
    assertEquals(expectedDisplay, circenses.draw());
  }

  @Test
  public void aPlayerEarns100AtFinishLine() throws ObstacleHitedException
  {
    Circenses circenses = new CircensesBuilder().addEmptySlot()
        .build();

    Player player = new Charioteer("tiberius");
    player.startGame(circenses);

    String expectedDisplay = new StringBuilder().append("|##|\n")
        .append("|  |\n")
        .append("|T |")
        .toString();
    assertEquals(expectedDisplay, circenses.draw());
    assertEquals(0, player.score());

    player.forward();

    assertEquals(0, player.score());

    player.forward();

    expectedDisplay = new StringBuilder().append("|T#|\n")
        .append("|  |\n")
        .append("|@ |")
        .toString();
    assertEquals(expectedDisplay, circenses.draw());
    assertEquals(100, player.score());
  }

  @Test
  public void aPlayerEarns10PerCoin() throws ObstacleHitedException
  {
    Circenses circenses = new CircensesBuilder().addCoin()
        .build();

    Player player = new Charioteer("tiberius");
    player.startGame(circenses);

    String expectedDisplay = new StringBuilder().append("|##|\n")
        .append("|o |\n")
        .append("|T |")
        .toString();
    assertEquals(expectedDisplay, circenses.draw());
    assertEquals(0, player.score());

    player.forward();

    expectedDisplay = new StringBuilder().append("|##|\n")
        .append("|T |\n")
        .append("|@ |")
        .toString();
    assertEquals(expectedDisplay, circenses.draw());
    assertEquals(10, player.score());

    player.forward();

    expectedDisplay = new StringBuilder().append("|T#|\n")
        .append("|x |\n")
        .append("|@ |")
        .toString();
    assertEquals(expectedDisplay, circenses.draw());
    assertEquals(110, player.score());

  }

  @Test
  public void complexCircensesBuilderTest()
  {
    Circenses circenses = new CircensesBuilder().addCoin()
        .addCoin()
        .addEmptySlot()
        .addCoin()
        .addObstacle()
        .right()
        .addCoin()
        .addEmptySlot()
        .addCoin()
        .addObstacle()
        .left()
        .addCoin()
        .addCoin()
        .right()
        .addEmptySlot()
        .addCoin()
        .addCoin()
        .build();

    String expectedDisplay = new StringBuilder().append("|##|\n")
        .append("| o|\n")
        .append("| o|\n")
        .append("|o |\n")
        .append("|o_|\n")
        .append("| o|\n")
        .append("|  |\n")
        .append("|_o|\n")
        .append("|o |\n")
        .append("|  |\n")
        .append("|o |\n")
        .append("|o |\n")
        .append("|  |")
        .toString();
    assertEquals(expectedDisplay, circenses.draw());
  }

  @Test
  public void aPlayerCanMoveRight() throws ObstacleHitedException
  {
    Circenses circenses = new CircensesBuilder().addEmptySlot()
        .build();

    Player player = new Charioteer("tiberius");
    player.startGame(circenses);

    player.right();

    String expectedDisplay = new StringBuilder().append("|##|\n")
        .append("|  |\n")
        .append("|@T|")
        .toString();
    assertEquals(expectedDisplay, circenses.draw());

    player.forward();

    expectedDisplay = new StringBuilder().append("|##|\n")
        .append("| T|\n")
        .append("|@ |")
        .toString();
    assertEquals(expectedDisplay, circenses.draw());
  }

  @Test
  public void aPlayerCanMoveLeft() throws ObstacleHitedException
  {
    Circenses circenses = new CircensesBuilder().addEmptySlot()
        .build();

    Player player = new Charioteer("tiberius");
    player.startGame(circenses);

    player.forward()
        .right()
        .forward();

    String expectedDisplay = new StringBuilder().append("|#T|\n")
        .append("|  |\n")
        .append("|@ |")
        .toString();
    assertEquals(expectedDisplay, circenses.draw());

  }

  @Test
  public void playerHitObstacle() throws ObstacleHitedException
  {
    Circenses circenses = new CircensesBuilder().addCoin()
        .addEmptySlot()
        .right()
        .addCoin()
        .addEmptySlot()
        .left()
        .addCoin()
        .addObstacle()
        .build();
    Player player = new Charioteer("augustus");
    player.startGame(circenses);

    String expectedDisplay = new StringBuilder().append("|##|\n")
        .append("|_ |\n")
        .append("|o |\n")
        .append("| o|\n")
        .append("|o |\n")
        .append("|A |")
        .toString();
    assertEquals(expectedDisplay, circenses.draw());

    player.forward()
        .right()
        .forward()
        .left()
        .forward();

    expectedDisplay = new StringBuilder().append("|##|\n")
        .append("|_ |\n")
        .append("|A |\n")
        .append("| x|\n")
        .append("|x |\n")
        .append("|@ |")
        .toString();

    assertEquals(expectedDisplay, circenses.draw());
    assertEquals(30, player.score());

    player.forward();

    expectedDisplay = new StringBuilder().append("|##|\n")
        .append("|D |\n")
        .append("|x |\n")
        .append("| x|\n")
        .append("|x |\n")
        .append("|@ |")
        .toString();

    assertEquals(expectedDisplay, circenses.draw());
    assertEquals(25, player.score());
  }

  @Test(expected = ObstacleHitedException.class)
  public void playerCantMoveAfterHitingObstacle() throws ObstacleHitedException
  {
    Circenses circenses = new CircensesBuilder().addCoin()
        .addObstacle()
        .build();
    Player player = new Charioteer("augustus");
    player.startGame(circenses);
    player.forward()
        .forward();

    String expectedDisplay = new StringBuilder().append("|##|\n")
        .append("|D |\n")
        .append("|x |\n")
        .append("|@ |")
        .toString();
    assertEquals(expectedDisplay, circenses.draw());

    player.forward();
  }

  @Test
  public void charioteerCanBypassHitObstacle() throws ObstacleHitedException
  {
    Circenses circenses = new CircensesBuilder().addCoin()
        .addEmptySlot()
        .right()
        .addCoin()
        .addEmptySlot()
        .left()
        .addCoin()
        .addObstacle()
        .build();
    Player player = new Knight("heniokhos");
    player.startGame(circenses);

    String expectedDisplay = new StringBuilder().append("|##|\n")
        .append("|_ |\n")
        .append("|o |\n")
        .append("| o|\n")
        .append("|o |\n")
        .append("|H |")
        .toString();

    assertEquals(expectedDisplay, circenses.draw());
    assertEquals(0, player.score());

    player.forward()
        .right()
        .forward()
        .left()
        .forward()
        .forward();

    expectedDisplay = new StringBuilder().append("|##|\n")
        .append("|H |\n")
        .append("|x |\n")
        .append("| x|\n")
        .append("|x |\n")
        .append("|@ |")
        .toString();

    assertEquals(expectedDisplay, circenses.draw());
    assertEquals(50, player.score());

    player.forward();

    expectedDisplay = new StringBuilder().append("|H#|\n")
        .append("|_ |\n")
        .append("|x |\n")
        .append("| x|\n")
        .append("|x |\n")
        .append("|@ |")
        .toString();

    assertEquals(expectedDisplay, circenses.draw());
    assertEquals(150, player.score());
  }
}
