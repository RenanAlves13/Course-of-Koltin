@file:Suppress("UNREACHABLE_CODE")

package taxipark

/*
data class TaxiPark(
        val allDrivers: Set<Driver>,
        val allPassengers: Set<Passenger>,
        val trips: List<Trip>)
 */

/*
 * Task #1. Find all the drivers who performed no trips.
 */

//Encontre todos os motoristas que não realizaram nenhuma viagem.
fun TaxiPark.findFakeDrivers(): Set<Driver> = allDrivers.filter { dr -> trips.none {it.driver == dr} }.toSet()

/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
//Encontre todos os clientes que completaram pelo menos o número de viagens fornecido.
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> = allPassengers.filter {
    pss -> trips.sumBy { tm -> if (pss in tm.passengers) 1 else 0} >= minTrips
}.toSet()


/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
//Encontre todos os passageiros que foram levados por um determinado motorista mais de uma vez.
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> = allPassengers.filter {
    pss -> trips.count { (driver == it.driver) && (pss in it.passengers)} > 1
}.toSet()


/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
//Encontre os passageiros que tiveram desconto na maioria de suas viagens.
fun TaxiPark.findSmartPassengers(): Set<Passenger> {
    var (discount, withoutDiscount) = trips.partition { it.discount is Double }
    return allPassengers.filter { pss ->  withoutDiscount.count { it.passengers.contains(pss) } <
        discount.count { it.passengers.contains(pss) }}.toSet()
}

/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
//Encontre a duração da viagem mais frequente entre os períodos de minuto 0..9, 10..19, 20..29 e assim por diante.
//  * Retorna qualquer período se muitos forem os mais frequentes, retorna `null` se não houver viagens.
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {
    return trips.groupBy {
        var start = it.duration / 10 * 10
        var end = start + 9
        start..end
    }.toList().maxBy { it -> it.second.size }?.first
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
//Verifique se 20% dos motoristas contribuem com 80% da renda.
fun TaxiPark.checkParetoPrinciple(): Boolean {
    //Se não houver viagem
    if (trips.isEmpty())
        return false
    var total = trips.sumByDouble (Trip::cost)
    var drivers: List<Double> = trips.groupBy ( Trip::driver ).map {
        (i, tripsDriver) -> tripsDriver.sumByDouble (Trip::cost)
    }.sortedDescending()
    var topDrivers = drivers.take((0.2 * allDrivers.size).toInt()).sum()
    var totalFinal = 0.8 * total

    return topDrivers >= totalFinal
}