#ifndef CONTROLLER_H
#define CONTROLLER_H
#include <memory>

class Game;
class View;

/*!
 * \brief The Controller class manages the game flow and links the view to the model
 */
class Controller
{
private:
    std::unique_ptr<Game> _game;
    std::unique_ptr<View> _view;
private:
    void start();
    void handleCommand(std::string command);
    void gameInitialization();
    void createGame();
    static int getInputInteger(const std::string& prompt);
    static bool getInputBoolean(const std::string& prompt);
public:
    Controller() = default;
    ~Controller() = default;
    void init();
};

#endif // CONTROLLER_H



