#ifndef GAME_H
#define GAME_H

#include "Bag.h"
#include "Board.h"
#include "../util/Observable.h"
#include <chrono>


/*!
 * \brief The Game class    facade class for game play
 */
class Game : public Observable
{
private:
    Bag* const _bag {Bag::getInstance()};
    Brick _brick;
    Board _board;
    int _score {};
    int _level {};
    int _linesCompleted {};
    std::chrono::system_clock::time_point _startingTime;

    void checkEndCollision(bool canMove, Direction d);
    void addNewBrick();
    void updateScore(int drop, int nbLines);
    void updateLevel();
public:
    explicit Game(int level = 1
                , bool randomize = false
                , int height = 20
                , int width = 10);
    ~Game() override = default;
    void init();
    void move(Direction d);
    void drop();
    void rotate(Rotation r);
    bool endGame();
    void increaseScore();

    const Board& getBoard() const;
    const Brick& getBrick() const;
    const int getLevel() const;
    const int getScore() const;
    const int getNbLinesCompleted() const;
};

#endif // GAME_H
