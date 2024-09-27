#ifndef RADIOCHOICE_H
#define RADIOCHOICE_H

#include <QButtonGroup>
#include <QHBoxLayout>
#include <QRadioButton>

/*!
 * \brief The RadioChoice class
 * contains two radios to choose between default setting and personlization settings
 */
class RadioChoice : public QHBoxLayout
{
private:
    QButtonGroup *_buttonGroup;
    QRadioButton *_radioDefault;
    QRadioButton *_radioPersonalized;
public:
    RadioChoice(QWidget *parent = nullptr);
    QRadioButton* getDefaultRadioButton() const;
    QRadioButton* getPersonalizedRadioButton() const;
};

#endif // RADIOCHOICE_H
