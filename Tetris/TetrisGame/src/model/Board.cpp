#include "Board.h"
#include <cstdlib>
#include <stdexcept>
#include "../ConstantsUtils.h"


/*!
 * \brief Board::Board  Constructor of Board
 * \param randomize     add random Brick before the game begin if true
 * \param height        the height of the board
 * \param width         the width of the board
 */
Board::Board(bool randomize, int height, int width)
    : _height {height - 1}  //! height - 1 because we count 0
    //! as the first elem (from 0 to n - 1)
    , _width {width - 1}    //! same reasonning than _height
{
    if (height <= 0 || width <= 0) {
        throw std::invalid_argument("Height and width must be greater than zero.");
    }
    if (height < c_minHeight || width < c_minWidth) {
        throw std::invalid_argument("Height must be greater than or equal to " + std::to_string(c_minHeight)
                                    + " and width must be greater than or equal to " + std::to_string(c_minWidth));
    }

    _lines.resize(height);
    for (int row = 0; row < height; ++row)
    {
        _lines[row].resize(width, Shape::emptyShape);
    }
    if (randomize)
    {
        brickBoardInitiator();
    }
}

/*!
 * \brief Board::canMove check whether a brick can move on a direction
 * \param brick a brick
 * \param d     a direction (except up)
 * \return      true if a brick can move
 */
bool Board::canMove(const Brick& brick, const Direction& d) const
{
    const Position& centerCoordinate {brick.getBoardCoordinate()};
    bool canBlockMove {};
    const std::vector<Position>& blockPositions {brick.getBrickCoordinates()};
    for (Position position : blockPositions) {
        switch (d)
        {
        case Direction::down:
            canBlockMove = checkDown(position, centerCoordinate);
            break;
        case Direction::left:
            canBlockMove = checkLeft(position, centerCoordinate);
            break;
        case Direction::right:
            canBlockMove = checkRight(position, centerCoordinate);
            break;
        }
        if (!canBlockMove)
        {
            return false;
        }
    }
    return true;
}

/*!
 * \brief Board::canRotate check whether a brick can rotate
 * \param brick a brick
 * \param r     the direction of the rotation
 * \return      true if a brick can rotate
 */
bool Board::canRotate(const Brick& brick, const Rotation& r)
{
    const Position& centerCoordinate {brick.getBoardCoordinate()};
    bool canBlockRotate {true};
    std::vector<Position> blockPositions {brick.getBrickCoordinates()};
    for (Position position : blockPositions) {
        canBlockRotate = checkRotation(position, centerCoordinate, r);
        if (!canBlockRotate)
        {
            return false;
        }
    }

    return true;
}

/**
 * @brief Board::insertBrick    insert a brick in _lines
 * @param brick                 a brick
 */
void Board::insertBrick(const Brick& brick)
{
    const Position& centerCoordinate {brick.getBoardCoordinate()};
    const std::vector<Position>& brickCoordinates {brick.getBrickCoordinates()};
    for (Position blockPositionRel : brickCoordinates)
    {
        int x = blockPositionRel.x + centerCoordinate.x;
        int y = blockPositionRel.y + centerCoordinate.y;

        if (x < 0 || y < 0 || x > _width || y > _height) {
            throw std::out_of_range("Brick position is outside of the board.");
        }
        if (_lines[y][x] != Shape::emptyShape) {
            throw std::runtime_error("A brick is already present at this position.");
        }

        _lines[y][x] = brick.getShape();
    }
}

/*!
 * \brief Board::deleteLinesCompleted   count the number of line completed and clear it
 *          , shift all the lines above downwards
 * \return  the number of line completed
 */
int Board::deleteLinesCompleted()
{
    int nbLinesCompleted {0};
    int nbShift {0};           //! number of line to be cleared
    for (int row = 0; row <= _height; ++row)
    {
        bool isLineCompleted {true};
        int nbEmptyShapeLine {0};
        for (int col = 0; col <= _width; ++col)
        {
            //! if a there are no shape, exit the loop
            //! warning : we must exit the loop only if all non Empty line are
            //! shifted
            if (_lines[row][col] == Shape::emptyShape)
            {
                isLineCompleted = false;
                ++nbEmptyShapeLine;

            }

            if (nbLinesCompleted > 0)
            {
                //! shift blocks downward by nbLinesCompleted
                _lines[row - nbLinesCompleted][col] = _lines[row][col];
            }
        }
        if (isLineCompleted)
        {
            ++nbLinesCompleted;
            ++nbShift;
        }
        //! tell if there are lines yet to be shifted
        //! if nbShift == 0 there are no line to be shifted
        if (nbEmptyShapeLine == _width)
        {
            --nbShift;
            if (nbShift <= 0)
            {
                break;
            }
        }
    }
    //! nbShift > 0 means we reached the top of board
    //! and we cannot clear further
    if (nbShift > 0)
    {
        clearTopLine(nbShift);
    }
    return nbLinesCompleted;
}

