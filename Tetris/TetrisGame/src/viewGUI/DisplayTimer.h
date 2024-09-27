#ifndef DISPLAYTIMER_H
#define DISPLAYTIMER_H

#include <memory>
#include <QGraphicsTextItem>
#include <QTimer>

/*!
 * \brief The DisplayTimer class
 * act like a chrono
 */
class DisplayTimer : public QGraphicsTextItem
{
private:
    int _elapsedTime; // time in seconds
    std::unique_ptr<QTimer> _timer;

private:
    void updateChrono();

public:
    DisplayTimer(QGraphicsItem* parent = nullptr);
    void stopTimer();
};

#endif // DISPLAYTIMER_H
