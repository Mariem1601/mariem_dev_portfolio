#include "Game.h"
#include "../ConstantsUtils.h"


/*!
 * \brief Game::Game    constructor of Game
 * \param level         the level of the game
 * \param randomize     randomization of the board
 * \param height        the height of the board
 * \param width         the width of the board
 */
Game::Game(int level
            , bool randomize
            , int height
            , int width)
    : _level {level}
    , _board {Board(randomize
                    , height
                    , width)}
    , _brick {Brick(_bag->getBrickShape()
                    , (width - 1) / 2
                    , height - 1)}
    , _startingTime {std::chrono::system_clock::now()}
{
    if (level <= 0)
    {
        throw std::invalid_argument("Level must be greater or equal to 1.");
    }
    _board.alignBrickBoard(_brick);
}

/*!
 * \brief Game::init    Send the starting state of game to the view
 */
void Game::init()
{
    notifyObservers(this);
}

/*!
 * \brief Game::move    move the current brick in the board if possible
 * \param d             a direction
 */
void Game::move(Direction d)
{
    bool canMove {_board.canMove(_brick, d)};
    if (d == Direction::down)
    {
        checkEndCollision(canMove, d);
    }
    else
    {
        if(canMove)
        {
            _brick.updateBrickCoordinate(d);
        }
    }
    notifyObservers(this);
}

/*!
 * \brief Game::drop instantly place a brick in the lowest possible row
 */
void Game::drop()
{
    const Position initPosition {_brick.getBoardCoordinate()};
    if (_board.searchDropInsert(_brick, false))
    {
        int nbLinesDrop {initPosition.y - _brick.getBoardCoordinate().y};
        updateScore(nbLinesDrop, c_noLinesCompleted);
        move(Direction::down);
    }
}

/*!
 * \brief Game::rotate  rotate the current brick in the board if possible
 * \param r             a direction of rotation
 */
void Game::rotate(Rotation r)
{
    if ((_brick.getShape() != Shape::cShape) && _board.canRotate(_brick, r))
    {
        _brick.rotate(r);
    }
    notifyObservers(this);
}

/*!
 * \brief Game::endGame indicate whether the game is finished
 * \return  true if end of game
 */
bool Game::endGame()
{
    std::chrono::time_point<std::chrono::system_clock> currentTime {std::chrono::system_clock::now()};
    long long elapsedTime {std::chrono::duration_cast<std::chrono::minutes>(currentTime - _startingTime).count()};
    return !_board.canBeInserted(_brick)
            || _score > c_maxScore
           || (((_level - 1) * c_updateLevel) + _linesCompleted) > c_maxNblinesCompleted
           || elapsedTime >= c_maxMinutes;
}

/*!
 * \brief Game::increaseScore Increase the score by 1
 */
void Game::increaseScore()
{
    ++_score;
}

/*!
 * \brief Game::checkEndCollision   check whether there is a colision with another brick
 * \param canMove                   boolean that indicate if the brick can move
 * \param d                         a direction
 */
void Game::checkEndCollision(bool canMove, Direction d)
{
    if (canMove)
    {
        _brick.updateBrickCoordinate(d);
    }
    else
    {
        _board.insertBrick(_brick);
        int nbLines {_board.deleteLinesCompleted()};
        _linesCompleted += nbLines;
        updateScore(c_noDrop, nbLines);
        updateLevel();
        addNewBrick();
    }
}

//! create a new brick
void Game::addNewBrick()
{
    _brick = Brick(_bag->getBrickShape()
                    , _board.getWidth() / 2
                    , _board.getHeight());
    _board.alignBrickBoard(_brick);
}

//! update level and reajust _linesCompleted
//! each 10 lines level increase
void Game::updateLevel()
{
    if (_linesCompleted >= c_updateLevel)
    {
        ++_level;
        _linesCompleted %= c_updateLevel;
    }
}

//! update the score
void Game::updateScore(int drop, int nbLines)
{
    switch (nbLines) {
    case 0:
    case 1:
        _score += (((c_score1LineCompleted * nbLines) + drop) * _level);
        break;
    case 2:
        _score += (((c_score2LineCompleted * nbLines) + drop) * _level);
        break;
    case 3:
        _score += (((c_score3LineCompleted * nbLines) + drop) * _level);
        break;
    case 4:
        _score += (((c_score4LineCompleted * nbLines) + drop) * _level);
        break;
    default:
        break;
    }
}

const Board& Game::getBoard() const
{
    return _board;
}
const Brick& Game::getBrick() const
{
    return _brick;
}
const int Game::getLevel() const
{
    return _level;
}
const int Game::getScore() const
{
    return _score;
}

const int Game::getNbLinesCompleted() const
{
    return _linesCompleted;
}





