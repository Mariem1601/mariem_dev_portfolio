#ifndef DATABRICK_H
#define DATABRICK_H

#include <mutex>
#include <map>
#include <vector>
#include "Shape.h"
#include "Position.h"

/*!
 * \brief The DataBrick class data base
 * of all initial relative coordinate of each brick
 */
class DataBrick
{
private:
    //! unique instance of dataBrick
    static inline DataBrick* s_pInstance {nullptr};
    static inline std::mutex s_lock;
    std::map<Shape, std::vector<Position>> _data {};   //! contains all initial
                                                        //! relative coordinate of each brick

private:
    DataBrick();
    ~DataBrick() = default;
    void initialize();

public:
    DataBrick(DataBrick& other) = delete;
    void operator=(const DataBrick&) = delete;
    static DataBrick* getInstance();

    std::vector<Position> getInitCoordinates(const Shape& shape);
};

#endif // DATABRICK_H
