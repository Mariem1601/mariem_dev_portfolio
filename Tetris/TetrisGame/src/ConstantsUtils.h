#ifndef CONSTANTSUTILS_H
#define CONSTANTSUTILS_H

/*!
 *  Const variable that set the Board
 */
static constexpr int c_minHeight {5};
static constexpr int c_minWidth {5};
static constexpr int c_nbRandomHeightMax {10};  //! the maximum height that a random brick can be place
static constexpr int c_endRandomTry {10};

/*!
 * Victory condition for EndGame() in Game
 */
static constexpr int c_maxScore {9999};
static constexpr int c_maxNblinesCompleted {200};

/*!
 *  Const variable that set the Game
 */
static constexpr int c_updateLevel {10};        //! number of line necessary to increase level
static constexpr int c_maxMinutes {5};          //! maximum duration of the game
static constexpr int c_noDrop {0};              //! tell that no drop has been effectued
static constexpr int c_noLinesCompleted {0};    //! tell that no lines has been completed
static constexpr int c_score1LineCompleted {40};
static constexpr int c_score2LineCompleted {100};
static constexpr int c_score3LineCompleted {300};
static constexpr int c_score4LineCompleted {1200};

/*!
 *  Const variable that set the View GUI
 */
static constexpr int c_blockSize {30};
static constexpr int c_xShift {10};     //! x offset from the grid
static constexpr int c_yShift {100};    //! y offset from the grid
static constexpr int c_defaultLevel {1};
static constexpr int c_defaultHeight {20};
static constexpr int c_defaultWidth {10};
static constexpr bool c_defaultRandom {false};
static constexpr double c_speedFactor {1.1};
static constexpr int c_speedMaxLevel {20};

#endif // CONSTANTSUTILS_H
