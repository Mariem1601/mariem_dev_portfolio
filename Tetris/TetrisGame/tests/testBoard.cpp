#include <catch2/catch_test_macros.hpp>
#include "../src/model/Board.h"

TEST_CASE("Test of Board initialization and Brick insertion") {
    Board board {false, 30, 20};

    SECTION("Test Board Initialization with random bricks") {
        Board board2 {true, 20, 10};
        REQUIRE_FALSE(board2.isEmpty());
    }

    SECTION("Test Brick Insertion") {
        Brick brick {Shape::tShape, 4, 4};
        board.insertBrick(brick);

        REQUIRE(board.getBlock(4, 4) == Shape::tShape);
        REQUIRE(board.getBlock(4, 3) == Shape::tShape);
        REQUIRE(board.getBlock(5, 4) == Shape::tShape);
        REQUIRE(board.getBlock(4, 5) == Shape::tShape);

    }

    SECTION("Cannot insert a brick on top of another brick") {
        Brick brick1 {Shape::tShape, 4, 4};
        board.insertBrick(brick1);

        Brick brick2 {Shape::tShape, 4, 4};
        REQUIRE_THROWS_AS(board.insertBrick(brick2), std::runtime_error);
    }

}

TEST_CASE("Test canMove method") {
    Board board(false,20, 10);

    SECTION("Test canMove when brick is at the top right border of the board") {
        Brick brick(Shape::tShape, 8, 18); // top right
        REQUIRE_FALSE(board.canMove(brick, Direction::right));
    }

    SECTION("Test canMove when brick is at the top left border of the board") {
        Brick brick(Shape::tShape, 1, 18); // top left
        REQUIRE_FALSE(board.canMove(brick, Direction::left));
    }

    SECTION("Test canMove when brick is at the bottom  of the board") {
        Brick brick(Shape::tShape, 5, 0); // bottom rigth
        REQUIRE_FALSE(board.canMove(brick, Direction::down));
    }

    SECTION("Test canMove when there is another adjacent brick") {
        Brick brick1(Shape::tShape, 5, 5);
        board.insertBrick(brick1);

        Brick brick2(Shape::tShape, 2, 5); // on the left of brick1
        Brick brick3(Shape::tShape, 5, 7); // on the top of brick1
        Brick brick4(Shape::tShape, 8, 5); // on the right of brick1

        REQUIRE_FALSE(board.canMove(brick2, Direction::right));
        REQUIRE_FALSE(board.canMove(brick3, Direction::down));
        REQUIRE_FALSE(board.canMove(brick4, Direction::left));
    }
}

TEST_CASE("Test canRotate method") {
    Board board(false,20, 10);

    SECTION("Test canRotate when Z brick is at the top border of the board") {
        Brick brick(Shape::zShape, 5, 19); // top

        REQUIRE_FALSE(board.canRotate(brick, Rotation::clockWise));
        REQUIRE_FALSE(board.canRotate(brick, Rotation::counterClockWise));
    }

    SECTION("Test canMove when S brick is at the top border of the board") {
        Brick brick(Shape::sShape, 5, 19); // top

        REQUIRE_FALSE(board.canRotate(brick, Rotation::clockWise));
        REQUIRE_FALSE(board.canRotate(brick, Rotation::counterClockWise));
    }

    SECTION("Test canMove when J brick is at the right border of the board") {
        Brick brick(Shape::jShape, 9, 16); // rigth

        REQUIRE_FALSE(board.canRotate(brick, Rotation::clockWise));
        REQUIRE_FALSE(board.canRotate(brick, Rotation::counterClockWise));
    }

    SECTION("Test canMove when L brick is at the left border of the board") {
        Brick brick(Shape::lShape, 0, 16);

        REQUIRE_FALSE(board.canRotate(brick, Rotation::clockWise));
        REQUIRE_FALSE(board.canRotate(brick, Rotation::counterClockWise));
    }

    SECTION("Test canMove when I brick is at the top or the bottom border of the board") {
        Brick brick1(Shape::iShape, 2, 19);
        Brick brick2(Shape::iShape, 2, 0);

        REQUIRE_FALSE(board.canRotate(brick1, Rotation::clockWise));
        REQUIRE_FALSE(board.canRotate(brick1, Rotation::counterClockWise));
        REQUIRE_FALSE(board.canRotate(brick2, Rotation::clockWise));
        REQUIRE_FALSE(board.canRotate(brick2, Rotation::counterClockWise));
    }

    SECTION("Test canMove when T brick is at the bottom border of the board") {
        Brick brick(Shape::tShape, 5, 0);

        REQUIRE_FALSE(board.canRotate(brick, Rotation::clockWise));
        REQUIRE_FALSE(board.canRotate(brick, Rotation::counterClockWise));
    }

    SECTION("Test canMove when C brick") {
        Brick brick(Shape::cShape, 5, 1);

        REQUIRE(board.canRotate(brick, Rotation::clockWise));
        REQUIRE(board.canRotate(brick, Rotation::counterClockWise));
    }
}

