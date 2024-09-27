#ifndef CONTROLLERGUI_H
#define CONTROLLERGUI_H

#include <QObject>
#include <memory>
#include "../viewGUI/ViewGUI.h"
#include "../model/Game.h"

class ControllerGUI : public QObject {
    Q_OBJECT
private:
    std::unique_ptr<ViewGUI> _view;
    std::unique_ptr<Game> _game;

public:
    ControllerGUI();
    ~ControllerGUI();

public slots:
    void handleMoveLeft();
    void handleMoveRight();
    void handleMoveDown();
    void handleDrop();
    void handleRotate();
    void handleCounterRotate();
    void handleGameCreation(int level, int height, int width, bool random);
    void sendMove();
};

#endif // CONTROLLERGUI_H
