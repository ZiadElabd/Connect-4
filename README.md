# Connect-4
# Using Minimax Algorithm to Make Connect 4 AI Agent

## heuristic:-
**Calculate score**  =  total points of computer – total points of players
### For every four neighboring  chips in the same row or  In the same column or Diagonally
- If the four chips are empty or has computer & player chips   do nothing 
- If the four chips are for computer  add 900,000 to the score 
- If the four chips are for player  subtract 900,000 to the score 
- If  three chips are for computer and one is empty  add 300,000 to the score 
- If three chips are for the player and one is empty  subtract 300,000 to the score 
- If  two  chips are for computer and two are empty  add 500 to the score 
- If two  chips are for the player and two are empty   subtract 500 to the score 
- If  one  chip is for the computer and three are empty  add 200 to the score 
- If one chip is for the player and three are empty  subtract 200 to the score
![maxwin](https://user-images.githubusercontent.com/58531158/145482304-a425d63a-0452-4959-9d61-16098ef11ab3.PNG)
![Alphawin](https://user-images.githubusercontent.com/58531158/145482370-bdfe7851-9e2c-46d2-8a49-f3a112af9a89.PNG)


 
