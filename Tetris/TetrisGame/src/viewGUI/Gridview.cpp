#include "ColorUtils.h"
#include "Gridview.h"
#include "../ConstantsUtils.h"


/*!
 * \brief Constructs a GridView object.
 * \param height The height of the grid.
 * \param width The width of the grid.
 * \param parent The parent QGraphicsItem.
 */
GridView::GridView(int height, int width, QGraphicsItem *parent)
    : QGraphicsItemGroup(), _height {height}, _width {width}, _squareSize {c_blockSize}
{
    drawGrid();
}

/*!
 * \brief Updates the grid based on the provided board.
 * \param board The game board.
 */
void GridView::updateGrid(Board *board)
{
    int height = board -> getHeight();
    int width = board -> getWidth();
    for (int row = 0; row <= height; ++row) {
        for (int col = 0; col <= width; ++col) {
            Shape shape {board -> getBlock(row, col)};
            QColor color {ColorUtils::getColorForShape(shape)};
            _grid[height - row][col] -> setColor(color);
        }
    }
}

//! Draws the grid.
void GridView::drawGrid()
{
    _grid.resize(_height);
    for (int row = 0; row < _height; ++row) {
        _grid[row].resize(_width);
        for (int col = 0; col < _width; ++col) {
            std::unique_ptr<Square> square = std::make_unique<Square>(col * _squareSize, row * _squareSize, _squareSize, this);
            this -> addToGroup(square.get());
            _grid[row][col] = std::move(square);
        }
    }
}

int GridView::getWidthSize()
{
    return _width * _squareSize;
}
