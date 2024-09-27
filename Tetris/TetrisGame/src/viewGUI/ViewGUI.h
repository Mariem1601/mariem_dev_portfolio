#ifndef VIEWGUI_H
#define VIEWGUI_H

#include <QGraphicsView>
#include <QKeyEvent>
#include "Mainpane.h"
#include "initGameView/Settings.h"
#include <memory>
#include <QTimer>

/*!
 * \brief The ViewGUI class
 * represents the graphical user interface for the Tetris game.
 *
 * It inherits from QGraphicsView and acts as the main view for the game.
 */
class ViewGUI : public QGraphicsView {
    Q_OBJECT

private:
    std::unique_ptr<MainPane> _mainPane;
    std::unique_ptr<Settings> _settings;
    QTimer _timer;
    int _msDefaultTime {};

public:
    ViewGUI(QWidget* parent = nullptr);
    void setTimeSpeed(int level);
    void endGame();
    MainPane* getMainPane();
    const QTimer* getTimer() const;

protected:
    void keyPressEvent(QKeyEvent *event) override;

public slots:
    void handleSettings(int level, int height, int width, bool random);

signals:
    void moveLeftPressed();
    void moveRightPressed();
    void moveDownPressed();
    void dropPressed();
    void rotatePressed();
    void counterRotatePressed();
    void gameSettings(int level, int height, int width, bool random);
};

#endif // VIEWGUI_H
