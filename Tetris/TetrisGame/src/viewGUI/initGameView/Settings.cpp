#include "InputMessage.h"
#include "RadioChoice.h"
#include "Settings.h"
#include "../../ConstantsUtils.h"

/*!
 * \brief Settings::Settings Constructor of Settings
 * \param parent    a QWidget parent
 */
Settings::Settings(QWidget *parent) : QDialog(parent)
{

    _mainDialog = new QVBoxLayout(this);
    this -> setLayout(_mainDialog);

    _radioChoice = new RadioChoice();
    _persoChoice = new PersonalizedSettings();
    _buttonBox = new QDialogButtonBox(QDialogButtonBox::Ok | QDialogButtonBox::Cancel);
    connect(_buttonBox, &QDialogButtonBox::accepted, this, &Settings::okSettings);
    connect(_buttonBox, &QDialogButtonBox::rejected, this, &Settings::reject);

    connect(_radioChoice->getDefaultRadioButton()
            , &QRadioButton::clicked, this
            , &Settings::radioDefaultClicked);
    connect(_radioChoice->getPersonalizedRadioButton()
            , &QRadioButton::clicked, this
            , &Settings::radioPersonalizedClicked);



    _mainDialog->addLayout(_radioChoice);
    _mainDialog->addLayout(_persoChoice);
    _mainDialog->addWidget(_buttonBox);



    setWindowTitle(tr("Dialog"));
}

//! Resets the personalized settings to their default values
//! when the default radio button is clicked.
void Settings::radioDefaultClicked()
{
    _persoChoice -> resetToDefaultValue();
    _persoChoice -> setLineEditable(false);
}

//! Sets the personalized settings fields to editable
//! when the personalized radio button is clicked.
void Settings::radioPersonalizedClicked()
{
    _persoChoice -> setLineEditable(true);
}

//! Validates the input settings and emits the sendSettings signal with the appropriate settings.
//! If the input is invalid, displays an error message dialog.
void Settings::okSettings()
{
    if (_radioChoice->getDefaultRadioButton()->isChecked()) {
        emit sendSettings(c_defaultLevel, c_defaultHeight, c_defaultWidth, c_defaultRandom);
    } else {
        int level = _persoChoice->getLevel()->text().toInt();
        int height = _persoChoice->getHeight()->text().toInt();
        int width = _persoChoice->getWidth()->text().toInt();
        bool random = _persoChoice->getIsRandom()->isChecked();
        if (level <= 0 || height < c_minHeight || width < c_minWidth)
        {
            InputMessage* errorInput = new InputMessage("Invalid input, please enter number > 0 !", this);
            errorInput->show();
        }
        else
        {
            emit sendSettings(level, height, width, random);
        }
    }
}
