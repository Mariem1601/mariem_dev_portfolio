#ifndef SCORE_H
#define SCORE_H

#include <QGraphicsTextItem>
#include <qfont.h>
#include "../model/Game.h"

/*!
 * \brief The Score class
 * display the score
 */
class Score : public QGraphicsTextItem
{
private:
    int _score {};
public:
    Score(QGraphicsItem * parent = nullptr);
    void update(Game* game);
};

#endif // SCORE_H
