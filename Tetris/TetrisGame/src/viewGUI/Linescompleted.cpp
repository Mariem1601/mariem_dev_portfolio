#include "Linescompleted.h"
#include "../ConstantsUtils.h"


/*!
 * \brief Constructs a LinesCompleted object.
 * \param parent The parent QGraphicsItem.
 */
LinesCompleted::LinesCompleted(QGraphicsItem *parent)
    :  QGraphicsTextItem(parent)
    , _nbLines {0}
{
    setPlainText(QString("Lines: ") + QString::number(_nbLines));
    setDefaultTextColor(Qt::blue);
    setFont(QFont("Times", 16));
}

/*!
 * \brief Updates the number of lines completed displayed based on the game's level and number of lines completed.
 * \param game The game object.
 */
void LinesCompleted::update(Game *game)
{
    _nbLines = (game ->getLevel() - 1) * c_updateLevel + game -> getNbLinesCompleted();
    setPlainText(QString("Lines: ") + QString::number(_nbLines));
}
