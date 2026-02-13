/*
TITLE:Footsteps Game
AUTHOR/PROGRAMMER: Lavanthi Narasimman
DATE DUE: 2-9-2026
DATE SUBMITTED: 2-9-2026
COURSE TITLE: Game Design
MEETING TIME(S): 10:30-12:00
DESCRIPTION: Footsteps is a two player binding ame. Players will have 
50 points and the token will be placed in the 4th box out of the 7 boxes aka the middle.
Every turn, both players secretly big points. Who ever bigs a higher number moves the token
one space toward their side. Both players lose the points thy bid. A player wins by getting
the token to thier end of the board. If a player runs out points, the other continues binding,
which basically causes the other person to win the game. If both players run out of points,
then its a draw.
HONOR CODE: Lavanthi Narasimman
HOWTO: saw the files, and then upload into the assighment
INPUT FILE: [N/A]
OUTPUT FILE: [N/A]
BIBLIOGRAPHY: 
Below are a list of websites i used when working on this project:
    (these two i used for the break and switch case commands)
    -https://www.w3schools.com/java/java_break.asp
    -https://www.datacamp.com/doc/java/break
    -https://www.w3schools.com/java/java_switch.asp
(include any websites, books, et cetera, that you referenced to aide
you in completing your work. This would include your textbook.)

RESOURCES: N/A
TUTORS: N/a
COMMENTS: N/A,

REFLECTION: This game took me 8 hours to code. I planned the game to have different game states like
the start screen, where its player A's turn, player B's turn and processing the move and the game over/end
screen. I faced a few problems when working on this probelms. Since my entire game was based on the 
different gameStates, I used switch(gameState) in both the draw() and keyPressed() functions. I didn't
use the break command after each case. This became a problem. Because of this, the program would run
multiple cases at once instead of stopping at the right one. This  would make the game skip screens, 
and run the player B and A state at the same time. 

I researched and looked at examples online( I also asked chatgpt what are breaks and how they work, and
to give me examples), I realized that without the break command, the switch statement just fell to the next
case. I fixed this by adding a break command after every case so that only one game state ran at a time. Once
I did that, the game flow worked properly, and the screen came when they were supposed to. 

Another challenge I had was with the iser input for bidding. I needed to make sure that the users were
only entering numbers. As I tested out the game, I realised the players should also be able to delete inputs
usng backspace(if they ever chnaged their minds), and click enter to submit the inputs. 
Before I worked on this problem, I realized that game would accept letters, and allow empty bids. This caused 
problems cause then how could subtraction work in the game. I also realizsed that I had to make sure the player 
could not add more point than what they had. To fix this, I carefully checked each key press. I added a condition 
to allowed numeric keys (0-9) to be accepted, abckspace would remove the last character of the string and enter 
would only submit the bid if it was not empty and had a number. I wrote a code saying if the bid was higher than
the max number allowed, the program automatically corrected it to the highest valid value. After these changes, the
bidding part worked much better. I also struggled with the win scenario. Cause there were 3 ways the game ended. 
Either player a wins, player b wins, or its tie. This happen if either the token ends up in one side of if one/both
players run out of points. I was not sure how to write all the ending possibilties without the code getting messy.
Then I thought how I made different gamestates, I can make different winnign ends. So I made a variable called winner.
If winner = 2 player B wins, winner =1 player a wins, if winner = 3 then its a draw. I also made a checkWinConditions
so, that i can be able to properly check to see if someone won so I can show the end screen.


 the length of time it took you to complete this assignment. Include any
problems that you encountered and how you overcame them.
*/
