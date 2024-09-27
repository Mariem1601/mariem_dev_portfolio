#ifndef VIEW_H
#define VIEW_H

#include "../util/Observer.h"
#include "../model/Game.h"

/*!
 * \brief The View class consol view of tetris game
 */
class View : public Observer
{

private:
    void displayBloc(Shape shape) const;
    void searckBrickZone( Position& upperLeft
            , Position& lowerRight
            , const Brick& brick);
    bool isBrickCoordinate(int x, int y, const Brick& brick);
    void displayLevel(int level);
    void displayScore(int score);

public:
    View() = default;
    void displayWelcome();
    void displayHelp();
    void displayEndGame(int score);
    void displayBoard(Game* const model);
    void update(void* object) override;
};

#endif // VIEW_H
