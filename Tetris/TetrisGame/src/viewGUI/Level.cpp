#include "Level.h"

/*!
 * \brief Constructs a Level object.
 * \param parent The parent QGraphicsItem.
 */
Level::Level(QGraphicsItem *parent)
    :  QGraphicsTextItem(parent)
    , _level {0}
{
    setPlainText(QString("Level: ") + QString::number(_level));
    setDefaultTextColor(Qt::blue);
    setFont(QFont("Times", 16));
}

/*!
 * \brief Updates the level displayed based on the game's level.
 * \param game The game object.
 */
void Level::update(Game *game)
{
    _level = game -> getLevel();
    setPlainText(QString("Level: ") + QString::number(_level));
}
