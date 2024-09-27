#include "View.h"
#include <iostream>
#include <iomanip>

/*!
 * \brief View::displayWelcome  display a welcome message
 */
void View::displayWelcome()
{
    std::cout << "--------------- JEU TETRIS ---------------" << std::endl;
}

/*!
 * \brief View::displayHelp display the help command
 */
void View::displayHelp()
{
    std::cout << "\n------------- TETRIS COMMAND -------------" << std::endl;
    std::cout << "- ml : move left" << std::endl;
    std::cout << "- mr : move right" << std::endl;
    std::cout << "- md : move down" << std::endl;
    std::cout << "- r : rotate clock wise" << std::endl;
    std::cout << "- rc : rotate counter clock wise" << std::endl;
    std::cout << "- d : drop" << std::endl;
    std::cout << "- h : help" << std::endl;
    std::cout << "- q : exit\n" << std::endl;

}

/*!
 * \brief View::displayEndGame  display an end game message
 * \param score
 */
void View::displayEndGame(int score)
{
    std::cout << "\n---------------- END GAME ----------------" << std::endl;
    std::cout << "Your score : " << score << std::endl;

}

/*!
 * \brief View::displayBoard    display the bord and the current brick
 * \param model     the game model
 */
void View::displayBoard(Game* const model)
{
    const Brick& brick {model->getBrick()};
    const Board& board {model->getBoard()};

    Position upperLeft {0, 0};
    Position lowerRight {0, 0};
    searckBrickZone(upperLeft, lowerRight, brick);
    for (int y = board.getHeight(); y >= 0; --y)
    {
        std::cout << std::setw(2) << y << "| ";
        for (int x = 0; x <= board.getWidth(); ++x)
        {
            if (   y <= upperLeft.y
                && y >= lowerRight.y
                && x >= upperLeft.x
                && x <= lowerRight.x
                && isBrickCoordinate(x, y, brick))
            {
                displayBloc(brick.getShape());
            }
            else
            {
                displayBloc(board.getBlock(y, x));

            }
        }
        std::cout << "|" << "\n";
    }
    std::cout << std::setw(4) << " ";
    for (int x = 0; x <= board.getWidth(); ++x) {
        std::cout << std::setw(2) << std::left << x;
    }
    std::cout << std::endl;
}

/*!
 * \brief View::update  update the view
 * \param object        the change made by Game
 */
void View::update(void* object)
{
    Game* game {static_cast<Game*>(object)};
    displayLevel(game->getLevel());
    displayScore(game->getScore());
    displayBoard(game);
}

/*!
 * \brief View::displayBloc display the shape of the brick
 * \param shape     a shape
 */
void View::displayBloc(Shape shape) const
{
    switch (shape)
    {
    case Shape::emptyShape:
        std::cout << ". ";
        break;
    case Shape::cShape:
        std::cout << "C ";
        break;
    case Shape::iShape:
        std::cout << "i ";
        break;
    case Shape::lShape:
        std::cout << "L ";
        break;
    case Shape::jShape:
        std::cout << "J ";
        break;
    case Shape::tShape:
        std::cout << "T ";
        break;
    case Shape::sShape:
        std::cout << "S ";
        break;
    case Shape::zShape:
        std::cout << "Z ";
        break;
    }
}

//! indicates the area where the brick can be found in the board
void View::searckBrickZone(Position& upperLeft
                           , Position& lowerRight
                           , const Brick& brick)
{
    std::vector<Position> bricksRelPosition {brick.getBrickCoordinates()};
    Position brickCenterPos {brick.getBoardCoordinate()};
    int xMin {0};
    int xMax {0};
    int yMin {0};
    int yMax {0};
    for (Position position : bricksRelPosition)
    {
        if (position.x <= xMin)
        {
            xMin = position.x;
        }
        if (position.x > xMax)
        {
            xMax = position.x;
        }
        if (position.y <= yMin)
        {
            yMin = position.y;
        }
        if (position.y > yMax)
        {
            yMax = position.y;
        }
        //that's what we return
        upperLeft = {xMin + brickCenterPos.x
                     , yMax + brickCenterPos.y};
        lowerRight = {xMax + brickCenterPos.x
                      , yMin + brickCenterPos.y};
    }
}

/*!
 * \brief View::isBrickCoordinate   checks whether a brick block is
 *                                  in the required position
 * \param x         x coordinate of board
 * \param y         y coordinate of board
 * \param brick     the current brick
 * \return          true if a block is in (x, y)
 */
bool View::isBrickCoordinate(int x, int y, const Brick& brick)
{
    std::vector<Position> bricksRelPosition {brick.getBrickCoordinates()};
    Position brickCenterPos {brick.getBoardCoordinate()};
    for (Position position : bricksRelPosition) {
        if (x == (position.x + brickCenterPos.x)
            && y == (position.y + brickCenterPos.y))
        {
            return true;
        }
    }
    return false;
}

/*!
 * \brief View::displayScore    display the player's score
 * \param score                 an int representing a score
 */
void View::displayScore(int score)
{
    std::cout << "SCORE : " << score << "\n";
}

/*!
 * \brief View::displayLevel    display the game's level
 * \param level                 an int representing a level
 */
void View::displayLevel(int level)
{
    std::cout << "LEVEL : " << level << "\n";
}
