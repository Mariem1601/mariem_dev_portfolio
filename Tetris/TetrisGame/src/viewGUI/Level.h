#ifndef LEVEL_H
#define LEVEL_H

#include <QGraphicsTextItem>
#include "../model/Game.h"
#include <QFont>

class Game;

/*!
 * \brief The Level class
 * display the level
 */
class Level : public QGraphicsTextItem
{
private:
    int _level {};
public:
    Level(QGraphicsItem * parent = nullptr);;
    void update(Game* game);;

};

#endif // LEVEL_H
