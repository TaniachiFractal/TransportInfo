package maslovat.taniachifractal.transportinfo

import java.util.UUID

/** Data of a mean of transportation*/
class TransportData public constructor(
    val id: UUID,
    val markName: String,
    val isMotorcycle: Boolean,
    val axis: Int,
    val capacity: Int
)
{
    override fun toString(): String {
        return  markName
    }
    public fun FullInfo():String{
        return getTransportType() + " "+ markName + "\n осей: " + axis + "\n грузоподъёмность: " + capacity + " т";
    }
    private fun getTransportType(): String
    {
        if (isMotorcycle) return " Мотоцикл"
        else return " Автомобиль"
    }
}