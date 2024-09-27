#include "ColorUtils.h"

QColor ColorUtils::getColorForShape(const Shape shape)
{
    switch(shape) {
    case Shape::lShape:
        return QColor(88, 164, 207); // Blue
    case Shape::jShape:
        return QColor(153, 195, 84); // Green
    case Shape::sShape:
        return QColor(247, 212, 80); // Yellow
    case Shape::zShape:
        return QColor(134, 108, 163); // Purple
    case Shape::tShape:
        return QColor(209, 95, 128); // Pink
    case Shape::iShape:
        return QColor(234, 152, 62); // Orange
    case Shape::cShape:
        return QColor(221, 79, 83); // Red
    case Shape::emptyShape:
        return QColor(236, 236, 236); // Gray
    default:
        return QColor(255, 255, 255); //White
    }
}
