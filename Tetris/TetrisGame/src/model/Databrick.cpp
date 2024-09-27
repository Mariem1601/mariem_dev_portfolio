#include "Databrick.h"

DataBrick::DataBrick()
{
    initialize();
}

/**
 * @brief DataBrick::initialize initialize _data
 *          with the relative coordinates of each shape
 */
void DataBrick::initialize()
{
    _data[Shape::lShape] = std::vector{Position{0, 0}
                                        , Position{0, 1}
                                        , Position{0, -1}
                                        , Position{1, -1}};

    _data[Shape::jShape] = std::vector{Position{0, 0}
                                        , Position{0, 1}
                                        , Position{0, -1}
                                        , Position{-1, -1}};

    _data[Shape::zShape] = std::vector{Position{0, 0}
                                        , Position{-1, 0}
                                        , Position{0, -1}
                                        , Position{1, -1}};

    _data[Shape::sShape] = std::vector{Position{0, 0}
                                        , Position{1, 0}
                                        , Position{0, -1}
                                        , Position{-1, -1}};

    _data[Shape::tShape] = std::vector{Position{0, 0}
                                        , Position{-1, 0}
                                        , Position{1, 0}
                                        , Position{0, 1}};

    _data[Shape::iShape] = std::vector{Position{0, 0}
                                        , Position{-1, 0}
                                        , Position{1, 0}
                                        , Position{2, 0}};

    _data[Shape::cShape] = std::vector{Position{0, 0}
                                        , Position{1, 0}
                                        , Position{1, -1}
                                        , Position{0, -1}};
}

/*!
 * \brief DataBrick::getInstance guarantees dataBrick uniqueness
 * \return  a new instance of dataBrick if it was not initialized
 *          else, it return the same dataBrick
 */
DataBrick* DataBrick::getInstance()
{
    std::lock_guard<std::mutex> lock{s_lock};
    if (s_pInstance == nullptr)
    {
        s_pInstance = new DataBrick();
    }
    return s_pInstance;
}

std::vector<Position> DataBrick::getInitCoordinates(const Shape& shape)
{
    return _data[shape];
}
