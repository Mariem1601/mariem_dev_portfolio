#include "Brick.h"
#include <stdexcept>


/*!
 * \brief Brick::Brick  constructor of Brick
 * \param shape         a shape
 * \param xBoard        the x position in a board
 * \param yBoard        the y position in a board
 */
Brick::Brick(Shape shape, int xBoard, int yBoard)
    : _shape {shape}
    , _brickCoordinates {DataBrick::getInstance()
                            ->getInitCoordinates(_shape)}
    , _boardCoordinate {xBoard, yBoard}
{
    if (xBoard < 0 || yBoard < 0) {
        throw std::invalid_argument("xBoard and yBoard must be greater than zero.");
    }
}

/*!
 * \brief Brick::rotate do a rotation of all block in a brick
 * \param r the direction of the rotation
 */
void Brick::rotate(const Rotation& r)
{
    int save {};
    for (Position& position : _brickCoordinates)
    {
        switch (r) {
        case Rotation::clockWise:
            save = position.x;
            position.x = position.y;
            position.y = -save;
            break;
        case Rotation::counterClockWise:
            save = position.y;
            position.y = position.x;
            position.x = -save;
        }
    }
}

/*!
 * \brief Brick::updateBrickCoordinate move the brick
 * \param d     a direction (except up)
 */
void Brick::updateBrickCoordinate(Direction d)
{
    switch (d) {
    case Direction::down:
        _boardCoordinate.y -= 1;
        break;
    case Direction::left:
        _boardCoordinate.x -= 1;
        break;
    case Direction::right:
        _boardCoordinate.x += 1;
        break;
    }
}

const Position Brick::getBoardCoordinate() const
{
    return _boardCoordinate;
}

std::vector<Position> Brick::getBrickCoordinates() const
{
    return _brickCoordinates;
}
const Shape Brick::getShape() const
{
    return _shape;
}

