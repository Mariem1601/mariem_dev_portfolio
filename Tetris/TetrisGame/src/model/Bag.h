#ifndef BAG_H
#define BAG_H

#include <vector>
#include <mutex>
#include "Shape.h"

/*!
 * \brief The Bag class unique bag that will contains shape
 */
class Bag
{
private:
    //! unique instance of bag
    static inline Bag* s_pInstance {nullptr};
    static inline std::mutex s_lock;

    /*! Note: array is better when we use a constexpr variable
        in this case, we cannot use constexpr
        because we will shuffle inside so in this case,
        vector is a better choice. Moreover, we can later
        implement new shape if we want with vector */
    std::vector<Shape> _bricks {};  //! Contains shape that we will be shuffle
    std::size_t _index {};          //! index of the next shape that brick will have
private:
    Bag();
    ~Bag() = default;
    void  shuffle();
public:
    Bag(Bag& other) = delete;
    void operator=(const Bag&) = delete;
    static Bag* getInstance();
    Shape getBrickShape();

};

#endif // BAG_H
