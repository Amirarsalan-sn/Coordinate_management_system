# Coordinate_management_system
Coordinate management system of banks in a two dimension region (In this project, points are interpreted as banks in the real world, you can use this project to model 
your own management system in an n-dimensional space).

This project is developed using Java language, and you can use it to manage banks that are available in a region.
- You can add central banks (with their names, their coordinates, and the list of their branches).
- You can add branch banks related to the central banks (with their names, coordinates, and the name of the central bank which they are related to).
- You can add a neighborhood defined with its name and coordinates (coordinates of a rectangle in a 2 dimension space). Neighborhoods are used for
  searching available banks in that specific region(neighborhoods may overlap).
- You can delete a branch.
- You can find the closest bank to a specified coordinate.
- You can search banks and neighborhoods using their names.

In order to find the closest bank to a specific point, a **KD-tree** is used (in this case a 2D-tree).
For searching banks and neighborhoods by their names, a **Trie-tree** is used.
So this project is implemented mainly using **KD-tree** and **Trie-tree**.
For a full description of the project and its commands see the project_description file. It has been written in Persian language and the English version of Project_
description will be added to this read-me file any time soon.
