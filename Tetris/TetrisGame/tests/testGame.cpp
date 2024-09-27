#include <catch2/catch_test_macros.hpp>
#include "../src/model/Game.h"

TEST_CASE("Move brick in Game") {
    Game game{};

    SECTION("Move brick down") {
        Brick initialBrick = game.getBrick();
        game.move(Direction::down);
        Brick updatedBrick = game.getBrick();

        REQUIRE(updatedBrick.getBoardCoordinate().y < initialBrick.getBoardCoordinate().y);
        REQUIRE(game.getBoard().isEmpty());
    }

    SECTION("Move brick left") {
        Brick initialBrick = game.getBrick();
        game.move(Direction::left);
        Brick updatedBrick = game.getBrick();

        REQUIRE(updatedBrick.getBoardCoordinate().x < initialBrick.getBoardCoordinate().x);
        REQUIRE(game.getBoard().isEmpty());
    }
}

TEST_CASE("Drop brick in Game") {
    Game game{};

    SECTION("Drop brick is inserted") {
        game.drop();

        REQUIRE_FALSE(game.getBoard().isEmpty());
    }
}

