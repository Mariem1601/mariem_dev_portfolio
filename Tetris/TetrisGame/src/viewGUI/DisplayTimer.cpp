#include "DisplayTimer.h"

#include <QFont>
#include <QGraphicsItem>

/*!
 * \brief Constructs a DisplayTimer.
 * \param parent The parent QGraphicsItem.
 */
DisplayTimer::DisplayTimer(QGraphicsItem* parent)
    : QGraphicsTextItem(parent)
    , _elapsedTime(0)
    , _timer {std::make_unique<QTimer>(this)}
{
    setPlainText(QString("0:00"));
    setDefaultTextColor(Qt::blue);
    setFont(QFont("Times", 16));

    connect(_timer.get(), &QTimer::timeout, this, &DisplayTimer::updateChrono);
    _timer->start(1000);
}

//!
//! \brief DisplayTimer::stopTimer  Stops the timer.
//!
void DisplayTimer::stopTimer()
{
    _timer -> stop();
}

//! Updates the timer text to display the elapsed time.
void DisplayTimer::updateChrono()
{
    _elapsedTime++;

    int minutes = _elapsedTime / 60;
    int seconds = _elapsedTime % 60;

    setPlainText(QString("%1:%2").arg(minutes).arg(seconds, 2, 10, QChar('0')));
}


