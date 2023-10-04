package com.akshaw.drinkreminder.core.util

class SWWException : Exception("Something went wrong")
class WeightOutOfLimitException : Exception("Weight is not in the limit")
class AgeOutOfLimitException : Exception("Age is not in the limit")

class NoExactAlarmPermissionException : Exception("Permission not allowed to set exact alarm")
class NoNotificationPermissionException : Exception("Permission not allowed to show notification")
class ReminderOffException : Exception("Reminder is off or no day is selected")

class SameWaterUnitException: Exception("Current water unit is same")
class SameWeightUnitException: Exception("Current weight unit is same")

class InvalidDrinkQuantityException: Exception("Drink quantity isn't valid")