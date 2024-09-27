#include "Mainpane.h"
#include "GraphicsBrick.h"
#include "../model/Game.h"
#include "../model/Board.h"
#include "../model/Brick.h"
#include "../ConstantsUtils.h"

/*!
 * \brief Constructs a MainPane object.
 * \param height The height of the grid.
 * \param width The width of the grid.
 * \param parent The parent QObject.
 */
MainPane::MainPane(int height, int width, QObject *parent)
    : QGraphicsScene(parent)
    , _gridView {std::make_unique<GridView>(height, width)}
    , _score {std::make_unique<Score>()}
    , _level {std::make_unique<Level>()}
    , _linesCompleted {std::make_unique<LinesCompleted>()}
    , _brick {std::make_unique<GraphicsBrick>()}
    , _timer {std::make_unique<DisplayTimer>()}
{
    this -> addItem(_gridView.get());
    this -> addItem(_score.get());
    this -> addItem(_level.get());
    this -> addItem(_linesCompleted.get());
    this -> addItem(_brick.get());
    this -> addItem(_timer.get());

    int size {_gridView -> getWidthSize()};
    _score -> setPos(_score -> x() + size + c_xShift, _score -> y());
    _level -> setPos(_level -> x() + size + c_xShift, _level -> y() + c_yShift);
    _linesCompleted -> setPos(_linesCompleted -> x() + size + c_xShift, _linesCompleted -> y() + (2 * c_yShift));
    _timer -> setPos(_timer -> x() + size + c_xShift, _timer -> y() + (3 * c_yShift));
}

/*!
 * \brief Updates the MainPane with the current game state.
 * \param object Pointer to the Game object containing the current state.
 */
void MainPane::update(void* object)
{
    Game* game {static_cast<Game*>(object)};
    std::unique_ptr<Board> const board {std::make_unique<Board>(game->getBoard())};
    std::unique_ptr<Brick> const brick {std::make_unique<Brick>(game->getBrick())};
    _brick -> setBrick(*brick.get(), board -> getHeight());
    _gridView -> updateGrid(board.get());
    _score ->update(game);
    _level ->update(game);
    _linesCompleted ->update(game);
}

/*!
 * \brief Stops the timer in the MainPane.
 */
void MainPane::stopTimer()
{
    _timer -> stopTimer();
}
