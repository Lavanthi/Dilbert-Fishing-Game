/**
 * Dilbert Carpet Fishing
 */

//Game constants
final int GRID_SIZE = 5;
final int TOTAL_SQUARES = GRID_SIZE * GRID_SIZE;
final int WIN_SCORE = 10;        // Win at 10 points
final int LOSE_SCORE = -50;
final int SPAWN_DELAY = 3000;    // 3 seconds

//game states
int currentStringSquare = 12;    // Start at square 13 (0-indexed)
int score = 0;
boolean gameActive = false;
int lastSpawnTime = 0;
int lastSpawnedSquare = 0;
String lastSpawnedFish = "";
String statusMessage = "Click on a square to move your fishing string";

// starts the game in the start screen, which is called splash screen
String currentScreen = "SPLASH";

// making the grid
int cellSize = 90;
int gridOffsetX, gridOffsetY;
int[][] gridX, gridY;

//the different fishes and their points
String[] fishNames = {
  "MARGIN",     // +10 points - rare
  "BLUEY",      // +5 points  - uncommon  
  "DD",         // +3 points  - common
  "CLOWNFISH",  // -10 points - common
  "DEG"         // GAME OVER  - very rare
};

int[] fishPoints = {10, 5, 3, -10, 0};
float[] fishRarity = {0.10, 0.20, 0.30, 0.35, 0.05};

//line 42 - 77 is all for aesthetic purposes so it look nice presentable
// Ocean color palette
color oceanDeep = color(10, 35, 55);
color oceanMid = color(25, 75, 95);
color oceanShallow = color(55, 115, 125);
color oceanSurface = color(100, 165, 170);
color oceanFoam = color(210, 235, 235);
color coral = color(255, 127, 111);
color sand = color(245, 235, 200);
color seaweed = color(70, 140, 110);
color sunset = color(255, 180, 110);

// UI Colors
color textLight = color(255, 255, 255, 240);
color textDark = color(20, 45, 55);
color accentGold = color(255, 210, 110);
color accentCoral = color(255, 140, 120);
color accentSeafoam = color(140, 220, 200);

// Fish colors
color[] fishColors = {
  color(67, 170, 139),  // MARGIN - seafoam
  color(64, 145, 188),  // BLUEY - ocean
  color(255, 193, 84),  // DD - gold
  color(255, 121, 97),  // CLOWNFISH - coral
  color(239, 83, 80)    // DEG - red
};

// Fonts
PFont titleFont;
PFont headingFont;
PFont bodyFont;
PFont boldFont;
PFont quoteFont;
PFont monoFont;
PFont displayFont;
PFont smallFont;

final String YOUR_NAME = "Lavanthi Narasimman";

// setting up the screen
void setup() {
  size(1400, 1000);
  pixelDensity(2);
  smooth(8);
  
  // load all fonts
  titleFont = createFont("Arial Bold", 52);
  headingFont = createFont("Arial Bold", 28);
  bodyFont = createFont("Arial", 18);
  boldFont = createFont("Arial Bold", 18);
  quoteFont = createFont("Georgia Italic", 20);
  monoFont = createFont("Courier New", 16);
  displayFont = createFont("Arial Bold", 42);
  smallFont = createFont("Arial", 14);
  
  // getting the center of the grid
  gridOffsetX = width/2 - (GRID_SIZE * (cellSize + 12))/2;
  gridOffsetY = height/2 - (GRID_SIZE * (cellSize + 12))/2 + 50;
  
  // initializing grid positions
  gridX = new int[GRID_SIZE][GRID_SIZE];
  gridY = new int[GRID_SIZE][GRID_SIZE];
  
  for (int row = 0; row < GRID_SIZE; row++) {
    for (int col = 0; col < GRID_SIZE; col++) {
      gridX[row][col] = gridOffsetX + col * (cellSize + 12);
      gridY[row][col] = gridOffsetY + row * (cellSize + 12);
    }
  }
}

void draw() {
  //if currentscreen is supposed to be in  certain gamestate, it takes the person to that game state
  if (currentScreen.equals("SPLASH")) {
    drawSplashScreen();
  } else if (currentScreen.equals("INSTRUCTIONS")) {
    drawInstructionsScreen();
  } else if (currentScreen.equals("GAME")) {
    drawGameScreen();
    if (gameActive) updateGame();
  } else if (currentScreen.equals("WIN")) {
    drawWinScreen();
  } else if (currentScreen.equals("LOSE")) {
    drawLoseScreen();
  } else if (currentScreen.equals("FIN")) {
    drawFinScreen();
  }
}

