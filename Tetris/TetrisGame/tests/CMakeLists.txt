cmake_minimum_required(VERSION 3.5)

add_executable(Tetris_Tests
        testBrick.cpp
        testBoard.cpp
        testGame.cpp
        ../src/model/Bag.cpp
        ../src/model/Brick.cpp
        ../src/model/Databrick.cpp
        ../src/model/Board.cpp
        ../src/model/Game.cpp
        ../src/util/Observable.cpp
)

target_include_directories(Tetris_Tests PRIVATE
    ../src
)

Include(FetchContent)

FetchContent_Declare(
  Catch2
  GIT_REPOSITORY https://github.com/catchorg/Catch2.git
  GIT_TAG        v3.3.1 # or a later release
)

FetchContent_MakeAvailable(Catch2)
#list(APPEND CMAKE_MODULE_PATH ${catch2_SOURCE_DIR}/extras)

target_link_libraries(Tetris_Tests Catch2::Catch2WithMain)
