# ShadowDimension

**University of Melbourne - SWEN20003 Object Oriented Software Development**

## Project Overview

ShadowDimension is a fantasy role-playing game developed in Java as part of the Object Oriented Software Development course (SWEN20003) at the University of Melbourne. The game is a continuation of Project 1, where students are tasked with implementing a two-level game using the Basic Academic Game Engine Library (Bagel).

## Game Story

A dark evil has emerged in your hometown after a group of government scientists accidentally opened a portal to another dimension, the "Over Under." Ruled by the powerful creature Navec and his demon minions, the Over Under poses a significant threat. As Fae, the daughter of one of the captive scientists, you must navigate through dangerous environments, avoid sinkholes, and defeat the evil forces to save your father and the world.

## Game Levels

- **Level 0: The Lab**
  - Navigate through the lab avoiding sinkholes.
  - Reach the gate located in the bottom right corner to proceed to the next level.
  - Falling into a sinkhole will reduce the player's health.
  
- **Level 1: The Over Under**
  - Defeat Navec and his demons while avoiding sinkholes and trees.
  - The player can attack enemies, and both the player and enemies have health points.
  - Adjust the game difficulty by changing the timescale, which affects the speed of enemies.

## Key Features

- **Two Levels:** Distinct environments with unique challenges.
- **Health and Damage System:** Both player and enemies have health points and can inflict damage.
- **Player States:** Idle, Attack, and Invincible states determine the player's interactions.
- **Enemy Behavior:** Includes passive and aggressive demons, each with unique movement and attack patterns.
- **Timescale Controls:** Change the difficulty dynamically by adjusting enemy speeds.
- **Collision Detection:** Prevent players and enemies from moving through certain objects and detect overlaps.
- **Game Over and Win Conditions:** Implement checks to determine when the game is won or lost.

## Development Tools

- **Programming Language:** Java
- **Game Engine:** Bagel (Basic Academic Game Engine Library)
- **Recommended IDE:** IntelliJ IDEA

## Installation and Setup

1. **Clone the repository:**
   ```bash
   git clone https://gitlab.com/<username>/project-2.git
   ```
2. **Open the project in IntelliJ IDEA:**
   - Ensure the `pom.xml` file is loaded to manage dependencies.

3. **Build and Run:**
   - Use IntelliJ to build the project and run the `ShadowDimension` class to start the game.

## Usage

- **Starting the Game:** Press the space bar to begin the game in Level 0.
- **Controls:**
  - Arrow keys to move Fae.
  - 'A' key to attack in Level 1.
  - 'L' key to increase timescale (difficulty).
  - 'K' key to decrease timescale (difficulty).
- **Game Objectives:**
  - Avoid hazards, defeat enemies, and reach the gate to complete each level.

## Project Submission

- **Project 2A:** Submit a UML diagram as a PDF on Canvas showing your planned class design.
- **Project 2B:** Submit the complete implementation via GitLab with a minimum of 5 meaningful commits.

## Customization

Students are encouraged to add custom features or modifications to enhance the game. Customization does not affect grading but could be considered for a competition.

## Documentation

Refer to the full [Bagel documentation](https://people.eng.unimelb.edu.au/mcmurtrye/bagel-doc/) for detailed instructions on using the game engine.

## License

This project is licensed under the University of Melbourne's academic policy. Please refer to the university's policy on academic integrity for more details.

