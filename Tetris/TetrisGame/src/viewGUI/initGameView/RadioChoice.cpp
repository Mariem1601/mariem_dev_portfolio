#include "RadioChoice.h"

/*!
 * \brief RadioChoice::RadioChoice Constructor of RadioChoice
 * \param parent    a QWidget parent
 */
RadioChoice::RadioChoice(QWidget *parent) : QHBoxLayout(parent)
{
    _buttonGroup = new QButtonGroup(this);
    _radioDefault = new QRadioButton("Défault Settings");
    _radioPersonalized = new QRadioButton("Personalized Settings");
    _buttonGroup -> addButton(_radioDefault);
    _buttonGroup -> addButton(_radioPersonalized);
    this -> addWidget(_radioDefault);
    this -> addWidget(_radioPersonalized);

    _radioPersonalized -> setChecked(true);
}

QRadioButton* RadioChoice::getDefaultRadioButton() const
{
    return _radioDefault;
}

QRadioButton* RadioChoice::getPersonalizedRadioButton() const
{
    return _radioPersonalized;
}
