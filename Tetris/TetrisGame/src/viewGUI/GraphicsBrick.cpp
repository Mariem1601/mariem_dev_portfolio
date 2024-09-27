#include "GraphicsBrick.h"
#include <QPen>
#include "ColorUtils.h"
#include "../ConstantsUtils.h"


/*!
 * \brief Constructs a GraphicsBrick object.
 */
GraphicsBrick::GraphicsBrick() : _blockSize {c_blockSize}
{}

/*!
 * \brief Sets the brick to be displayed.
 * \param brick The brick object to display.
 * \param boardHeight The height of the game board.
 */
void GraphicsBrick::setBrick(const Brick& brick, int boardHeight)

{
    clearBrick();

    std::vector<Position> brickCoordinates = brick.getBrickCoordinates();

    QColor color = ColorUtils::getColorForShape(brick.getShape());

    for (const auto& position : brickCoordinates) {
        int xCoordinate {position.x + brick.getBoardCoordinate().x};
        int yCoordinate {boardHeight - (position.y + brick.getBoardCoordinate().y)};
        QGraphicsRectItem *block = new QGraphicsRectItem(xCoordinate * _blockSize,
                                                         yCoordinate * _blockSize,
                                                         _blockSize, _blockSize);
        block->setBrush(color);
        block->setPen(QPen(Qt::gray));
        addToGroup(block);
        _brickBlocks.push_back(block);
    }
}


//! Clears the displayed brick from the scene.
void GraphicsBrick::clearBrick() {
    for (QGraphicsRectItem* block : _brickBlocks) {
        removeFromGroup(block);
        delete block;
    }
    _brickBlocks.clear();
}