//spash/start screen
void drawSplashScreen() {
  // ocean gradient background
  for (int i = 0; i <= height; i++) {
    float inter = map(i, 0, height, 0, 1);
    color c = lerpColor(oceanDeep, oceanMid, inter);
    stroke(c);
    line(0, i, width, i);
  }
  
  // main card
  pushMatrix();
  translate(width/2, height/2 - 50);
  
  // card shadow
  fill(0, 0, 0, 30);
  noStroke();
  rectMode(CENTER);
  rect(8, 8 + sin(frameCount * 0.02) * 3, 800, 550, 30);
  
  // main card
  fill(sand);
  stroke(oceanShallow);
  strokeWeight(3);
  rect(0, sin(frameCount * 0.02) * 3, 800, 550, 30);
  
  // header bar
  fill(oceanMid);
  noStroke();
  rect(0, -180, 800, 120, 20);
  
  // title
  fill(oceanFoam);
  textAlign(CENTER, CENTER);
  textFont(titleFont);
  text("DILBERT", 0, -190);
  
  fill(accentGold);
  textFont(headingFont);
  text("Fishing Game", 0, -140);
  
  // writing author name
  fill(accentCoral);
  textFont(quoteFont);
  text("by " + YOUR_NAME, 0, -20);
  
  // making play button
  fill(accentCoral);
  noStroke();
  rect(-180, 150, 280, 70, 40);
  
  // making the player be able to hover and click the play button
  if (mouseX > width/2 - 280 && mouseX < width/2 && 
      mouseY > height/2 + 100 && mouseY < height/2 + 170) {
    fill(accentCoral, 200);
    rect(-180, 150, 280, 70, 40);
  }
  
  fill(textLight);
  textFont(boldFont);
  text("START FISHING", -180, 150);
  
  // instructions button
  fill(oceanShallow);
  rect(180, 150, 280, 70, 40);
  
  // hovering effect for instruction button
  if (mouseX > width/2 + 80 && mouseX < width/2 + 360 && 
      mouseY > height/2 + 100 && mouseY < height/2 + 170) {
    fill(oceanShallow, 200);
    rect(180, 150, 280, 70, 40);
  }
  
  fill(textLight);
  text("HOW TO PLAY", 180, 150);
  popMatrix();
}

