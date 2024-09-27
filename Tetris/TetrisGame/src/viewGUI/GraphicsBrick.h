#ifndef GRAPHICS_BRICK_H
#define GRAPHICS_BRICK_H

#include <QGraphicsItemGroup>
#include <QGraphicsRectItem>
#include <QPainter>
#include "../model/Brick.h"

/*!
 * \brief The GraphicsBrick class
 * represent the different bricks in the view
 */
class GraphicsBrick : public QGraphicsItemGroup {
private:
    std::vector<QGraphicsRectItem*> _brickBlocks;
    const int _blockSize {};

private:
    void clearBrick();

public:
    GraphicsBrick();
    ~GraphicsBrick() = default;
    void setBrick(const Brick& brick, int boardHeight);

};


#endif // GRAPHICS_BRICK_H