/*!
 * \brief Board::searchDropInsert search the lowest line where a brick can be inserted
 * \param brick         a brick
 * \param randomize     a boolean used to know if method is used to drop random brick for
 *                      brickBoardInitiator
 * \return              true if the brick can be inserted and respect maximum insertion height
 */
bool Board::searchDropInsert(Brick& brick, bool randomize)
{
    while (brick.getBoardCoordinate().y > 0
            && canMove(brick, Direction::down))
    {
        brick.updateBrickCoordinate(Direction::down);
    }
    int height {brick.getBoardCoordinate().y};
    return canBeInserted(brick)
           && ((!randomize) || height <= c_nbRandomHeightMax);
}

/*!
 * \brief Board::canBeInserted check whether a brick can be inserted on the board
 * \param brick a brick
 * \return      true if it can be inserted
 */
bool Board::canBeInserted(const Brick& brick)
{
    const Position& centerCoordinate {brick.getBoardCoordinate()};
    const std::vector<Position>& brickCoordinate {brick.getBrickCoordinates()};
    for (Position blockPositionRel : brickCoordinate) {
        int positionX {blockPositionRel.x + centerCoordinate.x};
        int positionY {blockPositionRel.y + centerCoordinate.y};

        if ((positionX < 0 || positionX > _width)
            || (positionY < 0 || positionY > _height)
            || (_lines[positionY][positionX] != Shape::emptyShape))
        {
            return false;
        }
    }
    return true;
}

/*!
 * \brief Board::alignBrickBoard align the brick
 *          with the board so the entire brick can be visible
 * \param brick the current brick
 */
void Board::alignBrickBoard(Brick& brick)
{
    const std::vector<Position>& brickCoordinates {brick.getBrickCoordinates()};
    int max {0};
    for (Position blockPositionRel : brickCoordinates)
    {
        if (blockPositionRel.y > max)
        {
            max = blockPositionRel.y;
        }
    }
    //! shift downwards so that the highest block is visible on the board
    for (int yShiftDown = 0; yShiftDown < max; ++yShiftDown) {
        brick.updateBrickCoordinate(Direction::down);
    };
}

/**
 * @brief Board::isEmpty    check if _lines is Empty
 * @return  true if _lines only contains emptyShape values
 */
bool Board::isEmpty() const
{
    for (const std::vector<Shape>& line : _lines)
    {
        for (Shape shape : line)
        {
            if (shape != Shape::emptyShape)
            {
                return false;
            }
        }
    }
    return true;
}

//! add random brick in the board before game start
void Board::brickBoardInitiator()
{
    srand(time(0));
    int tryFailed {0};
    while (tryFailed < c_endRandomTry) {
        //! check number of enum Shape (see Shape.h)
        //! On future implementation, if we add more shape,
        //! we must update the max number of shape
        int randomShape {rand() % static_cast<int>(Shape::count)};
        //! this expression is used to have a number
        //! between 1 and _width because
        //! xBoard and yBoard of brick
        //! cannot have the value 0
        int randomCol {(rand() % _width) + 1};

        Shape shape {static_cast<Shape>(randomShape)};
        Brick brick {shape, randomCol, _height};
        if (searchDropInsert(brick, true))
        {
            insertBrick(brick);
        }
        else {
            ++tryFailed;
        }
    }
}

/*!
 * \brief Board::checkRotation check whether a block can rotate
 * \param blockPosition        the relative position a block in the brick
 * \param centerCoordinate     the coordinate in the bord of the central block (marked by a (0, 0))
 * \param r                     the direction of a rotation
 * \return                      true if a block can go to it's final location
 *                              (at the end of the rotation)
 */
bool Board::checkRotation(  const Position& blockPosition
                          , const Position& centerCoordinate
                          , Rotation r)
{
    Position newPosition;
    if (r == Rotation::clockWise) {
        newPosition = {blockPosition.y, -blockPosition.x};
    }
    else {
        newPosition = {-blockPosition.y, blockPosition.x};
    }
    Position currentPosition {blockPosition.x, blockPosition.y};
    while (currentPosition.x != newPosition.x
           || currentPosition.y != newPosition.y)
    {
        //! dynamicRotation == false means that an extern block is in the way
        if (!dynamicRotation(currentPosition, newPosition, centerCoordinate, r))
        {
            return false;
        }
    }
    return true;
}


//! check wheter there is a block in the way during the rotation
//! update currentPos at the same time
bool Board::checkRotUp(Position& currentPos
                       , const Position& finishPos
                       , const Position& centerCoordinate)
{
    for (int positionY = currentPos.y; positionY < finishPos.y; ++positionY, ++currentPos.y) {
        if (!checkUp(currentPos, centerCoordinate))
        {
            return false;
        }
    }

    return true;
}

//! check wheter there is a block in the way during the rotation
bool Board::checkRotDown(Position& currentPos
                         , const Position& finishPos
                         , const Position& centerCoordinate)
{
    for (int positionY = currentPos.y; positionY > finishPos.y; --positionY, --currentPos.y) {
        if (!checkDown(currentPos, centerCoordinate))
        {
            return false;
        }
    }

    return true;
}