// Instructions screen
void drawInstructionsScreen() {
  // Ocean gradient background
  for (int i = 0; i <= height; i++) {
    float inter = map(i, 0, height, 0, 1);
    color c = lerpColor(oceanShallow, oceanFoam, inter);
    stroke(c);
    line(0, i, width, i);
  }
  
  // container for text
  fill(sand);
  noStroke();
  rect(40, 40, width - 80, height - 80, 30);
  
  // header
  fill(oceanDeep);
  textAlign(LEFT);
  textFont(titleFont);
  text("OBJECTIVE", 80, 120);
  
  // Objective box
  fill(oceanMid);
  noStroke();
  rect(80, 140, 700, 100, 15);
  
  fill(oceanFoam);
  textFont(bodyFont);
  text("Catch fish by moving your string to the right square at the right time!", 100, 175);
  textFont(boldFont);
  text("WIN: Score 10 points | LOSE: Score -50 points OR catch the DEG fish", 100, 215);
  
  // Left column - How to play
  int leftX = 80;
  int rightX = 650;
  int yPos = 280;
  
  fill(oceanDeep);
  textFont(headingFont);
  text("HOW TO PLAY", leftX, yPos);
  yPos += 50;
  
  //line 251-334 are for isntructions, to tell the player how to play the game
  // step 1
  fill(oceanFoam, 200);
  noStroke();
  rect(leftX, yPos - 15, 500, 45, 10);
  fill(oceanMid);
  circle(leftX + 25, yPos + 8, 30);
  fill(oceanFoam);
  textFont(boldFont);
  text("1", leftX + 21, yPos + 13);
  fill(oceanDeep);
  textFont(bodyFont);
  text("Click on ANY carpet square to move your fishing string", leftX + 60, yPos + 10);
  yPos += 60;
  
  // step 2
  fill(oceanFoam, 200);
  rect(leftX, yPos - 15, 500, 45, 10);
  fill(oceanMid);
  circle(leftX + 25, yPos + 8, 30);
  fill(oceanFoam);
  text("2", leftX + 21, yPos + 13);
  fill(oceanDeep);
  text("Every 3 seconds, a fish appears on a random square", leftX + 90, yPos + 10);
  yPos += 60;
  
  // step 3
  fill(oceanFoam, 200);
  rect(leftX, yPos - 15, 500, 45, 10);
  fill(oceanMid);
  circle(leftX + 25, yPos + 8, 30);
  fill(oceanFoam);
  text("3", leftX + 21, yPos + 13);
  fill(oceanDeep);
  text("IF your string is on that square → YOU CATCH THE FISH!", leftX + 60, yPos + 10);
  yPos += 60;
  
  // step 4
  fill(oceanFoam, 200);
  rect(leftX, yPos - 15, 500, 45, 10);
  fill(oceanMid);
  circle(leftX + 25, yPos + 8, 30);
  fill(oceanFoam);
  text("4", leftX + 21, yPos + 13);
  fill(oceanDeep);
  text("Keep catching fish to reach 10 points and WIN!", leftX + 60, yPos + 10);
  yPos += 80;
  
  // warning about the deg fish
  fill(accentCoral, 60);
  noStroke();
  rect(leftX, yPos - 15, 500, 100, 15);
  
  fill(accentCoral);
  textFont(boldFont);
  text("DANGER: THE DEG FISH", leftX + 20, yPos + 15);
  
  fill(oceanDeep);
  textFont(bodyFont);
  text("• There is only ONE DEG fish in the entire 5x5 grid", leftX + 40, yPos + 50);
  text("• If you catch it → IMMEDIATE GAME OVER", leftX + 40, yPos + 80);
  
  // fish database
  yPos = 280;
  
  fill(oceanDeep);
  textFont(headingFont);
  text("FISH DATABASE", rightX, yPos);
  yPos += 50;
  
  //all the fish cards
  drawFishCard(rightX, yPos, "MARGIN", "+10", "RARE", "10%", fishColors[0]);
  yPos += 75;
  drawFishCard(rightX, yPos, "BLUEY", "+5", "UNCOMMON", "20%", fishColors[1]);
  yPos += 75;
  drawFishCard(rightX, yPos, "DD", "+3", "COMMON", "30%", fishColors[2]);
  yPos += 75;
  drawFishCard(rightX, yPos, "CLOWNFISH", "-10", "COMMON", "35%", fishColors[3]);
  yPos += 75;
  drawFishCard(rightX, yPos, "DEG", "GAME OVER", "VERY RARE", "5%", fishColors[4]);
  
  // back button
  pushMatrix();
  translate(width/2, height - 80);
  
  fill(oceanMid);
  noStroke();
  rect(0, 0, 240, 60, 30);
  
  //hover effect for back button
  if (mouseX > width/2 - 120 && mouseX < width/2 + 120 && 
      mouseY > height - 110 && mouseY < height - 50) {
    fill(oceanDeep);
    rect(0, 0, 240, 60, 30);
  }
  
  fill(oceanFoam);
  textFont(boldFont);
  text("← BACK TO MENU", 0, 0);
  popMatrix();
}

//drawing fish info
void drawFishCard(int x, int y, String name, String points, String rarity, String chance, color col) {
  // Card background
  fill(oceanFoam, 220);
  noStroke();
  rect(x, y - 15, 400, 60, 10);
  
  // fish icon- the symbol of a fish in this game is a simple cirlce
  fill(col);
  noStroke();
  circle(x + 30, y + 10, 16);
  
  // fish name
  fill(oceanDeep);
  textAlign(LEFT);
  textFont(boldFont);
  text(name, x + 55, y + 8);
  
  // points
  fill(col);
  textFont(boldFont);
  text(points, x + 180, y + 8);
  
  //rarity- so how rare is this fish
  fill(oceanDeep);
  textFont(bodyFont);
  text(rarity, x + 250, y + 8);
  
  //chance
  fill(oceanMid);
  textFont(smallFont);
  text(chance, x + 350, y + 8);
}

