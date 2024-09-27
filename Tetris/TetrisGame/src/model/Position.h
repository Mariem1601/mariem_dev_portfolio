#ifndef POSITION_H
#define POSITION_H

//! represente an (x, y) coordinate
struct Position
{
    int x {};
    int y {};

    bool operator==(const Position& other) const {
        return x == other.x && y == other.y;
    }

    bool operator!=(const Position& other) const {
        return !(*this == other);
    }
};

#endif // POSITION_H
