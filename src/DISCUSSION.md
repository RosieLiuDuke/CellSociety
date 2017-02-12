# Discussion of refactoring

Yilin Gao (yg95), Dennis Ling(dl186)

## Code Smell Refactoring

After discussing with my partner, I decided to refactor the following duplicate codes:

1. Extract the steps to initialize an alert into a method in the `Page` super class, which takes a string variable as input to indicate the error message. This is able to remove duplicate code in the `stepButton` , `startButton` methods in `GamePage` class. This can be a natural method to deal with duplicates in parallel methods.
	 
2. Create a new class to store all parameters for all current simulations, and remove all getting and setting methods of these parameters to this new class. This is because in our group's code there are several duplicate code problems related to parameter setting and reading. After discussing with my partner and consulting with Professor Duvall, I decided to do the major refactoring on my code, to better separate functions of different classes, and reduce duplicate code. 


## Checklist refactoring

The checking results indicate that there is no fatal problem in our group's code. 

1. There are a lot of magic values mainly in the `XMLParser` class, but since they correspond to tags of XML files, we both agreed that it seems difficult to remove these magic values.

2. With refactoring on the new parameter class, the length of `XMLParser` class and its major methods are reduced for a little bit. 