//game screen
void drawGameScreen() {
  // aestehtic floor thing
  for (int i = 0; i <= height; i++) {
    float inter = map(i, 0, height, 0, 1);
    color c = lerpColor(oceanDeep, oceanMid, inter);
    stroke(c);
    line(0, i, width, i);
  }
  
  //light rays
  stroke(oceanFoam, 20);
  strokeWeight(3);
  for (int i = 0; i < 5; i++) {
    float x = (frameCount * 0.2 + i * 200) % (width + 400) - 200;
    line(x, 0, x - 100, height);
  }
  
  //header bar
  fill(oceanDeep, 180);
  noStroke();
  rect(0, 0, width, 100);
  
  // Score display
  fill(oceanFoam);
  textFont(bodyFont);
  text("YOUR SCORE", 90, 35);
  
  fill(accentGold);
  textFont(displayFont);
  text(score + " pts", 90, 85);
  
  // current position display
  fill(oceanFoam);
  textFont(bodyFont);
  text("FISHING HERE", 280, 35);
  
  fill(accentCoral);
  textFont(headingFont);
  text("SQUARE " + nf(currentStringSquare + 1, 2), 280, 80);
  
  // win condition reminder
  fill(oceanFoam, 200);
  textFont(smallFont);
  text("WIN at 10 pts | LOSE at -50 pts or DEG", 500, 35);
  
  // quit button
  fill(oceanShallow, 200);
  rect(width - 120, 30, 80, 40, 20);
  fill(oceanFoam);
  textFont(bodyFont);
  text("QUIT", width - 100, 25);
  
  // carpet grid
  drawOceanGrid();
  
  // status message
  fill(oceanFoam);
  textFont(monoFont);
  text(statusMessage, 80, height - 30);
}

// drawing the game grid
void drawOceanGrid() {
  // grid's shadow
  fill(oceanDeep, 50);
  noStroke();
  rect(gridOffsetX - 20, gridOffsetY - 20, 
       GRID_SIZE * (cellSize + 12) + 40, 
       GRID_SIZE * (cellSize + 12) + 40, 20);
  
  for (int row = 0; row < GRID_SIZE; row++) {
    for (int col = 0; col < GRID_SIZE; col++) {
      int square = row * GRID_SIZE + col;
      int x = gridX[row][col];
      int y = gridY[row][col];
      
      // background
      if (square == currentStringSquare) {
        fill(accentGold); // Current position
      } else {
        fill(sand);
      }
      
      stroke(oceanShallow);
      strokeWeight(2);
      rect(x, y, cellSize, cellSize, 12);
      
      // square number
      fill(oceanDeep);
      textAlign(CENTER, CENTER);
      
      if (square == currentStringSquare) {
        textFont(boldFont);
        fill(oceanDeep);
        text(nf(square + 1, 2), x + cellSize/2, y + cellSize/2 - 5);
        textFont(smallFont);
        fill(oceanDeep);
        text("YOUR STRING", x + cellSize/2, y + cellSize/2 + 25);
      } else {
        textFont(bodyFont);
        fill(oceanDeep, 180);
        text(nf(square + 1, 2), x + cellSize/2, y + cellSize/2);
      }
      
      // Bobber animation on active square
      if (square == currentStringSquare && gameActive) {
        fill(accentCoral, 200);
        noStroke();
        circle(x + cellSize/2, y - 10, 12);
        circle(x + cellSize/2, y - 18, 6);
      }
    }
  }
}

// the win screen
void drawWinScreen() {
  for (int i = 0; i <= height; i++) {
    float inter = map(i, 0, height, 0, 1);
    color c = lerpColor(oceanSurface, oceanFoam, inter);
    stroke(c);
    line(0, i, width, i);
  }
  
  // cute confetti bubbles- i worked so hard makign these
  for (int i = 0; i < 50; i++) {
    float x = (frameCount * 0.5 + i * 47) % width;
    float y = (sin(frameCount * 0.02 + i) * 50 + i * 20) % height;
    fill(fishColors[i % 5], 150);
    noStroke();
    circle(x, y, 8);
  }
  
  pushMatrix();
  translate(width/2, height/2 - 50);
  
  // trophy
  fill(accentGold);
  noStroke();
  beginShape();
  vertex(-40, -50);
  vertex(-30, -80);
  vertex(30, -80);
  vertex(40, -50);
  vertex(30, -20);
  vertex(-30, -20);
  endShape(CLOSE);
  
  fill(oceanDeep);
  textFont(titleFont);
  text("VICTORY!", 0, 40);
  
  fill(oceanMid);
  textFont(headingFont);
  text("You reached 10 points!", 0, 110);
  
  fill(oceanDeep);
  textFont(displayFont);
  text(score + " pts", 0, 180);
  
  // play again button
  fill(oceanMid);
  rect(0, 260, 300, 60, 30);
  fill(oceanFoam);
  textFont(boldFont);
  text("PLAY AGAIN", 0, 290);
  
  // FIN button
  fill(oceanShallow);
  rect(0, 340, 300, 60, 30);
  fill(oceanFoam);
  text("F.I.N.", 0, 370);
  
  popMatrix();
}

