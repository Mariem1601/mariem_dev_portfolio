#include <catch2/catch_test_macros.hpp>
#include "../src/model/Databrick.h"
#include "../src/model/Brick.h"


TEST_CASE("Brick Rotation") {
    Shape testShape = Shape::sShape;
    Brick brick(testShape, 5, 3);

    SECTION("Clockwise Rotation") {
        brick.rotate(Rotation::clockWise);

        std::vector<Position> expectedCoords = {
            {0, 0},   // Original: {0, 0}
            {0, -1},   // Original: {1, 0}
            {-1, 0},   // Original: {0, -1}
            {-1, 1}   // Original: {-1, -1}
        };

        REQUIRE(brick.getBrickCoordinates() == expectedCoords);
    }

    SECTION("CounterClockwise Rotation") {
        brick.rotate(Rotation::counterClockWise);

        std::vector<Position> expectedCoords = {
            {0, 0},   // Original: {0, 0}
            {0, 1},  // Original: {1, 0}
            {1, 0},  // Original: {0, -1}
            {1, -1}   // Original: {-1, -1}
        };

        REQUIRE(brick.getBrickCoordinates() == expectedCoords);
    }
}


TEST_CASE("Brick Movement") {
    Shape testShape = Shape::sShape;
    Brick brick(testShape, 5, 6);

    SECTION("Moving Down") {
        brick.updateBrickCoordinate(Direction::down);
        Position expectedPos = {5, 5};
        REQUIRE(brick.getBoardCoordinate() == expectedPos);
    }

    SECTION("Moving Left") {
        brick.updateBrickCoordinate(Direction::left);
        Position expectedPos = {4, 6};
        REQUIRE(brick.getBoardCoordinate() == expectedPos);
    }

    SECTION("Moving Right") {
        brick.updateBrickCoordinate(Direction::right);
        Position expectedPos = {6, 6};
        REQUIRE(brick.getBoardCoordinate() == expectedPos);
    }
}
