#ifndef BRICK_H
#define BRICK_H

#include "Databrick.h"
#include "Rotation.h"
#include "Direction.h"

/*!
 * \brief The Brick class a brick can have different shape
 *          , a starting position on the board
 *          and the relative position of the blocks
 *          it contains (position determined from a central block)
 */
class Brick
{
private:
    Shape _shape {};
    std::vector<Position> _brickCoordinates {}; //! relative coordinate of each block
    Position _boardCoordinate {};               //! absolute coordinate (on the board) of the center block
public:
    Brick(Shape shape, int xBoard, int yBoard);
    ~Brick() = default;
    void rotate(const Rotation& r);
    void updateBrickCoordinate(Direction d);

    const Position getBoardCoordinate() const;
    std::vector<Position> getBrickCoordinates() const;
    const Shape getShape() const;
};

#endif // BRICK_H
