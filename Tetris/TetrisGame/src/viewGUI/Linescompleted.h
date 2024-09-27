#ifndef LINESCOMPLETED_H
#define LINESCOMPLETED_H

#include <QGraphicsTextItem>
#include "../model/Game.h"
#include <QFont>

class Game;

/*!
 * \brief The LinesCompleted class
 * display the total numbers of lines completed
 */
class LinesCompleted : public QGraphicsTextItem
{
private:
    int _nbLines {};
public:
    LinesCompleted(QGraphicsItem * parent = nullptr);;
    void update(Game* game);;
};

#endif // LINESCOMPLETED_H
