cmake_minimum_required(VERSION 3.5)

set(CMAKE_AUTOUIC ON)
set(CMAKE_AUTOMOC ON)
set(CMAKE_AUTORCC ON)

add_executable(Tetris_App main.cpp
        view/View.h view/View.cpp
        model/Bag.h model/Bag.cpp
        model/Databrick.h model/Databrick.cpp
        model/Shape.h
        model/Position.h
        model/Direction.h
        model/Rotation.h
        model/Brick.h model/Brick.cpp
        model/Board.h model/Board.cpp
        model/Game.h model/Game.cpp
        util/Observer.h
        util/Observable.h util/Observable.cpp controller/Controller.h controller/Controller.cpp
        viewGUI/GraphicsBrick.cpp
        viewGUI/GraphicsBrick.h
        viewGUI/Mainpane.h viewGUI/Mainpane.cpp
        viewGUI/Gridview.h viewGUI/Gridview.cpp
        viewGUI/Level.h viewGUI/Level.cpp
        viewGUI/Linescompleted.h viewGUI/Linescompleted.cpp

        viewGUI/Score.h viewGUI/Score.cpp
        viewGUI/Square.h viewGUI/Square.cpp
        controllerGUI/ControllerGUI.h controllerGUI/ControllerGUI.cpp
        viewGUI/ViewGUI.h viewGUI/ViewGUI.cpp

        viewGUI/initGameView/PersonalizedSettings.cpp viewGUI/initGameView/PersonalizedSettings.h
        viewGUI/initGameView/RadioChoice.cpp viewGUI/initGameView/RadioChoice.h
        viewGUI/initGameView/Settings.cpp viewGUI/initGameView/Settings.h
        viewGUI/ColorUtils.h viewGUI/ColorUtils.cpp
        ConstantsUtils.h
        viewGUI/DisplayTimer.h viewGUI/DisplayTimer.cpp
        viewGUI/initGameView/InputMessage.h viewGUI/initGameView/InputMessage.cpp
    )

find_package (QT NAMES Qt6 Qt5 REQUIRED COMPONENTS Widgets )
find_package ( Qt${QT_VERSION_MAJOR} REQUIRED COMPONENTS Widgets )
target_link_libraries(Tetris_App PRIVATE
    Qt${QT_VERSION_MAJOR}::Widgets )

install(TARGETS Tetris_App
    LIBRARY DESTINATION ${CMAKE_INSTALL_LIBDIR}
)