// lose screen
void drawLoseScreen() {
  for (int i = 0; i <= height; i++) {
    float inter = map(i, 0, height, 0, 1);
    color c = lerpColor(oceanDeep, color(30, 45, 65), inter);
    stroke(c);
    line(0, i, width, i);
  }
  
  pushMatrix();
  translate(width/2, height/2 - 50);
  
  // alert symbol
  fill(accentCoral);
  noStroke();
  circle(0, -40, 70);
  fill(oceanFoam);
  textFont(displayFont);
  text("!", 0, -30);
  
  fill(oceanFoam);
  textFont(titleFont);
  text("GAME OVER", 0, 60);
  
  // loss reason-so the player knows why they lost
  fill(oceanFoam);
  textFont(headingFont);
  
  if (lastSpawnedFish.equals("DEG")) {
    text("YOU CAUGHT THE DEG FISH", 0, 130);
    fill(oceanFoam, 200);
    textFont(bodyFont);
    text("The one deadly fish in the entire sea...", 0, 180);
  } else {
    text("SCORE FELL BELOW -50", 0, 130);
    fill(oceanFoam, 200);
    textFont(bodyFont);
    text("Final Score: " + score + " pts", 0, 180);
  }
  
  // try again button
  fill(oceanMid);
  rect(0, 260, 300, 60, 30);
  fill(oceanFoam);
  textFont(boldFont);
  text("TRY AGAIN", 0, 290);
  
  // FIN button
  fill(oceanShallow);
  rect(0, 340, 300, 60, 30);
  fill(oceanFoam);
  text("F.I.N.", 0, 370);
  
  popMatrix();
}

// FIN screen (Fishing Is Neverending)
void drawFinScreen() {
  for (int i = 0; i <= height; i++) {
    float inter = map(i, 0, height, 0, 1);
    color c = lerpColor(oceanDeep, oceanMid, inter);
    stroke(c);
    line(0, i, width, i);
  }
  
  // fish swimming
  for (int i = 0; i < 10; i++) {
    float x = (frameCount * 0.5 + i * 80) % (width + 100) - 50;
    float y = sin(frameCount * 0.02 + i) * 30 + i * 80 + 200;
    fill(oceanShallow, 100);
    noStroke();
    circle(x, y, 20);
    circle(x - 15, y - 5, 10);
  }
  
  pushMatrix();
  translate(width/2, height/2 - 30);
  
  fill(oceanFoam, 40);
  textFont(titleFont);
  text("FIN", 0, -40);
  
  fill(oceanFoam);
  textFont(headingFont);
  text("Fishing Is Neverending", 0, 60);
  
  fill(oceanFoam, 200);
  textFont(quoteFont);
  text("\"The carpet reef will always be there.\"", 0, 150);
  text("— Dogbert", 0, 190);
  
  // return button
  fill(oceanFoam, 100);
  rect(-150, 280, 300, 60, 30);
  
  // Return button hover effect
  if (mouseX > width/2 - 150 && mouseX < width/2 + 150 && 
      mouseY > height/2 + 250 && mouseY < height/2 + 310) {
    fill(oceanFoam, 150);
    rect(-150, 280, 300, 60, 30);
  }
  
  fill(oceanFoam);
  textFont(boldFont);
  text("RETURN TO CARPET", 0, 310);
  popMatrix();
}

//updating the game
void updateGame() {
  if (millis() - lastSpawnTime > SPAWN_DELAY) {
    spawnRandomFish();
    lastSpawnTime = millis();
  }
}

//to spawn a random fish at a random spot
void spawnRandomFish() {
  int square = int(random(TOTAL_SQUARES));
  int fishIndex = getRandomFishIndex();
  lastSpawnedFish = fishNames[fishIndex];
  lastSpawnedSquare = square;
  
  if (square == currentStringSquare) {
    processCatch(fishIndex);
  } else {
    statusMessage = fishNames[fishIndex] + " appeared at square " + nf(square + 1, 2) + " - you missed!";
  }
}

// gettign the random fish based ont he rarity
int getRandomFishIndex() {
  float rand = random(1);
  float cumulative = 0;
  
  for (int i = 0; i < fishRarity.length; i++) {
    cumulative += fishRarity[i];
    if (rand < cumulative) {
      return i;
    }
  }
  return fishRarity.length - 1;
}

