# LoraxGame

Overview
This project is a single-screen 2D Java game developed for the CSC 130: Data Structures and Algorithm Analysis final project. The game demonstrates mastery of Java data structures, collision detection, animation, and user input handling. It is designed to meet all requirements in the official CSC 130 grading template.

Features
4-Direction Movement:
Control the player character using the W, A, S, and D keys to move up, left, down, and right.

Smooth Animation:
Each direction features 5-frame animation, for a total of 20 unique player sprites.

Visual Room Borders:
The background image includes a visible wall border on all four sides. The player is prevented from leaving the play area by invisible bounding box logic.

Interactive Items:
Two unique items are placed within the room. When the player approaches and faces an item, descriptive text appears at the item’s location.

Collision Detection:
Custom boundingBox objects and an ArrayList collection ensure accurate collision for both room borders and items.

No Scrolling:
The game is a single, non-scrolling level that fits entirely within a 1440x900 window.

Clean Package Structure:
The code is organized into packages: Data, FileIO, gameloop, Graphics, Input, logic, Main, and timer.
