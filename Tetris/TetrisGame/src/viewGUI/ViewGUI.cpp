#include "ViewGUI.h"
#include "initGameView/InputMessage.h"
#include "../ConstantsUtils.h"

/*!
 * \brief Constructor for the ViewGUI class.
 * \param parent The parent QWidget.
 */
ViewGUI::ViewGUI(QWidget* parent)
    : QGraphicsView(parent)
    , _settings {std::make_unique<Settings>(this)}
    , _timer {new QTimer()}
    , _msDefaultTime {1000}
{

    setHorizontalScrollBarPolicy(Qt::ScrollBarAlwaysOff);
    setVerticalScrollBarPolicy(Qt::ScrollBarAlwaysOff);
    setFixedSize(800, 800);

    connect(_settings.get(), &Settings::sendSettings, this, &ViewGUI::handleSettings);
    _settings->show();
}

/*!
 * \brief Sets the time speed based on the game level.
 * \param level The game level.
 */
void ViewGUI::setTimeSpeed(int level)
{
    int reducedTime {};
    if (level <= c_speedMaxLevel)
    {
        reducedTime = _msDefaultTime / pow(c_speedFactor, level - 1);
    }
    else
    {
        reducedTime = _msDefaultTime / pow(c_speedFactor, c_speedMaxLevel);
    }

    _timer.setInterval(reducedTime);
}

/*!
 * \brief Ends the game and displays a message.
 */
void ViewGUI::endGame()
{
    _mainPane -> stopTimer();
    _timer.stop();
    InputMessage * inputMessage {new InputMessage("Game Over")};
    connect(inputMessage, &InputMessage::cancelPressed, this, &ViewGUI::close);
    inputMessage -> show();
}

/*!
 * \brief Overrides the keyPressEvent function to handle key events.
 * \param event The key event.
 */
void ViewGUI::keyPressEvent(QKeyEvent *event)
{
    switch (event->key()) {
    case Qt::Key_Left:
        emit moveLeftPressed();
        break;
    case Qt::Key_Right:
        emit moveRightPressed();
        break;
    case Qt::Key_Down:
        emit moveDownPressed();
        break;
    case Qt::Key_D:
        emit dropPressed();
        break;
    case Qt::Key_R:
        emit rotatePressed();
        break;
    case Qt::Key_C:
        emit counterRotatePressed();
        break;
    }
}

/*!
 * \brief Slot to handle the settings received from the settings dialog.
 * \param level The game level.
 * \param height The height of the game board.
 * \param width The width of the game board.
 * \param random Flag indicating whether to fill the board randomly.
 */
void ViewGUI::handleSettings(int level, int height, int width, bool random)
{

    _settings->close();

    _mainPane = std::make_unique<MainPane>(height, width);
    this->setScene(_mainPane.get());
    emit gameSettings(level, height, width, random);
    this->show();
    _timer.setInterval(_msDefaultTime);
    _timer.start();

}

MainPane* ViewGUI::getMainPane()
{
    return _mainPane.get();
}



const QTimer* ViewGUI::getTimer() const
{
    return &_timer;
}

