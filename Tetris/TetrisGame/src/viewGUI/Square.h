#ifndef SQUARE_H
#define SQUARE_H

#include <QGraphicsRectItem>
#include <QBrush>
#include <QPen>

/*!
 * \brief The Square class
 * It is used to create the grid and interact with the model elements by changing color.
 */
class Square : public QGraphicsRectItem
{
public:
    Square(qreal x, qreal y, qreal size, QGraphicsItemGroup *parent = nullptr);
    void setColor(const QColor &color);
};


#endif // SQUARE_H
