# Design for Cell Society

## Introduction
In this program, our group seeks to create a functional cell society simulator that can handle a variety of animations and has the flexibility to handle more being added to the program. Our group seeks to use inheritance for all of the animations, making sure they are all children of the animation parent class and that more animation child classes can be added to handle different animations. Our CellSociety main class will have instances of the other classes, meaning that instances of CellSociety have the ability to access other classes, but they do not have access to each other. The use of this master class will allow for flexibility without the corruption of information between classes. We think that intelligent uses of inheritance and private variables will lead to a well-designed, expandable, and fully functional program. 

## Overview

**Here's a look at the design:**

![alt text](Overview.jpg)

The entrance of the program is `Main`, and the total controller is `CellSociety`, which depends on two classes `Animation` and `Page`. `Animation` works on simulation algorithm implementation, `Page` works on GUI set ups. Both classes depends on the `Parameters` class to get and update parameters in simulations. `Page` depends on the `Cell` class to set up cells in grids, and `Animation` depends on the `Grid` class to get neighbors for a given cell. `Page` depends on `CellSociety` to control the timeline. Moreover, `XMLParametersController` is a controller to update variables in `Parameters` from XML parsed inputs.

## User Interface
For our user interface, the user is first given a splash screen to select display language, select input XML file and a button to start game. The other scene will display the animation and will continue running until the user clicks a button which brings them back to the main menu. If the user wants to interact with how the program performs, they can edit the XML files provided with the program to change the behavior, starting percentages, and the like. 

The only foreseeable error happens when an XML file is not properly input in a way which the program can read. Using a try-catch, we will not allow the animation to launch if there is an error in the way that the XML file is read in.

**Here's a look at the menu:**
![alt text](Menu_UI.jpg "Menu UI") 

**Here's a look at a level UI:**
![alt text](Level_UI.jpg "Level UI") 

## Design Details 
Overall Goal: Able to change number of cells and number of simulations easily.

We design to implement following major classes in our program, and their major variables and methods are also listed. Bold fonts indicate there is some collaboration between this variable/method with other variables/methods. 

- `Main`: the class to launch the game
- `CellSociety`: the class to manage the entire game
	- **`private Page[] pages`**: all `Page` instances that the program contains, including 1 `Page` for splash screen, and 1 `Page` for each animation.
	- **`private Animation[] animations`**: all `Animation` instances that the program requires to control the game. 1 `Animation` instance for each animation, used to call methods in the corresponding `Animation subclass`.
	- `public CellSociety()`: constructor of the class `CellSociety`
	- `private void initializeWelcomePage()`: initialize the splash screen
	- `private void initializeSimulationPage(int typeOfSimulation)`: initialize a `Page` for a specific simulation game
	- `private void setupGameLoop()`: manage the game loop
	- `private void startGameLoop()`: start the game loop
	- `private void endGameLoop()`: stop the game loop
	- `private void actionsPerFrame(double elapsedTime)`: called by `setupGameLoop()` to control actions in each frame
	- **Other methods in `Animation` and `Page` subclasses** are called by `actionsPerFrame()` to control actions during simulation.
- `Page`: superclass for different types of scenes
	- `public static final int WIDTH`: width of the 2D game screen
	- `public static final int HEIGHT`: height of the 2D game screen
	- `private Scene scene`: the `Scene` to hold all component nodes
	- `private Group root`: the root node in the scene
	- `public Page()`: constructor of the class `Page`, initializing scene and root
	- `private void readInputXML(String path)`: read input XML file from the given path, and store parameters in class variables (if necessary, this method can be abstract and to be implemented in each subclass).
- `Page`: Subclasses responsible for each scene
	- `Welcome Page`: setup instructions, prompting the user to choose input XML file, preparing to load simulation page.
		- `private Text title`: the title and other introduction messages shown on the splash screen
		- `private ComboBox language`: the drop down menu to choose language
		- `private Button chooseFile`: the button to choose input file
		- `private Button start`: the button to go to the simulation
		- `public WelcomePage()`: the constructor of the Welcome Page, calls **superclass constructor**
	- `Specific simulation Page`: Set up GUI for each simulation (one class for each simulation) (there is an inheritance hierarchy, and some intermediate abstract classes are not described here)
		- `private Cell[][] cells`: all cells in the simulation
		- some variables for GUI setups
		- `public XXX()`: the constructor for the class, set up parameter text field, set up cells according to parameters, and **add cells to the scene**.
		- **`public void updateCells(//some inputs to indicate changed cells and new status)`**: to change status of all required cells, for each cell, the `changeStatus()` and `changeColor()` method in the class `Cell` will be called. 
		- `private void updateParameters()`: to handle user adjustment for simulation parameters.
- `Cell`: super class for one cell in simulations
	- `private int status`: indicator of cell status
	- `public Cell (int status)`: constructor of the `Cell` class. Initialize the status of the cell.
	- **`public Rectangle getCell()`**: get the rectangle variable for the cell to be added to scene in class Page
	- `public void changeStatus(int newStatus)`: change cell status to the input variable.
