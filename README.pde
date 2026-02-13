/*
TITLE:DIlbert Fishing Game
AUTHOR/PROGRAMMER: Lavanthi Narasimman
DATE DUE: 2-9-2026
DATE SUBMITTED: 2-12-2026(3 day extension)
COURSE TITLE: Game Design
MEETING TIME(S): 10:30-12:00
DESCRIPTION: 
Dilbert Fishing is a simple catching game where you move your fishing string around a 5x5 grid
to catch fish. You start on square 13 (middle of the grid).Then you click any square to move your
fishing string there. Every 3 seconds, a fish appears on a random square.If your string is on that 
square when the fish appears, YOU CATCH IT! If you miss, the fish gets away.
These are the different fish I came up with , plus their points and how rare they are
MARGIN - +10 points (Rare - 10% chance)
BLUEY - +5 points (Uncommon - 20% chance)
DD - +3 points (Common - 30% chance)
CLOWNFISH - -10 points (Common - 35% chance)
DEG - GAME OVER (Very Rare - 5% chance)
You win if you get 50 points, you lose if you get below -50 points, or if you catch the DEG Fish.
HONOR CODE: Lavanthi Narasimman
HOWTO: saw the files, and then upload into the assighment
INPUT FILE: [N/A]
OUTPUT FILE: [N/A]
BIBLIOGRAPHY: 
Below are all the websites I used to code this game:
  -https://docs.oracle.com/javase/8/docs/api/java/awt/Color.html
  -https://processing.org/reference/mousePressed_.html
  -https://www.geeksforgeeks.org/java/mouselistener-mousemotionlistener-java/
  -https://docs.oracle.com/javase/8/docs/technotes/guides/2d/spec/j2d-fonts.html
  -https://www.geeksforgeeks.org/java/print-2-d-array-matrix-java/
  -https://www.w3schools.com/java/ref_math_sin.asp
  -https://www.geeksforgeeks.org/java/java-8-clock-millis-method-with-examples/
  -https://www.tutorialspoint.com/java/lang/system_currenttimemillis.htm
  -https://www.youtube.com/watch?v=ooh9_l1ZpKk
  -https://www.youtube.com/watch?v=FmEWqS2FP3U
  
(include any websites, books, et cetera, that you referenced to aide
you in completing your work. This would include your textbook.)
RESOURCES: N/A
TUTORS: N/a
COMMENTS: N/A,
REFLECTION: 

This project in my opinion was much ahrder to develop. I took liek 6 days to work on this. 
The reason this ended being a few dyas late is cause I had a bad start witht he processing. 
My computer was taking forever to fully update it, anytime I tried to run it install it, it always 
stopped in the middle out of nowhere not letting it run. When working on this project,
I had a bunch of problems that I had to overcome. Here are some of them. 
Firts was with clicking the buttons.The buttons kept not working. I would click "START FISHING" 
and nothing would happen.I spent hours trying to figure out why. Finally I realized my mouseX/mouseY 
coordinates were off because I was using translate() and pushMatrix() but forgetting that the button 
positions change when you move the whole screen around. I had to do a lot of math to figure 
out the exact pixel positions. Line 215-221 and 230-236 were fixed like five times. Another
problem was with the Fonts Crashing. Originally I was using Montserrat and Open Sans because they
look nice. But Processing couldn't find them on some computers and the whole program would crash. 
I wasted a whole day trying to fix this before just switching to Arial and Georgia. It doesn't look as
fancy but at least it runs.Another problem was with theThe Confetti Bubbles in the end screen. 
Technically this part was not reuired but I really wanted to add this. This was actually fun to make 
but hard to get right. I wanted little bubbles floating up on the win screen. I had to use sin() and 
frameCount and modulo and it took me forever to understand why they weren't moving. I still don't fully 
get the math but it works so I'm not touching it.Another problem was with the Fish Spawning Every 3 
Seconds during the game screen. At first I used frameCount and tried to spawn a fish every 180 frames 
(60fps * 3 seconds). But if the game lagged, the timing was off. Then I switched to millis() which tracks 
actual time. But then I forgot to reset lastSpawnTime when restarting the game, so fish would spawn 
instantly. Fixed that on line 838. A final problem was with the the quit Button Position.On line 499-503 
I have the quit button in the top right corner. I must have adjusted the x position 15 times. It kept 
showing up partially off the screen or too far left. 1400 - 120 = 1280 was the sweet spot.
*/
