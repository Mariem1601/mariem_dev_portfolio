#include "Observable.h"
#include "Observer.h"

/*!
 * \brief Observable::registerObserver   Registers an observer to receive notifications from this Observable.
 * \param observer  The observer to register.
 */
void Observable::registerObserver(Observer * observer)
{
    _observers.insert(observer);
}

/*!
 * \brief Observable::unregisterObserver  Unregisters an observer from receiving notifications from this Observable.
 * \param observer  The observer to unregister.
 */
void Observable::unregisterObserver(Observer * observer)
{
    _observers.erase(observer);
}

/*!
 * \brief Observable::notifyObservers   Notifies all registered observers with the given object.
 * \param object    object The object to pass to the observers.
 */
void Observable::notifyObservers(void* object) {
    for (Observer* observer : _observers) {
        observer->update(object);
    }
}

