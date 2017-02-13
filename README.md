# Cell Society Team 01

>CompSci 308 Cell Society Project

**Name**: Yilin Gao, Joshua Kopen, Harry Liu

**Date started**: 29 January 2017

**Date finished**: 12 February 2017

**Hours worked**: 90 hours (Total hours of group)

**Resources used**: CodeSmells, StackOverflow, YouTube, Lynda.com, Lab_Browser, The Java™ Tutorials

**Main Class File**: Main.java

**Data/Resource files**: XML files in Data Folder, styles.css, bg.png, English.properties, Spanish.properties

**Responsibilities**:
- Yilin Gao: XML Reading and Parsing, Page Classes and SubClasses, and connection between classes
- Joshua Kopen: Animation class files and Game Page
- Harry Liu: UI setup, styling, ResourceBundles, Page Setup

**Simulation/Test files**: Fire.xml, GameOfLife.xml, Predator.xml, Segregation.xml, Slime.xml

**Additional File Information**: The OverallConfiguration.xml allows you to adjust initial shape and the visibility of grid lines.

**Functionality Issues**: Triangle class exists, but is not fully functional. We have other shapes such as Hexagon and Square, so we should be able to support it in the future. Also for the probability sliders, currently, we don't have it setup so that all values must add up to 1.

**Impressions of the Assignment**: Our Back-end and Front-end is separate, but for the future, we should try to do the Model–view–controller design pattern. Also, we haven't learned too much about a better way of doing the `initializePage()` method of `CellSociety.java`. Something we might do to improve it in the future is to use Reflections. Also, we probably should not have passed CellSociety around and Pages in the constructors to decrease the dependencies between classes. This is because CellSociety is sort of like the middle man and other pages should not have access to the methods in it. Over all, this was a very challenging assignment because we had to learn how to not only use git in a team, but also because we had to from the start think about the design of the program to make sure that it was flexible and easy to edit for new features.
