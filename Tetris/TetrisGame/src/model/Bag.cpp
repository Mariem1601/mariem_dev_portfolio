#include "Bag.h"
#include <algorithm>
#include <random>

/*! Private constructor ofBag,
 *  give _brick the different shape
 *  that will be present in the game.
 *  When _bricks is initialized,
 *  _bricks is shuffled to garantee random shape
*/
Bag::Bag()
    : _bricks {Shape::iShape
              , Shape::cShape
              , Shape::jShape
              , Shape::lShape
              , Shape::sShape
              , Shape::tShape
              , Shape::zShape}
    , _index {_bricks.size()}
{
    shuffle();
}

/*!
 * \brief Bag::getInstance guarantees bag uniqueness
 * \return  a new instance of bag if it was not initialized
 *          else, it return the same bag
 */
Bag* Bag::getInstance()
{
    std::lock_guard<std::mutex> lock{s_lock};
    if (s_pInstance == nullptr)
    {
        s_pInstance = new Bag();
    }
    return s_pInstance;
}

/*!
 * \brief Bag::getBrickShape give a shape to a brick
 *        and increase _index
 * \return the future current brick's shape
 */
Shape Bag::getBrickShape()
{
    Shape shape {_bricks[_index]};
    //! we want to give the next shape to the next brick
    ++_index;
    shuffle();
    return shape;
}

/*! Shuffle the bag when _index is at the top of the vector
 *  and _index is reset to 0 to restart the shape cycle
*/
void Bag::shuffle()
{
    //todo utiliser la methode standard
    if (_index == _bricks.size())
    {
        auto rd = std::random_device {};
        auto rng = std::default_random_engine { rd() };
        std::shuffle(std::begin(_bricks), std::end(_bricks), rng);
        _index = 0;
    }
}
