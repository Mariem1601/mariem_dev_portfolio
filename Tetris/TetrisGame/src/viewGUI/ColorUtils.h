#ifndef COLORUTILS_H
#define COLORUTILS_H

#include <QColor>
#include "../model/Shape.h"

/*!
 * \brief The ColorUtils class
 * utilities method to change the color of a block in GUI
 */
class ColorUtils
{
public:
    static QColor getColorForShape(const Shape shape);
};

#endif // COLORUTILS_H
