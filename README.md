# Taboo web game - Lazar & Davide

We developed our web game using Vaadin which is a framework that can be used in java to create web apps using HTML 
elements as Java objects and spring boot mechanics for page routing. 

### About the game
A group of at least two players sit together and launch the game on one device. The user can configure a game by 
choosing our included game categories, or he can also import his own json file. 

The program selects different players alternately to try to describe as many words as possible
in a given time (can also be configured by the users :D ) The describer gets the device in order to see the word he has
to describe, and the taboo words he is not allowed to say which makes the game hard and fun. If a word is too hard
to explain / guess he can skip the words and gets no points. When someone from the describers group guesses the word the 
describer can click on a button and the team will get a new word and one point for one guessed word. 

After every round the players will be sent in a queue page where they can see which player can try his best to describe some new words. Then they can 
decide together weather they want to continue or quit the game to see some cool game stats like the MVP and the winner team. 

### Other
We designed a [Figma sketch](https://www.figma.com/file/oQvgs21FXPOLCQx47sUa1s/Taboo?node-id=0%3A1) which helped us to 
assign issues in github by splitting tasks by the visible components. 

--------------------------------------------------------------------------------------------------

# Project Base for Vaadin and Spring Boot
## Running the Application
The project is a standard Maven project. To run it from the command line, type `mvn` and open http://localhost:8080 in your browser.

You can also import the project to your IDE of choice as you would with any
Maven project. Read more on [how to set up a development environment for
Vaadin projects](https://vaadin.com/docs/latest/guide/install) (Windows, Linux, macOS).

### Running Integration Tests

Integration tests are implemented using [Vaadin TestBench](https://vaadin.com/testbench). The tests take a few minutes to run and are therefore included in a separate Maven profile. We recommend running tests with a production build to minimize the chance of development time toolchains affecting test stability. To run the tests using Google Chrome, execute

`mvn verify -Pit,production`

and make sure you have a valid TestBench license installed (you can obtain a 
trial license from the [trial page](
https://vaadin.com/trial)).

## Project structure

The project follow Maven's [standard directory layout structure](https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html):
- Under the `srs/main/java` are located Application sources
    - `Application.java` is a runnable Java application class and the app's 
      starting point
    - `GreetService.java` is a Spring service class
    - `MainView.java` is an example Vaadin view
- Under the `srs/test` are located the TestBench test files
- `src/main/resources` contains configuration files and static resources
- The `frontend` directory in the root folder contains client-side 
  dependencies and resource files. Example CSS styles used by the application 
  are located under `frontend/styles`

## Useful links

- Read the documentation at [vaadin.com/docs](https://vaadin.com/docs).
- Follow the tutorials at [vaadin.com/tutorials](https://vaadin.com/tutorials).
- Watch training videos and get certified at [vaadin.com/learn/training]( https://vaadin.com/learn/training).
- Create new projects at [start.vaadin.com](https://start.vaadin.com/).
- Search UI components and their usage examples at [vaadin.com/components](https://vaadin.com/components).
- Find a collection of solutions to common use cases in [Vaadin Cookbook](https://cookbook.vaadin.com/).
- Find Add-ons at [vaadin.com/directory](https://vaadin.com/directory).
- Ask questions on [Stack Overflow](https://stackoverflow.com/questions/tagged/vaadin) or join our [Discord channel](https://discord.gg/MYFq5RTbBn).
- Report issues, create pull requests in [GitHub](https://github.com/vaadin/).
