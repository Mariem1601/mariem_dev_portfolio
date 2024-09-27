#ifndef GRIDVIEW_H
#define GRIDVIEW_H

#include <QGraphicsItem>
#include "Square.h"
#include "../model/Shape.h"
#include "../model/Board.h"

class Board;
class Square;

/*!
 * \brief The GridView class
 * simulates the game grid in the view
 */
class GridView : public QGraphicsItemGroup
{
private:
    int _height {};
    int _width {};
    qreal _squareSize {};
    std::vector<std::vector<std::unique_ptr<Square>>> _grid;

private:
    void drawGrid();

public:
    GridView(int height, int width, QGraphicsItem  *parent = nullptr);    
    void updateGrid(Board* board);
    int getWidthSize();
};

#endif // GRIDVIEW_H
