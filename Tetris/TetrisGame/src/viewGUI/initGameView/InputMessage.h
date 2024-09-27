#ifndef INPUTMESSAGE_H
#define INPUTMESSAGE_H

#include <QDialog>

/*!
 * \brief The InputMessage class    Display a Dialog with a personalized message
 */
class InputMessage : public QDialog
{
    Q_OBJECT
private slots:
    void closeDialog();

public:
    InputMessage(const QString& errorMessage
                 , QWidget* parent = nullptr);

signals:
    void cancelPressed();
};

#endif // INPUTMESSAGE_H
