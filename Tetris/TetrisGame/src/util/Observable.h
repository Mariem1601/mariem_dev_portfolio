#ifndef OBSERVABLE_H
#define OBSERVABLE_H

#include <set>

/*!
 *  The Observable class represents an object
 *  that can be observed by one or more observers.
 */
class Observer;

class Observable
{
public:
    virtual ~Observable() = default;
    void registerObserver(Observer * observer);
    void unregisterObserver(Observer * observer);
    void notifyObservers(void* object);

private:
    std::set<Observer *> _observers;
};


#endif // OBSERVABLE_H