TEST_CASE("Test deleteLinesCompleted method") {
    Board board(false, 20, 10);

    SECTION("Test no completed lines to delete") {
        // 19| . T Z Z Z Z Z Z . . |
        // 18| T T T Z Z Z Z Z Z . |
        //     0 1 2 3 4 5 6 7 8 9
        board.insertBrick(Brick(Shape::tShape, 1, 18));
        board.insertBrick(Brick(Shape::zShape, 3, 19));
        board.insertBrick(Brick(Shape::zShape, 5, 19));
        board.insertBrick(Brick(Shape::zShape, 7, 19));

        REQUIRE(board.deleteLinesCompleted() == 0);
    }

    SECTION("Test deleting one completed line on the top of the board") {
        // 19| . T Z Z Z Z . . T . |
        // 18| T T T Z Z Z Z T T T |
        //     0 1 2 3 4 5 6 7 8 9
        board.insertBrick(Brick(Shape::tShape, 1, 18));
        board.insertBrick(Brick(Shape::zShape, 3, 19));
        board.insertBrick(Brick(Shape::zShape, 5, 19));
        board.insertBrick(Brick(Shape::tShape, 8, 18));

        REQUIRE(board.deleteLinesCompleted() == 1);
    }

    SECTION("Test deleting two successive completed lines") {
        // 11 | i i i i i i i i C C |
        // 10 | i i i i i i i i C C |
        //      0 1 2 3 4 5 6 7 8 9
        board.insertBrick(Brick(Shape::iShape, 1, 10));
        board.insertBrick(Brick(Shape::iShape, 5, 10));
        board.insertBrick(Brick(Shape::iShape, 1, 11));
        board.insertBrick(Brick(Shape::iShape, 5, 11));
        board.insertBrick(Brick(Shape::cShape, 8, 11));

        REQUIRE(board.deleteLinesCompleted() == 2);
    }

    SECTION("Test deleting two non successive completed lines") {
        // 7 | . . . . . . . . . . |
        // 6 | . . . . . . . . C C |
        // 5 | i i i i i i i i C C |
        // 4 | . . S S C C i i i i |
        // 3 | . S S . C C . . C C |
        // 2 | i i i i i i i i C C |
        // 1 | . . . . . . . . . . |
        // 0 | . . . . . . . . . . |
        //     0 1 2 3 4 5 6 7 8 9

        board.insertBrick(Brick(Shape::iShape, 1, 5));
        board.insertBrick(Brick(Shape::iShape, 5, 5));
        board.insertBrick(Brick(Shape::cShape, 8, 6));
        board.insertBrick(Brick(Shape::iShape, 7, 4));
        board.insertBrick(Brick(Shape::sShape, 2, 4));
        board.insertBrick(Brick(Shape::cShape, 4, 4));
        board.insertBrick(Brick(Shape::iShape, 1, 2));
        board.insertBrick(Brick(Shape::iShape, 5, 2));
        board.insertBrick(Brick(Shape::cShape, 8, 3));

        REQUIRE(board.deleteLinesCompleted() == 2);
    }

}