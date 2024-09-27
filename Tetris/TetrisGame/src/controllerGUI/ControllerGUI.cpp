#include "ControllerGUI.h"
#include "../model/Direction.h"

/*!
 * \brief ControllerGUI::ControllerGUI  Constructor of ControllerGUI
 */
ControllerGUI::ControllerGUI()
{
    _view = std::make_unique<ViewGUI>();

    connect(_view.get(), &ViewGUI::moveLeftPressed, this, &ControllerGUI::handleMoveLeft);
    connect(_view.get(), &ViewGUI::moveRightPressed, this, &ControllerGUI::handleMoveRight);
    connect(_view.get(), &ViewGUI::moveDownPressed, this, &ControllerGUI::handleMoveDown);
    connect(_view.get(), &ViewGUI::dropPressed, this, &ControllerGUI::handleDrop);
    connect(_view.get(), &ViewGUI::rotatePressed, this, &ControllerGUI::handleRotate);
    connect(_view.get(), &ViewGUI::counterRotatePressed, this, &ControllerGUI::handleCounterRotate);
    connect(_view.get(), &ViewGUI::gameSettings, this, &ControllerGUI::handleGameCreation);
    connect(_view ->getTimer(), &QTimer::timeout, this, &ControllerGUI::sendMove);
}

/*!
 * \brief ControllerGUI::~ControllerGUI Destructor of ControllerGUI
 */
ControllerGUI::~ControllerGUI()
{

}

/*!
 * \brief ControllerGUI::handleGameCreation Create the game and launch the game
 * \param level     the level of the game
 * \param height    the height of the grid
 * \param width     the width of the grid
 * \param random    grid randomely initialized
 */
void ControllerGUI::handleGameCreation(int level, int height, int width, bool random)
{
    _game = std::make_unique<Game>(level, random, height, width);
    _game->registerObserver(_view->getMainPane());
    _game -> init();
}

/*!
 * \brief ControllerGUI::handleMoveLeft Handle the case when user press the left Arrow key
 */
void ControllerGUI::handleMoveLeft()
{
    _game->move(Direction::left);
}

/*!
 * \brief ControllerGUI::handleMoveLeft Handle the case when user press the right Arrow key
 */
void ControllerGUI::handleMoveRight()
{
    _game->move(Direction::right);
}

/*!
 * \brief ControllerGUI::handleMoveLeft Handle the case when user press the down Arrow key
 * if user press this key, it increase the score by 1
 */
void ControllerGUI::handleMoveDown()
{
    if (_game->endGame())
    {
        _view ->endGame();
    }
    else {
        _game->increaseScore();
        _game->move(Direction::down);
    }

}

/*!
 * \brief ControllerGUI::handleMoveLeft Handle the case when user press the D key
 */
void ControllerGUI::handleDrop()
{
    if (_game->endGame())
    {
        _view ->endGame();
    }
    else {
        _game->drop();
    }

}

/*!
 * \brief ControllerGUI::handleMoveLeft Handle the case when user press the R key
 */
void ControllerGUI::handleRotate()
{
    _game->rotate(Rotation::clockWise);
}

/*!
 * \brief ControllerGUI::handleMoveLeft Handle the case when user press the C key
 */
void ControllerGUI::handleCounterRotate()
{
    _game->rotate(Rotation::counterClockWise);
}

/*!
 * \brief ControllerGUI::sendMove Handle the TimeOut case (see timer on ViewGUI)
 */
void ControllerGUI::sendMove()
{
    if (_game->endGame())
    {
        _view ->endGame();
    }
    else {
        _game -> move(Direction::down);
        _view -> setTimeSpeed(_game ->getLevel());
    }

}

