#ifndef SETTINGS_H
#define SETTINGS_H


#include "PersonalizedSettings.h"

#include <QDialog>
#include <QDialogButtonBox>

class RadioChoice;

/*!
 * \brief The Settings class
 * Dialog View to choose settings before playing game
 */
class Settings : public QDialog
{
    Q_OBJECT
private:
    QVBoxLayout* _mainDialog;
    RadioChoice* _radioChoice;
    PersonalizedSettings* _persoChoice;
    QDialogButtonBox* _buttonBox;

private slots:
    void radioDefaultClicked();
    void radioPersonalizedClicked();
    void okSettings();

public:
    Settings(QWidget *parent = nullptr);
    ~Settings() = default;

signals:
    void sendSettings(int level, int height, int width, bool random);
};

#endif // SETTINGS_H
