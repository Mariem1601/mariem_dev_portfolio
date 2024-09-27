#include "PersonalizedSettings.h"

#include <QIntValidator>

/*!
 * \brief PersonalizedSettings::PersonalizedSettings    Constructor of PersonalizedSettings
 * \param parent    a QWidget parent
 */
PersonalizedSettings::PersonalizedSettings(QWidget *parent)
    : QFormLayout(parent)
{
    _levelLabel = new QLabel("Level:");
    _heightLabel = new QLabel("Height:");
    _widthLabel = new QLabel("Width:");
    _randomLabel = new QLabel("Random?");

    _levelLineEdit = new QLineEdit("1");
    _heightLineEdit = new QLineEdit("20");
    _widthLineEdit = new QLineEdit("10");
    _randomCheckBox = new QCheckBox("Yes");

    this->addRow(_levelLabel,  _levelLineEdit);
    this->addRow(_heightLabel, _heightLineEdit);
    this->addRow(_widthLabel,  _widthLineEdit);
    this->addRow(_randomLabel, _randomCheckBox);

    QIntValidator *intValidator = new QIntValidator(this);
    _levelLineEdit->setValidator(intValidator);
    _heightLineEdit->setValidator(intValidator);
    _widthLineEdit->setValidator(intValidator);
}

/*!
 * \brief Resets the input fields to their default values.
 */
void PersonalizedSettings::resetToDefaultValue()
{
    _levelLineEdit->setText("1");
    _heightLineEdit->setText("20");
    _widthLineEdit->setText("10");
    _randomCheckBox->setChecked(false);
}

/*!
 * \brief Sets the editability of the input fields.
 * \param editable True to make the fields editable, false otherwise.
 */
void PersonalizedSettings::setLineEditable(bool editable)
{
    _levelLineEdit->setReadOnly(!editable);
    _heightLineEdit->setReadOnly(!editable);
    _widthLineEdit->setReadOnly(!editable);
    _randomCheckBox->setEnabled(editable);
}

QLineEdit *PersonalizedSettings::getLevel() const
{
    return _levelLineEdit;
}

QLineEdit *PersonalizedSettings::getHeight() const
{
    return _heightLineEdit;
}

QLineEdit *PersonalizedSettings::getWidth() const
{
    return _widthLineEdit;
}

QCheckBox *PersonalizedSettings::getIsRandom() const
{
    return _randomCheckBox;
}
