#include <QApplication>
#include "controller/Controller.h"
#include "controllerGUI/ControllerGUI.h"


int main(int argc, char *argv[])
{
    QApplication application(argc, argv);
    ControllerGUI controller;

    return application.exec();
}
