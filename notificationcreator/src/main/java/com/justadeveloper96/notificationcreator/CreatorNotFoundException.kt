package com.justadeveloper96.notificationcreator

class CreatorNotFoundException(type: String) : Exception("No creator found for this notification type: $type")
