# Intelligent Agents

A simple representation of intelligent agents

## Stage 1 
#### Requirements 
- World : a grid of squares
- Square : contains a single tree (several kinds of tree exist)
- Robot (agent) : in a square / only one in each square at a time (otherwise crash) 
- Time in ticks 
- On each tick, each robot can do nothing, or perform a single action.
- A robot can move ahead, or rotate one quarter turn left or right.   
- A robot can always see the tree in the square it is in, and they can look and see the tree in any adjacent square.  
- Robots can recognise the different sorts of tree. 

## Stage 2 
#### Requirements 
- Robots have a health level (goes down one unit per tick)   
- If the health level reaches zero, the robot dies.  
- A robot can eat a fruit from the tree of the square they are in.  
- The level goes up, or down, or is unaffected, when they eat the fruit of certain kinds of tree - but they do not initially know what the effect of each sort of fruit on them is. 
- After eating each sort of fruit, the robot can notice the change in its health level.   
- At this stage, the robots’ goal is to live as long as possible.
- At each tick, a robot has to work out what to do.  How does it do that at this stage?   

## Stage 3
#### Requirements 
- Robots map as much of the world as possible.
- Know what sort of tree is on which square.  (memory) 
- There is no time signal
- There is no GPS

## Run instructions

Included are 3 branches.
They have been created sequentially, each is a continuation of the previous one, and they are the solutions for stages 1, 2 and 3.

Can either be compiled using the source files in the src folder
or run in a command line using the .class files in the out folder