// whiel player is tryign to catch the fish, and writign the msg to show player they caught the fish
void processCatch(int fishIndex) {
  if (fishNames[fishIndex].equals("DEG")) {
    statusMessage = "CATASTROPHE! You caught the legendary DEG fish! GAME OVER!";
    gameActive = false;
    currentScreen = "LOSE";
    return;
  }
  
  score += fishPoints[fishIndex];
  String sign = fishPoints[fishIndex] > 0 ? "+" : "";
  statusMessage = "CAUGHT! " + fishNames[fishIndex] + " (" + sign + fishPoints[fishIndex] + ")";
  
  if (score >= WIN_SCORE) {
    gameActive = false;
    currentScreen = "WIN";
  } else if (score <= LOSE_SCORE) {
    gameActive = false;
    currentScreen = "LOSE";
  }
}

// move the fishing string to a square
void moveString(int square) {
  currentStringSquare = square;
  statusMessage = "String moved to square " + nf(square + 1, 2) + " - waiting for fish...";
}

// reseting the game
void resetGame() {
  score = 0;
  currentStringSquare = 12;
  statusMessage = "Click on a square to move your fishing string";
  gameActive = false;
  lastSpawnedFish = "";
  lastSpawnTime = millis();
}

// mouse movements
void mousePressed() {
  // Splash screen
  if (currentScreen.equals("SPLASH")) {
    // Play button
    if (mouseX > width/2 - 280 && mouseX < width/2 && 
        mouseY > height/2 + 100 && mouseY < height/2 + 170) {
      resetGame();
      currentScreen = "GAME";
      gameActive = true;
    }
    
    // instructions button
    if (mouseX > width/2 + 80 && mouseX < width/2 + 360 && 
        mouseY > height/2 + 100 && mouseY < height/2 + 170) {
      currentScreen = "INSTRUCTIONS";
    }
  }
  
  // instructions screen - back button
  else if (currentScreen.equals("INSTRUCTIONS")) {
    if (mouseX > width/2 - 120 && mouseX < width/2 + 120 && 
        mouseY > height - 110 && mouseY < height - 50) {
      currentScreen = "SPLASH";
    }
  }
  
  // game screen
  else if (currentScreen.equals("GAME")) {
    // quit button
    if (mouseX > width - 120 && mouseX < width - 40 && 
        mouseY > 30 && mouseY < 70) {
      gameActive = false;
      currentScreen = "SPLASH";
    }
    
    // grid clicks - move the string
    if (gameActive) {
      for (int row = 0; row < GRID_SIZE; row++) {
        for (int col = 0; col < GRID_SIZE; col++) {
          int x = gridX[row][col];
          int y = gridY[row][col];
          
          if (mouseX > x && mouseX < x + cellSize && 
              mouseY > y && mouseY < y + cellSize) {
            moveString(row * GRID_SIZE + col);
          }
        }
      }
    }
  }
  
  // win screen
  else if (currentScreen.equals("WIN")) {
    // Play again button
    if (mouseX > width/2 - 150 && mouseX < width/2 + 150 && 
        mouseY > height/2 + 210 && mouseY < height/2 + 270) {
      resetGame();
      currentScreen = "GAME";
      gameActive = true;
    }
    // FIN button
    if (mouseX > width/2 - 150 && mouseX < width/2 + 150 && 
        mouseY > height/2 + 290 && mouseY < height/2 + 350) {
      currentScreen = "FIN";
    }
  }
  
  // lose screen
  else if (currentScreen.equals("LOSE")) {
    // Try again button
    if (mouseX > width/2 - 150 && mouseX < width/2 + 150 && 
        mouseY > height/2 + 210 && mouseY < height/2 + 270) {
      resetGame();
      currentScreen = "GAME";
      gameActive = true;
    }
    // FIN button
    if (mouseX > width/2 - 150 && mouseX < width/2 + 150 && 
        mouseY > height/2 + 290 && mouseY < height/2 + 350) {
      currentScreen = "FIN";
    }
  }
  
  // FIN screen
  else if (currentScreen.equals("FIN")) {
    // return button
    if (mouseX > width/2 - 150 && mouseX < width/2 + 150 && 
        mouseY > height/2 + 250 && mouseY < height/2 + 310) {
      resetGame();
      currentScreen = "GAME";
      gameActive = true;
    }
  }
}

// key press handling
void keyPressed() {
  if (currentScreen.equals("GAME") && key == ESC) {
    gameActive = false;
    currentScreen = "SPLASH";
    key = 0; // Prevent default ESC behavior
  }
}
