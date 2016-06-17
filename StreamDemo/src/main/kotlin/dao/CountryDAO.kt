package dao

import com.github.salomonbrys.kotson.*
import com.google.gson.Gson
import domain.Country
import java.io.InputStreamReader
import java.io.Reader

/**
 * Load Countries from JSON File
 */
fun loadCountries(): List<Country> {
    val gson = Gson()
    val reader: Reader = InputStreamReader(Gson::class.java.getResourceAsStream("/countries.json"))
    val countries = gson.fromJson<List<Country>>(reader)
    return countries
}

/**
 * Data Access Object to Fetch Details of Countries
 */
class CountryDAO {
    private val countries: List<Country> by lazy { loadCountries() }

    fun findCountryByIsoCode(isoCode: String): Country? {
        return countries.filter{it.isoCode == isoCode}.firstOrNull()
    }

    fun findCountriesByRegion(region: String): List<Country> {
        return countries.filter { it.region == region }
    }

    fun findCountriesByRegionAndSubregion(region: String, subregion: String): List<Country> {
        return countries
                .filter { it.region == region }
                .filter { it.subregion == subregion }
    }


    fun fetchCapitalCities(): List<String> {
        return countries
                .filter { !it.capital.isBlank() }
                .map { it.capital }
    }

    fun findLargestCountriesByRegion(region: String, rowCount: Int = 5): List<Country> {
        return countries
                .filter {it.region == region}
                .sortedByDescending{it.area}
                .take(rowCount)
    }
/*
    fun findAverageSizeOfCountries(): Double {
        return null
    }

    fun splitByLandLockedStatus(): Pair<List<Country>, List<Country>> {
        return null
    }

    fun fetchCountryNamesGroupedByRegion(): Map<String, List<String>> {
        return null
    }
    */
}

