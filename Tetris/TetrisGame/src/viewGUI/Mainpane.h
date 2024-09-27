#ifndef MAINPANE_H
#define MAINPANE_H

#include <QGraphicsScene>
#include <memory>
#include "../util/Observer.h"
#include "GraphicsBrick.h"
#include "Gridview.h"
#include "Score.h"
#include "Level.h"
#include "Linescompleted.h"
#include "DisplayTimer.h"


class GridView;
class Score;
class Level;
class LinesCompleted;
class GraphicsBrick;
/*!
 * \brief The MainPane class
 * the scene that contains game play
 */
class MainPane : public QGraphicsScene, public Observer
{
private:
    std::unique_ptr<GridView> _gridView;
    std::unique_ptr<Score> _score;
    std::unique_ptr<Level> _level;
    std::unique_ptr<LinesCompleted> _linesCompleted;
    std::unique_ptr<GraphicsBrick> _brick;
    std::unique_ptr<DisplayTimer> _timer;

public:
    MainPane(int height, int width, QObject *parent = nullptr);
    void update(void* object) override;
    void stopTimer();
};

#endif // MAINPANE_H