//! check wheter there is a block in the way during the rotation
bool Board::checkRotRight(Position& currentPos
                          , const Position& finishPos
                          , const Position& centerCoordinate)
{
    for (int positionX = currentPos.x; positionX < finishPos.x; ++positionX, ++currentPos.x) {
        if (!checkRight(currentPos, centerCoordinate))
        {
            return false;
        }
    }

    return true;
}

//! check wheter there is a block in the way during the rotation
bool Board::checkRotLeft(Position& currentPos
                         , const Position& finishPos
                         , const Position& centerCoordinate)
{
    for (int positionX = currentPos.x; positionX > finishPos.x; --positionX, --currentPos.x) {
        if (!checkLeft(currentPos, centerCoordinate))
        {
            return false;
        }
    }

    return true;
}

//! check if there is a empty space (Up)
bool Board::checkUp(  const Position& blockPosition
                    , const Position& centerCoordinate) const
{
    if (blockPosition.y + centerCoordinate.y >= _height
        || _lines[centerCoordinate.y + blockPosition.y + 1]
                 [centerCoordinate.x + blockPosition.x]
               != Shape::emptyShape)
    {
        return false;
    }
    return true;
}

//! check if there is a empty space (Down)
bool Board::checkDown(  const Position& blockPosition
                      , const Position& centerCoordinate) const
{
    if (blockPosition.y + centerCoordinate.y <= 0
        || _lines[centerCoordinate.y + blockPosition.y - 1]
                 [centerCoordinate.x + blockPosition.x]
               != Shape::emptyShape)
    {
        return false;
    }
    return true;
}

//! check if there is a empty space (Left)
bool Board::checkLeft(  const Position& blockPosition
                      , const Position& centerCoordinate) const
{
    if (blockPosition.x + centerCoordinate.x <= 0
        || _lines[centerCoordinate.y + blockPosition.y]
                 [centerCoordinate.x + blockPosition.x - 1]
               != Shape::emptyShape)
    {
        return false;
    }
    return true;
}

//! check if there is a empty space (Right)
bool Board::checkRight(  const Position& blockPosition
                       , const Position& centerCoordinate) const
{
    if (blockPosition.x + centerCoordinate.x >= _width
        || _lines[centerCoordinate.y + blockPosition.y]
                 [centerCoordinate.x + blockPosition.x + 1]
               != Shape::emptyShape)
    {
        return false;
    }
    return true;
}

/*!
 * \brief Board::dynamicRotation move currentPosition
 *          until it has the coordinate of newPosition
 * \param currentPosition   the position of the bloc moving
 *                          towards its final position
 * \param newPosition       the desired position
 * \param centerCoordinate  the coordinate in the bord of the central block
 *                          (marked by a (0, 0))
 * \param r                 the direction of a rotation
 * \return                  true if currentPosition can move
 */
bool Board::dynamicRotation(Position& currentPosition
                            , const Position& newPosition
                            , const Position& centerCoordinate
                            , Rotation r)
{
    //! move the block vertically (downwards)
    if ((currentPosition.y > newPosition.y) &&
        ( (r == Rotation::clockWise && currentPosition.x >= newPosition.x) ||
         (r == Rotation::counterClockWise && currentPosition.x <= newPosition.x) )
        )
    {
        return checkRotDown(currentPosition
                            , newPosition, centerCoordinate);
    }
    //! move the block vertically (upward)
    else if ((currentPosition.y < newPosition.y) &&
             ( (r == Rotation::clockWise && currentPosition.x <= newPosition.x) ||
              (r == Rotation::counterClockWise && currentPosition.x >= newPosition.x) )
             )
    {
        return checkRotUp(currentPosition
                          , newPosition, centerCoordinate);
    }
    //! move the block horizontally (Left)
    else if (currentPosition.x > newPosition.x)
    {
        return checkRotLeft(currentPosition, newPosition, centerCoordinate);
    }
    //! move the block horizontally (Right)
    else if (currentPosition.x < newPosition.x)
    {
        return checkRotRight(currentPosition, newPosition, centerCoordinate);
    }
    return true;
}

//! Clear the non cleared line situed at the top
void Board::clearTopLine(int nbClear)
{
    for (int nbLineCleard = 0; nbLineCleard < nbClear; ++nbLineCleard) {
        for (int col = 0; col <= _width; ++col) {
            _lines[_height - nbLineCleard][col]
                = Shape::emptyShape;
        }
    }
}

/*! it was initialy the method: bool get(int row, int col)
        we decide to change its name with a more understanding name
        we decided to change the return type to Shape because initialy,
        _line was a vector<bool>
     */
const Shape& Board::getBlock(int row, int col) const
{
    return _lines[row][col];
}
const int Board::getHeight() const
{
    return _height;
}
const int Board::getWidth() const
{
    return _width;
}







