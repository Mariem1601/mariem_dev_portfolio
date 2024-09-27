#include <iostream>
#include <limits>
#include "Controller.h"
#include "../model/Game.h"
#include "../view/View.h"

/*!
 * \brief Controller::init initialize and launch the game.
 *          it also create a custom Game and a View
 */
void Controller::init()
{
    gameInitialization();

    _view->displayWelcome();
    _view->displayHelp();
    _view->displayBoard(_game.get());
    start();
    _view->displayEndGame(_game->getScore());
}

//! manages the game flow
void Controller::start()
{
    std::string command;

    do {
        std::cout << "\nEnter a command: ";
        std::getline(std::cin >> std::ws, command); // Ignore whitespace and '\n'
        handleCommand(command);
    } while(command != "q" && !_game->endGame());

}

//! executes an action based on the answer entered by the user
void Controller::handleCommand(std::string command)
{
    std::transform(command.begin(), command.end(), command.begin(), ::tolower);

    if (command == "ml") {
        _game->move(Direction::left);
    } else if (command == "mr") {
        _game->move(Direction::right);
    } else if (command == "md") {
        _game->move(Direction::down);
    } else if (command == "r") {
        _game->rotate(Rotation::clockWise);
    } else if (command == "rc") {
        _game->rotate(Rotation::counterClockWise);
    } else if (command == "d") {
        _game->drop();
    } else if (command == "h") {
        _view->displayHelp();
    } else if (command != "q") {
        std::cout << "Invalid command." << std::endl;
    }
}

//! allows you to create either a game with default or custom settings
void Controller::gameInitialization()
{
    std::cout << "Would you like to personalize the game ? (y/n) : ";
    char answer;
    std::cin >> answer;
    std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n'); //clear the input buffer

    if (answer == 'y' || answer == 'Y') {
        createGame();                     //! custom game
    } else {
        _game = std::make_unique<Game>(); //! game with default setting
    }

    _view = std::make_unique<View>();
    _game->registerObserver(_view.get());
}

//! create the custom game
void Controller::createGame()
{
    bool validInput = false;
    int level, height, width;
    bool randomize;

    while (!validInput) {
        try {
            level = getInputInteger("Enter the level (integer): ");
            randomize = getInputBoolean("Should the board be initially randomized? (true/false): ");
            height = getInputInteger("Enter the height (integer): ");
            width = getInputInteger("Enter the width (integer): ");

            _game = std::make_unique<Game>(level, randomize, height, width);
            validInput = true;
        } catch (const std::invalid_argument& e) {
            std::cout << e.what() << std::endl;
            std::cin.clear();
            std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
        }
    }
}

int Controller::getInputInteger(const std::string& prompt)
{
    int value;
    std::cout << prompt;
    std::cin >> value;
    if (std::cin.fail()) {
        throw std::invalid_argument("Invalid input. Please enter a valid integer.");
    }
    return value;
}

bool Controller::getInputBoolean(const std::string& prompt)
{
    bool value;
    std::cout << prompt;
    std::cin >> std::boolalpha >> value;
    if (std::cin.fail()) {
        throw std::invalid_argument("Invalid input. Please enter true or false.");
    }
    return value;
}
