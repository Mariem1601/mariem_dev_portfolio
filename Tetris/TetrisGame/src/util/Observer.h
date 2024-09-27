#ifndef OBSERVER_H
#define OBSERVER_H

/*!
 * \brief The Observer class    The Observer class is an interface
 *           for objects that wish to receive updates from Observable objects.
 */
class Observer
{
public:
    virtual ~Observer() = default;
    virtual void update(void* object) = 0;
};

#endif // OBSERVER_H
