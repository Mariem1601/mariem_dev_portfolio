#ifndef BOARD_H
#define BOARD_H

#include "Brick.h"

/**
 * @brief The Board class is where the game take place,
 *          it's represented by a 2D vector, a height and a width
 */
class Board
{
private:
    /*!
       we will use std::vector instead of list, because it's not efficent when
       we want to check if we can move or rotate,
       list don't have random access memory so when we want to know if a brick can
       change std::list to std::vector
     */
    std::vector<std::vector<Shape>> _lines {};  //! the representation of board
    int _height {};
    int _width {};
private:
    void brickBoardInitiator();
    bool checkRotation(const Position& blockPosition
                        , const Position& centerCoordinate
                        , Rotation r);
    bool checkRotUp(Position& currentPos
                        , const Position& finishPos
                        , const Position& centerCoordinate);
    bool checkRotDown(Position& currentPos
                        , const Position& finishPos
                        , const Position& centerCoordinate);
    bool checkRotRight(Position& currentPos
                        , const Position& finishPos
                        , const Position& centerCoordinate);
    bool checkRotLeft(Position& currentPos
                        , const Position& finishPos
                        , const Position& centerCoordinate);
    bool checkUp(const Position& blockPosition
                        , const Position& centerCoordinate) const;
    bool checkDown(const Position& blockPosition
                        , const Position& centerCoordinate) const;
    bool checkLeft(const Position& blockPosition
                        , const Position& centerCoordinate) const;
    bool checkRight(const Position& blockPosition
                        , const Position& centerCoordinate) const;
    bool dynamicRotation(Position& currentPosition
                        , const Position& newPosition
                        , const Position& centerCoordinate
                        , Rotation r);
    void clearTopLine(int nbClear);
public:
    explicit Board(bool randomize, int height = 20, int width =10);
    ~Board() = default;
    bool canMove(const Brick& brick, const Direction& d) const;
    bool canRotate(const Brick& brick, const Rotation& r);
    void insertBrick(const Brick& brick);
    int deleteLinesCompleted();
    bool searchDropInsert(Brick& brick, bool randomize);
    bool canBeInserted(const Brick& brick);
    void alignBrickBoard(Brick& brick);
    bool isEmpty() const;

    /*! it was initialy the method: bool get(int row, int col)
        we decide to change its name with a more understanding name
        we decided to change the return type to Shape because initialy,
        _line was a vector<bool>
     */
    const Shape& getBlock(int row, int col) const;
    const int getHeight() const;
    const int getWidth() const;
};



#endif // BOARD_H
