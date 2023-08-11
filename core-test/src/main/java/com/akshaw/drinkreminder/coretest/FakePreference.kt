package com.akshaw.drinkreminder.coretest

import com.akshaw.drinkreminder.core.domain.preferences.Preferences
import com.akshaw.drinkreminder.core.util.Constants
import com.akshaw.drinkreminder.core.util.Gender
import com.akshaw.drinkreminder.core.util.WaterUnit
import com.akshaw.drinkreminder.core.util.WeightUnit
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class FakePreference : Preferences {

    private var gender: String = Constants.DEFAULT_GENDER.name
    private var age: Int = Constants.DEFAULT_AGE
    private var weight: Float = Constants.DEFAULT_WEIGHT.toFloat()
    private var weightUnit: String = Constants.DEFAULT_WEIGHT_UNIT.name
    private var waterUnit: String = Constants.DEFAULT_WATER_UNIT.name
    private var selectedTrackableDrinkId: Long = Constants.DEFAULT_SELECTED_TRACKABLE_DRINK_ID

    //TODO change time to sync from Constants files
    private var bedTime: String = "22:00"
    private var wakeupTime: String = "07:00"


    override fun saveGender(gender: Gender) {
        this.gender = gender.name
    }

    override fun saveAge(age: Int) {
        this.age = age
    }

    override fun saveWeight(weight: Float) {
        this.weight = weight
    }

    override fun saveWeightUnit(unit: WeightUnit) {
        this.weightUnit = unit.name
    }

    override fun saveBedTime(time: LocalTime) {
        val formatter = DateTimeFormatter.ofPattern("hh:mm")
        val timeString = time.format(formatter)

        this.bedTime = timeString
    }

    override fun saveWakeupTime(time: LocalTime) {
        val formatter = DateTimeFormatter.ofPattern("hh:mm")
        val timeString = time.format(formatter)

        this.wakeupTime = timeString
    }

    override fun saveWaterUnit(unit: WaterUnit) {
        this.waterUnit = unit.name
    }

    override fun saveSelectedTrackableDrinkId(id: Long) {
        this.selectedTrackableDrinkId = id
    }


    override fun loadGender(): Gender {
        return Gender.fromString(gender)
    }

    override fun loadAge(): Int {
        return age
    }

    override fun loadWeight(): Float {
        return weight
    }

    override fun loadWeightUnit(): WeightUnit {
        return WeightUnit.fromString(weightUnit)
    }

    override fun loadBedTime(): LocalTime {
        return LocalTime.parse(bedTime)
    }

    override fun loadWakeupTime(): LocalTime {
        return LocalTime.parse(wakeupTime)
    }

    override fun loadWaterUnit(): WaterUnit {
        return WaterUnit.fromString(waterUnit)
    }

    override fun loadSelectedTrackableDrinkId(): Long {
        return selectedTrackableDrinkId
    }
}