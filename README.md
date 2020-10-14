# NotificationCreator [ ![Download](https://api.bintray.com/packages/har5hit/Notification/NotificationCreator/images/download.svg?version=2.0.2) ](https://bintray.com/har5hit/Notification/NotificationCreator/2.0.2/link)

Simplified creation of Android notifications directly from data sent from Server using <b>Delegate</b> Pattern.

## Download

Add the library to app build.gradle

```
implementation 'com.justadeveloper96.android:notification-creator:2.0.2'
```


# Description

This library aims to lessen boilerplate required to create notifications by accepting a data model `NotificationData` or any derived class of it.
 
`GenericDelegate` is a abstract class provided which handles generic notification which can be extended to make app specific modifications. 

Multiple Delegates can be created to handle different usecases and can be sent to NotificationCreationFactory instance which will handle delegation to appropriate delegate. Each delegate specify what type of payload they can handle by themselves in function `canHandle(type:String)`.
<br>

Refer AppDelegate class in Demo app for sample.


# Payload
```
NotificationData
{
  "type": "Generic",
  "title": "Test Message",
  "message": "Hello How are you",
  "channelId": "greeting",
  "channelName": "Greeting",
  "channelPriority": "max",
  "landingId": "main",
  "action": "android.intent.action.VIEW",
  "style": "BIG_PICTURE",
  "icon": "https://yourdomainaddress/jpgs/icon.jpg",
  "bigPictureStyle": {
    "bigPicture": "https://yourdomainaddress/jpgs/abc.jpg"
  },
  "extras": {
    "int": 1,
    "double": 1.3,
    "string": "abc",
    "stringArray": [
      "abc",
      "xyz"
    ],
    "boolean": true
  },
  "actions": [
    {
      "label": "View",
      "drawable": "ic_music",
      "action": "view",
      "extras": {
        
      }
    }
  ]
}
```

* <b>type</b> - To distinguish which delegate should handle this payload.
 
* <b>title</b> - Title of the notification.

* <b>message</b> <i>(optional)</i> - Message body.

* <b>channelId,channelName</b> - Android channel stuff. [Notification Channels](https://developer.android.com/training/notify-user/channels)
 
* <b>channelPriority</b> - Priority of message. Values one of ("max", "high", "low", "min").
Default value set to Default priority

* <b>landingId</b> <i>(optional)</i> - The Id of the activity to be opened.

* <b>action</b> <i>(optional)</i> - The action for the intent to be opened.

<i><b>Attention</b>: landingId and action shouldn't be sent together. Only one of these attributes will be accepted, with landingId getting priority</i>

* <b>style</b> <i>(optional)</i> - One of ("message","bigPicture").

* <b>icon</b> <i>(optional)</i> - Icon of notification.

* <b>extras</b> <i>(optional)</i> - Extra Map<String,Any> data for handling of notifications and to be sent as bundle to the intent created by landingId/landingAction.

* <b>actions</b> <i>(optional)</i> - List of actions to be shown in notification.
    * <b>label</b> - Label shown in action.
    * <b>drawable</b> <i>(optional)</i> - resource icon for the action.
    * <b>action</b> <i>(optional)</i> - Intent action of the intent created by landingId/landingAction.
    * <b>extras</b> <i>(optional)</i> - Extra Map<String,Any> data for handling of notifications and to be sent as bundle to the intent created by landingId/landingAction.

## Usage

* Create instances of GenericCreator or ICreator.
* Create instance of NotificationCreatorPool
* Parse the notification to data model.
* Send the data model and context to the pool to handle.

```
 class AppDelegate(private val context: Context) : GenericCreator(){
 ...
 ...
 }
 
...
 
val delegate=AppDelegate(context)

val delegates=listOf(delegate)

val pool = NotificationCreatorPool(delegates)

....

remoteMessage?.data?.isNotEmpty()?.let {
            val data=NotificationData.parser(remoteMessage.data)
            Log.d(TAG, "data payload: " + data)
            pool(data,applicationContext)
        }
```


## Customization

`GenericDelegate` has functions which can be Overridden to modify notification data, builder and other changes specific to usecase/delegate.
You can create a delegate from scratch implementing `ICreator` interface


## Demo

The project is a demo with a sample AppDelegate to test sample notifications. How delegate is created, connected to factory and how is notification data sent to factory for payload processing.




## License

```
Copyright 2019 Harshith Shetty

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

```




