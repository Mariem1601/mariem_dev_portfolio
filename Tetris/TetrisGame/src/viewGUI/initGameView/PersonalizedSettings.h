#ifndef PERSONALIZEDSETTINGS_H
#define PERSONALIZEDSETTINGS_H

#include <QCheckBox>
#include <QFormLayout>
#include <QLabel>
#include <QLineEdit>

/*!
 * \brief The PersonalizedSettings class
 * conntains data about the personalization of the game
 */
class PersonalizedSettings : public QFormLayout
{
private:
    QLabel *_levelLabel;
    QLabel *_heightLabel;
    QLabel *_widthLabel;
    QLabel *_randomLabel;

    QLineEdit *_levelLineEdit;
    QLineEdit *_heightLineEdit;
    QLineEdit *_widthLineEdit;
    QCheckBox *_randomCheckBox;

public:
    PersonalizedSettings(QWidget *parent = nullptr);
    void setLineEditable(bool editable);
    void resetToDefaultValue();
    QLineEdit* getLevel() const;
    QLineEdit* getHeight() const;
    QLineEdit* getWidth() const;
    QCheckBox* getIsRandom() const;
};

#endif // PERSONALIZEDSETTINGS_H