- `Cell`: sub classes for different cell shapes
	- `private Shape shape`:  a JavaFX variable to represent the cell (including triangle, square, and hexagon).
	- `public Cell (double x, double y, double width, double height, int status, boolean hasGrid)`: constructor of the `Cell` subclass. Determines the position of a specific cell.
- `Animation`: Superclass for the other animations. Allow for the declaration of a generic Animation class.
- `Animation`: Subclasses for each individual animation (each game). Update and handle situations in the right way.
	- **`private CellSociety cellsociety`**: the `CellSociety` instance of the program, to get access to pages, scene, and cells in the animation.
	- `public Animation(CellSociety theCellSociety)`: constructor of the class. When an `Animation subclass` instance is initialized in `CellSociety`, the calling `CellSociety` instance will be passed into the constructor.
	- **`private void calculateMove()`**: the major method to implement rules of each animation. Other methods called by it will be implemented as required. If some cells need to change status, the `updateCells()` method in the corresponding `Page` subclass will be called.
- `Grid`: interface to return neighbors of a given cell.
	-`public ArrayList<Indices> getImmediateNeighbors(int x, int y, int xMax, int yMax)`: the method to get immediate neighbors of a cell.
	- `getAllNeighbors(int x, int y, int xMax, int yMax)`: the method to get all neighbors of a cell.
- `Grid`: sub class of the interface for each cell shape.
	Each sub class implements two methods in the interface.
- `Parameters`: the class to hold all parameters of all simulations.
	The class only has getter and setter methods for each parameter.
- `XMLInputParser`: the class to parse an XML input file.
- `XMLConfigParser`: the class to parse an XML overall configuration file.
- `XMLParametersController`: the controller between `XMLParser` and `Parameters`. Contain methods to insert parsed values into the `Parameters` class.

To extend the program, we have come up with several possible new requirements and their solutions:

 1. Add a new type of simulation.
	Firstly one more XML file will be prepared, and relative components in the xml parser, parameters class and the parser controller will be required. And one `Page subclass` and one `Animation subclass` will be implemented to handle specific layouts and rules of the new simulation.
	
 2. Allow for the user to adjust simulation parameters
	In each `Page` subclass, we provide sliders to adjust all parameters, and have corresponding methods to handle slider events. 

Use Cases Analysis

 1. Apply rules to a middle cell 
 
	Method  `calculateMove()` in the `Animation subclass` for Game of Life is responsible for defining the rules, doing the calculation and applying changes by calling other methods.
	
    Methods `updateCells()` in `Page subclass`, and `changeStatus(int newStatus)` in `Cell` class will be consecutively called to apply the changes to the cell.
 
 2. Apply rules to an edge cell

	Because we use border cells around normal cells, all valid cells in the animation will be regarded as middle cells, eliminating special treatment for edge cells. It is safe to apply the rules of Game of Life. But we need to pay attention to specific cases in other animations.
 
 3. Move to the next generation

	Method `actionsPerFrame()` in the `CellSociety` class (and methods called by it in `Animation subclass`) is responsible for updates. We regard each frame as one step, and in each step, rules will be applied and cells status will be updated.
 
 4. Set a simulation parameter 

	With final updates, all simulation parameters can be read in from XML files, or changed during the simulation process. If a user want to set the parameter, he or she has to modify the XML file and restart the game, or directly manipulate GUI components.
 
 5. Switch simulation
 
	During one simulation, if the user wants to switch to another one, he or she has to click the “back to main menu” button on the simulation page back to the splash screen, and choose a new XML file input.

## Design Considerations 

 1. Border cells
 
	To reduce implementation complexity, we add a layer of invisible border cells around normal cells, so that all valid cells will be considered as middle cells. However, we will be careful on how to count neighbors for each simulation rule.

 2. Dependencies among classes
 
	A lot of dependencies among classes are in the program design.

 3. `Animation` is where all the individual rules are executed
 
	We extract out a separate class to handle simulation updates in each step, instead of wrapping everything in the `CellSociety` class.

 4. Parameters can be modified in XML files and from GUI
 
	With final updates, all simulation parameters can be read in from XML files, or changed during the simulation process. If a user want to set the parameter, he or she has to modify the XML file and restart the game, or directly manipulate GUI components.

 5. Report errors on parameters to the user
 
	When errors regarding formats on input files and parameters occur, alerts with error messages will be displayed on the screen. 

## Team Responsibilities

> All group members will help out with the `Animation` superclass and subclasses (rules for making sure we get the right patterns). First checkpoint (responsibilities below) is just to write out the skeleton (variable names, imports, etc) of each class.

Yilin Gao: implement the `Page` superclass and its subclasses. Connect classes together.

Harry Liu: implement `Main` and `Cells` superclass and its subclasses. Connect classes together.

Josh Kopen: implement `Cell Society` and `Animation` superclass and its subclasses. Connect classes together.

