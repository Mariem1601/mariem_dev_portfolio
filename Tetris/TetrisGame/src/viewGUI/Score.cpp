#include "Score.h"

/*!
 * \brief Constructs a Score object.
 * \param parent The parent QGraphicsItem.
 */
Score::Score(QGraphicsItem *parent)
    :  QGraphicsTextItem(parent)
    , _score {0}
{
    setPlainText(QString("Score: ") + QString::number(_score));
    setDefaultTextColor(Qt::blue);
    setFont(QFont("Times", 16));
}

/*!
 * \brief Updates the score displayed in the Score object.
 * \param game Pointer to the Game object containing the current score.
 */
void Score::update(Game *game)
{
    _score = game->getScore();
    setPlainText(QString("Score: ") + QString::number(_score));
}
