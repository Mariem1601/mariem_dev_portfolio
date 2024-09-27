#include "InputMessage.h"
#include <QLabel>
#include <QVBoxLayout>
#include <QPushButton>

/*!
 * \brief InputMessage::InputMessage    Constructor of InputMessage
 * \param errorMessage  a personalized message
 * \param parent        a QWidget parent
 */
InputMessage::InputMessage(const QString &errorMessage, QWidget *parent) : QDialog(parent)
{
    QVBoxLayout* layout = new QVBoxLayout(this);
    QLabel* infoLabel = new QLabel(errorMessage);
    layout->addWidget(infoLabel);

    QPushButton* closeButton = new QPushButton("Close");
    connect(closeButton, &QPushButton::clicked, this, &InputMessage::closeDialog);
    layout->addWidget(closeButton);
}

//! Closes the dialog and emits a signal to close the GUI when button is pressed.
void InputMessage::closeDialog()
{
    emit cancelPressed();
    close();
}
