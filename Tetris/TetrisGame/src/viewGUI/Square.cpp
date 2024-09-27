#include "Square.h"

/*!
 * \brief Constructs a Square object with the specified position and size.
 * \param x The x-coordinate of the square's top-left corner.
 * \param y The y-coordinate of the square's top-left corner.
 * \param size The size (width and height) of the square.
 * \param parent The parent QGraphicsItemGroup.
 */
Square::Square(qreal x, qreal y, qreal size, QGraphicsItemGroup *parent) : QGraphicsRectItem(x, y, size, size, parent)
{
    setPen(QPen(Qt::gray));
}

/*!
 * \brief Sets the color of the square.
 * \param color The color to set.
 */
void Square::setColor(const QColor &color)
{
    setBrush(color);
}
