package g61692.qwirkle.model;

import g61692.qwirkle.view.View;
import org.junit.jupiter.api.*;

import static g61692.qwirkle.view.View.display;
import static org.junit.jupiter.api.Assertions.*;
import static g61692.qwirkle.model.Color.*;
import static g61692.qwirkle.model.Direction.*;
import static g61692.qwirkle.model.Shape.*;
import static g61692.qwirkle.model.QwirkleTestUtils.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GridTest {

    @Nested
    public class GridTestMethods {

        private Grid grid;

        @BeforeEach
        void setUp() {
            grid = new Grid();
        }

        @Test
        void firstAdd_one_tile() {
            var tile = new Tile(BLUE, CROSS);
            grid.firstAdd(RIGHT, tile);
            assertSame(get(grid, 0, 0), tile);
        }

        @Test
        void firstAdd_cannot_be_called_twice() {
            Tile redcross = new Tile(RED, CROSS);
            Tile reddiamond = new Tile(RED, DIAMOND);
            grid.firstAdd(UP, redcross, reddiamond);
            assertThrows(QwirkleException.class, () -> grid.firstAdd(DOWN, redcross, reddiamond));
        }

        @Test
        void firstAdd_must_be_called_first_simple() {
            Tile redcross = new Tile(RED, CROSS);
            assertThrows(QwirkleException.class, () -> add(grid, 0, 0, redcross));
        }

        @Test
        @DisplayName("get outside the grid should return null, not throw")
        void can_get_tile_outside_virtual_grid() {
            var g = new Grid();
            assertDoesNotThrow(() -> get(g, -250, 500));
            assertNull(get(g, -250, 500));
        }


        @Test
        void firstAdd_RightDirection() {
            Tile[] line = new Tile[]{
                    new Tile(RED, ROUND),
                    new Tile(RED, Shape.SQUARE),
                    new Tile(RED, DIAMOND)
            };
            grid.firstAdd(Direction.RIGHT, line);

            assertEquals(line[0], get(grid, 0, 0));
            assertEquals(line[1], get(grid, 0, 1));
            assertEquals(line[2], get(grid, 0, 2));
        }

        @Test
        void firstAdd_LeftDirection() {
            Grid grid = new Grid();
            Tile[] line = new Tile[]{
                    new Tile(RED, ROUND),
                    new Tile(RED, Shape.SQUARE),
                    new Tile(RED, DIAMOND)
            };
            grid.firstAdd(Direction.LEFT, line);

            assertEquals(line[0], get(grid, 0, 0));
            assertEquals(line[1], get(grid, 0, -1));
            assertEquals(line[2], get(grid, 0, -2));
        }

        @Test
        void firstAdd_UpDirection() {
            Tile[] line = new Tile[]{
                    new Tile(RED, ROUND),
                    new Tile(RED, Shape.SQUARE),
                    new Tile(RED, DIAMOND)
            };
            grid.firstAdd(UP, line);

            assertEquals(line[0], get(grid, 0, 0));
            assertEquals(line[1], get(grid, -1, 0));
            assertEquals(line[2], get(grid, -2, 0));
        }

        @Test
        void firstAdd_DownDirection() {
            Tile[] line = new Tile[]{
                    new Tile(RED, ROUND),
                    new Tile(RED, Shape.SQUARE),
                    new Tile(RED, DIAMOND)
            };
            grid.firstAdd(Direction.DOWN, line);

            assertEquals(line[0], get(grid, 0, 0));
            assertEquals(line[1], get(grid, 1, 0));
            assertEquals(line[2], get(grid, 2, 0));

        }

        @Test
        void firstAdd_gridIsEmpty() {
            assertTrue(grid.isEmpty());
        }

        @Test
        void firstAdd_gridIsNotEmpty() {
            Tile[] line = new Tile[]{
                    new Tile(RED, ROUND),
                    new Tile(RED, Shape.SQUARE),
                    new Tile(RED, DIAMOND)
            };
            grid.firstAdd(Direction.DOWN, line);

            assertFalse(grid.isEmpty());
        }

        @Test
        void firstAdd_TilesNotSameColor_And_NotSameShape() {
            Tile[] line = new Tile[]{
                    new Tile(RED, ROUND),
                    new Tile(RED, Shape.SQUARE),
                    new Tile(Color.BLUE, DIAMOND)
            };

            Assertions.assertThrows(QwirkleException.class, () -> {
                grid.firstAdd(Direction.DOWN, line);
            });

            assertNull(get(grid, 0, 0));
            assertNull(get(grid, 1, 0));
            assertNull(get(grid, 2, 0));
        }

        @Test
        void firstAdd_TilesSameColor_And_SameShape() {
            Tile[] line = new Tile[]{
                    new Tile(Color.BLUE, ROUND),
                    new Tile(RED, Shape.SQUARE),
                    new Tile(Color.BLUE, ROUND)
            };

            Assertions.assertThrows(QwirkleException.class, () -> {
                grid.firstAdd(Direction.DOWN, line);
            });

            assertNull(get(grid, 0, 0));
            assertNull(get(grid, 1, 0));
            assertNull(get(grid, 2, 0));
        }

        @Test
        void firstAdd_PlusDeSixTuiles() {
            Tile[] line = new Tile[]{
                    new Tile(Color.BLUE, ROUND),
                    new Tile(Color.BLUE, Shape.SQUARE),
                    new Tile(Color.BLUE, PLUS),
                    new Tile(Color.BLUE, DIAMOND),
                    new Tile(Color.BLUE, Shape.CROSS),
                    new Tile(Color.BLUE, Shape.STAR),
                    new Tile(Color.BLUE, ROUND),
            };

            Assertions.assertThrows(QwirkleException.class, () -> {
                grid.firstAdd(Direction.DOWN, line);
            });

            assertNull(get(grid, 0, 0));
            assertNull(get(grid, 1, 0));
            assertNull(get(grid, 2, 0));
        }

        @Test
        void add_PlaceFree() {
            Tile[] line = new Tile[]{
                    new Tile(RED, ROUND),
                    new Tile(RED, Shape.SQUARE),
                    new Tile(RED, DIAMOND)
            };
            grid.firstAdd(Direction.DOWN, line);

            Tile tile = new Tile(RED, PLUS);
            add(grid, 3, 0, tile);

            assertEquals(line[0], get(grid, 0, 0));
            assertEquals(line[1], get(grid, 1, 0));
            assertEquals(line[2], get(grid, 2, 0));
            assertEquals(tile, get(grid, 3, 0));
        }

        @Test
        void add_PlaceNotFree() {
            Tile[] line = new Tile[]{
                    new Tile(RED, ROUND),
                    new Tile(RED, Shape.SQUARE),
                    new Tile(RED, DIAMOND)
            };
            grid.firstAdd(Direction.DOWN, line);

            Assertions.assertThrows(QwirkleException.class, () -> {
                add(grid, 0, 0, new Tile(RED, PLUS));
            });
        }

        @Test
        void add_NoTileAround() {
            Tile[] line = new Tile[]{
                    new Tile(RED, ROUND),
                    new Tile(RED, Shape.SQUARE),
                    new Tile(RED, DIAMOND)
            };
            grid.firstAdd(Direction.DOWN, line);

            Assertions.assertThrows(QwirkleException.class, () -> {
                add(grid, 5, 5, new Tile(RED, PLUS));
            });
        }

        @Test
        void add_checkBoardCol_isNotOk() {
            Tile[] lineCol = new Tile[]{
                    new Tile(RED, ROUND),
                    new Tile(RED, Shape.SQUARE),
                    new Tile(RED, DIAMOND)
            };
            grid.firstAdd(Direction.DOWN, lineCol);

            Assertions.assertThrows(QwirkleException.class, () -> {
                add(grid, 3, 0, new Tile(RED, ROUND));
            });
        }

        @Test
        void add_checkBoardRow_isNotOk() {
            Tile[] lineCol = new Tile[]{
                    new Tile(RED, ROUND),
                    new Tile(RED, Shape.SQUARE),
                    new Tile(RED, DIAMOND),
            };
            grid.firstAdd(Direction.RIGHT, lineCol);

            Assertions.assertThrows(QwirkleException.class, () -> {
                add(grid, 0, 3, new Tile(RED, ROUND));
            });
        }

        // ------------------------------- TESTS NRI ---------------------------------
        public static final int INITIAL_ROW = 45;
        public static final int INITIAL_COL = 45;

        @Test
        void gridInitiallyEmpty() {
            var g = new Grid();
            for (int row = -45; row < 45; row++) {
                for (int col = -45; col < 45; col++) {
                    assertNull(get(g, row, col));
                }
            }
        }

        @Test
        @DisplayName("get outside the grid should return null, not throw")
        void canGetOutsideVirtualGrid() {
            var g = new Grid();
            assertDoesNotThrow(() -> get(g, -250, 500));
            assertNull(get(g, -250, 500));
        }

        // simple adds

        @Test
        void addSimpleUP() {
            var g = new Grid();
            g.firstAdd(UP, TILE_RED_CROSS, TILE_RED_DIAMOND);
            assertSame(TILE_RED_CROSS, get(g, 0, 0));
            assertSame(TILE_RED_DIAMOND, get(g, -1, 0));
            assertNull(get(g, 1, 0));
            assertNull(get(g, 0, 1));
            assertNull(get(g, 0, -1));
        }

        @Test
        void addSimpleDOWN() {
            var g = new Grid();
            g.firstAdd(DOWN, TILE_RED_CROSS, TILE_RED_DIAMOND);
            assertSame(TILE_RED_CROSS, get(g, 0, 0));
            assertSame(TILE_RED_DIAMOND, get(g, 1, 0));
            assertNull(get(g, -1, 0));
            assertNull(get(g, 0, 1));
            assertNull(get(g, 0, -1));
        }

        @Test
        void addSimpleLEFT() {
            var g = new Grid();
            g.firstAdd(LEFT, TILE_RED_CROSS, TILE_RED_DIAMOND);
            assertSame(TILE_RED_CROSS, get(g, 0, 0));
            assertSame(TILE_RED_DIAMOND, get(g, 0, -1));
            assertNull(get(g, 1, 0));
            assertNull(get(g, -1, 0));
            assertNull(get(g, 0, 1));
        }

        @Test
        void addSimpleRIGHT() {
            var g = new Grid();
            g.firstAdd(RIGHT, TILE_RED_CROSS, TILE_RED_DIAMOND);
            assertSame(TILE_RED_CROSS, get(g, 0, 0));
            assertSame(TILE_RED_DIAMOND, get(g, 0, 1));
            assertNull(get(g, 1, 0));
            assertNull(get(g, -1, 0));
            assertNull(get(g, 0, -1));
        }

        @Test
        void addSimpleDoubleShouldThrow() {
            var g = new Grid();
            for (Direction d : Direction.values()) {
                assertThrows(QwirkleException.class, () -> g.firstAdd(d, TILE_RED_CROSS, TILE_RED_CROSS));
                assertNull(get(g, 0, 0));
                assertNull(get(g, -1, 0));
                assertNull(get(g, 1, 0));
                assertNull(get(g, 0, -1));
                assertNull(get(g, 0, 1));
            }

        }

        // firstAdd must be called first

        @Test
        void addFirstCannotBeCalledTwice() {
            var g = new Grid();
            g.firstAdd(UP, TILE_RED_CROSS, TILE_RED_DIAMOND);
            assertThrows(QwirkleException.class, () -> g.firstAdd(DOWN, TILE_RED_CROSS, TILE_RED_DIAMOND));
        }

        @Test
        void addFirstMustBeCalledFirst_dir() {
            var g = new Grid();
            assertThrows(QwirkleException.class, () -> add(g, 0, 0, DOWN, TILE_RED_CROSS, TILE_RED_DIAMOND));
        }

        @Test
        void addFirstMustBeCalledFirst_tap() {
            var g = new Grid();
            assertThrows(QwirkleException.class, () -> g.add(createTileAtpos(0, 0, TILE_RED_CROSS)));
        }

        @Test
        void addFirstMustBeCalledFirst_simple() {
            var g = new Grid();
            assertThrows(QwirkleException.class, () -> add(g, 0, 0, TILE_RED_CROSS));
        }

        // neighbours

        @Test
        void aTileMustHaveNeighbours() {
            var g = new Grid();
            g.firstAdd(UP, TILE_RED_CROSS);
            assertThrows(QwirkleException.class, () -> add(g, 2, 0, TILE_RED_DIAMOND));
            assertNull(get(g, 2, 0));
        }


        // overwriting

        @Test
        void canNotAddTwiceAtTheSamePlace_equalTile() {
            var g = new Grid();
            g.firstAdd(DOWN, TILE_RED_CROSS, TILE_RED_DIAMOND);
            assertThrows(QwirkleException.class, () -> add(g, 1, 0, TILE_RED_DIAMOND_2));
            assertSame(get(g, 1, 0), TILE_RED_DIAMOND);
        }

        @Test
        void canNotAddTwiceAtTheSamePlace_differentTile_simple() {
            var g = new Grid();
            g.firstAdd(DOWN, TILE_RED_CROSS, TILE_RED_DIAMOND);
            assertThrows(QwirkleException.class, () -> add(g, 1, 0, TILE_RED_PLUS));
            assertSame(get(g, 1, 0), TILE_RED_DIAMOND);
        }

        @Test
        void canNotAddTwiceAtTheSamePlace_differentTile_dir() {
            var g = new Grid();
            g.firstAdd(DOWN, TILE_RED_CROSS, TILE_RED_DIAMOND);
            assertThrows(QwirkleException.class, () -> add(g, 0, 0, DOWN, TILE_RED_PLUS, TILE_RED_STAR));
            assertSame(get(g, 0, 0), TILE_RED_CROSS);
            assertSame(get(g, 1, 0), TILE_RED_DIAMOND);
        }

        @Test
        void canNotAddTwiceAtTheSamePlace_differentTile_taps() {
            var g = new Grid();
            g.firstAdd(DOWN, TILE_RED_CROSS, TILE_RED_DIAMOND);
            TileAtPosition tap1 = createTileAtpos(0, 0, TILE_RED_PLUS);
            TileAtPosition tap2 = createTileAtpos(1, 0, TILE_RED_STAR);
            assertThrows(QwirkleException.class, () -> g.add(tap1, tap2));
            assertSame(TILE_RED_CROSS, get(g, 0, 0));
            assertSame(TILE_RED_DIAMOND, get(g, 1, 0));
        }


        // alignment
        @Test
        void canNotAddInDifferentLines() {
            var g = new Grid();
            g.firstAdd(UP, TILE_RED_CROSS);
            var tap1 = createTileAtpos(0, 1, TILE_RED_DIAMOND);
            var tap2 = createTileAtpos(1, 0, TILE_RED_STAR);
            assertThrows(QwirkleException.class, () -> g.add(tap1, tap2));
            assertSame(TILE_RED_CROSS, get(g, 0, 0));
            assertNull(get(g, 0, 1));
            assertNull(get(g, 1, 0));
        }

        // must share common trait
        @Test
        void canNotAddIfNoCommonTrait_tap() {
            var g = new Grid();
            g.firstAdd(UP, TILE_RED_CROSS);
            var tap1 = createTileAtpos(0, 1, TILE_YELLOW_DIAMOND);
            assertThrows(QwirkleException.class, () -> g.add(tap1));
            assertSame(TILE_RED_CROSS, get(g, 0, 0));
            assertNull(get(g, 0, 1));
            assertNull(get(g, 1, 0));
        }

        @Test
        void canNotAddIfNoCommonTrait_simple() {
            var g = new Grid();
            g.firstAdd(UP, TILE_RED_CROSS);
            assertThrows(QwirkleException.class, () -> add(g, 0, 1, TILE_YELLOW_DIAMOND));
            assertSame(TILE_RED_CROSS, get(g, 0, 0));
            assertNull(get(g, 0, 1));
            assertNull(get(g, 1, 0));
        }

        @Test
        void canNotAddIfNoCommonTrait_dir() {
            var g = new Grid();
            g.firstAdd(UP, TILE_RED_CROSS);
            assertThrows(QwirkleException.class, () -> add(g, 0, 1, LEFT, TILE_YELLOW_DIAMOND));
            assertSame(TILE_RED_CROSS, get(g, 0, 0));
            assertNull(get(g, 0, 1));
            assertNull(get(g, 1, 0));
        }

        @Test
        void canNotCompleteToALineWithDifferentTraits_simple() {
            var g = new Grid();
            g.firstAdd(RIGHT, TILE_RED_CROSS, TILE_RED_STAR, TILE_RED_DIAMOND);

            add(g, 1, 0, TILE_RED_DIAMOND_2);
            add(g, 2, 0, TILE_RED_PLUS);

            add(g, 1, 2, TILE_GREEN_DIAMOND);
            add(g, 2, 2, TILE_YELLOW_DIAMOND);

            // the "hole" in 2, 1 can never be filled because 2, 0 and 2, 2 share no trait
            for (var color : Color.values()) {
                for (var shape : Shape.values()) {
                    assertThrows(QwirkleException.class, () -> add(g, 2, 1, new Tile(color, shape)));
                    assertNull(get(g, 2, 1));
                }
            }
        }

        @Test
        void canNotCompleteToALineWithDifferentTraits_dir() {
            var g = new Grid();
            g.firstAdd(RIGHT, TILE_RED_CROSS, TILE_RED_STAR, TILE_RED_DIAMOND);

            add(g, 1, 0, TILE_RED_DIAMOND_2);
            add(g, 2, 0, TILE_RED_PLUS);

            add(g, 1, 2, TILE_GREEN_DIAMOND);
            add(g, 2, 2, TILE_YELLOW_DIAMOND);

            // the "hole" in 2, 1 can never be filled because 2, 0 and 2, 2 share no trait
            for (var color : Color.values()) {
                for (var shape : Shape.values()) {
                    for (Direction dir : Direction.values()) {
                        assertThrows(QwirkleException.class, () -> add(g, 2, 1, dir, new Tile(color, shape)));
                        assertNull(get(g, 2, 1));
                    }
                }
            }
        }

        @Test
        void canNotCompleteToALineWithDifferentTraits_tap() {
            var g = new Grid();
            g.firstAdd(RIGHT, TILE_RED_CROSS, TILE_RED_STAR, TILE_RED_DIAMOND);

            add(g, 1, 0, TILE_RED_DIAMOND_2);
            add(g, 2, 0, TILE_RED_PLUS);

            add(g, 1, 2, TILE_GREEN_DIAMOND);
            add(g, 2, 2, TILE_YELLOW_DIAMOND);

            // the "hole" in 2, 1 can never be filled because 2, 0 and 2, 2 share no trait
            for (var color : Color.values()) {
                for (var shape : Shape.values()) {
                    assertThrows(QwirkleException.class, () -> g.add(createTileAtpos(2, 1, new Tile(color, shape))));
                    assertNull(get(g, 2, 1));
                }
            }
        }

        // never identical
        @Test
        void canNotCompleteToALineWithIdenticalTiles_simple() {
            var g = new Grid();
            g.firstAdd(RIGHT, TILE_RED_CROSS, TILE_RED_STAR, TILE_RED_DIAMOND);

            add(g, 1, 0, TILE_RED_SQUARE);
            add(g, 2, 0, TILE_RED_PLUS);

            add(g, 1, 2, TILE_RED_ROUND);
            add(g, 2, 2, TILE_RED_PLUS_2);

            // the "hole" in 2, 1 can never be filled because 2, 0 and 2, 2 are identical
            for (var color : Color.values()) {
                for (var shape : Shape.values()) {
                    assertThrows(QwirkleException.class, () -> add(g, 2, 1, new Tile(color, shape)));
                    assertNull(get(g, 2, 1));
                }
            }
        }

        @Test
        void canNotCompleteToALineWithIdenticalTiles_tap() {
            var g = new Grid();
            g.firstAdd(RIGHT, TILE_RED_CROSS, TILE_RED_STAR, TILE_RED_DIAMOND);

            add(g, 1, 0, TILE_RED_SQUARE);
            add(g, 2, 0, TILE_RED_PLUS);

            add(g, 1, 2, TILE_RED_ROUND);
            add(g, 2, 2, TILE_RED_PLUS_2);

            // the "hole" in 2, 1 can never be filled because 2, 0 and 2, 2 are identical
            for (var color : Color.values()) {
                for (var shape : Shape.values()) {
                    assertThrows(QwirkleException.class, () -> g.add(createTileAtpos(2, 1, new Tile(color, shape))));
                    assertNull(get(g, 2, 1));
                }
            }
        }

        @Test
        void canNotCompleteToALineWithIdenticalTiles_dir() {
            var g = new Grid();
            g.firstAdd(RIGHT, TILE_RED_CROSS, TILE_RED_STAR, TILE_RED_DIAMOND);

            add(g, 1, 0, TILE_RED_SQUARE);
            add(g, 2, 0, TILE_RED_PLUS);

            add(g, 1, 2, TILE_RED_ROUND);
            add(g, 2, 2, TILE_RED_PLUS_2);

            // the "hole" in 2, 1 can never be filled because 2, 0 and 2, 2 are identical
            for (var color : Color.values()) {
                for (var shape : Shape.values()) {
                    // there is only one tile but let's try to add it in all directions anyway
                    for (Direction direction : Direction.values()) {
                        assertThrows(QwirkleException.class, () -> add(g, 2, 1, direction, new Tile(color, shape)));
                        assertNull(get(g, 2, 1));
                    }
                }
            }
        }

        // various other tests, pertaining to filling existing holes
        @Test
        void canCompleteToALineLeftRight() {
            var g = new Grid();
            g.firstAdd(RIGHT, TILE_RED_CROSS, TILE_RED_STAR, TILE_RED_DIAMOND);

            add(g, 1, 0, TILE_GREEN_CROSS);
            add(g, 2, 0, TILE_YELLOW_CROSS);

            add(g, 1, 2, TILE_GREEN_DIAMOND);
            add(g, 2, 2, TILE_YELLOW_DIAMOND);

            add(g, 2, 1, TILE_YELLOW_PLUS);
            assertSame(TILE_YELLOW_PLUS, get(g, 2, 1));

        }

        @Test
        void canCompleteToALineLeftRightUpDown() {
            var g = new Grid();
            g.firstAdd(RIGHT, TILE_RED_CROSS, TILE_RED_PLUS, TILE_RED_DIAMOND);

            add(g, 1, 0, TILE_GREEN_CROSS);
            add(g, 2, 0, TILE_YELLOW_CROSS);

            add(g, 1, 2, TILE_GREEN_DIAMOND);
            add(g, 2, 2, TILE_YELLOW_DIAMOND);

            add(g, 2, 1, TILE_YELLOW_PLUS);
            add(g, 1, 1, TILE_GREEN_PLUS);
            assertSame(TILE_GREEN_PLUS, get(g, 1, 1));
        }

        @Test
        @DisplayName("Complete a line leaving holes during intermediary steps")
        void canCompleteALine_Left_Middle_Right() {
            var g = new Grid();
            g.firstAdd(RIGHT, TILE_RED_CROSS, TILE_RED_PLUS, TILE_RED_DIAMOND);

            add(g, 1, 0, TILE_GREEN_CROSS);
            add(g, 2, 0, TILE_YELLOW_CROSS);

            add(g, 1, 2, TILE_GREEN_DIAMOND);
            add(g, 2, 2, TILE_YELLOW_DIAMOND);

            TileAtPosition plus_left = createTileAtpos(2, -1, TILE_YELLOW_PLUS);
            TileAtPosition round_center = createTileAtpos(2, 1, TILE_YELLOW_ROUND);
            TileAtPosition star_right = createTileAtpos(2, 3, TILE_YELLOW_STAR);
            assertDoesNotThrow(() -> {
                g.add(plus_left, star_right, round_center); // make sur having the center tile last does not throw.
            });
            assertAtCorrectPosition(g, plus_left);
            assertAtCorrectPosition(g, round_center);
            assertAtCorrectPosition(g, star_right);
        }

        @Test
        @DisplayName("Complete a line leaving holes during intermediary steps")
        void canCompleteALine_Left2_Left() {
            var g = new Grid();
            g.firstAdd(RIGHT, TILE_RED_CROSS, TILE_RED_PLUS, TILE_RED_DIAMOND);

            add(g, 1, 0, TILE_GREEN_CROSS);
            add(g, 2, 0, TILE_YELLOW_CROSS);

            add(g, 1, 2, TILE_GREEN_DIAMOND);
            add(g, 2, 2, TILE_YELLOW_DIAMOND);

            TileAtPosition plus_left_left = createTileAtpos(2, -2, TILE_YELLOW_PLUS);
            TileAtPosition round_left = createTileAtpos(2, -1, TILE_YELLOW_ROUND);
            assertDoesNotThrow(() -> {
                g.add(plus_left_left, round_left); // make sur having the "left" tile after the "left left" tile does not throw
            });
            assertAtCorrectPosition(g, plus_left_left);
            assertAtCorrectPosition(g, round_left);
        }

        // private methods

        private void add(Grid g, int row, int col, Tile tile) {
            g.add(INITIAL_ROW + row, INITIAL_COL + col, tile);
        }

        private void add(Grid g, int row, int col, Direction d, Tile... line) {
            g.add(INITIAL_ROW + row, INITIAL_COL + col, d, line);
        }

        private Tile get(Grid g, int row, int col) {
            return g.get(INITIAL_ROW + row, INITIAL_COL + col);
        }

        private TileAtPosition createTileAtpos(int row, int col, Tile tile) {
            return new TileAtPosition(INITIAL_ROW + row, INITIAL_COL + col, tile);
        }


        private void assertAtCorrectPosition(Grid g, TileAtPosition tileAtPosition) {
            int row = tileAtPosition.row();
            int col = tileAtPosition.col();
            assertSame(tileAtPosition.tile(), g.get(row, col));
        }

        // ------------------------------ FIN TESTS NRI -------------------------------------

    }


    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class GridTestReglesJeu {

        static Grid grid;

        @BeforeAll
        static void setUp() {
            grid = new Grid();
        }

        @Test
        @Order(1)
        void rules_sonia_a_adapted_to_fail() {
            var t1 = new Tile(RED, ROUND);
            var t2 = new Tile(RED, DIAMOND);
            var t3 = new Tile(RED, DIAMOND);
            assertThrows(QwirkleException.class, () -> {
                grid.firstAdd(UP, t1, t2, t3);
            });
            assertNull(get(grid, 0, 0));
            assertNull(get(grid, -1, 0));
            assertNull(get(grid, -2, 0));
        }

        @Test
        @Order(2)
        void rules_sonia_a() {
            var t1 = new Tile(RED, ROUND);
            var t2 = new Tile(RED, DIAMOND);
            var t3 = new Tile(RED, PLUS);
            int points = grid.firstAdd(UP, t1, t2, t3);
            assertEquals(points, 3);
            assertEquals(t1, get(grid, 0, 0));
            assertEquals(t2, get(grid, -1, 0));
            assertEquals(t3, get(grid, -2, 0));
        }

        @Test
        @Order(3)
        void rules_cedric_b_adapted_to_fail() {
            var t1 = new Tile(RED, SQUARE);
            var t2 = new Tile(RED, SQUARE);
            var t3 = new Tile(PURPLE, SQUARE);
            assertThrows(QwirkleException.class, () -> {
                add(grid, 1, 0, RIGHT, t1, t2, t3);
            });
            assertNull(get(grid, 1, 0));
            assertNull(get(grid, 1, 1));
            assertNull(get(grid, 1, 2));
        }

        @Test
        @Order(4)
        void rules_cedric_b() {
            var t1 = new Tile(RED, SQUARE);
            var t2 = new Tile(BLUE, SQUARE);
            var t3 = new Tile(PURPLE, SQUARE);
            int points = add(grid, 1, 0, RIGHT, t1, t2, t3);
            assertEquals(points, 7);
            assertEquals(t1, get(grid, 1, 0));
            assertEquals(t2, get(grid, 1, 1));
            assertEquals(t3, get(grid, 1, 2));
        }

        @Test
        @Order(5)
        void rules_elvire_c_adapted_to_fail() {
            var t = new Tile(BLUE, SQUARE);
            assertThrows(QwirkleException.class, () -> {
                add(grid, 0, 1, t);
            });
            assertNull(get(grid, 0, 1));
        }

        @Test
        @Order(6)
        void rules_elvire_c() {
            var t = new Tile(BLUE, ROUND);
            int points = add(grid, 0, 1, t);
            assertEquals(points, 4);
            assertEquals(t, get(grid, 0, 1));
        }

        @Test
        @Order(7)
        void rules_vincent_d_adapted_to_fail() {
            var t1 = new Tile(GREEN, DIAMOND);
            var t2 = new Tile(GREEN, ROUND);
            assertThrows(QwirkleException.class, () ->
                    add(grid, -1, -1, UP, t1, t2));
            assertNull(get(grid, -1, -1));
            assertNull(get(grid, -2, -1));
        }

        @Test
        @Order(8)
        void rules_vincent_d() {
            var t1 = new Tile(GREEN, DIAMOND);
            var t2 = new Tile(GREEN, PLUS);
            int points = add(grid, -1, -1, UP, t1, t2);
            assertEquals(points, 6);
            assertEquals(t1, get(grid, -1, -1));
            assertEquals(t2, get(grid, -2, -1));
        }

        @Test
        @Order(9)
        void rules_sonia_e_adapted_to_fail() {
            Tile t1 = new Tile(RED, ROUND);
            Tile t2 = new Tile(GREEN, STAR);

            TileAtPosition tile1 = createTileAtpos(0, -1, t1);
            TileAtPosition tile2 = createTileAtpos(-3, -1, t2);
            assertThrows(QwirkleException.class, () ->
                    grid.add(tile1, tile2));
            assertNull(get(grid, 0, -1));
            assertNull(get(grid, -3, -1));
        }

        @Test
        @Order(10)
        void rules_sonia_e() {
            Tile t1 = new Tile(GREEN, ROUND);
            Tile t2 = new Tile(GREEN, STAR);
            TileAtPosition tile1 = createTileAtpos(0, -1, t1);
            TileAtPosition tile2 = createTileAtpos(-3, -1, t2);
            int points = grid.add(tile1, tile2);
            assertEquals(points, 7);
            assertEquals(t1, get(grid, 0, -1));
            assertEquals(t2, get(grid, -3, -1));
        }

        @Test
        @Order(11)
        void rules_cedric_f_adapted_to_fail() {
            var t1 = new Tile(RED, SQUARE);
            var t2 = new Tile(ORANGE, SQUARE);
            assertThrows(QwirkleException.class, () ->
                    add(grid, 1, 3, DOWN, t1, t2));
            assertNull(get(grid, 1, 3));
            assertNull(get(grid, 2, 3));
        }

        @Test
        @Order(12)
        void rules_cedric_f() {
            var t1 = new Tile(ORANGE, SQUARE);
            var t2 = new Tile(RED, SQUARE);
            int points = add(grid, 1, 3, DOWN, t1, t2);
            assertEquals(points, 6);
            assertEquals(t1, get(grid, 1, 3));
            assertEquals(t2, get(grid, 2, 3));
        }

        @Test
        @Order(13)
        void rules_elvire_g_adapted_to_fail() {
            var t1 = new Tile(YELLOW, STAR);
            var t2 = new Tile(YELLOW, STAR);
            assertThrows(QwirkleException.class, () ->
                    add(grid, -3, -2, LEFT, t1, t2));
            assertNull(get(grid, -3, -2));
            assertNull(get(grid, -3, -3));
        }

        @Test
        @Order(14)
        void rules_elvire_g() {
            var t1 = new Tile(YELLOW, STAR);
            var t2 = new Tile(ORANGE, STAR);
            int points = add(grid, -3, -2, LEFT, t1, t2);
            assertEquals(points, 3);
            assertEquals(t1, get(grid, -3, -2));
            assertEquals(t2, get(grid, -3, -3));
        }

        @Test
        @Order(15)
        void rules_vincent_h_adapted_to_fail() {
            var t1 = new Tile(ORANGE, CROSS);
            var t2 = new Tile(ORANGE, CROSS);
            assertThrows(QwirkleException.class, () ->
                    add(grid, -2, -3, DOWN, t1, t2));
            assertNull(get(grid, -2, -3));
            assertNull(get(grid, -1, -3));
        }

        @Test
        @Order(16)
        void rules_vincent_h() {
            var t1 = new Tile(ORANGE, CROSS);
            var t2 = new Tile(ORANGE, DIAMOND);
            int points = add(grid, -2, -3, DOWN, t1, t2);
            assertEquals(points, 3);
            assertEquals(t1, get(grid, -2, -3));
            assertEquals(t2, get(grid, -1, -3));
        }

        @Test
        @Order(17)
        void rules_sonia_i_adapted_to_fail() {
            Tile t1 = new Tile(YELLOW, ROUND);
            Tile t2 = new Tile(YELLOW, STAR);
            assertThrows(QwirkleException.class, () ->
                    add(grid, 0, -2, UP, t1, t2));
            assertNull(get(grid, 0, -2));
            assertNull(get(grid, -1, -2));
        }

        @Test
        @Order(18)
        void rules_sonia_i() {
            Tile t1 = new Tile(YELLOW, ROUND);
            Tile t2 = new Tile(YELLOW, DIAMOND);
            int points = add(grid, 0, -2, UP, t1, t2);
            assertEquals(points, 10);
            assertEquals(t1, get(grid, 0, -2));
            assertEquals(t2, get(grid, -1, -2));
        }

        @Test
        @Order(19)
        void rules_cedric_j_adapted_to_fail() {
            Tile t = new Tile(RED, CROSS);
            assertThrows(QwirkleException.class, () ->
                    add(grid, -3, 0, t));
            assertNull(get(grid, -3, 0));
        }

        @Test
        @Order(20)
        void rules_cedric_j() {
            Tile t = new Tile(RED, STAR);
            int points = add(grid, -3, 0, t);
            assertEquals(points, 9);
            assertEquals(t, get(grid, -3, 0));
        }

        @Test
        @Order(21)
        void rules_elvire_k_adapted_to_fail() {
            Tile t1 = new Tile(BLUE, CROSS);
            Tile t2 = new Tile(RED, CROSS);
            Tile t3 = new Tile(ORANGE, CROSS);
            assertThrows(QwirkleException.class, () ->
                    add(grid, 3, 1, LEFT, t1, t2, t3));
            assertNull(get(grid, 3, 1));
            assertNull(get(grid, 3, 0));
            assertNull(get(grid, 3, -1));
        }

        @Test
        @Order(22)
        void rules_elvire_k() {
            Tile t1 = new Tile(BLUE, CROSS);
            Tile t2 = new Tile(RED, CROSS);
            Tile t3 = new Tile(ORANGE, CROSS);
            int points = add(grid, 2, 1, LEFT, t1, t2, t3);
            assertEquals(points, 18);
            assertEquals(t1, get(grid, 2, 1));
            assertEquals(t2, get(grid, 2, 0));
            assertEquals(t3, get(grid, 2, -1));

            display(new GridView(grid));
        }

        @Test
        @Order(23)
        void rules_vicent_l_adapted_to_fail() {
            Tile t1 = new Tile(BLUE, SQUARE);
            Tile t2 = new Tile(YELLOW, SQUARE);
            assertThrows(QwirkleException.class, () ->
                    add(grid, 1, 4, DOWN, t1, t2));
            assertNull(get(grid, 1, 4));
            assertNull(get(grid, 2, 4));
        }

        @Test
        @Order(24)
        void rules_vicent_l() {
            Tile t1 = new Tile(YELLOW, SQUARE);
            Tile t2 = new Tile(BLUE, SQUARE);
            int points = add(grid, 1, 4, DOWN, t1, t2);
            assertEquals(points, 9);
            assertEquals(t1, get(grid, 1, 4));
            assertEquals(t2, get(grid, 2, 4));
        }

        @AfterAll
        static void tearDown() {
            grid = null;
        }

    }

}
